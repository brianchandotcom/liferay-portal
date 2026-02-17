/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as esbuild from 'esbuild';

import getCustomBuildConfig from '../configuration/getCustomBuildConfig.mjs';
import getNamedArguments from '../util/getNamedArguments.mjs';

const DEFAULT_PORT = 8081;

export default async function main() {
	const {serve} = getNamedArguments({
		serve: '--serve',
	});

	const customBuildConfig = await getCustomBuildConfig();

	if (serve) {
		const ctx = await esbuild.context({
			...customBuildConfig.esbuild,
		});

		await ctx.watch();

		const {port} = await ctx.serve({
			port: DEFAULT_PORT,
		});

		console.log(`Bundle served at 'localhost:${port}'`);
	}
	else {
		await Promise.all([
			customBuildConfig.other
				? Promise.resolve(customBuildConfig.other())
				: Promise.resolve(),
			esbuild.build({
				minify: process.env.NODE_ENV === 'production',
				...customBuildConfig.esbuild,
			}),
		]);
	}
}
