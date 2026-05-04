import ActivityStreamCard from './components/ActivityStreamCard';
import ActivityStreamCardHeader from './components/ActivityStreamCardHeader';
import BaseCard from 'shared/components/base-card';
import React, {useContext} from 'react';
import {ChannelContext} from 'shared/context/channel';
import {Interval, RangeSelectors} from 'shared/types';
import {useParams} from 'react-router-dom';

// TODO: temporary hard-coded id for end-to-end testing — revert to `id` from
// `useParams()` once the page is wired in production.
const TEST_ACCOUNT_ID =
	'1e74cbd38ebda5b7ba279c8e13443b67cf976dd444fc98c0e42b673ed7c5e43a';

const Activities = () => {
	const {selectedChannel} = useContext(ChannelContext);

	const {channelId: routeChannelId} = useParams<{
		channelId: string;
		groupId: string;
		id: string;
	}>();

	const channelId = routeChannelId ?? selectedChannel?.id ?? '';
	const id = TEST_ACCOUNT_ID;

	return (
		<>
			<BaseCard
				className='account-activity-stream-card'
				description={Liferay.Language.get(
					'chronological-timeline-of-the-accounts-activities-within-the-selected-timeframe-with-details-on-events-and-session-context'
				)}
				Header={ActivityStreamCardHeader}
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
