import Card from 'shared/components/Card';
import CompositionChart from 'shared/components/CompositionChart';
import React from 'react';
import {PropTypes} from 'prop-types';

export default class CompositionCard extends React.Component {
	static propTypes = {
		activeIndividualCount: PropTypes.number,
		individualCount: PropTypes.number,
		knownIndividualCount: PropTypes.number
	};

	render() {
		const {
			activeIndividualCount,
			individualCount,
			knownIndividualCount
		} = this.props;

		return (
			<Card className='composition-card-root'>
				<Card.Header>
					<Card.Title>
						{Liferay.Language.get('segment-composition')}
					</Card.Title>
				</Card.Header>

				<Card.Body>
					<CompositionChart
						innerData={{
							label: Liferay.Language.get('known-members'),
							value: knownIndividualCount
						}}
						outerData={{
							label: Liferay.Language.get('active-last-30-days'),
							value: activeIndividualCount
						}}
						total={individualCount}
					/>
				</Card.Body>
			</Card>
		);
	}
}
