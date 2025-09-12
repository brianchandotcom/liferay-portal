/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import {BUILD_NPM_EXPORTS_PATH} from '../../util/constants.mjs';
import getFlatName from '../../util/getFlatName.mjs';
import getCSSLoadJavaScript from '../util/getCSSLoadJavaScript.mjs';

export default async function writeCSSExportsLoaderModules(
	projectExports,
	projectWebContextPath
) {
	if (!projectExports) {
		return;
	}

	await Promise.all(
		projectExports
			.filter((moduleName) => moduleName.endsWith('.css'))
			.map((moduleName) =>
				writeCSSExportLoaderModule(projectWebContextPath, moduleName)
			)
	);
}

async function writeCSSExportLoaderModule(webContextPath, moduleName) {
	const flatModuleName = getFlatName(moduleName);

	const cssLoaderPath = path.join(
		BUILD_NPM_EXPORTS_PATH,
		`${flatModuleName}.js`
	);

	await fs.mkdir(path.dirname(cssLoaderPath), {recursive: true});
	await fs.writeFile(
		cssLoaderPath,
		getCSSLoadJavaScript(
			webContextPath,
			`__liferay__/css/${flatModuleName}`
		)
	);
}
