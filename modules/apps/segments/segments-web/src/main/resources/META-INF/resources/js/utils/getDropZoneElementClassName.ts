/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Criteria} from '../../types/Criteria';
import {Position} from '../contexts/KeyboardMovementContext';

export default function getDropZoneElementClassname(
	propertyKey: string,
	groupId: Criteria['groupId'],
	index: number,
	position: Position
) {
	return `drop-zone-${propertyKey}-${groupId}-${index}-${position}`;
}
