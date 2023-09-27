/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = {
	liferay: {
		excludes: {
			// eslint-disable-next-line @liferay/portal/no-explicit-extend
			presets: ['@babel/preset-react'],
		},
	},
	plugins: [
		[
			'incremental-dom',
			{
				components: true,
				namespaceAttributes: true,
				prefix: 'IncrementalDOM',
				runtime: 'iDOMHelpers',
			},
		],
	],
	presets: [
		[
			'@babel/preset-env',
			{
				targets: 'defaults',
			},
		],
	],
};
