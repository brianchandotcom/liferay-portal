/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import {
	BUILD_CSS_EXPORTS_PATH,
	BUILD_NPM_EXPORTS_PATH,
} from '../../util/constants.mjs';
import getFlatName from '../../util/getFlatName.mjs';
import calculateFileHash from '../util/calculateFileHash.mjs';
import extractFileHash from '../util/extractFileHash.mjs';

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

	const baseFlatModuleName = flatModuleName.substring(
		0,
		flatModuleName.length - 4
	);

	const cssFiles = await fs.readdir(path.join(BUILD_CSS_EXPORTS_PATH));

	const cssFile = cssFiles.find((cssFile) =>
		cssFile.startsWith(`${baseFlatModuleName}.(`)
	);

	const cssFileHash = extractFileHash(cssFile);

	const source = `
const link = document.createElement('link');
link.setAttribute('rel','stylesheet');
link.setAttribute('type','text/css');
link.setAttribute('href', Liferay.ThemeDisplay.getPathContext() + '/o${webContextPath}/__liferay__/css/${baseFlatModuleName}.(${cssFileHash}).css');
if (Liferay.CSP) {
	link.setAttribute('nonce', Liferay.CSP.nonce);
}

document.querySelector('head').appendChild(link);
`;

	const hash = await calculateFileHash(source);

	const cssLoaderPath = path.join(
		BUILD_NPM_EXPORTS_PATH,
		`${flatModuleName}.(${hash}).js`
	);

	await fs.mkdir(path.dirname(cssLoaderPath), {recursive: true});
	await fs.writeFile(cssLoaderPath, source);
}
