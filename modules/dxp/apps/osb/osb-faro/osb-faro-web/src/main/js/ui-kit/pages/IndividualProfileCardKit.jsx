import IndividualProfileCard from 'individual/profile/components/ProfileCard';
import React from 'react';
import {Individual} from 'shared/util/records';
import {mockIndividual} from 'test/data';

class IndividualProfileCardKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<IndividualProfileCard
					entity={new Individual(mockIndividual())}
					groupId='35205'
				/>
			</div>
		);
	}
}

export default IndividualProfileCardKit;
