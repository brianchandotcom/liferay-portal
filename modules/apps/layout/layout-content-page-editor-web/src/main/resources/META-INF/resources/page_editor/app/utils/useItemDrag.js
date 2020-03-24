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

import {useEffect} from 'react';
import {useDrag} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {useSelectItem} from '../components/Controls';

export function useItemDrag({canDrag = () => true, type, name, onDragEnd}) {
	const selectItem = useSelectItem();

	const [, dragRef, preview] = useDrag({
		begin() {
			selectItem(null);
		},

		canDrag(...args) {
			return canDrag(...args);
		},

		end(_item, monitor) {
			const result = monitor.getDropResult();

			if (!result) {
				return;
			}

			const {parentId, position} = result;

			if (parentId) {
				onDragEnd(parentId, position);
			}
		},

		item: {
			name,
			type,
		},
	});

	useEffect(() => {
		preview(getEmptyImage(), {captureDraggingState: true});
	}, [preview]);

	return dragRef;
}
