/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import getFlatName from '../../../util/getFlatName.mjs';
import {WORK_EXPORT_PATH} from '../../../util/locations.mjs';

export default function getExportBridgePath(moduleName) {
	return path.resolve(WORK_EXPORT_PATH, getFlatName(moduleName, 'js'));
}
