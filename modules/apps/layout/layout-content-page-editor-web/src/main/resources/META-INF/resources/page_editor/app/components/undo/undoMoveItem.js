/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import moveItem from '../../thunks/moveItem';

function undoAction({action, store}) {
	const {itemId, parentItemId, position} = action;
	const {segmentsExperienceId} = store;

	return moveItem({itemId, parentItemId, position, segmentsExperienceId});
}

function getDerivedStateForUndo({action, state}) {
	const {itemId} = action;
	const {layoutData} = state;

	const item = layoutData.items[itemId];

	const parent = layoutData.items[item.parentId];

	const position = parent.children.indexOf(itemId);

	return {
		itemId: action.itemId,
		parentItemId: parent.itemId,
		position,
	};
}

export {undoAction, getDerivedStateForUndo};
