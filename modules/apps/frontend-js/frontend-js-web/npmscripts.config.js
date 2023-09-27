/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = {
	bridges: ['lodash.groupby', 'lodash.isequal', 'svg4everybody'],
	build: {
		customBridges: {
			'bridge/frontend-js-web/index':
				'../../../../../frontend-js-web/__liferay__/index.js',
		},
		main: 'src/main/resources/META-INF/resources/index.es.js',
	},
};
