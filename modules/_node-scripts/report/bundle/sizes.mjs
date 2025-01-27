/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import {
	BUILD_RESOURCES_PATH,
	SRC_PATH,
	getRootDir,
} from '../../util/constants.mjs';
import getNamedArguments from '../../util/getNamedArguments.mjs';
import getYarnWorkspaceProjects from '../../util/getYarnWorkspaceProjects.mjs';
import launchSoffice from '../../util/launchSoffice.mjs';
import getBundleSizes from './getBundleSizes.mjs';

export default async function main() {
	const {withInternals} = getNamedArguments({
		withInternals: '--with-internals',
	});

	const [projectDirectories, rootDir] = await Promise.all([
		getYarnWorkspaceProjects(),
		getRootDir(),
	]);

	const bundleSizes = await getBundleSizes(projectDirectories);

	let csvFile;
	let lines;

	if (withInternals) {
		csvFile = 'bundle-sizes-with-internals.csv';
		lines = ['BUNDLE;GZIP;SIZE'];

		const internalBundleSizes = getInternalBundleSizes(bundleSizes);

		Object.entries(internalBundleSizes)
			.sort(([a], [b]) => a.localeCompare(b))
			.forEach(([bundle, {gzip, uncompressed}]) => {
				const bundlePath = path
					.relative(rootDir, bundle)
					.replace(`${path.sep}${BUILD_RESOURCES_PATH}`, '')
					.replace(SRC_PATH, '');

				lines.push(`${bundlePath};${gzip};${uncompressed}`);
			});
	}
	else {
		csvFile = 'bundle-sizes.csv';
		lines = ['BUNDLE;GZIP;SIZE'];

		const totalBundleSizes = getTotalBundleSizes(bundleSizes);

		Object.entries(totalBundleSizes)
			.sort(([a], [b]) => a.localeCompare(b))
			.forEach(([bundle, {gzip, uncompressed}]) => {
				const bundlePath = path
					.relative(rootDir, bundle)
					.replace(`${path.sep}${BUILD_RESOURCES_PATH}`, '');

				lines.push(`${bundlePath};${gzip};${uncompressed}`);
			});
	}

	await fs.writeFile(csvFile, lines.join('\n'));

	await launchSoffice(csvFile);
}

function getInternalBundleSizes(bundleSizes) {
	const internalBundleSizes = {};

	bundleSizes.forEach((stat) => {
		const {projectDir, sizes} = stat;

		Object.entries(sizes).forEach(([bundle, {inputs}]) => {
			Object.entries(inputs).forEach(
				([objectPath, {gzip, uncompressed}]) => {
					if (
						objectPath.includes('/$/') ||
						objectPath.includes('[unassigned]')
					) {
						return;
					}

					internalBundleSizes[
						`${path.join(projectDir, bundle)}:${objectPath}`
					] = {gzip, uncompressed};
				}
			);
		});
	});

	return internalBundleSizes;
}

function getTotalBundleSizes(bundleSizes) {
	const totalBundleSizes = {};

	bundleSizes.forEach((stat) => {
		const {projectDir, sizes} = stat;

		Object.entries(sizes).forEach(([bundle, {gzip, uncompressed}]) => {
			totalBundleSizes[path.join(projectDir, bundle)] = {
				gzip,
				uncompressed,
			};
		});
	});

	return totalBundleSizes;
}
