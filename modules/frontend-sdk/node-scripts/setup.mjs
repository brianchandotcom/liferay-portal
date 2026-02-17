/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {$} from 'execa';
import path from 'path';

import installSassBinary from './bundle/sass/util/installSassBinary.mjs';
import {FRONTEND_SDK_DIR} from './util/locations.mjs';

async function buildPrettierPlugin() {
	const location = path.join(FRONTEND_SDK_DIR, 'prettier-plugin');

	await $({cwd: location})`yarn run build`;
}

export default async function main() {
	await installSassBinary();
	await buildPrettierPlugin();
}
