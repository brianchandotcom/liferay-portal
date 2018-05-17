import register from './register.es';

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

export default {
	getInitData,
	register
};