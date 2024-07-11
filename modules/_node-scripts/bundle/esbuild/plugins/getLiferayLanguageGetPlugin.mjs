/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import {SRC_PATH} from '../../../util/constants.mjs';

const REGEXP = /Liferay\.Language\.get\(([^)]+)\)/g;

/**
 * This plugin detects usages of `Liferay.Language.get` and injects a dynamic import for
 * `@liferay/language/...` when necessary.
 *
 * This technique is only used for liferay-portal internal code (ie: it is not applied to external
 * npm packages).
 */
export default function getLiferayLanguageGetPlugin(
	projectWebContextPath,
	languageJSON
) {
	return {
		name: 'liferay-language-get-loader-plugin',

		async setup(build) {
			build.onLoad(
				{
					filter: new RegExp(
						`.*\\/${SRC_PATH.split(path.sep)
							.join(path.posix.sep)
							.replaceAll('/', '\\/')}.*\\.[jt]sx?$`
					),
				},
				async (args) => {
					let contents = await fs.readFile(args.path, 'utf-8');

					const matches = contents.matchAll(REGEXP);

					if (matches) {
						contents =
							'await import(`@liferay/language/' +
							'${Liferay.ThemeDisplay.getLanguageId()}' +
							projectWebContextPath +
							'/all.js`);\n' +
							contents;

						for (const match of matches) {
							languageJSON.keys.push(
								match[1]
									.trim()
									.replaceAll("'", '')
									.replaceAll('"', '')
									.replaceAll('`', '')
							);
						}
					}

					let loader = 'jsx';

					if (args.path.endsWith('.ts')) {
						loader = 'ts';
					}
					else if (args.path.endsWith('.tsx')) {
						loader = 'tsx';
					}

					return {
						contents,
						loader,
					};
				}
			);
		},
	};
}
