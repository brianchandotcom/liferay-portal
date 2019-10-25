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

import React, {useContext, useMemo, useState} from 'react';

import EmptyState from '../../shared/components/list/EmptyState.es';
import ReloadButton from '../../shared/components/list/ReloadButton.es';
import LoadingState from '../../shared/components/loading/LoadingState.es';
import PaginationBar from '../../shared/components/pagination/PaginationBar.es';
import PromisesResolver from '../../shared/components/request/PromisesResolver.es';
import {AppContext} from '../AppContext.es';
import WorkloadByAssigneePage from './WorkloadByAssigneePage.es';

const Body = ({page, pageSize, processId, sort}) => {
	const {client} = useContext(AppContext);
	const [data, setData] = useState({});

	const fetchData = () =>
		client
			.get(
				`/processes/${processId}/assignee-users?page=${page}&pageSize=${pageSize}&sort=${sort}`
			)
			.then(({data}) => {
				setData(data);
			});

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const promises = useMemo(() => [fetchData()], [
		page,
		pageSize,
		processId,
		sort
	]);

	return (
		<PromisesResolver promises={promises}>
			<PromisesResolver.Pending>
				<LoadingView />
			</PromisesResolver.Pending>

			<PromisesResolver.Resolved>
				{data.totalCount ? (
					<>
						<WorkloadByAssigneePage.Table items={data.items} />

						<PaginationBar
							page={page}
							pageCount={data.items.length}
							pageSize={pageSize}
							totalCount={data.totalCount}
						/>
					</>
				) : (
					<EmptyView />
				)}
			</PromisesResolver.Resolved>

			<PromisesResolver.Rejected>
				<ErrorView />
			</PromisesResolver.Rejected>
		</PromisesResolver>
	);
};

const EmptyView = () => {
	return (
		<EmptyState
			className="border-1"
			hideAnimation={false}
			message={Liferay.Language.get(
				'once-there-are-active-processes-metrics-will-appear-here'
			)}
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
	return (
		<div className={`border-1 pb-6 pt-6 sheet`} data-testid="loadingView">
			<LoadingState />
		</div>
	);
};

export {Body, EmptyView, ErrorView, LoadingView};
