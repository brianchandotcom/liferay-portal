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

import React, {useContext, useState, useMemo} from 'react';

import Panel from '../../../shared/components/Panel.es';
import {getFiltersParam} from '../../../shared/components/filter/util/filterUtil.es';
import EmptyState from '../../../shared/components/list/EmptyState.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import LoadingState from '../../../shared/components/loading/LoadingState.es';
import PromisesResolver from '../../../shared/components/request/PromisesResolver.es';
import Request from '../../../shared/components/request/Request.es';
import {AppContext} from '../../AppContext.es';
import {TimeRangeFilter} from '../filter/TimeRangeFilter.es';
import {
	TimeRangeContext,
	TimeRangeProvider
} from '../filter/store/TimeRangeStore.es';
import {Body, Empty} from './PerformanceByStepCardBody.es';
import {Item, Table} from './PerformanceByStepCardTable.es';

const Container = ({processId, query}) => {
	const {client} = useContext(AppContext);
	const [data, setData] = useState({});
	const {getSelectedTimeRange} = useContext(TimeRangeContext);

	const timeRange = getSelectedTimeRange();

	const fetchData = () => {
		return client
			.get(getRequestUrl(processId, timeRange))
			.then(({data}) => {
				setData(data);
			});
	};

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const promises = useMemo(() => [fetchData()], [processId, query]);

	return (
		<PromisesResolver promises={promises}>
			<PerformanceByStepCard.Header totalCount={data.totalCount} />

			<PromisesResolver.Pending>
				<PerformanceByStepCard.LoadingView />
			</PromisesResolver.Pending>

			<PromisesResolver.Resolved>
				<PerformanceByStepCard.Body
					data={data}
					processId={processId}
					timeRange={timeRange}
				/>
			</PromisesResolver.Resolved>

			<PromisesResolver.Rejected>
				<PerformanceByStepCard.ErrorView />
			</PromisesResolver.Rejected>
		</PromisesResolver>
	);
};

const ErrorView = () => {
	return (
		<EmptyState
			actionButton={<ReloadButton />}
			className="border-0"
			hideAnimation={true}
			message={Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			)}
			messageClassName="small"
			type="error"
		/>
	);
};

const getRequestUrl = (processId, timeRange) => {
	let requestUrl = `/processes/${processId}/tasks?completed=true`;

	if (timeRange) {
		const {dateEnd, dateStart} = timeRange;

		requestUrl += `&dateEnd=${dateEnd.toISOString()}&dateStart=${dateStart.toISOString()}`;
	}

	requestUrl += '&page=1&pageSize=10&sort=durationAvg:desc';

	return requestUrl;
};

const Header = ({totalCount}) => (
	<Panel.HeaderWithOptions
		description={Liferay.Language.get('performance-by-step-description')}
		elementClasses="dashboard-panel-header"
		title={Liferay.Language.get('performance-by-step')}
	>
		<PromisesResolver.Resolved>
			{totalCount > 0 && (
				<div className="autofit-col m-0 management-bar management-bar-light navbar">
					<ul className="navbar-nav">
						<TimeRangeFilter
							filterKey="performanceTimeRange"
							hideControl={true}
							position="right"
							showFilterName={false}
						/>
					</ul>
				</div>
			)}
		</PromisesResolver.Resolved>
	</Panel.HeaderWithOptions>
);

const LoadingView = () => {
	return (
		<div className={`border-0 pb-6 pt-6 sheet`} data-testid="loadingView">
			<LoadingState />
		</div>
	);
};

const PerformanceByStepCard = ({processId, query}) => {
	const {performanceTimeRange = []} = getFiltersParam(query);

	return (
		<Request>
			<TimeRangeProvider timeRangeKeys={performanceTimeRange}>
				<Panel>
					<PerformanceByStepCard.Container
						processId={processId}
						query={query}
					/>
				</Panel>
			</TimeRangeProvider>
		</Request>
	);
};

PerformanceByStepCard.Body = Body;
PerformanceByStepCard.Container = Container;
PerformanceByStepCard.Empty = Empty;
PerformanceByStepCard.ErrorView = ErrorView;
PerformanceByStepCard.Header = Header;
PerformanceByStepCard.Item = Item;
PerformanceByStepCard.LoadingView = LoadingView;
PerformanceByStepCard.Table = Table;

export default PerformanceByStepCard;
