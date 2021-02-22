/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayLabel from '@clayui/label';
import {usePrevious, useTimeout} from '@liferay/frontend-js-react-web';
import {AppContext} from 'app-builder-web/js/AppContext.es';
import NoPermissionState from 'app-builder-web/js/components/empty-state/NoPermissionState.es';
import useDataListView from 'app-builder-web/js/hooks/useDataListView.es';
import useEntriesActions from 'app-builder-web/js/hooks/useEntriesActions.es';
import usePermissions from 'app-builder-web/js/hooks/usePermissions.es';
import {
	buildEntries,
	navigateToEditPage,
} from 'app-builder-web/js/pages/entry/utils.es';
import {getLocalizedUserPreferenceValue} from 'app-builder-web/js/utils/lang.es';
import Loading from 'data-engine-js-components-web/js/components/loading/Loading.es';
import ManagementToolbar from 'data-engine-js-components-web/js/components/management-toolbar/ManagementToolbar.es';
import ManagementToolbarResultsBar from 'data-engine-js-components-web/js/components/management-toolbar/ManagementToolbarResultsBar.es';
import SearchContext, {
	reducer,
} from 'data-engine-js-components-web/js/components/management-toolbar/SearchContext.es';
import TableWithPagination from 'data-engine-js-components-web/js/components/table/TableWithPagination.es';
import useQuery from 'data-engine-js-components-web/js/hooks/useQuery.es';
import {getItem} from 'data-engine-js-components-web/js/utils/client.es';
import {errorToast} from 'data-engine-js-components-web/js/utils/toast.es';
import {
	concatValues,
	isEqualObjects,
} from 'data-engine-js-components-web/js/utils/utils.es';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import useAppWorkflow from '../../hooks/useAppWorkflow.es';
import useDataRecordApps from '../../hooks/useDataRecordApps.es';
import ReassignEntryModal from './ReassignEntryModal.es';

const WORKFLOW_COLUMNS = [
	{key: 'status', value: Liferay.Language.get('status')},
	{key: 'taskNames', value: Liferay.Language.get('step')},
	{key: 'assignee', value: Liferay.Language.get('assignee')},
];

