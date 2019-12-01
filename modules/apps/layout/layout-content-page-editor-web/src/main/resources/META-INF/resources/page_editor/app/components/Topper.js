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
import React, {useContext, useRef, useState} from 'react';
import {useDrag} from 'react-dnd';

import useOnClickOutside from '../../core/hooks/useOnClickOutside';
import {moveItem, removeItem} from '../actions/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {DispatchContext} from '../reducers/index';
import {
	useIsSelected,
	useIsHovered,
	useSelectItem,
	useHoverItem
} from './Controls';

const TopperListItem = React.forwardRef(
	({children, className, expand, isDragHandler, isTitle, ...props}, ref) => (
		<li
			{...props}
			className={classNames(
				'fragments-editor__topper__item tbar-item',
				className,
				{
					'fragments-editor__drag-handler': isDragHandler,
					'fragments-editor__topper__title': isTitle,
					'tbar-item-expand': expand
				}
			)}
			ref={ref}
		>
			{children}
		</li>
	)
);

export default function Topper({children, item, name}) {
	const containerRef = useRef(null);
	const dispatch = useContext(DispatchContext);
	const [dragHover] = useState(null);
	const hoverItem = useHoverItem();
	const isHovered = useIsHovered();
	const isSelected = useIsSelected();
	const selectItem = useSelectItem();

	const [{isDragging}, drag] = useDrag({
		end(_item, _monitor) {
			const {itemId, position, siblingId} = _monitor.getDropResult();

			dispatch(moveItem({itemId, position, siblingId}));
		},
		item: {
			...item,
			type: LAYOUT_DATA_ITEM_TYPES[item.type]
		}
	});

	useOnClickOutside(containerRef, event => {
		if (!event.shiftKey) {
			selectItem(null);
		}
	});

	return (
		<div
			className={classNames(
				'fragments-editor__drag-source fragments-editor__drag-source--fragment fragments-editor__drop-target fragments-editor__topper-wrapper fragment-entry-link-wrapper',
				{
					'fragments-editor__topper-wrapper--active': isSelected(
						item.itemId
					),
					'fragments-editor__topper-wrapper--hovered fragment-entry-link-wrapper--hovered': isHovered(
						item.itemId
					),
					'fragments-editor-border-bottom': dragHover === 1,
					'fragments-editor-border-top': dragHover === 0
				}
			)}
			onClick={event => {
				event.stopPropagation();

				const multiSelect = event.shiftKey;

				selectItem(item.itemId, {multiSelect});
			}}
			onMouseLeave={event => {
				event.stopPropagation();

				if (isHovered(item.itemId)) {
					hoverItem(null);
				}
			}}
			onMouseOver={event => {
				if (!isDragging) {
					event.stopPropagation();
					hoverItem(item.itemId);
				}
			}}
			ref={containerRef}
		>
			<div className="fragments-editor__topper tbar">
				<ul className="tbar-nav">
					<TopperListItem className="pr-0" isDragHandler ref={drag}>
						<ClayIcon
							className="fragments-editor__topper__drag-icon fragments-editor__topper__icon"
							symbol="drag"
						/>
					</TopperListItem>
					<TopperListItem expand isDragHandler isTitle ref={drag}>
						{name}
					</TopperListItem>
					<TopperListItem>
						<ClayButton displayType="unstyled" small>
							<ClayIcon
								className="fragments-editor__topper__icon"
								symbol="comments"
							/>
						</ClayButton>
					</TopperListItem>
					<TopperListItem>
						<ClayButton
							displayType="unstyled"
							onClick={event => {
								event.stopPropagation();
								dispatch(removeItem({itemId: item.itemId}));
							}}
							small
						>
							<ClayIcon
								className="fragments-editor__topper__icon"
								symbol="times-circle"
							/>
						</ClayButton>
					</TopperListItem>
				</ul>
			</div>
			<div
				className={classNames('fragment-entry-link-content', {
					dragged: isDragging
				})}
			>
				{children}
			</div>
		</div>
	);
}
