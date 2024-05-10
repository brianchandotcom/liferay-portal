import path from 'path';

import getImportBridgePath from '../getImportBridgePath.mjs';

export default function getExactAliasPlugin(globalImports, type, exclusions) {
	const aliases = getAliases(globalImports, type, exclusions);

	return {
		name: 'exact-alias-plugin',

		setup(build) {
			Object.entries(aliases).forEach(
				([moduleName, filePath]) => {
					const regexp = new RegExp(`^${moduleName}$`);

					build.onResolve(
						{
							filter: regexp
						}, 
						args => {
							return {
								path: args.path.replace(regexp, path.resolve(filePath))
							};
						}
					);
				}
			);
		},
	};
}

function getAliases(globalImports, type, exclusions) {
	if (exclusions === undefined) {
		exclusions = [];
	}

	return Object.keys(globalImports)
		.filter(moduleName => !moduleName.endsWith('.css') && !exclusions.includes(moduleName))
		.reduce(
			(aliases, moduleName) => {
				aliases[moduleName] = getImportBridgePath(moduleName, type);

				return aliases;
			},
			{}
		);
}
