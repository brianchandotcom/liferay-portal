import BaseCard from 'shared/components/base-card';
import ProfileCardWithData from '../components/ProfileCard';
import React from 'react';
import {Individual} from 'shared/util/records';

interface IProfileCardProps extends React.HTMLAttributes<HTMLElement> {
	channelId: string;
	entity: Individual;
	groupId: string;
	tabId: string;
	timeZoneId: string;
}

const ProfileCard: React.FC<IProfileCardProps> = ({tabId, ...props}) => (
	<BaseCard
		className='individual-profile-card-root page-display'
		headerProps={{
			showRangeKey: false,
			tabId
		}}
		label={Liferay.Language.get('individual-events')}
		legacyDropdownRangeKey={false}
		showInterval={false}
	>
		{({
			interval,
			onChangeInterval,
			onRangeSelectorsChange,
			rangeSelectors
		}) => (
			<ProfileCardWithData
				{...props}
				interval={interval}
				onChangeInterval={onChangeInterval}
				onRangeSelectorsChange={onRangeSelectorsChange}
				rangeSelectors={rangeSelectors}
				tabId={tabId}
			/>
		)}
	</BaseCard>
);

export default ProfileCard;
