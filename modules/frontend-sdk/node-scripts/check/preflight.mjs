/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import doPreflight from '../util/preflight/doPreflight.mjs';

export default async function main() {
	const errors = await doPreflight();

	if (errors.length) {
		console.error(`
❌ Preflight check failed:
${errors.map((error) => `   · ${error}`).join('\n')}
`);

		process.exit(1);
	}
}
