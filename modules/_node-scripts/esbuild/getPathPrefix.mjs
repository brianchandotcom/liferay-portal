const TYPE_PREFIX = {
	exports: '../../..',
	main: '../..'
};

export default function getPathPrefix(type) {
	const prefix = TYPE_PREFIX[type];

	if (prefix === undefined) {
		throw new Error(`Invalid type: ${type}`);
	}

	return prefix;
}
