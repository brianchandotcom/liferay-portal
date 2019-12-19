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
import {removeItem, switchSidebarPanel} from '../actions/index';
import {LAYOUT_DATA_ALLOWED_PARENT_TYPES} from '../config/constants/layoutDataAllowedParentTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {ConfigContext} from '../config/index';
import {DispatchContext} from '../reducers/index';
import {StoreContext} from '../store/index';
import moveItem from '../thunks/moveItem';
import {
	useCurrentFloatingToolbar,
	useIsSelected,
	useIsHovered,
	useSelectItem,
	useHoverItem
} from './Controls';

const EDGE = {
	BOTTOM: 1,
	TOP: 0
};

const TopperListItem = React.forwardRef(
	({children, className, expand, ...props}, ref) => (
		<li
			{...props}
			className={classNames(
				'page-editor-topper__item',
				'tbar-item',
				{'tbar-item-expand': expand},
				className
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
	const [edge, setEdge] = useState(null);
	const containerRef = useRef(null);
	const config = useContext(ConfigContext);
	const dispatch = useContext(DispatchContext);
	const store = useContext(StoreContext);
	const hoverItem = useHoverItem();
	const isHovered = useIsHovered();
	const isSelected = useIsSelected();
	const selectItem = useSelectItem();

	const floatingToolbarRef = useCurrentFloatingToolbar();

	const [{isDragging}, drag] = useDrag({
		collect: monitor => ({
			isDragging: monitor.isDragging()
		}),
		end(_item, _monitor) {
			const result = _monitor.getDropResult();

			if (!result) {
				return;
			}

			const {itemId, parentId, position} = result;

			if (itemId !== parentId) {
				dispatch(
					moveItem({
						config,
						itemId,
						parentItemId: parentId,
						position,
						store
					})
				);
			}
		},
		item: {
			...item,
			type: LAYOUT_DATA_ITEM_TYPES[item.type]
		}
	});

	const [{canDrop, isOver}, drop] = useDrop({
		accept: acceptDrop,
		collect(_monitor) {
			return {
				canDrop: _monitor.canDrop(),
				isOver: _monitor.isOver({shallow: true})
			};
		},
		drop(_item, _monitor) {
			if (!_monitor.didDrop()) {
				const {parentId, position} = getParentItemIdAndPositon({
					edge,
					item: _item,
					items: layoutData.items,
					siblingOrParentId: item.itemId
				});

				return {
					itemId: _item.itemId,
					itemType: _monitor.getItemType(),
					parentId,
					position
				};
			}
		},
		hover(_item, _monitor) {
			const dragId = _item.itemId;
			const dragParentId = _item.parentId;
			const hoverId = item.itemId;

			// Don't replace items with themselves
			if (dragId === hoverId) {
				setEdge(null);

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
					setEdge(EDGE.TOP);
					return;
				}

				// Dragging upwards
				if (
					parentChildren[dragIndex - 1] !== hoverId &&
					hoverClientY > hoverMiddleY
				) {
					setEdge(EDGE.BOTTOM);
					return;
				}
			} else {
				if (hoverClientY < hoverMiddleY) {
					setEdge(EDGE.TOP);
					return;
				}

				if (hoverClientY > hoverMiddleY) {
					setEdge(EDGE.BOTTOM);
					return;
				}
			}

			setEdge(null);
		}
	});

	useOnClickOutside(
		[containerRef.current, floatingToolbarRef.current],
		event => {
			if (!event.shiftKey) {
				selectItem(null);
			}
		}
	);

	const styles = {
		active: isSelected(item.itemId),
		'drag-over-bottom': edge === 1 && isOver,
		'drag-over-top': edge === 0 && isOver,
		dragged: isDragging,
		hovered: isHovered(item.itemId),
		'page-editor-topper': true
	};

	const childrenElement = children({canDrop, isOver});

	if (!activeTopper) {
		const isFragment = childrenElement.type === React.Fragment;
		const realChildren = isFragment
			? childrenElement.props.children
			: childrenElement;

		return React.Children.map(realChildren, child => {
			if (!child) {
				return child;
			}

			return React.cloneElement(child, {
				className: classNames(child.className, styles),
				ref: node => {
					containerRef.current = node;
					drop(node);

					// Call the original ref, if any.
					const {ref} = child;
					if (typeof ref === 'function') {
						ref(node);
					}
				}
			});
		});
	}

	const {sidebarPanels} = config;

	const commentsPanelId = sidebarPanels.comments.sidebarPanelId;

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
			<div className="page-editor-topper__bar tbar">
				<ul className="tbar-nav">
					<TopperListItem
						className="page-editor-topper__drag-handler"
						ref={drag}
					>
						<ClayIcon
							className="page-editor-topper__drag-icon page-editor-topper__icon"
							symbol="drag"
						/>
					</TopperListItem>
					<TopperListItem
						className="page-editor-topper__title"
						expand
					>
						{name || Liferay.Language.get('Element')}
					</TopperListItem>
					<TopperListItem>
						<ClayButton displayType="unstyled" small>
							<ClayIcon
								className="page-editor-topper__icon"
								onClick={() => {
									dispatch(
										switchSidebarPanel({
											sidebarOpen: true,
											sidebarPanelId: commentsPanelId
										})
									);
								}}
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
								className="page-editor-topper__icon"
								symbol="times-circle"
							/>
						</ClayButton>
					</TopperListItem>
				</ul>
			</div>

			<div className="page-editor-topper__content" ref={drop}>
				{childrenElement}
			</div>
		</div>
	);
}

function getParentItemIdAndPositon({edge, item, items, siblingOrParentId}) {
	const siblingOrParent = items[siblingOrParentId];

	if (isNestingSupported(item.type, siblingOrParent.type)) {
		return {
			parentId: siblingOrParentId,
			position: siblingOrParent.children.length
		};
	} else {
		const parent = items[siblingOrParent.parentId];

		const siblingIndex = parent.children.indexOf(siblingOrParentId);

		let position = edge === EDGE.TOP ? siblingIndex : siblingIndex + 1;

		// Moving an item in the same parent
		if (parent.children.includes(item.itemId)) {
			const itemIndex = parent.children.indexOf(item.itemId);

			position = itemIndex < siblingIndex ? position - 1 : position;
		}

		return {
			parentId: parent.itemId,
			position
		};
	}
}

function isNestingSupported(itemType, parentType) {
	return LAYOUT_DATA_ALLOWED_PARENT_TYPES[itemType].includes(parentType);
}
