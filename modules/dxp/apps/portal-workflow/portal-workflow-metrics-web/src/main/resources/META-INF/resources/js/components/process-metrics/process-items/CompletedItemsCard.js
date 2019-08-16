import React, {useContext} from 'react';
import {
	TimeRangeContext,
	TimeRangeProvider
} from '../filter/store/TimeRangeStore';
import {getFiltersParam} from '../../../shared/components/filter/util/filterUtil';
import ProcessItemsCard from './ProcessItemsCard';
import Request from '../../../shared/components/request/Request';
import {TimeRangeFilter} from '../filter/TimeRangeFilter';

function CompletedItemsCard({processId, query}) {
	const {timeRange = []} = getFiltersParam(query);

	return (
		<Request>
			<TimeRangeProvider timeRangeKeys={timeRange}>
				<CompletedItemsCard.Body processId={processId} />
			</TimeRangeProvider>
		</Request>
	);
}

CompletedItemsCard.Body = ({processId}) => {
	const {getSelectedTimeRange} = useContext(TimeRangeContext);

	return (
		<ProcessItemsCard
			completed
			description={Liferay.Language.get('completed-items-description')}
			processId={processId}
			timeRange={getSelectedTimeRange()}
			title={Liferay.Language.get('completed-items')}
		>
			<Request.Success>
				<TimeRangeFilter filterKey="timeRange" position="right" />
			</Request.Success>
		</ProcessItemsCard>
	);
};

export default CompletedItemsCard;
