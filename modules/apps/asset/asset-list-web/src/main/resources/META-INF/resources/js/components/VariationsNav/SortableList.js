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

import ClayList from '@clayui/list';
import PropTypes from 'prop-types';
import React, {useCallback, useState} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import SortableListItem from './SortableListItem';

const SortableList = ({items}) => {
	const [listItems, setListItems] = useState(items);

	const handleMove = useCallback(
		(dragIndex, hoverIndex) => {
			const tempList = [...listItems];

			tempList.splice(dragIndex, 1);

			tempList.splice(hoverIndex, 0, listItems[dragIndex]);

			setListItems(tempList);
		},
		[listItems]
	);

	return (
		<DndProvider backend={HTML5Backend}>
			<ClayList className="mt-4">
				{listItems.map((item, index) => (
					<SortableListItem
						id={`sortableListItem-${item.editAssetListEntryURL}`}
						index={index}
						key={item.editAssetListEntryURL}
						onMove={handleMove}
						sortableListItem={item}
					/>
				))}
			</ClayList>
		</DndProvider>
	);
};

SortableList.propTypes = {
	items: PropTypes.array.isRequired,
};

export default SortableList;
