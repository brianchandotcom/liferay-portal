import KnownIndividualsListCard from '../hocs/KnownIndividualsListCard';
import React from 'react';
import {Router} from 'shared/types';

const KnownIndividuals: React.FC<{
	router: Router;
}> = ({router}) => (
	<div className='row'>
		<div className='col-sm-12'>
			<KnownIndividualsListCard router={router} />
		</div>
	</div>
);

export default KnownIndividuals;
