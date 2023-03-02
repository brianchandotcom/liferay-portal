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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React from 'react';
import {useDrag} from 'react-dnd';

import DropZone from './DropZone';
import {ITEM_TYPES} from './itemTypes';

/**
 * Props are calculated in the `useInputSets` function `_getInputSetItemProps`.
 * @see {@link useInputSets#_getInputSetItemProps}
 */
function Item({
	children,
	index,
	isLastItem,
	onInputSetItemDelete,
	onInputSetItemMove,
}) {
	const [{isDragging}, drag, dragPreview] = useDrag({
		collect: (monitor) => ({
			isDragging: !!monitor.isDragging(),
		}),
		item: {index, type: ITEM_TYPES.ITEM},
	});

	return (
		<div className="input-sets-item-root">
			<DropZone index={index} move={onInputSetItemMove} />

			<ClayForm.Group
				className="input-sets-item-form-group"
				ref={dragPreview}
			>
				<ClayInput.Group>
					<ClayInput.GroupItem
						ref={drag}
						shrink
						style={{
							cursor: 'move',
							opacity: isDragging ? 0.5 : 1,
						}}
					>
						<ClayButton
							aria-label={Liferay.Language.get('move')}
							borderless
							className="drag-handle"
							displayType="secondary"
							monospaced
							small
						>
							<ClayIcon symbol="drag" />
						</ClayButton>
					</ClayInput.GroupItem>

					{children}

					<ClayInput.GroupItem shrink>
						<ClayButton
							aria-label={Liferay.Language.get('delete')}
							borderless
							displayType="secondary"
							monospaced
							onClick={onInputSetItemDelete(index)}
							small
						>
							<ClayIcon symbol="trash" />
						</ClayButton>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>

			{isLastItem && (
				<DropZone index={index + 1} move={onInputSetItemMove} />
			)}
		</div>
	);
}

export default Item;
