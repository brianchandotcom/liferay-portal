/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';
import prettier from 'prettier';

import {MODULES_DIR} from '../../locations.mjs';

const {default: PRETTIER_CONFIG} = await import(
	'file://' + path.join(MODULES_DIR, '.prettierrc.js')
);

export default function formatWithPrettier(
	input,
	filePath,
	overrideConfig = {}
) {

	// NOTE: Overriding the base configuration is not supported because we've
	// never needed. If we wanted to support it in the future we would need to
	// manually load cascaded configuration files from here based on file path.

	return prettier.format(input, {
		...PRETTIER_CONFIG,
		...overrideConfig,
		filepath: filePath,
	});
}
