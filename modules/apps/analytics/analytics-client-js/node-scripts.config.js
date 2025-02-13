/**
 * SPDX-FileCopyrightText: [(c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: [LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const path = require('path');

const esbuild = {
	bundle: true,
	entryPoints: [path.resolve('src', 'index.js')],
	loader: {'.ts': 'ts'},
	minify: true,
	outfile: path.resolve('build', 'analytics-all-min.js'),
	sourcemap: true,
	target: ['es2020'],
};

module.exports = {
	customBuild: {
		esbuild,
	},
};
