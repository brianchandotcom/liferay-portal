'use strict';

import register from './portlet_hub/register.es';

/**
 * Returns the page render state initial data.
 */

const getInitData = function() {
	return {
		encodedCurrentURL: '',
		portlets: {},
		prpMap: {}
	};
};

export {
	getInitData,
	register
};