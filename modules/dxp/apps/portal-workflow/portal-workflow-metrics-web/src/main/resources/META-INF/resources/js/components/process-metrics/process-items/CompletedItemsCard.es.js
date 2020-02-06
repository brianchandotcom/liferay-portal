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

import {useFilter} from '../../../shared/hooks/useFilter.es';
import TimeRangeFilter from '../../filter/TimeRangeFilter.es';
import ProcessItemsCard from './ProcessItemsCard.es';

const CompletedItemsCard = ({routeParams}) => {
	const filterKeys = ['timeRange'];
	const prefixKey = 'completed';
	const prefixKeys = [prefixKey];

	const {
		filterState: {completedTimeRange: timeRange = []}
	} = useFilter({filterKeys, prefixKeys});

	const timeRangeParams = timeRange && timeRange.length ? timeRange[0] : {};

	return (
		<ProcessItemsCard
			completed={true}
			description={Liferay.Language.get('completed-items-description')}
			timeRange={timeRangeParams}
			title={Liferay.Language.get('completed-items')}
			{...routeParams}
		>
			<TimeRangeFilter
				options={{position: 'right'}}
				prefixKey={prefixKey}
			/>
		</ProcessItemsCard>
	);
};

export default CompletedItemsCard;
