/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Criteria} from '../../types/Criteria';

export default function getGroup(
	groupId: Criteria['groupId'],
	criteria: Criteria
): Criteria | undefined {
	if (criteria.groupId === groupId) {
		return criteria;
	}

	for (const child of criteria.items) {
		if ('groupId' in child) {
			const group = getGroup(groupId, child);

			if (group) {
				return group;
			}
		}
	}
}
