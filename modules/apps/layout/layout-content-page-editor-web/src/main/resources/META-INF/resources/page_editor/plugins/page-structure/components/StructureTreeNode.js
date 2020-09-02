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
import PropTypes from 'prop-types';
import React, {useEffect, useRef} from 'react';

import {useToControlsId} from '../../../app/components/CollectionItemContext';
import {
	useActivationOrigin,
	useActiveItemId,
	useHoverItem,
	useHoveredItemId,
	useSelectItem,
} from '../../../app/components/Controls';
import {fromControlsId} from '../../../app/components/layout-data-items/Collection';
import {ITEM_ACTIVATION_ORIGINS} from '../../../app/config/constants/itemActivationOrigins';
import {ITEM_TYPES} from '../../../app/config/constants/itemTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import selectCanUpdatePageStructure from '../../../app/selectors/selectCanUpdatePageStructure';
import selectSegmentsExperienceId from '../../../app/selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../../app/store/index';
import deleteItem from '../../../app/thunks/deleteItem';
import moveItem from '../../../app/thunks/moveItem';
import checkAllowedChild from '../../../app/utils/dragAndDrop/checkAllowedChild';
import {DRAG_DROP_TARGET_TYPE} from '../../../app/utils/dragAndDrop/constants/dragDropTargetType';
import {TARGET_POSITION} from '../../../app/utils/dragAndDrop/constants/targetPosition';
import getTargetPosition from '../../../app/utils/dragAndDrop/getTargetPosition';
import itemIsAncestor from '../../../app/utils/dragAndDrop/itemIsAncestor';
import toControlsId from '../../../app/utils/dragAndDrop/toControlsId';
import {
	initialDragDrop,
	useDragItem,
	useDropTarget,
} from '../../../app/utils/dragAndDrop/useDragAndDrop';

const HOVER_EXPAND_DELAY = 1000;

const nodeIsHovered = (nodeId, hoveredItemId) =>
	nodeId === fromControlsId(hoveredItemId);
const nodeIsSelected = (nodeId, activeItemId) =>
	nodeId === fromControlsId(activeItemId);

export default function StructureTreeNode({node}) {
	const activationOrigin = useActivationOrigin();
	const activeItemId = useActiveItemId();
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const dispatch = useDispatch();
	const hoverItem = useHoverItem();
	const hoveredItemId = useHoveredItemId();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const nodeRef = useRef();
	const selectItem = useSelectItem();
	const toControlsId = useToControlsId();

	const isActive = node.activable && nodeIsSelected(node.id, activeItemId);
	const isDisabled = !node.activable || node.disabled;

	const item = {
		children: node.children,
		icon: node.icon,
		itemId: node.id,
		name: node.name,
		origin: ITEM_ACTIVATION_ORIGINS.structureTree,
		parentId: node.parentItemId,
		type: node.type || node.itemType,
	};

	const {isOverTarget, targetPosition, targetRef} = useDropTarget(
		item,
		computeHover
	);

	const {handlerRef, isDraggingSource} = useDragItem(
		item,
		(parentItemId, position) =>
			dispatch(
				moveItem({
					itemId: node.id,
					parentItemId,
					position,
					segmentsExperienceId,
				})
			)
	);

	useEffect(() => {
		if (
			isActive &&
			activationOrigin === ITEM_ACTIVATION_ORIGINS.pageEditor &&
			nodeRef.current
		) {
			nodeRef.current.scrollIntoView({
				behavior: 'smooth',
				block: 'center',
				inline: 'nearest',
			});
		}
	}, [activationOrigin, isActive]);

	useEffect(() => {
		let timeoutId = null;

		if (isOverTarget) {
			timeoutId = setTimeout(() => {
				node.onHoverNode(node.id);
			}, HOVER_EXPAND_DELAY);
		}

		return () => {
			clearTimeout(timeoutId);
		};
	}, [isOverTarget, node]);

	return (
		<div
			aria-disabled={isDisabled}
			aria-selected={isActive}
			className={classNames('page-editor__page-structure__tree-node', {
				'drag-over-bottom':
					isOverTarget && targetPosition === TARGET_POSITION.BOTTOM,
				'drag-over-middle':
					isOverTarget && targetPosition === TARGET_POSITION.MIDDLE,
				'drag-over-top':
					isOverTarget && targetPosition === TARGET_POSITION.TOP,
				dragged: isDraggingSource,
				'page-editor__page-structure__tree-node--activable':
					node.activable && node.itemType !== ITEM_TYPES.editable,
				'page-editor__page-structure__tree-node--active': isActive,
				'page-editor__page-structure__tree-node--hovered': nodeIsHovered(
					node.id,
					hoveredItemId
				),
			})}
			onMouseLeave={(event) => {
				event.stopPropagation();

				if (isDraggingSource) {
					return;
				}

				if (nodeIsHovered(node.id, hoveredItemId)) {
					hoverItem(null);
				}
			}}
			onMouseOver={(event) => {
				event.stopPropagation();

				if (isDraggingSource) {
					return;
				}

				hoverItem(node.id);
			}}
			ref={targetRef}
		>
			<div
				aria-label={Liferay.Util.sub(Liferay.Language.get('select-x'), [
					node.name,
				])}
				className="page-editor__page-structure__tree-node__mask"
				onClick={(event) => {
					event.stopPropagation();
					event.target.focus();

					if (node.activable) {
						selectItem(toControlsId(node.id), {
							itemType: node.itemType,
							origin: ITEM_ACTIVATION_ORIGINS.structureTree,
						});
					}
				}}
				onDoubleClick={(event) => event.stopPropagation()}
				ref={handlerRef}
				role="button"
			/>

			<NameLabel
				activable={node.activable}
				disabled={node.disabled}
				icon={node.icon}
				id={node.id}
				name={node.name}
				ref={nodeRef}
			/>

			{node.removable && canUpdatePageStructure && (
				<RemoveButton
					node={node}
					visible={
						nodeIsHovered(node.id, hoveredItemId) ||
						nodeIsSelected(node.id, activeItemId)
					}
				/>
			)}
		</div>
	);
}

