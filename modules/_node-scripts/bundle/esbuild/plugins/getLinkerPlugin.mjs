/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import {
	BUILD_SASS_CACHE_PATH,
	SRC_PATH,
	WORK_IMPORT_PATH,
} from '../../../util/constants.mjs';
import fileExists from '../../../util/fileExists.mjs';
import getFlatName from '../../../util/getFlatName.mjs';
import extractFileHash from '../../util/extractFileHash.mjs';
import CrossSubmoduleImportsChecker from '../util/CrossSubmoduleImportsChecker.mjs';
import getCSSLoadJavaScript from '../util/getCSSLoadJavaScript.mjs';
import getImportBridgeJavaScript from '../util/getImportBridgeJavaScript.mjs';
import getImportBridgePath from '../util/getImportBridgePath.mjs';
import getURL, {URLType} from '../util/getURL.mjs';

//
// CAVEATS FOR FUTURE DEVELOPERS:
//
// 1. Code generated via onLoad cannot rely on onResolve above, all generated
// imports must be already resolved because it looks like esbuild does not
// recursively apply onResolve/onLoad chains.
//
// That's one of the reasons why export bridges are written directly to disk
// instead of generating them on-the-fly, since they need esbuild resolution to
// happen for them to work.
//
// 2. Using resolve() resolutions inside onResolve (instead of returning {}) is
// not a good idea because it uses Node's resolution algorith, which may load
// server side versions of polymorphic libraries or even fail to resolve Node
// built-in packages.
//

/**
 * This plugin:
 *
 * 1. Configures how esbuild resolves import paths.
 * 2. Provides import bridge modules that help linking stuff together.
 * 3. Provides CSS load stubs that makes importing SCSS files possible.
 * 4. Checks local imports for cross-submodule references and links them.
 */
export default function getLinkerPlugin(
	globalImports,
	overridenPackageSymbols,
	projectWebContextPath,
	moduleName,
	projectEntryPoints
) {
	const urlPrefix = moduleName === 'main' ? '../..' : '../../..';

	const crossSubmoduleImportsChecker = new CrossSubmoduleImportsChecker(
		projectEntryPoints
	);

	return {
		name: 'linker-plugin',

		setup(build) {

			//
			// Import URL resolutions
			//

			build.onResolve(
				{
					filter: /.*/,
				},
				async (info) => {
					const {importer, kind, path} = info;

					// Resolve exported module locally inside export briges so
					// that it is bundled instead of linked.

					if (moduleName !== 'main' && path === moduleName) {
						return {};
					}

					// Leave DXP runtime URLs untouched as they are trusted to
					// be already correct.

					if (path.includes('/__liferay__/')) {
						return {
							external: true,
							path,
						};
					}

					// Pass resolution of non global imports to esbuild

					const globalImport = globalImports[path];

					if (!globalImport) {

						// For real imports check if they cross submodule
						// boundaries

						if (kind === 'import-statement') {
							const crossSubmoduleName =
								await crossSubmoduleImportsChecker.check(
									importer,
									path
								);

							// If the import crosses boundaries, link it to the
							// correct submodule runtime URL

							if (crossSubmoduleName) {
								return {
									external: true,
									path: getURL(
										URLType.PROJECT_SUBMODULE_EXPORT,
										urlPrefix,
										projectWebContextPath,
										crossSubmoduleName
									),
								};
							}
						}

						// Pass resolution of non global imports that don't
						// cross submodule boundaries to esbuild

						return {};
					}

					// Resolve existing global imports

					const {external, submodule, webContextPath} = globalImport;

					if (path.endsWith('.css')) {

						// For CSS files use external URLs pointing to CSS
						// export loader modules.

						return {
							external: true,
							path: getURL(
								URLType.CSS_EXPORT_LOADER_MODULE,
								urlPrefix,
								webContextPath,
								path
							),
						};
					}
					else if (external) {

						// For externals use local import bridges that are
						// generated on-the-fly (see below).

						return {
							path: getImportBridgePath(path),
						};
					}
					else if (submodule) {

						// For project submodule exports use external URLs

						// Get the final part of the imported path, removing
						// '@liferay' scope if it exists.
						//
						// For instance, given:
						//    '@liferay/frontend-js-state-web/react'
						// Set `submodulePath` to:
						//    'react'

						const submodulePath = path
							.split('/')
							.slice(path.startsWith('@') ? 2 : 1)
							.join('/');

						return {
							external: true,
							path: getURL(
								URLType.PROJECT_SUBMODULE_EXPORT,
								urlPrefix,
								webContextPath,
								submodulePath
							),
						};
					}

					// For project default exports use external URLs

					return {
						external: true,
						path: getURL(
							URLType.PROJECT_DEFAULT_EXPORT,
							urlPrefix,
							webContextPath
						),
					};
				}
			);

			//
			// Import bridges on-the-fly instantiation and caching.
			//
			// This is done lazily (as opposed to pre-generation of export
			// bridges, for example) because otherwise the build would take too
			// long to run since all bridges would need to be written for all
			// build operations.
			//

			build.onLoad(
				{
					filter: new RegExp(`.*/${WORK_IMPORT_PATH}/.*`),
				},
				async (info) => {
					const {path: filePath} = info;

					const importModuleName = getFlatName.reverse(
						path.basename(filePath),
						'js'
					);

					const {webContextPath} = globalImports[importModuleName];

					const contents = await getImportBridgeJavaScript(
						overridenPackageSymbols,
						urlPrefix,
						webContextPath,
						importModuleName
					);

					if (!(await fileExists(filePath))) {
						await fs.mkdir(path.dirname(filePath), {
							recursive: true,
						});
						await fs.writeFile(filePath, contents, 'utf-8');
					}

					return {
						contents,
						loader: 'js',
					};
				}
			);

			//
			// SCSS imports JavaScript stub generator.
			//
			// For SCSS imports we emit inline JavaScript code similar to the
			// one found in CSS export loader modules.
			//

			build.onLoad(
				{
					filter: /\.scss$/,
				},
				async ({path: filePath}) => {
					const projectRelativeFilePath = path.relative(
						SRC_PATH,
						filePath
					);

					const projectRelativeBaseNamePath =
						projectRelativeFilePath.replace(/\.scss$/, '');

					const cssFiles = await fs.readdir(
						path.join(
							BUILD_SASS_CACHE_PATH,
							path.dirname(projectRelativeFilePath)
						)
					);

					const cssBasename = cssFiles.find((cssFile) =>
						cssFile.startsWith(
							`${path.basename(projectRelativeBaseNamePath)}.(`
						)
					);

					const hash = extractFileHash(cssBasename);

					const basePath = projectRelativeFilePath
						.split(path.sep)
						.join(path.posix.sep)
						.replace(/\.scss$/, '');

					return {
						contents: getCSSLoadJavaScript(
							getURL(
								URLType.SASS_CSS_FILE,
								urlPrefix,
								projectWebContextPath,
								`${basePath}.css`,
								hash
							),
							getURL(
								URLType.SASS_CSS_FILE,
								urlPrefix,
								projectWebContextPath,
								`${basePath}_rtl.css`,
								hash
							)
						),
						loader: 'js',
					};
				}
			);
		},
	};
}
