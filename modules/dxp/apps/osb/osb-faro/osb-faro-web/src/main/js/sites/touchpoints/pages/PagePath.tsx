import PagePathCard from 'sites/touchpoints/components/sankey/PagePathCard';
import React from 'react';

const TouchpointPathPage = props => (
	<div className='row'>
		<div className='col-sm-12'>
			<PagePathCard {...props} />
		</div>
	</div>
);

export default TouchpointPathPage;
