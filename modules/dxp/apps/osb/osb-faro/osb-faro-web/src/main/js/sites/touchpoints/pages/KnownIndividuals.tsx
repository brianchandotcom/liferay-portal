import KnownIndividualsListCard from '../hocs/KnownIndividualsListCard';
import React from 'react';

interface ITouchpointKnownIndividualsPageProps {
	router: object;
}

const TouchpointKnownIndividualsPage: React.FC<ITouchpointKnownIndividualsPageProps> = ({
	router
}) => (
	<div className='row'>
		<div className='col-sm-12'>
			<KnownIndividualsListCard router={router} />
		</div>
	</div>
);

export default TouchpointKnownIndividualsPage;
