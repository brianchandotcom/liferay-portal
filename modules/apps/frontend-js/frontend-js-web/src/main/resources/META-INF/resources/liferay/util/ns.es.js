import memoize from 'lodash.memoize';

const cached = fn => {
	return memoize(fn, (...args) => {
		return args.length > 1
			? Array.prototype.join.call(args, '_')
			: String(args[0]);
	});
};

const nsCached = cached((namespace, str) => {
	if (typeof str !== 'undefined' && !(str.lastIndexOf(namespace, 0) === 0)) {
		str = `${namespace}${str}`;
	}

	return str;
});

export default function(namespace, obj) {
	let value;

	if (typeof obj !== 'object') {
		value = nsCached(namespace, obj);
	} else {
		value = {};

		const keys = Object.keys(obj);

		keys.forEach(item => {
			const originalItem = item;

			item = nsCached(namespace, item);
			value[item] = obj[originalItem];
		});
	}

	return value;
}
