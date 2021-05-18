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

import PropTypes from 'prop-types';
import React from 'react';

import {FRAGMENTS_DISPLAY_STYLES} from '../../../app/config/constants/fragmentsDisplayStyles';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {useDispatch, useSelector} from '../../../app/contexts/StoreContext';
import selectSegmentsExperienceId from '../../../app/selectors/selectSegmentsExperienceId';
import addFragment from '../../../app/thunks/addFragment';
import addItem from '../../../app/thunks/addItem';
import addWidget from '../../../app/thunks/addWidget';
import {useDragSymbol} from '../../../app/utils/drag-and-drop/useDragAndDrop';
import CardItem from './CardItem';
import ListItem from './ListItem';

export default function TabItem({displayStyle, item}) {
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const {isDraggingSource, sourceRef} = useDragSymbol(
		{
			icon: item.icon,
			label: item.label,
			type: item.type,
		},
		(parentId, position) => {
			let thunk;

			if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
				if (item.data.portletId) {
					thunk = addWidget;
				}
				else {
					thunk = addFragment;
				}
			}
			else {
				thunk = addItem;
			}

			dispatch(
				thunk({
					...item.data,
					parentItemId: parentId,
					position,
					store: {segmentsExperienceId},
				})
			);
		}
	);

	return displayStyle === FRAGMENTS_DISPLAY_STYLES.CARDS ? (
		<CardItem
			disabled={item.disabled || isDraggingSource}
			item={item}
			ref={sourceRef}
		/>
	) : (
		<ListItem
			disabled={item.disabled || isDraggingSource}
			item={item}
			ref={sourceRef}
		/>
	);
}

TabItem.propTypes = {
	displayStyle: PropTypes.oneOf(Object.values(FRAGMENTS_DISPLAY_STYLES)),
	item: PropTypes.shape({
		data: PropTypes.object.isRequired,
		icon: PropTypes.string.isRequired,
		label: PropTypes.string.isRequired,
		preview: PropTypes.string,
		type: PropTypes.string.isRequired,
	}).isRequired,
};
