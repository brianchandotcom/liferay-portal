/**
 * SPDX-FileCopyrightText: [(c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: [LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = {
	main: 'src/main/resources/META-INF/resources/index.es.js',
	npmscripts: {
		bridges: ['lodash.groupby', 'lodash.isequal', 'svg4everybody'],
	},
	typescript: {
		main: 'src/main/resources/META-INF/resources/index.d.ts',
	},
};
