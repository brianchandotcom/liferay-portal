/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import getFlatName from '../../../util/getFlatName.mjs';
import getPathPrefix from '../getPathPrefix.mjs';

/**
 * Emit an `import from` statement referencing the CSS export loader module whenever an import for
 * a CSS file appears while esbuild is bundling.
 *
 * The CSS export loader module will insert a link to the actual CSS into the HTML at runtime.
 */
export default function getCssLoaderPlugin(globalImports, type) {
	return {
		name: 'css-loader-plugin',

		setup(build) {
			build.onResolve(
				{
					filter: /\.css$/,
				},
				(args) => ({
					path:
						path.sep === '/'
							? `/$/css/${args.path}`
							: `C:\\$\\css\\${args.path}`,
				})
			);

			build.onLoad(
				{
					filter:
						path.sep === '/'
							? /\/\$\/css\/.*$/
							: /.?.?\\\$\\css\\.*$/,
				},
				async (args) => {
					const loadPath = args.path.replace(
						path.sep === '/' ? '/$/css/' : /.?.?\\\$\\css\\/,
						''
					);

					if (!globalImports[loadPath]) {
						throw new Error(
							`Cannot rewrite CSS import: ${loadPath}`
						);
					}

					const {webContextPath} = globalImports[loadPath];

					const contents = `
import '${getPathPrefix(
						type
					)}/${webContextPath}/__liferay__/exports/${getFlatName(
						loadPath
					)}.js';
`;

					return {
						contents,
						loader: 'js',
					};
				}
			);
		},
	};
}
