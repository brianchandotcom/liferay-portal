import * as API from 'shared/api';
import * as data from 'test/data';
import ActivitiesChartTimeline from 'contacts/components/ActivitiesChartTimeline';
import Promise from 'metal-promise';
import React from 'react';
import {EntityTypes, RangeKeyTimeRanges} from 'shared/util/constants';

const activityHistory = Array.from({length: 30}, (_, i) => ({
	intervalInitDate: new Date(2019, 0, i + 1).getTime(),
	totalElements: Math.round(Math.random() * 100)
}));

Object.assign(API.activities, {
	fetchGroup: () =>
		Promise.resolve({
			items: [data.mockSession(2)],
			total: 1
		})
});

const ActivitiesChartTimelineKit: React.FC = () => (
	<div>
		<ActivitiesChartTimeline
			activitiesLabel='Test label'
			entityType={EntityTypes.Account}
			groupId='23'
			history={activityHistory}
			id='1'
			rangeSelectors={{
				rangeKey: RangeKeyTimeRanges.Last30Days
			}}
		/>
	</div>
);

export default ActivitiesChartTimelineKit;
