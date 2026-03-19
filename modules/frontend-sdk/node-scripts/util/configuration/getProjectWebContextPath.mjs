/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';

export default async function getProjectWebContextPath() {
	const bnd = await fs.readFile('./bnd.bnd', 'utf-8');

	const webContextPathLine = bnd
		.split('\n')
		.find((line) => line.startsWith('Web-ContextPath:'));

	if (!webContextPathLine) {
		throw new Error("Project's bnd.bnd file has no Web-ContextPath entry");
	}

	const parts = webContextPathLine.split(':');

	return parts[1].trim();
}
