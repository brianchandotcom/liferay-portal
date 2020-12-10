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

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {AppContext} from 'app-builder-web/js/AppContext.es';
import ControlMenu from 'app-builder-web/js/components/control-menu/ControlMenu.es';
import {Loading} from 'app-builder-web/js/components/loading/Loading.es';
import useDataDefinition from 'app-builder-web/js/hooks/useDataDefinition.es';
import useQuery from 'app-builder-web/js/hooks/useQuery.es';
import {ViewDataLayoutPageValues} from 'app-builder-web/js/pages/entry/ViewEntry.es';
import ViewEntryUpperToolbar from 'app-builder-web/js/pages/entry/ViewEntryUpperToolbar.es';
import {addItem, getItem} from 'app-builder-web/js/utils/client.es';
import {getLocalizedUserPreferenceValue} from 'app-builder-web/js/utils/lang.es';
import {errorToast} from 'app-builder-web/js/utils/toast.es';
import {isEqualObjects} from 'app-builder-web/js/utils/utils.es';
import {usePrevious} from 'frontend-js-react-web';
import React, {useContext, useEffect, useState} from 'react';

import WorkflowInfoBar from '../../components/workflow-info-bar/WorkflowInfoBar.es';
import useDataLayouts from '../../hooks/useDataLayouts.es';
import ReassignEntryModal from './ReassignEntryModal.es';
import ActivitySection from './activity/ActivitySection.es';

