/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import getFlatName from '../../../util/getFlatName.mjs';
import {BUILD_NPM_EXPORTS_PATH} from '../../../util/locations.mjs';

export default function getCSSExportLoaderModulePath(moduleName, hash) {
	return path.join(
		BUILD_NPM_EXPORTS_PATH,
		getFlatName(moduleName, `(${hash}).js`)
	);
}
