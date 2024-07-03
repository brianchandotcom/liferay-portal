/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';

import {SRC_LANGUAGE_JSON_PATH} from '../util/constants.mjs';
import fileExists from '../util/fileExists.mjs';

export default async function getLanguageJSON() {
	if (!(await fileExists(SRC_LANGUAGE_JSON_PATH))) {
		return {
			keys: [],
		};
	}

	return JSON.parse(await fs.readFile(SRC_LANGUAGE_JSON_PATH, 'utf-8'));
}
