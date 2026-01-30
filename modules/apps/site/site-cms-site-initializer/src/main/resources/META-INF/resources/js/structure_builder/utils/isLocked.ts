/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {StructureChild} from '../types/Structure';
import isField from './isField';

export default function isLocked(child: StructureChild) {
	return isField(child) && child.locked;
}
