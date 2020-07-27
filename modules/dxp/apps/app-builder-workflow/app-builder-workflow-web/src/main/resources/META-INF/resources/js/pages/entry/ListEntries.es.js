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
import {errorToast} from 'app-builder-web/js/utils/toast.es';
import {concatValues, isEqualObjects} from 'app-builder-web/js/utils/utils.es';
import {usePrevious} from 'frontend-js-react-web';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import useAppWorkflow from '../../hooks/useAppWorkflow.es';

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
	} = useContext(AppContext);

	const {appWorkflowDefinitionId, appWorkflowTasks = []} = useAppWorkflow(
		appId
	);

	const {
		columns,
		dataDefinition,
		dataListView: {fieldNames},
		isLoading,
	} = useDataListView(dataListViewId, dataDefinitionId);
	const permissions = usePermissions();

	const [{isFetching, items, totalCount}, setFetchState] = useState({
		isFetching: true,
		items: [],
		totalCount: 0,
	});

	const [query, setQuery] = useQuery(history, {
		dataListViewId,
		keywords: '',
		page: 1,
		pageSize: defaultDelta,
		sort: '',
	});

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
						isFetching: response.totalCount !== 0,
						...response,
					});

					if (response.totalCount > 0) {
						const classPKs = response.items.map(({id}) => id);

						getItem(
							`/o/portal-workflow-metrics/v1.0/processes/${workflowDefinitionId}/instances`,
							{classPKs, page: 1, pageSize: response.items.length}
						).then((workflowResponse) => {
							let items = response.items;

							if (workflowResponse.totalCount > 0) {
								items = response.items.map((item) => {
									const {assignees, completed, taskNames} =
										workflowResponse.items.find(
											({classPK}) => classPK === item.id
										) || {};

									return {
										...item,
										assignees,
										completed,
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

					setFetchState((prevState) => ({
						...prevState,
						isFetching: false,
					}));
				});
		}
	};

	const onClickAddButton = () => navigateToEditPage(basePortletURL);

	const buildWorkflowItems = (items) => {
		return items
			.map(buildEntries(fieldNames, dataDefinition, permissions))
			.map((entry) => {
				const workflowValues = {};
				const emptyValue = '--';

				WORKFLOW_COLUMNS.forEach(({key}) => {
					switch (key) {
						case 'assignees': {
							const {assignees = [{}], taskNames = []} = entry;
							const {name = emptyValue, id} = assignees[0];

							if (id === -1) {
								const {appWorkflowRoleAssignments = []} =
									appWorkflowTasks.find(
										({name}) => name === taskNames[0]
									) || {};

								const roleNames = appWorkflowRoleAssignments.map(
									({roleName}) => roleName
								);

								workflowValues[key] = roleNames.length
									? concatValues(roleNames)
									: emptyValue;
							}
							else {
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

	const COLUMNS = [...columns, ...WORKFLOW_COLUMNS];

	const isEmpty = totalCount === 0;
	const showAddButton = showFormView && permissions.add;
	const showOptions = {
		update: ({completed}) => !completed,
	};

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
					actions={useEntriesActions({refetch, showOptions})}
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
		</Loading>
	);
}
