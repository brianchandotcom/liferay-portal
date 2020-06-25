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
import ControlMenu from 'app-builder-web/js/components/control-menu/ControlMenu.es';
import {Loading} from 'app-builder-web/js/components/loading/Loading.es';
import useDataLayout from 'app-builder-web/js/hooks/useDataLayout.es';
import useQuery from 'app-builder-web/js/hooks/useQuery.es';
import {ViewDataLayoutPageValues} from 'app-builder-web/js/pages/entry/ViewEntry.es';
import ViewEntryUpperToolbar from 'app-builder-web/js/pages/entry/ViewEntryUpperToolbar.es';
import {getItem} from 'app-builder-web/js/utils/client.es';
import {errorToast} from 'app-builder-web/js/utils/toast.es';
import {isEqualObjects} from 'app-builder-web/js/utils/utils.es';
import {usePrevious} from 'frontend-js-react-web';
import React, {useContext, useEffect, useState} from 'react';

import '../../../css/ViewEntry.scss';
import useAppWorkflow from '../../hooks/useAppWorkflow.es';

const WorkflowInfo = ({
	assignees = [],
	completed,
	taskNames = [],
	states = [],
}) => {
	const emptyValue = '--';

	const finalState = states.find(({initial}) => !initial) || {
		name: emptyValue,
	};
	const {name = emptyValue} = assignees.pop() || {};
	const status = completed ? (
		<ClayLabel displayType="success">
			{Liferay.Language.get('completed')}
		</ClayLabel>
	) : (
		<ClayLabel displayType="secondary">
			{Liferay.Language.get('pending')}
		</ClayLabel>
	);

	const stepName = taskNames.pop() || emptyValue;

	const items = [
		{
			label: Liferay.Language.get('status'),
			value: status,
		},
		{
			label: Liferay.Language.get('step'),
			value: completed ? finalState.name : stepName,
		},
		{
			label: Liferay.Language.get('assignee'),
			value: name,
		},
	];

	return (
		<div className="workflow-info">
			{items.map(({label, value}, index) => (
				<div className="info-item" key={index}>
					<span className="font-weight-bold text-secondary">
						{`${label}: `}
					</span>

					{value}
				</div>
			))}
		</div>
	);
};

export default function ViewEntry({
	history,
	match: {
		params: {entryIndex},
	},
}) {
	const {appId, dataDefinitionId, dataLayoutId} = useContext(AppContext);
	const {appWorkflowDefinitionId, appWorkflowStates} = useAppWorkflow(appId);
	const {
		dataDefinition,
		dataLayout: {dataLayoutPages},
		isLoading,
	} = useDataLayout(dataLayoutId, dataDefinitionId);

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

	const doFetch = (appWorkflowDefinitionId) => {
		if (appWorkflowDefinitionId) {
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

						return getItem(
							`/o/portal-workflow-metrics/v1.0/processes/${appWorkflowDefinitionId}/instances`,
							{classPKs: [state.dataRecord.id]}
						)
							.then((workflowResponse) => {
								if (workflowResponse.totalCount > 0) {
									state.workflowInfo = workflowResponse.items.pop();
								}

								setState((prevState) => ({
									...prevState,
									...state,
								}));
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
		}
	};

	useEffect(() => {
		if (
			!isEqualObjects(query, previousQuery) ||
			entryIndex !== previousIndex
		) {
			doFetch(appWorkflowDefinitionId);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [entryIndex, query]);

	useEffect(() => {
		doFetch(appWorkflowDefinitionId);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [appWorkflowDefinitionId]);

	return (
		<div className="view-entry">
			<ControlMenu
				backURL="../../"
				title={Liferay.Language.get('details-view')}
			/>

			<ViewEntryUpperToolbar
				dataRecordId={dataRecordId}
				page={page}
				totalCount={totalCount}
			>
				{workflowInfo && (
					<WorkflowInfo
						{...workflowInfo}
						states={appWorkflowStates}
					/>
				)}
			</ViewEntryUpperToolbar>

			<Loading isLoading={isLoading || isFetching}>
				<div className="container">
					<div className="justify-content-center row">
						<div className="col-lg-8">
							{dataLayoutPages &&
								dataRecordValues &&
								dataLayoutPages.map((dataLayoutPage, index) => (
									<div className="sheet" key={index}>
										<ViewDataLayoutPageValues
											dataDefinition={dataDefinition}
											dataLayoutPage={dataLayoutPage}
											dataRecordValues={dataRecordValues}
											key={index}
										/>
									</div>
								))}
						</div>
					</div>
				</div>
			</Loading>
		</div>
	);
}
