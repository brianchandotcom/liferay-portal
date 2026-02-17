/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {$} from 'execa';
import fs from 'fs/promises';
import sass from 'sass';

import installSassBinary from './installSassBinary.mjs';

const SASS_BINARY_PATH = await installSassBinary();

export default async function runSass(filePath, includePaths, outFilePath) {
	const outputStyle =
		process.env.NODE_ENV === 'production' ? 'compressed' : 'expanded';

	if (SASS_BINARY_PATH) {
		await $(
			SASS_BINARY_PATH,
			[
				filePath,
				outFilePath,
				...includePaths.map(
					(includePath) => `--load-path=${includePath}`
				),
				`--style=${outputStyle}`,
				'--source-map',
			],
			{
				stdio: 'inherit',
			}
		);

		const [css, map] = await Promise.all([
			fs.readFile(outFilePath, 'utf-8'),
			fs.readFile(`${outFilePath}.map`, 'utf-8'),
		]);

		return {
			css,
			map,
		};
	}
	else {
		return new Promise((resolve, reject) => {
			sass.render(
				{
					file: filePath,
					includePaths,
					outFile: outFilePath,
					outputStyle,
					sourceMap: true,
				},
				(error, result) => {
					if (error) {
						return reject(error);
					}

					resolve({
						css: result.css.toString(),
						map: result.map.toString(),
					});
				}
			);
		});
	}
}
