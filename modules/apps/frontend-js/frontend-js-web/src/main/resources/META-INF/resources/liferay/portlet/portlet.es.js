import register from './register.es';

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