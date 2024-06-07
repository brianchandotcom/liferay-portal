/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Criteria, CriteriaItem} from '../../types/Criteria';
import {ACTION_TYPES} from '../components/keyboard_movement/KeyboardMovementManager';
import {Source, Target} from '../contexts/KeyboardMovementContext';
import cleanCriteria from './cleanCriteria';
import getItem from './getItem';
import searchAndUpdateCriteria from './searchAndUpdateCriteria';
import {
	createNewGroup,
	getSupportedOperatorsFromType,
	replaceAtIndex,
} from './utils';

export default function dropInRow(
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

	let newGroup;

	if (target.groupId === 'root') {
		newGroup = createNewGroup([item]);
	}
	else {
		const targetItem = getItem(criteria, target.groupId, target.index);

		newGroup = createNewGroup([targetItem, item]);
	}

	let nextCriteria;

	if (actionType === ACTION_TYPES.move) {
		nextCriteria = searchAndUpdateCriteria(
			criteria,
			source.groupId!,
			source.index!,
			target.groupId,
			target.index,
			newGroup,
			true
		);
	}
	else {
		nextCriteria = insertGroup(
			nextCriteria || criteria,
			target.groupId,
			target.index,
			newGroup
		);
	}

	return cleanCriteria(nextCriteria) as Criteria;
}

function insertGroup(
	criteria: Criteria | CriteriaItem,
	targetGroupId: Criteria['groupId'],
	targetIndex: number,
	group: Criteria
): Criteria | CriteriaItem {
	if (!criteria) {
		return group;
	}

	if (!('groupId' in criteria)) {
		return criteria;
	}

	if (criteria.groupId === targetGroupId) {
		return {
			...criteria,
			items: replaceAtIndex(group, criteria.items, targetIndex),
		};
	}

	return {
		...criteria,
		items: criteria.items.map((child) =>
			insertGroup(child, targetGroupId, targetIndex, group)
		),
	};
}
