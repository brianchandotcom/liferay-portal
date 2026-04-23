import React from 'react';
import Touchpoints from 'sites/hocs/Touchpoints';
import {Router} from 'shared/types';

const TouchpointsPage = ({router}: {router: Router}) => (
	<div className='sites-dashboard-touchpoints-list-root'>
		<div className='row'>
			<div className='col-xl-12'>
				<Touchpoints router={router} />
			</div>
		</div>
	</div>
);

export default TouchpointsPage;
