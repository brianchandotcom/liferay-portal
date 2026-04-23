import IndividualProfileCard from 'individual/profile/components/ProfileCard';
import React from 'react';
import {Individual} from 'shared/util/records';
import {mockIndividual} from 'test/data';
import {RangeKeyTimeRanges} from 'shared/util/constants';

class IndividualProfileCardKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<IndividualProfileCard
					channelId='123'
					delta={20}
					entity={new Individual(mockIndividual())}
					groupId='35205'
					interval='DAY'
					onChangeInterval={() => {}}
					onDeltaChange={() => {}}
					onPageChange={() => {}}
					onQueryChange={() => {}}
					onRangeSelectorsChange={() => {}}
					page={1}
					query=''
					rangeSelectors={{
						rangeKey: RangeKeyTimeRanges.Last30Days
					}}
					resetPage={() => {}}
					tabId='activity'
				/>
			</div>
		);
	}
}

export default IndividualProfileCardKit;
