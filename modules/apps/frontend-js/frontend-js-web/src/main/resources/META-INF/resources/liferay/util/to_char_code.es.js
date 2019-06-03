import memoize from 'lodash.memoize';

const toCharCode = memoize(name =>
	name
		.split('')
		.map(val => val.charCodeAt())
		.join('')
);

export default toCharCode;
