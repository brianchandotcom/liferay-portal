const TYPE_PREFIX = {
	exports: '../../..',
	main: '../..'
};

export default function getExternals(globalImports, type) {
	const prefix = TYPE_PREFIX[type];

	if (prefix === undefined) {
		throw new Error(`Invalid type: ${type}`);
	}

	const externals = [

		//
		// Use a Set to deduplicate items
		//

		...new Set(
			Object.values(globalImports).map(
				({webContextPath}) => `${prefix}/${webContextPath}/*`
			)
		),
	];

	return externals;
}
