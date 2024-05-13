import crypto from 'crypto';
import fs from 'fs/promises';
import path from 'path';

import {BUILD_RESOURCES_PATH} from '../util/constants.mjs';

export default async function writeAMD2ESMBridges(projectDescription, projectWebContextPath) {
	const filePath = path.join(BUILD_RESOURCES_PATH, 'index.js');

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

	const rootDir = name.startsWith('@') ? '../../../..' : '../../..';

	const importPath = `${rootDir}${projectWebContextPath}/__liferay__/index.js`;

	const hashedModulePath = hashPathForVariable(importPath);

	const code = `
import * as ${hashedModulePath} from "${importPath}";

Liferay.Loader.define(
	"${name}@${version}/index",
	['module'], 
	function (module) {
		module.exports = ${hashedModulePath};
	}
);
`;

	await fs.mkdir(path.dirname(filePath), {recursive: true});
	await fs.writeFile(filePath, code, 'utf-8');
}

function hashPathForVariable(filePath) {
	const normalizedFilePath = filePath.split(path.sep).join('');

	// Prefixing the string with 'a' since variables can't start with an integer

	return (
		'a' +
		crypto.createHash('sha256').update(normalizedFilePath).digest('hex')
	);
}
