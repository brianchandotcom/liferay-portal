/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import {BUILD_RESOURCES_PATH} from '../../util/constants.mjs';
import hashPathForVariable from './util/hashPathForVariable.mjs';

export default async function writeMainBridge(
	projectDescription,
	projectEntryPoints,
	projectWebContextPath
) {
	if (!projectEntryPoints.main) {
		return;
	}

	const {name, version} = projectDescription;

	//
	// Compute the relative position of the bridge related to the real ES
	// module.
	//
	// Note that AMD modules are server under `/o/js/resolved-module/...`
	// whereas ESM are under `/o/my-context-path/__liferay__/exports/...`.
	//
	// Also, depending for npm-scoped scoped packages, and additional folder
	// level appears under `/o/js/resolved-module/...`.
	//

	const importPath =
		getPathToRoot(name) + projectWebContextPath + '/__liferay__/index.js';

	const hashedImportPath = hashPathForVariable(importPath);

	const code = `
import * as ${hashedImportPath} from "${importPath}";

Liferay.Loader.define(
	"${name}@${version}/index",
	['module'],
	function (module) {
		module.exports = ${hashedImportPath};
	}
);
`;

	// Write file

	const filePath = path.join(BUILD_RESOURCES_PATH, 'index.js');

	await fs.mkdir(path.dirname(filePath), {recursive: true});
	await fs.writeFile(filePath, code, 'utf-8');
}

function getPathToRoot(projectName) {
	return projectName.startsWith('@') ? '../../../..' : '../../..';
}
