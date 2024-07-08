/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as esbuild from 'esbuild';

import getCustomBuildConfig from '../configuration/getCustomBuildConfig.mjs';

export default async function main() {
	const customBuildConfig = await getCustomBuildConfig();

	await Promise.all([
		customBuildConfig.other
			? Promise.resolve(customBuildConfig.other())
			: Promise.resolve(),
		esbuild.build(customBuildConfig.esbuild),
	]);
}
