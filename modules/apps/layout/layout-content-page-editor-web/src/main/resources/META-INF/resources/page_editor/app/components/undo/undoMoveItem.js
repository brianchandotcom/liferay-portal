/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moveItem from '../../thunks/moveItem';

function undoAction({action}) {
	const {itemId, parentItemId, position} = action;

	return moveItem({itemId, parentItemId, position});
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
