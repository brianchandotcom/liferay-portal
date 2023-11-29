/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Criteria} from '../../types/Criteria';
import getGroup from './getGroup';

export default function getItem(
	criteria: Criteria,
	groupId: Criteria['groupId'],
	index: number
) {
	const group = getGroup(groupId, criteria)!;

	return group.items[index];
}
