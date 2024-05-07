import React from 'react';
import Touchpoints from 'sites/hocs/Touchpoints';

const TouchpointsPage = ({router}) => (
	<div className='sites-dashboard-touchpoints-list-root'>
		<div className='row'>
			<div className='col-xl-12'>
				<Touchpoints router={router} />
			</div>
		</div>
	</div>
);

export default TouchpointsPage;
