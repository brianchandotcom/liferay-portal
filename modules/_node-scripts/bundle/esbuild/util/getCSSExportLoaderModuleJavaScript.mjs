/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import {BUILD_CSS_EXPORTS_PATH} from '../../../util/constants.mjs';
import getFlatName from '../../../util/getFlatName.mjs';
import extractFileHash from '../../util/extractFileHash.mjs';
import getCSSLoadJavaScript from './getCSSLoadJavaScript.mjs';
import getURL, {URLType} from './getURL.mjs';

export default async function getCSSExportLoaderModuleJavaScript(
	urlPrefix,
	webContextPath,
	moduleName
) {
	const flatModuleName = getFlatName(moduleName);

	const baseFlatModuleName = flatModuleName.replace(/\.css$/, '');

	const cssFiles = await fs.readdir(path.join(BUILD_CSS_EXPORTS_PATH));

	const cssFile = cssFiles.find((cssFile) =>
		cssFile.startsWith(`${baseFlatModuleName}.(`)
	);

	if (!cssFile) {
		throw new Error(
			`Cannot generate CSS export loader module JavaScript if the CSS ` +
				`file is not yet generated because its hash is unknown ` +
				`(webContextPath=${webContextPath}, moduleName=${moduleName})`
		);
	}

	const cssFileHash = extractFileHash(cssFile);

	const cssPath = getURL(
		URLType.CSS_EXPORT,
		urlPrefix,
		webContextPath,
		moduleName,
		cssFileHash
	);

	return getCSSLoadJavaScript(cssPath);
}