export default function ListEntries({history}) {
	const {
		appId,
		basePortletURL,
		dataDefinitionId,
		dataListViewId,
		defaultDelta = 20,
		showFormView,
		userLanguageId,
	} = useContext(AppContext);

	const [dataRecordIds, setDataRecordIds] = useState([]);
	const [isModalVisible, setModalVisible] = useState(false);
	const [selectedEntry, setSelectedEntry] = useState();

	const {appWorkflowDefinitionId} = useAppWorkflow(appId);
	const dataRecordApps = useDataRecordApps(appId, dataRecordIds);
	const delay = useTimeout();
	const permissions = usePermissions();

	const {
		columns,
		dataDefinition,
		dataListView: {fieldNames},
		isLoading,
	} = useDataListView(dataListViewId, dataDefinitionId, permissions.view);

	const [{isFetching, items, totalCount}, setFetchState] = useState({
		isFetching: true,
		items: [],
		totalCount: 0,
	});

	const [query, setQuery] = useQuery(
		history,
		{
			dataListViewId,
			keywords: '',
			page: 1,
			pageSize: defaultDelta,
			sort: '',
		},
		appId
	);

	const dispatch = useCallback((action) => setQuery(reducer(query, action)), [
		query,
		setQuery,
	]);

	const previousQuery = usePrevious(query);

	const doFetch = ({
		entryInstanceId,
		newAssignee,
		query,
		workflowDefinitionId,
	}) => {
		if (workflowDefinitionId) {
			setFetchState((prevState) => ({
				...prevState,
				isFetching: true,
			}));

			getItem(
				`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-records`,
				query
			)
				.then((response) => {
					setFetchState({
						isFetching: response.items.length !== 0,
						...response,
					});

					if (response.items.length) {
						const classPKs = response.items.map(({id}) => id);

						setDataRecordIds(classPKs);

						const getWorkflowInfo = () => {
							getItem(
								`/o/portal-workflow-metrics/v1.0/processes/${workflowDefinitionId}/instances`,
								{
									classPKs,
									page: 1,
									pageSize: response.items.length,
								}
							).then((workflowResponse) => {
								let items = response.items;
								let retryCount = 0;

								if (entryInstanceId) {
									const {
										assignees,
									} = workflowResponse.items.find(
										({id}) => id === entryInstanceId
									);

									if (
										newAssignee &&
										newAssignee.id !== assignees?.[0]?.id &&
										retryCount <= 5
									) {
										retryCount++;

										return delay(getWorkflowInfo, 1000);
									}
								}

								if (workflowResponse.totalCount > 0) {
									items = response.items.map((item) => {
										const {
											assignees,
											completed,
											id: instanceId,
											taskNames,
										} =
											workflowResponse.items.find(
												({classPK}) =>
													classPK === item.id
											) || {};

										return {
											...item,
											assignees,
											completed,
											instanceId,
											taskNames,
										};
									});
								}

								setFetchState((prevState) => ({
									...prevState,
									isFetching: false,
									items,
								}));
							});
						};

						getWorkflowInfo();
					}
				})
				.catch(() => {
					errorToast();
					setDataRecordIds([]);
					setFetchState((prevState) => ({
						...prevState,
						isFetching: false,
					}));
				});
		}
	};

	const onClickAddButton = () =>
		navigateToEditPage(basePortletURL, {
			languageId: userLanguageId,
		});

	const refetch = ({entryInstanceId, newAssignee} = {}) =>
		doFetch({
			entryInstanceId,
			newAssignee,
			query,
			workflowDefinitionId: appWorkflowDefinitionId,
		});

	const onCloseModal = () => {
		setModalVisible(false);
		setSelectedEntry();
	};

	const buildWorkflowItems = (items) => {
		return items
			.map(
				buildEntries({
					dataDefinition,
					fieldNames,
					permissions,
					query,
				})
			)
			.map((entry) => {
				const workflowValues = {};
				const emptyValue = '--';

				WORKFLOW_COLUMNS.forEach(({key}) => {
					switch (key) {
						case 'assignee': {
							const {assignees = [], taskNames = []} = entry;

							const {id = -1, name = emptyValue, reviewer} =
								assignees[0] || {};

							if (id === -1) {
								const {appWorkflowTasks = []} =
									dataRecordApps[entry.id] || {};

								const {appWorkflowRoleAssignments = []} =
									appWorkflowTasks.find(
										({name}) => name === taskNames[0]
									) || {};

								const roleNames = appWorkflowRoleAssignments.map(
									({roleName}) => roleName
								);

								workflowValues.canReassign = reviewer;
								workflowValues[key] = roleNames.length
									? concatValues(roleNames)
									: emptyValue;
							}
							else {
								workflowValues.canReassign =
									Number(themeDisplay.getUserId()) === id;
								workflowValues[key] = name;
							}

							break;
						}
						case 'status': {
							if (typeof entry.completed === 'boolean') {
								workflowValues[key] = entry.completed ? (
									<ClayLabel displayType="success">
										{Liferay.Language.get('completed')}
									</ClayLabel>
								) : (
									<ClayLabel displayType="info">
										{Liferay.Language.get('pending')}
									</ClayLabel>
								);
							}
							else {
								workflowValues[key] = emptyValue;
							}

							break;
						}
						case 'taskNames': {
							const {taskNames = [emptyValue]} = entry;
							workflowValues[key] = taskNames[0];
							break;
						}
						default: {
							workflowValues[key] = entry[key] || emptyValue;
						}
					}
				});

				return {...entry, ...workflowValues};
			});
	};

	useEffect(() => {
		if (!isEqualObjects(query, previousQuery)) {
			refetch();
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [query]);

	useEffect(() => {
		doFetch({query, workflowDefinitionId: appWorkflowDefinitionId});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [appWorkflowDefinitionId]);

	const ACTIONS = [
		{
			action: (entry) => {
				setSelectedEntry(entry);
				setModalVisible(true);

				return Promise.resolve(false);
			},
			name: Liferay.Language.get('assign-to'),
			show: ({canReassign}) => canReassign,
		},
		...useEntriesActions({
			update: ({assignees, completed}) =>
				completed === false &&
				assignees?.[0]?.id === Number(themeDisplay.getUserId()),
		}),
	];

	const COLUMNS = [
		...columns.map(({value, ...column}) => ({
			...column,
			value: getLocalizedUserPreferenceValue(
				value,
				userLanguageId,
				dataDefinition.defaultLanguageId
			),
		})),
		...WORKFLOW_COLUMNS,
	];

	const isEmpty = items.length === 0;
	const showAddButton = showFormView && permissions.add;

	const refetchActions = ACTIONS.map((action = {}) => ({
		...action,
		action: (entry) =>
			action?.action(entry).then((isRefetch) => {
				if (isRefetch) {
					refetch();
				}
			}),
	}));

	if (!permissions.view) {
		return <NoPermissionState />;
	}

	return (
		<Loading isLoading={isLoading}>
			<SearchContext.Provider value={[query, dispatch]}>
				<ManagementToolbar
					addButton={() =>
						showAddButton && (
							<ClayButtonWithIcon
								className="nav-btn nav-btn-monospaced"
								onClick={onClickAddButton}
								symbol="plus"
								title={Liferay.Language.get('new-entry')}
							/>
						)
					}
					columns={COLUMNS}
					disabled={!query.keywords && isEmpty}
					totalCount={totalCount}
				/>

				<ManagementToolbarResultsBar
					isLoading={isFetching}
					totalCount={totalCount}
				/>

				<TableWithPagination
					actions={refetchActions}
					columns={COLUMNS}
					emptyState={{
						button: () =>
							showAddButton && (
								<ClayButton
									displayType="secondary"
									onClick={onClickAddButton}
								>
									{Liferay.Language.get('new-entry')}
								</ClayButton>
							),
						title: Liferay.Language.get('there-are-no-entries-yet'),
					}}
					isEmpty={isEmpty}
					isLoading={isFetching}
					items={buildWorkflowItems(items)}
					keywords={query.keywords}
					noActionsMessage={Liferay.Language.get(
						'you-do-not-have-the-permission-to-manage-this-entry'
					)}
					totalCount={totalCount}
				/>
			</SearchContext.Provider>

			{isModalVisible && (
				<ReassignEntryModal
					entry={selectedEntry}
					onCloseModal={onCloseModal}
					refetch={refetch}
				/>
			)}
		</Loading>
	);
}
