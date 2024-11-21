/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// eslint-disable-next-line @liferay/no-extraneous-dependencies
const {polyfillNode} = require('esbuild-plugin-polyfill-node');
const fs = require('fs/promises');
const path = require('path');

const outdir = path.resolve('build', 'node', 'packageRunBuild', 'resources');

module.exports = {
	customBuild: {
		esbuild: {
			bundle: true,
			entryNames: 'headless-discovery-web-min',
			entryPoints: [path.resolve('src', 'index.js')],
			loader: {
				'.js': 'jsx',
			},
			outdir,
			plugins: [polyfillNode({})],
			sourcemap: true,
			target: ['es2020'],
		},
		other: async () => {
			await fs.mkdir(outdir, {recursive: true});

			await Promise.all([
				fs.copyFile(
					path.resolve('src', 'index.html'),
					path.resolve(outdir, 'index.html')
				),
				fs.copyFile(
					path.resolve('src', 'css', 'main.css'),
					path.resolve(outdir, 'main.css')
				),
			]);
		},
	},
};
