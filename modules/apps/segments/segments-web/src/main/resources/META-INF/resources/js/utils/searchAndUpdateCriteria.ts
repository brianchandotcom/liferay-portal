/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Criteria, CriteriaItem} from '../../types/Criteria';
import {insertAtIndex, removeAtIndex, replaceAtIndex} from './utils';

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
): Criteria {
	let nextItems = 'items' in criteria ? criteria.items : [];

	if ('groupId' in criteria && criteria.groupId === destGroupId) {
		nextItems = replace
			? replaceAtIndex(addedItem, nextItems, destIndex)
			: insertAtIndex(addedItem, nextItems, destIndex);
	}

	if ('groupId' in criteria && criteria.groupId === startGroupId) {
		nextItems = removeAtIndex(
			nextItems,
			destGroupId === startGroupId && destIndex < startIndex && !replace
				? startIndex + 1
				: startIndex
		);
	}

	return {
		...criteria,
		items: nextItems.map((criteriaItem) => {
			return isGroupItem(criteriaItem)
				? searchAndUpdateCriteria(
						criteriaItem,
						startGroupId,
						startIndex,
						destGroupId,
						destIndex,
						addedItem,
						replace
					)
				: criteriaItem;
		}),
	} as Criteria;
}

function isGroupItem(item: Criteria | CriteriaItem) {
	return 'groupId' in item;
}
