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

import React from 'react';

import EmptyState from '../../shared/components/list/EmptyState.es';
import ReloadButton from '../../shared/components/list/ReloadButton.es';
import LoadingState from '../../shared/components/loading/LoadingState.es';
import PaginationBar from '../../shared/components/pagination/PaginationBar.es';
import PromisesResolver from '../../shared/components/request/PromisesResolver.es';
import WorkloadByAssigneePage from './WorkloadByAssigneePage.es';

const Body = ({data, processId, taskKeys}) => {
	return (
		<>
			<PromisesResolver.Pending>
				<WorkloadByAssigneePage.Loading />
			</PromisesResolver.Pending>

			<PromisesResolver.Resolved>
				{data.totalCount ? (
					<>
						<WorkloadByAssigneePage.Table
							items={data.items}
							processId={processId}
							taskKeys={taskKeys}
						/>

						<PaginationBar
							page={data.page}
							pageCount={data.items.length}
							pageSize={data.pageSize}
							totalCount={data.totalCount}
						/>
					</>
				) : (
					<WorkloadByAssigneePage.Empty />
				)}
			</PromisesResolver.Resolved>

			<PromisesResolver.Rejected>
				<WorkloadByAssigneePage.Error />
			</PromisesResolver.Rejected>
		</>
	);
};

const EmptyView = () => {
	return (
		<EmptyState
			className="border-1"
			hideAnimation={false}
			message={Liferay.Language.get('no-results-were-found')}
			type="not-found"
		/>
	);
};

const ErrorView = () => {
	return (
		<EmptyState
			actionButton={<ReloadButton />}
			className="border-1"
			hideAnimation={true}
			message={Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			)}
			messageClassName="small"
			type="error"
		/>
	);
};

const LoadingView = () => {
	return <LoadingState />;
};

export {Body, EmptyView, ErrorView, LoadingView};
