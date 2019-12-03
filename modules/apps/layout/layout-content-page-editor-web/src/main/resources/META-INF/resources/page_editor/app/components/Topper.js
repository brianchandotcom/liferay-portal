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
import {useDrag, useDrop} from 'react-dnd';

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

export default function Topper({
	acceptDrop,
	active: activeTopper,
	children,
	item,
	layoutData,
	name
}) {
	const containerRef = useRef(null);
	const dispatch = useContext(DispatchContext);
	const [dragHover, setDragHover] = useState(null);
	const hoverItem = useHoverItem();
	const isHovered = useIsHovered();
	const isSelected = useIsSelected();
	const selectItem = useSelectItem();

	const [{isDragging}, drag] = useDrag({
		collect: monitor => ({
			isDragging: monitor.isDragging()
		}),
		end(_item, _monitor) {
			const result = _monitor.getDropResult();

			if (!result) {
				return;
			}

			const {itemId, position, siblingId} = result;

			if (itemId !== siblingId) {
				dispatch(moveItem({itemId, position, siblingId}));
			}
		},
		item: {
			...item,
			type: LAYOUT_DATA_ITEM_TYPES[item.type]
		}
	});

	const [{isOver}, drop] = useDrop({
		accept: acceptDrop,
		collect(_monitor) {
			return {
				isOver: _monitor.isOver({shallow: true})
			};
		},
		drop(_item, _monitor) {
			if (!_monitor.didDrop()) {
				return {
					itemId: _item.itemId,
					itemType: _monitor.getItemType(),
					position: dragHover,
					siblingId: item.itemId
				};
			}
		},
		hover(_item, _monitor) {
			const dragId = _item.itemId;
			const dragParentId = _item.parentId;
			const hoverId = item.itemId;

			// Don't replace items with themselves
			if (dragId === hoverId) {
				setDragHover(null);

				return;
			}

			// Determine rectangle on screen
			const hoverBoundingRect = containerRef.current.getBoundingClientRect();

			// Get vertical middle
			const hoverMiddleY =
				(hoverBoundingRect.bottom - hoverBoundingRect.top) / 2;

			// Determine mouse position
			const clientOffset = _monitor.getClientOffset();

			// Get pixels to the top
			const hoverClientY = clientOffset.y - hoverBoundingRect.top;

			if (dragParentId) {
				const parentChildren = layoutData.items[dragParentId].children;

				const dragIndex = parentChildren.findIndex(
					child => child === dragId
				);

				// When dragging downwards, only move when the cursor is below 50%
				// When dragging upwards, only move when the cursor is above 50%
				// Dragging downwards
				if (
					parentChildren[dragIndex + 1] !== hoverId &&
					hoverClientY < hoverMiddleY
				) {
					setDragHover(0);
					return;
				}

				// Dragging upwards
				if (
					parentChildren[dragIndex - 1] !== hoverId &&
					hoverClientY > hoverMiddleY
				) {
					setDragHover(1);
					return;
				}
			} else {
				if (hoverClientY < hoverMiddleY) {
					setDragHover(0);
					return;
				}

				if (hoverClientY > hoverMiddleY) {
					setDragHover(1);
					return;
				}
			}

			setDragHover(null);
		}
	});

	useOnClickOutside(containerRef, event => {
		if (!event.shiftKey) {
			selectItem(null);
		}
	});

	const styles = {
		'fragments-editor__drag-source fragments-editor__drag-source--fragment fragments-editor__drop-target fragments-editor__topper-wrapper fragment-entry-link-wrapper': true,
		'fragments-editor__topper-wrapper--active': isSelected(item.itemId),
		'fragments-editor__topper-wrapper--hovered fragment-entry-link-wrapper--hovered': isHovered(
			item.itemId
		),
		'fragments-editor-border-bottom': dragHover === 1 && isOver,
		'fragments-editor-border-top': dragHover === 0 && isOver
	};

	if (!activeTopper) {
		return React.cloneElement(children, {
			className: classNames(children.className, styles),
			ref: node => {
				containerRef.current = node;
				drop(node);

				// Call the original ref, if any.
				const {ref} = children;
				if (typeof ref === 'function') {
					ref(node);
				}
			}
		});
	}

	return (
		<div
			className={classNames(styles)}
			onClick={event => {
				event.stopPropagation();

				if (!acceptDrop.length || isDragging) {
					return;
				}

				const multiSelect = event.shiftKey;

				selectItem(item.itemId, {multiSelect});
			}}
			onMouseLeave={event => {
				event.stopPropagation();

				if (!acceptDrop.length || isDragging) {
					return;
				}

				if (isHovered(item.itemId)) {
					hoverItem(null);
				}
			}}
			onMouseOver={event => {
				event.stopPropagation();

				if (!acceptDrop.length || isDragging) {
					return;
				}

				hoverItem(item.itemId);
			}}
			ref={containerRef}
		>
			<div className="fragments-editor__topper tbar">
				<ul className="tbar-nav">
					<TopperListItem expand isDragHandler ref={drag}>
						<ul className="tbar-nav">
							<TopperListItem className="pr-0">
								<ClayIcon
									className="fragments-editor__topper__drag-icon fragments-editor__topper__icon"
									symbol="drag"
								/>
							</TopperListItem>
							<TopperListItem isTitle>{name}</TopperListItem>
						</ul>
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
				ref={drop}
			>
				{children}
			</div>
		</div>
	);
}
