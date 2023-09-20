/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {DRAG_OVER_POSITIONS} from '../../config/constants/dragOverPositions';
import {Item} from './Item';

export function ItemList({items: initialItems, listId, updateLists}) {
	const [items, setItems] = useState(initialItems);

	const onDropItem = (itemId, nextIndex, dragOverPosition) => {
		const index = items.findIndex(({id}) => id === itemId);
		const item = items[index];
		const nextItems = [...items];

		let updatedNextIndex = nextIndex;

		if (Liferay.FeatureFlags['LPS-196420']) {
			if (dragOverPosition === DRAG_OVER_POSITIONS.bottom) {
				updatedNextIndex =
					updatedNextIndex < nextItems.length
						? updatedNextIndex + 1
						: updatedNextIndex;
			}

			if (updatedNextIndex > index) {
				updatedNextIndex =
					updatedNextIndex > 0
						? updatedNextIndex - 1
						: updatedNextIndex;
			}
		}

		nextItems.splice(index, 1);
		nextItems.splice(updatedNextIndex, 0, item);

		setItems(nextItems);
		updateLists(listId, nextItems);
	};

	return (
		<div className="p-4">
			{items.map((item, index) => (
				<Item
					index={index}
					item={item}
					key={item.id}
					numberOfItems={items.length}
					onDropItem={onDropItem}
				/>
			))}
		</div>
	);
}

ItemList.propTypes = {
	items: PropTypes.array,
	listId: PropTypes.number.isRequired,
	updateLists: PropTypes.func.isRequired,
};
