import ActivityStreamCard from './components/ActivityStreamCard';
import BaseCard from 'shared/components/base-card';
import React from 'react';
import {Interval, RangeSelectors} from 'shared/types';
import {useParams} from 'react-router-dom';

const Activities = () => {
	const {channelId, id} = useParams<{
		channelId: string;
		groupId: string;
		id: string;
	}>();

	return (
		<>
			<BaseCard
				className='account-activity-stream-card'
				description={Liferay.Language.get(
					'chronological-timeline-of-the-accounts-activities-within-the-selected-timeframe-with-details-on-events-and-session-context'
				)}
				headerProps={{showRangeKey: true}}
				label={Liferay.Language.get('activity-stream').toUpperCase()}
				legacyDropdownRangeKey={false}
				showInterval
			>
				{({
					interval,
					rangeSelectors
				}: {
					interval: Interval;
					rangeSelectors: RangeSelectors;
				}) => (
					<ActivityStreamCard
						accountId={id}
						channelId={channelId}
						interval={interval}
						rangeSelectors={rangeSelectors}
					/>
				)}
			</BaseCard>
		</>
	);
};

export default Activities;
