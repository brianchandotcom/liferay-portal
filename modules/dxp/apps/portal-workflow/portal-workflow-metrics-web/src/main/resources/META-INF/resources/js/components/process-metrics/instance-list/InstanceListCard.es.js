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

import {
	InstanceListProvider,
	InstanceListContext
} from './store/InstanceListStore.es';
import React, {useContext} from 'react';
import {ErrorContext} from '../../../shared/components/request/Error.es';
import {getFiltersParam} from '../../../shared/components/filter/util/filterUtil.es';
import {InstanceFiltersProvider} from './store/InstanceFiltersStore.es';
import InstanceItemDetail from './InstanceItemDetail.es';
import InstanceListFilters from './InstanceListFilters.es';
import InstanceListTable from './InstanceListTable.es';
import ListView from '../../../shared/components/list/ListView.es';
import {LoadingContext} from '../../../shared/components/request/Loading.es';
import PaginationBar from '../../../shared/components/pagination/PaginationBar.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import Request from '../../../shared/components/request/Request.es';

export function InstanceListCard({page, pageSize, processId, query}) {
	const filters = getFiltersParam(query);
	const {
		slaStatuses = [],
		statuses = [],
		taskKeys = [],
		timeRange = []
	} = filters;

	return (
		<Request>
			<InstanceFiltersProvider
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
					<InstanceListCard.Header
						processId={processId}
						query={query}
					/>

					<InstanceListCard.Body
						page={page}
						pageSize={pageSize}
						processId={processId}
					/>
				</InstanceListProvider>
			</InstanceFiltersProvider>
		</Request>
	);
}

const Body = ({page, pageSize, processId}) => {
	const {error} = useContext(ErrorContext);
	const {items = [], searching, totalCount} = useContext(InstanceListContext);
	const {loading} = useContext(LoadingContext);

	const emptyMessageText = searching
		? Liferay.Language.get('no-results-were-found')
		: Liferay.Language.get(
				'once-there-are-active-processes-metrics-will-appear-here'
		  );
	const errorMessageText = error
		? Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
		  )
		: null;
	const fetching = !loading && !totalCount;

	return (
		<>
			<div className="container-fluid-1280 mt-4">
				<ListView
					emptyActionButton={<ReloadButton />}
					emptyMessageText={emptyMessageText}
					errorMessageText={errorMessageText}
					fetching={fetching}
					loading={loading}
					searching={searching && totalCount === 0}
				>
					<InstanceListTable items={items} />

					<PaginationBar
						page={page}
						pageCount={items.length}
						pageSize={pageSize}
						totalCount={totalCount}
					/>
				</ListView>
			</div>

			<InstanceItemDetail processId={processId} />
		</>
	);
};

const Header = () => {
	const {totalCount} = useContext(InstanceListContext);

	return (
		<Request.Success>
			<InstanceListFilters totalCount={totalCount} />
		</Request.Success>
	);
};

InstanceListCard.Body = Body;
InstanceListCard.Header = Header;

export default InstanceListCard;
