import BasePage from 'shared/components/base-page';
import React, {useContext} from 'react';
import Sankey from 'sites/touchpoints/components/sankey/Sankey';
import {PropTypes} from 'prop-types';

/**
 * Touchpoint Path page
 * @class
 */
export default function TouchpointPathPage({pathRangeSelectors}) {
	const {filters, router} = useContext(BasePage.Context);

	const {touchpoint} = router.params;

	return (
		<div className='row'>
			<div className='analytics-sankey-column col-sm-12'>
				<Sankey
					filters={filters}
					rangeSelectors={pathRangeSelectors}
					router={router}
					touchpoint={touchpoint}
				/>
			</div>
		</div>
	);
}
TouchpointPathPage.propTypes = {
	pathRangeKey: PropTypes.string
};
