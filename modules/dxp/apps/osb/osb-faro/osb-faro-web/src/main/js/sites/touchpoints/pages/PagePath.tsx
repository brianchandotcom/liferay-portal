import PagePathCard from 'sites/touchpoints/components/sankey/PagePathCard';
import React from 'react';

const TouchpointPathPage = ({pathRangeSelectors}) => (
	<div className='row'>
		<div className='analytics-sankey-column col-sm-12'>
			<PagePathCard rangeSelectors={pathRangeSelectors} />
		</div>
	</div>
);

export default TouchpointPathPage;
