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
import React, {useContext, useRef, useState, useEffect, useMemo} from 'react';
import {useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {switchSidebarPanel} from '../actions/index';
import {LAYOUT_DATA_ALLOWED_PARENT_TYPES} from '../config/constants/layoutDataAllowedParentTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {ConfigContext} from '../config/index';
import {useDispatch, useSelector} from '../store/index';
import deleteItem from '../thunks/deleteItem';
import moveItem from '../thunks/moveItem';
import {
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
	layoutData
}) {
	const [edge, setEdge] = useState(null);
	const containerRef = useRef(null);
	const config = useContext(ConfigContext);
	const dispatch = useDispatch();
	const store = useSelector(state => state);
	const hoverItem = useHoverItem();
	const isHovered = useIsHovered();
	const isSelected = useIsSelected();
	const selectItem = useSelectItem();

	const [{isDragging}, drag, preview] = useDrag({
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
		item
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

			const hoverItem = layoutData.items[hoverId];

			const hoverParentOrItemId = hoverItem.parentId !== '' ? hoverItem.parentId : hoverId;

			const parentOrItemChildren = layoutData.items[hoverParentOrItemId].children;

			if (parentOrItemChildren.includes(dragId)) {
				// Get the index of the draggable element in the children if the drag is
				// happening inside the segment.
				const dragIndex = parentOrItemChildren.findIndex(
					child => child === dragId
				);

				// When dragging downwards, only move when the cursor is below 50%
				// When dragging upwards, only move when the cursor is above 50%
				// Dragging downwards
				if (
					parentOrItemChildren[dragIndex] !== parentOrItemChildren[0] &&
					parentOrItemChildren[dragIndex + 1] !== hoverId &&
					hoverClientY < hoverMiddleY
				) {
					setEdge(EDGE.TOP);
					return;
				}

				// Dragging upwards
				if (
					parentOrItemChildren[dragIndex] !== parentOrItemChildren[parentOrItemChildren.length - 1] &&
					parentOrItemChildren[dragIndex - 1] !== hoverId &&
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

	useEffect(() => {
		preview(getEmptyImage(), {captureDraggingState: true});
	}, [preview]);

	const showDeleteButton = useMemo(() => isRemovable(item, layoutData), [
		item,
		layoutData
	]);

	const styles = {
		active: isSelected(item.itemId),
		'drag-over-bottom': edge === EDGE.BOTTOM && isOver,
		'drag-over-top': edge === EDGE.TOP && isOver,
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
				...child.props,
				className: classNames(child.props.className, styles),
				ref: node => {
					containerRef.current = node;
					drop(node);

					// Call the original ref, if any.
					if (typeof child.props.ref === 'function') {
						child.props.ref(node);
					}
				}
			});
		});
	}

	const {sidebarPanels} = config;

	const commentsPanelId = sidebarPanels.comments.sidebarPanelId;

	const fragmentEntryLinks = store.fragmentEntryLinks;

	const getName = (item, fragmentEntryLinks) => {
		let name;

		if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
			name = fragmentEntryLinks[item.config.fragmentEntryLinkId].name;
		} else if (item.type === LAYOUT_DATA_ITEM_TYPES.container) {
			name = Liferay.Language.get('section');
		} else if (item.type === LAYOUT_DATA_ITEM_TYPES.column) {
			name = Liferay.Language.get('column');
		} else if (item.type === LAYOUT_DATA_ITEM_TYPES.dropZone) {
			name = Liferay.Language.get('drop-zone');
		} else if (item.type === LAYOUT_DATA_ITEM_TYPES.row) {
			name = Liferay.Language.get('row');
		}

		return name;
	};

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
						{getName(item, fragmentEntryLinks) ||
							Liferay.Language.get('element')}
					</TopperListItem>
					{item.type === LAYOUT_DATA_ITEM_TYPES.fragment && (
						<TopperListItem>
							<ClayButton
								displayType="unstyled"
								small
								title={Liferay.Language.get('comments')}
							>
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
					)}
					{showDeleteButton && (
						<TopperListItem>
							<ClayButton
								displayType="unstyled"
								onClick={event => {
									event.stopPropagation();

									dispatch(
										deleteItem({
											config,
											itemId: item.itemId,
											store
										})
									);
								}}
								small
								title={Liferay.Language.get('remove')}
							>
								<ClayIcon
									className="page-editor-topper__icon"
									symbol="times-circle"
								/>
							</ClayButton>
						</TopperListItem>
					)}
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

function isRemovable(item, layoutData) {
	function hasDropZoneChildren(item, layoutData) {
		return item.children.some(childrenId => {
			const children = layoutData.items[childrenId];

			return children.type === LAYOUT_DATA_ITEM_TYPES.dropZone
				? true
				: hasDropZoneChildren(children, layoutData);
		});
	}

	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.dropZone ||
		item.type === LAYOUT_DATA_ITEM_TYPES.column
	) {
		return false;
	}

	return !hasDropZoneChildren(item, layoutData);
}
