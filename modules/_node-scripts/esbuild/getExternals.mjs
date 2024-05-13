import getPathPrefix from "./getPathPrefix.mjs";

export default function getExternals(globalImports, type) {
	const prefix = getPathPrefix(type);

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