StructureTreeNode.propTypes = {
	node: PropTypes.shape({
		id: PropTypes.string.isRequired,
		name: PropTypes.string.isRequired,
		removable: PropTypes.bool,
	}).isRequired,
};

const NameLabel = React.forwardRef(
	({activable, disabled, icon, id, name}, ref) => {
		const activeItemId = useActiveItemId();

		return (
			<div
				className={classNames(
					'page-editor__page-structure__tree-node__name',
					{
						'page-editor__page-structure__tree-node__name--active':
							activable && nodeIsSelected(id, activeItemId),
						'page-editor__page-structure__tree-node__name--disabled': disabled,
					}
				)}
				ref={ref}
			>
				{icon && <ClayIcon symbol={icon || ''} />}

				{name || Liferay.Language.get('element')}
			</div>
		);
	}
);

const RemoveButton = ({node, visible}) => {
	const dispatch = useDispatch();
	const selectItem = useSelectItem();
	const store = useSelector((state) => state);

	return (
		<ClayButton
			aria-label={Liferay.Util.sub(Liferay.Language.get('remove-x'), [
				node.name,
			])}
			className={classNames(
				'page-editor__page-structure__tree-node__remove-button',
				{
					'page-editor__page-structure__tree-node__remove-button--visible': visible,
				}
			)}
			displayType="unstyled"
			onClick={(event) => {
				event.stopPropagation();

				dispatch(deleteItem({itemId: node.id, selectItem, store}));
			}}
		>
			<ClayIcon symbol="times-circle" />
		</ClayButton>
	);
};

