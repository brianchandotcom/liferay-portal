/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = {
	build: {
		bundler: {
			exclude: {
				'babel-runtime': true,
				'iconv-lite': true,
				'isomorphic-fetch': true,
				'lodash-es': true,
				'loose-envify': true,
				'node-fetch': true,
				'promise': true,
				'react': ['umd/**/*'],
				'react-dnd': ['dist/**/*'],
				'react-dnd-html5-backend': ['dist/**/*'],
				'react-dom': [
					'**/*profiling*',
					'**/*server*',
					'**/*test*',
					'**/*unstable*',
					'umd/**/*',
				],
				'react-is': ['umd/**/*'],
				'react-modal': ['dist/**/*'],
				'react-select': ['dist/**/*', 'src/**/*'],
				'react-tabs': ['dist/**/*', 'esm/**/*', 'src/**/*'],
				'recompose': [
					'dist/Recompose.esm.js',
					'dist/Recompose.min.js',
					'dist/Recompose.umd.js',
				],
				'redux': ['lib/**/*', 'es/**/*', 'src/**/*'],
				'regenerator-runtime': true,
				'scheduler': ['umd/**/*'],
				'tiny-warning': ['src/**/*'],
				'whatwg-fetch': true,
			},
		},
	},
};
