/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Criteria, CriteriaItem} from '../../types/Criteria';
import {ACTION_TYPES} from '../components/keyboard_movement/KeyboardMovementManager';
import {POSITIONS, Source, Target} from '../contexts/KeyboardMovementContext';
import cleanCriteria from './cleanCriteria';
import getItem from './getItem';
import searchAndUpdateCriteria from './searchAndUpdateCriteria';
import {getSupportedOperatorsFromType, insertAtIndex} from './utils';

export default function dropInGroup(
	criteria: Criteria,
	source: Source,
	target: Target
) {
	const actionType = source.groupId ? ACTION_TYPES.move : ACTION_TYPES.add;

	const operators = getSupportedOperatorsFromType(source.type);

	const item =
		actionType === ACTION_TYPES.move
			? getItem(criteria, source.groupId!, source.index!)
			: {
					operatorName: operators[0].name,
					propertyName: source.propertyName,
					value: source.defaultValue,
				};

	const targetIndex =
		target.position === POSITIONS.bottom ? target.index + 1 : target.index;

	let nextCriteria;

	if (actionType === ACTION_TYPES.move) {
		nextCriteria = searchAndUpdateCriteria(
			criteria,
			source.groupId!,
			source.index!,
			target.groupId,
			targetIndex,
			item,
			false
		);
	}
	else {
		nextCriteria = insertItem(
			criteria,
			target.groupId,
			targetIndex,
			item as CriteriaItem
		);
	}

	return cleanCriteria(nextCriteria) as Criteria;
}

function insertItem(
	criteria: Criteria | CriteriaItem,
	targetGroupId: Criteria['groupId'],
	targetIndex: number,
	item: CriteriaItem
): Criteria | CriteriaItem {
	if (!('groupId' in criteria)) {
		return criteria;
	}

	if (criteria.groupId === targetGroupId) {
		return {
			...criteria,
			items: insertAtIndex(item, criteria.items, targetIndex),
		};
	}

	return {
		...criteria,
		items: criteria.items.map((child) =>
			insertItem(child, targetGroupId, targetIndex, item)
		),
	};
}
