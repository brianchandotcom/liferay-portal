/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fg from 'fast-glob';
import fs from 'fs/promises';
import {r2} from 'liferay-theme-tasks';
import path from 'path';

import {BUILD_RESOURCES_PATH, SRC_PATH} from '../../util/constants.mjs';

export default async function processCSSFiles() {
	const cssFiles = await fg(['**/*.css'], {absolute: true, cwd: SRC_PATH});

	if (!cssFiles.length) {
		return;
	}

	await Promise.all(
		cssFiles.map(async (cssFile) => {
			const start = performance.now();

			await processCssFile(cssFile);

			const lapse = performance.now() - start;

			console.log(
				`⌛ Processing of ${cssFile} took: ${(lapse / 1000).toFixed(3)} s`
			);
		})
	);
}

async function processCssFile(filePath) {

	// Compute paths

	const relFilePath = path.relative(SRC_PATH, filePath);

	const outFilePath = path.join(BUILD_RESOURCES_PATH, relFilePath);

	const {dir, ext, name} = path.parse(outFilePath);

	const outRtlFilePath = path.join(dir, `${name}_rtl${ext}`);

	// Read CSS and apply RTL conversion

	const css = await fs.readFile(filePath, 'utf-8');

	const rtlCss = r2.swap(css);

	// Write stuff

	await fs.mkdir(path.dirname(outFilePath), {recursive: true});

	await Promise.all([
		fs.writeFile(outFilePath, css, 'utf-8'),
		fs.writeFile(outRtlFilePath, rtlCss, 'utf-8'),
	]);
}
