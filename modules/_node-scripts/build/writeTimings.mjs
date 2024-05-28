/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {constants} from 'fs';
import * as fs from 'fs/promises';
import * as path from 'path';

import {getRootDir} from '../util/constants.mjs';

export default async function writeTimings(start, endConfig) {
	const {LIFERAY_NPM_SCRIPTS_TIMING} = process.env;

	if (LIFERAY_NPM_SCRIPTS_TIMING) {
		const end = Date.now();

		const projectDir = path.relative(await getRootDir(), process.cwd());

		const csvFilePath = path.join(
			LIFERAY_NPM_SCRIPTS_TIMING,
			`${projectDir}.csv`
		);

		console.log(`Appending timing information to: ${csvFilePath}`);

		try {
			await fs.access(csvFilePath, constants.F_OK);
		}
		catch (error) {
			if (error.code !== 'ENOENT') {
				throw error;
			}

			await fs.mkdir(path.dirname(csvFilePath), {recursive: true});
			await fs.writeFile(csvFilePath, `label,start,end,delta\n`, 'utf-8');
		}

		await fs.appendFile(
			csvFilePath,
			`node-build,${start},${end},${end - start}\n` +
				`node-build:config,${start},${endConfig},${
					endConfig - start
				}\n` +
				`node-build:build,${endConfig},${end},${end - endConfig}\n`,
			'utf-8'
		);
	}
}