function computeHover({
	dispatch,
	layoutDataRef,
	monitor,
	siblingItem = null,
	sourceItem,
	targetItem,
	targetRefs,
}) {

	// Not dragging over direct child
	// We do not want to alter state here,
	// as dnd generate extra hover events when
	// items are being dragged over nested children

	if (!monitor.isOver({shallow: true})) {
		return;
	}

	// Dragging over itself or a descendant

	if (itemIsAncestor(sourceItem, targetItem, layoutDataRef)) {
		return dispatch({
			...initialDragDrop.state,
			type: DRAG_DROP_TARGET_TYPE.DRAGGING_TO_ITSELF,
		});
	}

	// Apparently valid drag, calculate vertical position and
	// nesting validation

	const [
		targetPositionWithMiddle,
		targetPositionWithoutMiddle,
		elevation,
	] = getItemPosition(
		siblingItem || targetItem,
		monitor,
		layoutDataRef,
		targetRefs
	);

	// Drop inside target

	const validDropInsideTarget = (() => {
		const targetIsColumn =
			targetItem.type === LAYOUT_DATA_ITEM_TYPES.column;
		const targetIsFragment =
			targetItem.type === LAYOUT_DATA_ITEM_TYPES.fragment;
		const targetIsContainer =
			targetItem.type === LAYOUT_DATA_ITEM_TYPES.container;
		const targetIsEmpty =
			layoutDataRef.current.items[targetItem.itemId]?.children.length ===
			0;
		const targetIsParent = sourceItem.parentId === targetItem.itemId;

		return (
			targetPositionWithMiddle === TARGET_POSITION.MIDDLE &&
			(targetIsEmpty || targetIsColumn || targetIsContainer) &&
			!targetIsFragment &&
			!targetIsParent
		);
	})();

	if (!siblingItem && validDropInsideTarget) {
		return dispatch({
			dropItem: sourceItem,
			dropTargetItem: targetItem,
			droppable: checkAllowedChild(sourceItem, targetItem, layoutDataRef),
			elevate: null,
			targetPositionWithMiddle,
			targetPositionWithoutMiddle,
			type: DRAG_DROP_TARGET_TYPE.INSIDE,
		});
	}

	// Valid elevation:
	// - dropItem should be child of dropTargetItem
	// - dropItem should be sibling of siblingItem

	if (
		siblingItem &&
		checkAllowedChild(sourceItem, targetItem, layoutDataRef)
	) {
		return dispatch({
			dropItem: sourceItem,
			dropTargetItem: siblingItem,
			droppable: true,
			elevate: true,
			targetPositionWithMiddle,
			targetPositionWithoutMiddle,
			type: DRAG_DROP_TARGET_TYPE.ELEVATE,
		});
	}

	// Try to elevate to a valid ancestor

	if (elevation) {
		const getElevatedTargetItem = (target) => {
			const parent = layoutDataRef.current.items[target.parentId]
				? {
						...layoutDataRef.current.items[target.parentId],
						collectionItemIndex: target.collectionItemIndex,
				  }
				: null;

			if (parent) {
				const [targetPosition] = getItemPosition(
					target,
					monitor,
					layoutDataRef,
					targetRefs
				);

				const [parentPosition] = getItemPosition(
					parent,
					monitor,
					layoutDataRef,
					targetRefs
				);

				if (
					(targetPosition === targetPositionWithMiddle ||
						parentPosition === targetPositionWithMiddle) &&
					checkAllowedChild(sourceItem, parent, layoutDataRef)
				) {
					return [parent, target];
				}
			}

			return [null, null];
		};

		const [elevatedTargetItem, siblingItem] = getElevatedTargetItem(
			targetItem
		);

		if (elevatedTargetItem && elevatedTargetItem !== targetItem) {
			return computeHover({
				dispatch,
				layoutDataRef,
				monitor,
				siblingItem,
				sourceItem,
				targetItem: elevatedTargetItem,
				targetRefs,
			});
		}
	}
}

const ELEVATION_BORDER_SIZE = 5;

function getItemPosition(item, monitor, layoutDataRef, targetRefs) {
	const targetRef = targetRefs.get(toControlsId(layoutDataRef, item));

	if (!targetRef || !targetRef.current) {
		return [null, null];
	}

	const clientOffsetY = monitor.getClientOffset().y;
	const hoverBoundingRect = targetRef.current.getBoundingClientRect();

	const [
		targetPositionWithMiddle,
		targetPositionWithoutMiddle,
	] = getTargetPosition(
		clientOffsetY,
		hoverBoundingRect,
		ELEVATION_BORDER_SIZE
	);

	const elevation = targetPositionWithMiddle !== TARGET_POSITION.MIDDLE;

	return [targetPositionWithMiddle, targetPositionWithoutMiddle, elevation];
}
