/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = {
	build: {
		exports: [
			{name: 'classnames', symbols: 'auto'},
			'formik',
			{name: 'prop-types', symbols: 'auto'},
			{name: 'react', symbols: 'auto'},
			{name: 'react-dnd', symbols: 'auto'},
			'react-dnd-html5-backend',
			{name: 'react-dom', symbols: 'auto'},
		],
		main: 'src/main/resources/META-INF/resources/js/index.ts',
	},
};
