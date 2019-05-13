import ProcessItemsCard from './ProcessItemsCard';
import React from 'react';

export default class PendingItemsCard extends React.Component {
	render() {
		const { processId } = this.props;

		return (
			<ProcessItemsCard
				description={Liferay.Language.get('pending-items-description')}
				processId={processId}
				title={Liferay.Language.get('pending-items')}
			/>
		);
	}
}