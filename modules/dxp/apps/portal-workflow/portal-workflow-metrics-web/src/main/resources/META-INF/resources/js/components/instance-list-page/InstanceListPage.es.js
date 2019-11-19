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
import EmptyState from '../../shared/components/list/EmptyState.es';
import ReloadButton from '../../shared/components/list/ReloadButton.es';
import LoadingState from '../../shared/components/loading/LoadingState.es';
import PaginationBar from '../../shared/components/pagination/PaginationBar.es';
import PromisesResolver from '../../shared/components/request/PromisesResolver.es';
import Request from '../../shared/components/request/Request.es';
import {AppContext} from '../AppContext.es';
import InstanceListPageFilters from './InstanceListPageFilters.es';
import InstanceListPageItemDetail from './InstanceListPageItemDetail.es';
import InstanceListPageTable from './InstanceListPageTable.es';
import {InstanceFiltersProvider} from './store/InstanceListPageFiltersStore.es';
import {
	InstanceListProvider,
	InstanceListContext
} from './store/InstanceListPageStore.es';

export function InstanceListPage({page, pageSize, processId, query}) {
	const filters = getFiltersParam(query);
	const {
		assigneeUserIds = [],
		slaStatuses = [],
		statuses = [],
		taskKeys = [],
		timeRange = []
	} = filters;

	const {client, setTitle} = useContext(AppContext);

	useEffect(() => {
		client.get(`/processes/${processId}/title`).then(({data}) => {
			setTitle(`${data}: ${Liferay.Language.get('all-items')}`);
			return data;
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<Request>
			<InstanceFiltersProvider
				assigneeKeys={assigneeUserIds}
				processId={processId}
				processStatusKeys={statuses}
				processStepKeys={taskKeys}
				slaStatusKeys={slaStatuses}
				timeRangeKeys={timeRange}
			>
				<InstanceListProvider
					page={page}
					pageSize={pageSize}
					processId={processId}
					query={query}
				>
					<InstanceListPage.Header
						processId={processId}
						query={query}
					/>

					<InstanceListPage.Body
						page={page}
						pageSize={pageSize}
						processId={processId}
						query={query}
					/>
				</InstanceListProvider>
			</InstanceFiltersProvider>
		</Request>
	);
}

const Body = ({page, pageSize, processId, query}) => {
	const {fetchInstances, items, searching, totalCount} = useContext(
		InstanceListContext
	);

	const emptyMessageText = searching
		? Liferay.Language.get('no-results-were-found')
		: Liferay.Language.get(
				'once-there-are-active-processes-metrics-will-appear-here'
		  );
	const errorMessageText = Liferay.Language.get(
		'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
	);

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const promises = useMemo(() => [fetchInstances()], [
		page,
		pageSize,
		processId,
		query
	]);

	return (
		<>
			<div className="container-fluid-1280 mt-4">
				<PromisesResolver promises={promises}>
					<PromisesResolver.Pending>
						<LoadingState />
					</PromisesResolver.Pending>

					<PromisesResolver.Resolved>
						{items && items.length ? (
							<>
								<InstanceListPageTable items={items} />

								<PaginationBar
									page={page}
									pageCount={items.length}
									pageSize={pageSize}
									totalCount={totalCount}
								/>
							</>
						) : (
							<EmptyState
								className="border-1"
								hideAnimation={false}
								message={emptyMessageText}
								type="not-found"
							/>
						)}
					</PromisesResolver.Resolved>

					<PromisesResolver.Rejected>
						<EmptyState
							actionButton={<ReloadButton />}
							className="border-1"
							hideAnimation={true}
							message={errorMessageText}
							messageClassName="small"
							type="error"
						/>
					</PromisesResolver.Rejected>
				</PromisesResolver>
			</div>

			<InstanceListPageItemDetail processId={processId} />
		</>
	);
};

const Header = () => {
	const {totalCount} = useContext(InstanceListContext);

	return (
		<Request.Success>
			<InstanceListPageFilters totalCount={totalCount} />
		</Request.Success>
	);
};

InstanceListPage.Body = Body;
InstanceListPage.Header = Header;

export default InstanceListPage;
