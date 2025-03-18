/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import fileExists from '../../../util/fileExists.mjs';
import getImportBridgePath from '../getImportBridgePath.mjs';

const SOURCE_EXTENSIONS = ['.js', '.jsx', '.ts', '.tsx'];

/**
 * This plugin is needed to map in-project relative paths that refer to entry points to their
 * runtime counterparts, ie: to the URL of the bundle where those local files end up living (as if
 * they were externals).
 */
export default function getRuntimeLinkerPlugin(
	mainEntryPoint,
	projectDescription,
	submodules
) {

	// Create a map of project relative file paths to entry point names

	const map = {};

	map[path.posix.relative('.', path.resolve(mainEntryPoint))] = '';

	for (const [submodule, submoduleEntryPoint] of Object.entries(submodules)) {
		map[path.posix.relative('.', path.resolve(submoduleEntryPoint))] =
			`/${submodule}`;
	}

	// Return the plugin implementation

	return {
		name: 'runtime-linker-plugin',

		setup(build) {
			build.onResolve(
				{
					filter: /\.\/.*/,
				},
				async ({path: importPath, resolveDir}) => {
					const filePath = await findSourceFile(
						path.resolve(resolveDir, importPath)
					);

					if (!filePath) {
						return undefined;
					}

					const fileRelPath = path.posix.relative('.', filePath);

					const mapPath = map[fileRelPath];

					if (mapPath === undefined) {
						return undefined;
					}

					const moduleName = `${projectDescription.name}${mapPath}`;

					return {
						path: getImportBridgePath(moduleName, 'main'),
					};
				}
			);
		},
	};
}

async function findSourceFile(filePath) {
	if (await fileExists(filePath)) {
		return filePath;
	}

	for (const extension of SOURCE_EXTENSIONS) {
		const guessFilePath = `${filePath}${extension}`;

		if (await fileExists(guessFilePath)) {
			return guessFilePath;
		}
	}

	return undefined;
}
