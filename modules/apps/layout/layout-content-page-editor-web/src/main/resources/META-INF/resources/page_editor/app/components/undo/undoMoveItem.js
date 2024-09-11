/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moveItems from '../../thunks/moveItems';

function undoAction({action}) {
	const {itemIds, parentItemIds, positions} = action;

	return moveItems({itemIds, parentItemIds, positions});
}

function getDerivedStateForUndo({action, state}) {
	const {itemIds} = action;
	const {layoutData} = state;

	const positions = [];
	const parentItemIds = [];

	for (const itemId of itemIds) {
		const item = layoutData.items[itemId];
		const parent = layoutData.items[item.parentId];

		parentItemIds.push(parent.itemId);
		positions.push(parent.children.indexOf(itemId));
	}

	return {
		itemIds,
		parentItemIds,
		positions,
	};
}

export {undoAction, getDerivedStateForUndo};
