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

import ClayLabel from '@clayui/label';
import {AppContext} from 'app-builder-web/js/AppContext.es';
import Button from 'app-builder-web/js/components/button/Button.es';
import NoPermissionState from 'app-builder-web/js/components/empty-state/NoPermissionState.es';
import {Loading} from 'app-builder-web/js/components/loading/Loading.es';
import ManagementToolbar from 'app-builder-web/js/components/management-toolbar/ManagementToolbar.es';
import ManagementToolbarResultsBar from 'app-builder-web/js/components/management-toolbar/ManagementToolbarResultsBar.es';
import SearchContext, {
	reducer,
} from 'app-builder-web/js/components/management-toolbar/SearchContext.es';
import TableWithPagination from 'app-builder-web/js/components/table/TableWithPagination.es';
import useDataListView from 'app-builder-web/js/hooks/useDataListView.es';
import useEntriesActions from 'app-builder-web/js/hooks/useEntriesActions.es';
import usePermissions from 'app-builder-web/js/hooks/usePermissions.es';
import useQuery from 'app-builder-web/js/hooks/useQuery.es';
import {
	buildEntries,
	navigateToEditPage,
} from 'app-builder-web/js/pages/entry/utils.es';
import {getItem} from 'app-builder-web/js/utils/client.es';
import {getLocalizedUserPreferenceValue} from 'app-builder-web/js/utils/lang.es';
import {errorToast} from 'app-builder-web/js/utils/toast.es';
import {concatValues, isEqualObjects} from 'app-builder-web/js/utils/utils.es';
import {usePrevious} from 'frontend-js-react-web';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import useAppWorkflow from '../../hooks/useAppWorkflow.es';
import useDataRecordApps from '../../hooks/useDataRecordApps.es';
import ReassignEntryModal from './ReassignEntryModal.es';

const WORKFLOW_COLUMNS = [
	{key: 'status', value: Liferay.Language.get('status')},
	{key: 'taskNames', value: Liferay.Language.get('step')},
	{key: 'assignees', value: Liferay.Language.get('assignee')},
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

	const doFetch = (query, workflowDefinitionId) => {
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

						getItem(
							`/o/portal-workflow-metrics/v1.0/processes/${workflowDefinitionId}/instances`,
							{classPKs, page: 1, pageSize: response.items.length}
						).then((workflowResponse) => {
							let items = response.items;

							if (workflowResponse.totalCount > 0) {
								items = response.items.map((item) => {
									const {
										assignees,
										completed,
										id: instanceId,
										taskNames,
									} =
										workflowResponse.items.find(
											({classPK}) => classPK === item.id
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

	const onCloseModal = (isRefetch) => {
		setModalVisible(false);
		setSelectedEntry();

		if (isRefetch) {
			refetch();
		}
	};

	const buildWorkflowItems = (items) => {
		return items
			.map(
				buildEntries({
					dataDefinition,
					fieldNames,
					permissions,
					scope: appId,
				})
			)
			.map((entry) => {
				const workflowValues = {};
				const emptyValue = '--';

				WORKFLOW_COLUMNS.forEach(({key}) => {
					switch (key) {
						case 'assignees': {
							const {assignees = [], taskNames = []} = entry;

							const {id, name = emptyValue, reviewer} =
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

	const refetch = () => doFetch(query, appWorkflowDefinitionId);

	useEffect(() => {
		if (!isEqualObjects(query, previousQuery)) {
			refetch();
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [query]);

	useEffect(() => {
		doFetch(query, appWorkflowDefinitionId);
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
			update: ({completed}) => completed === false,
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
							<Button
								className="nav-btn nav-btn-monospaced"
								onClick={onClickAddButton}
								symbol="plus"
								tooltip={Liferay.Language.get('new-entry')}
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
								<Button
									displayType="secondary"
									onClick={onClickAddButton}
								>
									{Liferay.Language.get('new-entry')}
								</Button>
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
				/>
			)}
		</Loading>
	);
}