export default function ViewEntry({
	history,
	match: {
		params: {entryIndex},
	},
}) {
	const {
		appId,
		dataDefinitionId,
		dataLayoutId,
		dataListViewId,
		userLanguageId,
	} = useContext(AppContext);
	const [dataLayoutIds, setDataLayoutIds] = useState([]);
	const [isModalVisible, setModalVisible] = useState(false);

	const getDataLayoutIds = ({completed, taskNames = [], tasks}) => {
		const initialIds = [];

		if (!completed) {
			tasks = tasks.filter(({name}) => taskNames.includes(name));
		}
		else {
			initialIds.push(Number(dataLayoutId));
		}

		return tasks.reduce(
			(dataLayoutIds, {appWorkflowDataLayoutLinks}) => [
				...dataLayoutIds,
				...appWorkflowDataLayoutLinks?.reduce(
					(stepDataLayoutIds, {dataLayoutId}) =>
						dataLayoutIds.includes(dataLayoutId)
							? stepDataLayoutIds
							: [...stepDataLayoutIds, dataLayoutId],
					[]
				),
			],
			initialIds
		);
	};

	const dataDefinition = useDataDefinition(dataDefinitionId);
	const dataLayouts = useDataLayouts(dataLayoutIds);

	const [
		{dataRecord, isFetching, page, totalCount, workflowInfo},
		setState,
	] = useState({
		dataRecord: {},
		isFetching: true,
		page: 1,
		totalCount: 0,
		workflowInfo: null,
	});

	const {dataRecordValues = {}, id: dataRecordId} = dataRecord;

	const [query] = useQuery(history, {
		keywords: '',
		page: 1,
		sort: '',
	});

	const previousQuery = usePrevious(query);
	const previousIndex = usePrevious(entryIndex);

	const doFetch = () => {
		setState({
			dataRecord: {},
			isFetching: true,
			page: 1,
			totalCount: 0,
			workflowInfo: null,
		});

		getItem(
			`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-records`,
			{...query, dataListViewId, page: entryIndex, pageSize: 1}
		)
			.then(({items = [], ...response}) => {
				if (items.length) {
					const state = {
						dataRecord: items.pop(),
						isFetching: false,
						workflowInfo: null,
						...response,
					};

					const dataRecordIds = [state.dataRecord.id];

					addItem(
						`/o/app-builder-workflow/v1.0/apps/${appId}/app-workflows/data-record-links`,
						{dataRecordIds}
					)
						.then(({items}) => {
							if (items.length) {
								const {
									appWorkflow: {
										appVersion,
										appWorkflowDefinitionId,
										appWorkflowTasks: tasks,
									},
								} = items.pop();

								return getItem(
									`/o/portal-workflow-metrics/v1.0/processes/${appWorkflowDefinitionId}/instances`,
									{classPKs: dataRecordIds}
								).then(({items}) => {
									if (items.length) {
										const {id, ...instance} = items.pop();

										const [assignee] =
											instance.assignees || [];

										const assignedToUser =
											Number(themeDisplay.getUserId()) ===
											assignee?.id;

										state.workflowInfo = {
											...instance,
											appVersion,
											canReassign:
												assignedToUser ||
												assignee?.reviewer,
											instanceId: id,
											tasks,
										};

										setDataLayoutIds(
											getDataLayoutIds(state.workflowInfo)
										);
									}

									setState((prevState) => ({
										...prevState,
										...state,
									}));
								});
							}
							else {
								setDataLayoutIds([Number(dataLayoutId)]);

								setState((prevState) => ({
									...prevState,
									...state,
								}));
							}
						})
						.catch(() => {
							setState((prevState) => ({
								...prevState,
								...state,
							}));
						});
				}
			})
			.catch(() => {
				setState((prevState) => ({
					...prevState,
					isFetching: false,
				}));

				errorToast();
			});
	};

	useEffect(() => {
		if (!isEqualObjects(query, previousQuery) || !previousIndex) {
			doFetch();
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [entryIndex, query]);

	const showButtons = {
		update:
			workflowInfo?.completed === false &&
			workflowInfo?.assignees?.[0]?.id ===
				Number(themeDisplay.getUserId()),
	};

	return (
		<div className="view-entry">
			<ControlMenu
				backURL="../../"
				title={Liferay.Language.get('details-view')}
			/>

			<ViewEntryUpperToolbar
				additionalButtons={
					workflowInfo?.canReassign && (
						<ClayTooltipProvider>
							<ClayButtonWithIcon
								className="mr-2"
								data-tooltip-align="bottom"
								data-tooltip-delay="200"
								displayType="secondary"
								onClick={() => setModalVisible(true)}
								small
								symbol="change"
								title={Liferay.Language.get('assign-to')}
							/>
						</ClayTooltipProvider>
					)
				}
				dataRecordId={dataRecordId}
				page={page}
				showButtons={showButtons}
				totalCount={totalCount}
			>
				{workflowInfo && <WorkflowInfoBar {...workflowInfo} />}
			</ViewEntryUpperToolbar>

			<Loading isLoading={isFetching}>
				<div className="container">
					<div className="justify-content-center row">
						<div className="col-lg-8">
							{dataRecordValues &&
								dataLayouts.map(
									({dataLayoutPages = [], ...dataLayout}) => (
										<div key={dataLayout.id}>
											<h3>
												{getLocalizedUserPreferenceValue(
													dataLayout.name,
													userLanguageId,
													dataDefinition.defaultLanguageId
												)}
											</h3>

											{dataLayoutPages.map(
												(dataLayoutPage, index) => (
													<div
														className="sheet"
														key={index}
													>
														<ViewDataLayoutPageValues
															dataDefinition={
																dataDefinition
															}
															dataLayoutPage={
																dataLayoutPage
															}
															dataRecordValues={
																dataRecordValues
															}
															key={index}
														/>
													</div>
												)
											)}
										</div>
									)
								)}

							{workflowInfo?.instanceId && (
								<ActivitySection
									workflowInstanceId={workflowInfo.instanceId}
								/>
							)}
						</div>
					</div>
				</div>
			</Loading>

			{isModalVisible && (
				<ReassignEntryModal
					entry={workflowInfo}
					onCloseModal={() => setModalVisible(false)}
					refetch={doFetch}
				/>
			)}
		</div>
	);
}
