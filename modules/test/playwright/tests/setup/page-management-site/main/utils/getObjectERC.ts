/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	OBJECT_ENTITIES,
	PageManagementObjectEntity,
} from '../constants/objects';

export function getObjectERC(entityName: PageManagementObjectEntity) {
	const entity = OBJECT_ENTITIES[entityName];

	return entity.ERC;
}
