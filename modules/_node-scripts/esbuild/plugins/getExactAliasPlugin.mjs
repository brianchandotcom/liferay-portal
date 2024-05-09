/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import getImportBridgePath from '../getImportBridgePath.mjs';

/**
 * This plugin is needed to prevent esbuild from interpreting aliases as prefixes (the OOTB
 * behavior). Instead this plugin treats each alias as a specific complete path.
 */
export default function getExactAliasPlugin(globalImports, type, exclusions) {
	const aliases = getAliases(globalImports, type, exclusions);

	return {
		name: 'exact-alias-plugin',

		setup(build) {
			Object.entries(aliases).forEach(([moduleName, filePath]) => {
				const regexp = new RegExp(`^${moduleName}$`);

				build.onResolve(
					{
						filter: regexp,
					},
					(args) => {
						return {
							path: args.path.replace(
								regexp,
								path.resolve(filePath)
							),
						};
					}
				);
			});
		},
	};
}

function getAliases(globalImports, type, exclusions) {
	if (exclusions === undefined) {
		exclusions = [];
	}

	return Object.keys(globalImports)
		.filter(
			(moduleName) =>
				!moduleName.endsWith('.css') && !exclusions.includes(moduleName)
		)
		.reduce((aliases, moduleName) => {
			aliases[moduleName] = getImportBridgePath(moduleName, type);

			return aliases;
		}, {});
}
