/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';

export default async function relocateSourcemap(
	filePath,
	projectWebContextPath
) {
	const sourcemap = JSON.parse(await fs.readFile(filePath, 'utf-8'));

	const prefix = `/sourcemap${projectWebContextPath}`;

	for (let i = 0; i < sourcemap.sources.length; i++) {
		sourcemap.sources[i] = sourcemap.sources[i]
			.replace(regexp('/node_modules/'), `${prefix}/node_modules/`)
			.replace(
				regexp('/node-scripts/export/'),
				`${prefix}/generated/export/`
			)
			.replace(
				regexp('/\\$/bridge/for/'),
				`${prefix}/generated/bridge/for/`
			)
			.replace(
				regexp('/src/main/resources/META-INF/resources/'),
				`${prefix}/src/`
			);
	}

	await fs.writeFile(filePath, JSON.stringify(sourcemap), 'utf-8');
}

function regexp(pathPrefix) {
	return new RegExp(`[./]*${pathPrefix}`);
}
