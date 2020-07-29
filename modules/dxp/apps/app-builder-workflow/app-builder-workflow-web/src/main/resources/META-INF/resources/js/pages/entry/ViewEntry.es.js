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

import {AppContext} from 'app-builder-web/js/AppContext.es';
import ControlMenu from 'app-builder-web/js/components/control-menu/ControlMenu.es';
import {Loading} from 'app-builder-web/js/components/loading/Loading.es';
import useDataDefinition from 'app-builder-web/js/hooks/useDataDefinition.es';
import useQuery from 'app-builder-web/js/hooks/useQuery.es';
import {ViewDataLayoutPageValues} from 'app-builder-web/js/pages/entry/ViewEntry.es';
import ViewEntryUpperToolbar from 'app-builder-web/js/pages/entry/ViewEntryUpperToolbar.es';
import {addItem, getItem} from 'app-builder-web/js/utils/client.es';
import {getLocalizedValue} from 'app-builder-web/js/utils/lang.es';
import {errorToast} from 'app-builder-web/js/utils/toast.es';
import {isEqualObjects} from 'app-builder-web/js/utils/utils.es';
import {usePrevious} from 'frontend-js-react-web';
import React, {useContext, useEffect, useState} from 'react';

import WorkflowInfoBar from '../../components/workflow-info-bar/WorkflowInfoBar.es';
import useDataLayouts from '../../hooks/useDataLayouts.es';

export default function ViewEntry({
	history,
	match: {
		params: {entryIndex},
	},
}) {
	const {appId, dataDefinitionId, dataLayoutId} = useContext(AppContext);
	const [dataLayoutIds, setDataLayoutIds] = useState([]);

	const getDataLayoutIds = (tasks) => {
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
			[Number(dataLayoutId)]
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
		getItem(
			`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-records`,
			{...query, page: entryIndex, pageSize: 1}
		)
			.then(({items = [], ...response}) => {
				if (items.length > 0) {
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
							if (items.length > 0) {
								const {
									appWorkflow: {
										appVersion,
										appWorkflowDefinitionId,
										appWorkflowTasks: tasks,
									},
								} = items.pop();

								setDataLayoutIds(getDataLayoutIds(tasks));

								return getItem(
									`/o/portal-workflow-metrics/v1.0/processes/${appWorkflowDefinitionId}/instances`,
									{classPKs: dataRecordIds}
								).then((workflowResponse) => {
									if (workflowResponse.totalCount > 0) {
										state.workflowInfo = {
											...workflowResponse.items.pop(),
											appVersion,
											tasks,
										};
									}

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
		update: !workflowInfo?.completed,
	};

	return (
		<div className="view-entry">
			<ControlMenu
				backURL="../../"
				title={Liferay.Language.get('details-view')}
			/>

			<ViewEntryUpperToolbar
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
												{getLocalizedValue(
													dataDefinition.defaultLanguageId,
													dataLayout.name
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
						</div>
					</div>
				</div>
			</Loading>
		</div>
	);
}
