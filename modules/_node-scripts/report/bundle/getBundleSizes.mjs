/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fg from 'fast-glob';
import fs from 'fs/promises';
import path from 'path';

import {BUNDLE_REPORTS_PATH} from '../../util/constants.mjs';

export default async function getBundleSizes(projectDirectories) {
	const bundleSizes = await Promise.all(
		projectDirectories.map((projectDir) =>
			getProjectBundleSizes(projectDir)
		)
	);

	return bundleSizes.filter((size) => size !== null);
}

async function getProjectBundleSizes(projectDir) {
	const bundleReportsPath = path.join(projectDir, BUNDLE_REPORTS_PATH);

	const files = await fg('**/*.json', {
		cwd: bundleReportsPath,
	});

	try {
		const jsons = await Promise.all(
			files.map(async (file) => {
				return JSON.parse(
					await fs.readFile(
						path.join(bundleReportsPath, file),
						'utf-8'
					)
				);
			})
		);

		const outputs = jsons.map((json) => json.outputs);

		const sizes = {};

		outputs.forEach((output) => {
			Object.entries(output).forEach(([bundle, data]) => {
				sizes[bundle] = data;
			});
		});

		return {
			projectDir,
			sizes,
		};
	}
	catch (error) {
		if (error.code !== 'ENOENT') {
			throw error;
		}

		return null;
	}
}
