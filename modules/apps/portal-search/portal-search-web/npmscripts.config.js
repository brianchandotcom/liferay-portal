/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = {
	build: {
		bundler: {
			ignore: [
				'**/config.js',
				'**/custom_filter.js',
				'**/facet_util.js',
				'**/modified_facet_configuration.js',
				'**/modified_facet.js',
				'**/sort_configuration.js',
				'**/sort_util.js',
			],
		},
	},
};
