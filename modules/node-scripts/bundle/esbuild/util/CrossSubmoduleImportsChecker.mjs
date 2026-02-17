/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import fileExists from '../../../util/fileExists.mjs';
import {SRC_PATH} from '../../../util/locations.mjs';

export default class CrossSubmoduleImportsChecker {
	static SOURCE_EXTENSIONS = ['.js', '.jsx', '.ts', '.tsx'];

	/**
	 * A map consisting of submodule dirs as keys and the entry point filename
	 * and submodule's name as values.
	 */
	_submodulesMap = null;

	constructor(projectEntryPoints) {
		if (
			!projectEntryPoints ||
			!projectEntryPoints.submodules ||
			!Object.keys(projectEntryPoints.submodules).length
		) {
			return;
		}

		const submodulesMap = {};

		const mainPath = path.resolve(projectEntryPoints.main);
		const mainDir = path.dirname(mainPath);

		submodulesMap[mainDir] = {
			entryPointFileName: path.relative(mainDir, mainPath),
			submoduleName: 'main',
		};

		for (const [submoduleName, submoduleEntryPoint] of Object.entries(
			projectEntryPoints.submodules
		)) {
			const submodulePath = path.resolve(submoduleEntryPoint);
			const submoduleDir = path.dirname(submodulePath);

			submodulesMap[submoduleDir] = {
				entryPointFileName: path.relative(submoduleDir, submodulePath),
				submoduleName,
			};
		}

		// Sort dirs longest first so that nested submodules are evaluated first
		// to avoid them being interpreted as part of the nester submodule

		const sortedDirs = Object.keys(submodulesMap).sort(
			(a, b) => b.length - a.length
		);

		this._submodulesMap = {};

		for (const dir of sortedDirs) {
			this._submodulesMap[dir] = submodulesMap[dir];
		}
	}

	async check(importer, importPath) {
		if (this._submodulesMap === null) {
			return;
		}

		const importFilePath = await this._findSourceFile(
			path.resolve(path.dirname(importer), importPath)
		);

		if (!importFilePath) {
			return;
		}

		const importerModuleDir = this._getFileModuleDir(importer);
		const importModuleDir = this._getFileModuleDir(importFilePath);

		if (importerModuleDir === undefined || importModuleDir === undefined) {
			console.warn(`
⚠️  WARNING: Import '${importPath}' from file '${path.relative(SRC_PATH, importer)}' references a file outside of any configured project submodule.
   Consider moving that file to a submodule folder to avoid duplications in output JavaScript bundles.
`);
		}
		else if (importerModuleDir !== importModuleDir) {
			const importFileName = path.relative(
				importModuleDir,
				importFilePath
			);
			const {entryPointFileName} = this._submodulesMap[importModuleDir];

			if (importFileName !== entryPointFileName) {
				const correctEntryPointPath = path.join(
					importModuleDir,
					entryPointFileName
				);

				throw new Error(
					`Imports between different submodules can only reference the imported submodule entry point: ` +
						`import '${importPath}' in file '${path.relative(SRC_PATH, importer)}' should reference '${path.relative(SRC_PATH, correctEntryPointPath)}' instead.`
				);
			}

			let {submoduleName} = this._submodulesMap[importModuleDir];

			if (submoduleName === 'main') {
				submoduleName = 'index';
			}

			return submoduleName;
		}
	}

	async _findSourceFile(filePath) {
		if (await fileExists(filePath)) {
			return filePath;
		}

		for (const extension of CrossSubmoduleImportsChecker.SOURCE_EXTENSIONS) {
			const guessFilePath = `${filePath}${extension}`;

			if (await fileExists(guessFilePath)) {
				return guessFilePath;
			}
		}

		return undefined;
	}

	_getFileModuleDir(modulePath) {
		for (const dir of Object.keys(this._submodulesMap)) {
			if (modulePath.startsWith(dir)) {
				return dir;
			}
		}
	}
}
