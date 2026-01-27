/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import {WORK_IMPORT_PATH} from '../../../util/constants.mjs';
import getFlatName from '../../../util/getFlatName.mjs';

export default function getImportBridgePath(moduleName) {
	return path.resolve(WORK_IMPORT_PATH, getFlatName(moduleName, 'js'));
}
