/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Criteria, CriteriaItem} from '../../types/Criteria';

/**
 * Moves an item from start groupId and index to destination
 * groupId and index. When target is an item, replace
 * will be true, and that item will be replaced with a group.
 * Otherwise, when target is a group, replace will be false.
 */
export default function searchAndUpdateCriteria(
	criteria: Criteria | CriteriaItem,
	startGroupId: Criteria['groupId'],
	startIndex: number,
	destGroupId: Criteria['groupId'],
	destIndex: number,
	addedItem: Criteria | CriteriaItem,
	replace: boolean
): Criteria;
