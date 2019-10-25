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

import React, {useContext, useEffect, useMemo} from 'react';

import {getFiltersParam} from '../../shared/components/filter/util/filterUtil.es';
import Request from '../../shared/components/request/Request.es';
import {AppContext} from '../AppContext.es';
import {ProcessStepProvider} from '../process-metrics/filter/store/ProcessStepStore.es';
import {
	Body,
	EmptyView,
	ErrorView,
	LoadingView
} from './WorkloadByAssigneePageBody.es';
import {Item, Table} from './WorkloadByAssigneePageTable.es';

const WorkloadByAssigneePage = ({page, pageSize, processId, query, sort}) => {
	const {assigneeTaskKeys = []} = useMemo(() => getFiltersParam(query), [
		query
	]);
	const {client, setTitle} = useContext(AppContext);

	useEffect(() => {
		client.get(`/processes/${processId}/title`).then(({data}) => {
			setTitle(
				`${data}: ${Liferay.Language.get('workload-by-assignee')}`
			);
			return data;
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<Request>
			<ProcessStepProvider
				processId={processId}
				processStepKeys={assigneeTaskKeys}
			>
				<div className="container-fluid-1280 mt-4">
					<WorkloadByAssigneePage.Body
						page={page}
						pageSize={pageSize}
						processId={processId}
						sort={sort}
					/>
				</div>
			</ProcessStepProvider>
		</Request>
	);
};

WorkloadByAssigneePage.Body = Body;
WorkloadByAssigneePage.Empty = EmptyView;
WorkloadByAssigneePage.Error = ErrorView;
WorkloadByAssigneePage.Item = Item;
WorkloadByAssigneePage.Loading = LoadingView;
WorkloadByAssigneePage.Table = Table;

export default WorkloadByAssigneePage;
