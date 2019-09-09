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

import React, {useContext} from 'react';
import {
	TimeRangeContext,
	TimeRangeProvider
} from '../filter/store/TimeRangeStore.es';
import {getFiltersParam} from '../../../shared/components/filter/util/filterUtil.es';
import ProcessItemsCard from './ProcessItemsCard.es';
import Request from '../../../shared/components/request/Request.es';
import {TimeRangeFilter} from '../filter/TimeRangeFilter.es';

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
				<TimeRangeFilter
					filterKey="timeRange"
					hideControl={true}
					position="right"
					showFilterName={false}
				/>
			</Request.Success>
		</ProcessItemsCard>
	);
};

export default CompletedItemsCard;
