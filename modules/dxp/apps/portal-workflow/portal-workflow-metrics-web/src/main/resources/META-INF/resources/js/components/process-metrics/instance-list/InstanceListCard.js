import {
	InstanceListProvider,
	InstanceListContext
} from './store/InstanceListStore';
import React, {useContext} from 'react';
import {ErrorContext} from '../../../shared/components/request/Error';
import {getFiltersParam} from '../../../shared/components/filter/util/filterUtil';
import {InstanceFiltersProvider} from './store/InstanceFiltersStore';
import InstanceItemDetail from './InstanceItemDetail';
import InstanceListFilters from './InstanceListFilters';
import InstanceListTable from './InstanceListTable';
import ListView from '../../../shared/components/list/ListView';
import {LoadingContext} from '../../../shared/components/request/Loading';
import PaginationBar from '../../../shared/components/pagination/PaginationBar';
import ReloadButton from '../../../shared/components/list/ReloadButton';
import Request from '../../../shared/components/request/Request';

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

InstanceListCard.Body = ({page, pageSize, processId}) => {
	const {error} = useContext(ErrorContext);
	const {items = [], totalCount} = useContext(InstanceListContext);
	const {loading} = useContext(LoadingContext);

	const fetching = !loading && !totalCount;
	const errorMessage = error
		? Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
		  )
		: null;

	return (
		<>
			<div className="container-fluid-1280 mt-4">
				<ListView
					emptyActionButton={<ReloadButton />}
					emptyMessageText={Liferay.Language.get(
						'there-are-no-process-items-at-the-moment'
					)}
					errorMessageText={errorMessage}
					fetching={fetching}
					loading={loading}
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

InstanceListCard.Header = () => {
	const {totalCount} = useContext(InstanceListContext);

	return (
		<Request.Success>
			<InstanceListFilters totalCount={totalCount} />
		</Request.Success>
	);
};

export default InstanceListCard;
