/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import {SRC_PATH} from '../../../util/constants.mjs';

/**
 * This plugin transforms `import from` statements for .scss files into JavaScript code that inserts
 * a link to the actual file in the HTML at rutime.
 *
 * This technique is only used for liferay-portal internal code (ie: it is not applied to external
 * npm packages).
 */
export default function getScssLoaderPlugin(projectWebContextPath) {
	return {
		name: 'scss-loader-plugin',

		setup(build) {
			build.onLoad(
				{
					filter: /\.scss$/,
				},
				async (args) => {
					const projectPath = path.relative(SRC_PATH, args.path);

					const cssPath = projectPath
						.split(path.sep)
						.join(path.posix.sep)
						.replace(/scss$/, 'css');

					const contents = `
const link = document.createElement('link');
link.setAttribute('rel', 'stylesheet');
link.setAttribute('type', 'text/css');
link.setAttribute(
	'href', 
	Liferay.ThemeDisplay.getPathContext() + '/o${projectWebContextPath}/${cssPath}'
);
if (Liferay.CSP) {
	link.setAttribute('nonce', Liferay.CSP.nonce);
}
document.querySelector('head').appendChild(link);
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
