/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import format from '../format/format.mjs';
import {createGlobalConfig} from '../util/createGlobalConfig.mjs';
import {
	GLOBAL_NODE_SCRIPTS_CONFIG_FILE,
	PORTAL_DIR,
} from '../util/locations.mjs';

export default async function main() {
	const config = await createGlobalConfig();

	await fs.writeFile(GLOBAL_NODE_SCRIPTS_CONFIG_FILE, config);

	await format(true, [
		path.relative(PORTAL_DIR, GLOBAL_NODE_SCRIPTS_CONFIG_FILE),
	]);
}
