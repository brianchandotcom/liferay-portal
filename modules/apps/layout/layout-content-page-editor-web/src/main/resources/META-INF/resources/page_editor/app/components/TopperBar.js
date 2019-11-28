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
import ClayIcon from '@clayui/icon';

import classNames from 'classnames';
import React, {useContext, useState, useRef} from 'react';
import {useDrag} from 'react-dnd';
import {moveItem} from '../actions/index';
import Topper, {
	TopperContext,
	TOPPER_ACTIVE,
	TOPPER_HOVER
} from './Topper';
import {DispatchContext} from '../reducers/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

const TopperBar = ({children, item, name}) => {
	const containerRef = useRef(null);
	const [dragHover, ] = useState(null);
	const [{active, hover}, dispatch] = useContext(TopperContext);
	const dispatchStore = useContext(DispatchContext);

	const [{isDragging}, drag] = useDrag({
		end(_item, _monitor) {
			const {itemId, position, siblingId} = _monitor.getDropResult();

			dispatchStore(moveItem({itemId, position, siblingId}));
		},
		item: {
			...item,
			type: LAYOUT_DATA_ITEM_TYPES[item.type]
		}
	});

	return (
		<div
			className={classNames(
				'fragments-editor__drag-source fragments-editor__drag-source--fragment fragments-editor__drop-target fragments-editor__topper-wrapper fragment-entry-link-wrapper',
				{
					'fragments-editor__topper-wrapper--active':
						active === item.itemId,
					'fragments-editor__topper-wrapper--hovered fragment-entry-link-wrapper--hovered':
						hover === item.itemId,
					'fragments-editor-border-bottom': dragHover === 1 && isOver,
					'fragments-editor-border-top': dragHover === 0 && isOver
				}
			)}
			onClick={event => {
				event.stopPropagation();

				dispatch({payload: item.itemId, type: TOPPER_ACTIVE});
			}}
			onMouseLeave={event => {
				event.stopPropagation();

				dispatch({payload: null, type: TOPPER_HOVER});
			}}
			onMouseOver={event => {
				if (!isDragging) {
					event.stopPropagation();

					dispatch({payload: item.itemId, type: TOPPER_HOVER});
				}
			}}
			ref={containerRef}
		>
			<Topper>
				<Topper.Item className="pr-0" isDragHandler ref={drag}>
					<ClayIcon
						className="fragments-editor__topper__drag-icon fragments-editor__topper__icon"
						symbol="drag"
					/>
				</Topper.Item>
				<Topper.Item expand isDragHandler isTitle ref={drag}>
					{name}
				</Topper.Item>
				<Topper.Item>
					<ClayButton displayType="unstyled" small>
						<ClayIcon
							className="fragments-editor__topper__icon"
							symbol="comments"
						/>
					</ClayButton>
				</Topper.Item>
				<Topper.Item>
					<ClayButton displayType="unstyled" small>
						<ClayIcon
							className="fragments-editor__topper__icon"
							symbol="times-circle"
						/>
					</ClayButton>
				</Topper.Item>
			</Topper>
			<div
				className={classNames('fragment-entry-link-content', {
					dragged: isDragging
				})}
			>
				{children}
			</div>
		</div>
	);
};

export default TopperBar;
