/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * This plugin tells esbuild which import paths must be treated as externals.
 */
export default function getExternalsPlugin() {
	return {
		name: 'externals-plugin',

		setup(build) {
			build.onResolve(
				{
					filter: /\.\.\/.*\/__liferay__\/.*/,
				},
				({path}) => ({
					external: true,
					path,
				})
			);
		},
	};
}
