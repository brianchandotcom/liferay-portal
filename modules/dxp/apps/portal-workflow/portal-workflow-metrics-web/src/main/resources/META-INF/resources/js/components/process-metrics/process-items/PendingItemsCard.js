import ProcessItemsCard from './ProcessItemsCard';
import React from 'react';

const PendingItemsCard = ({processId}) => {
	return (
		<ProcessItemsCard
			description={Liferay.Language.get('pending-items-description')}
			processId={processId}
			title={Liferay.Language.get('pending-items')}
		/>
	);
};

export default PendingItemsCard;
