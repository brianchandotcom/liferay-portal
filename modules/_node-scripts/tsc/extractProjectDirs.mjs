/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getFileProjectDir from '../util/getFileProjectDir.mjs';

export default async function extractProjectDirs(modifiedFiles) {
	modifiedFiles = modifiedFiles.filter(
		(file) => file.endsWith('.ts') || file.endsWith('.tsx')
	);
	modifiedFiles = modifiedFiles.filter((file) => file.startsWith('modules/'));
	modifiedFiles = modifiedFiles.map((file) => file.substring(8));
	modifiedFiles = modifiedFiles.filter(
		(file) => file.startsWith('apps/') || file.startsWith('dxp/')
	);

	const projectDirs = new Set();

	for (const file of modifiedFiles) {
		projectDirs.add(await getFileProjectDir(file));
	}

	return [...projectDirs];
}
