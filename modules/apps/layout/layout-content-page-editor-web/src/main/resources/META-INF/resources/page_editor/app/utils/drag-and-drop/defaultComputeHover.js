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

import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import checkAllowedChild from './checkAllowedChild';
import {DRAG_DROP_TARGET_TYPE} from './constants/dragDropTargetType';
import {TARGET_POSITION} from './constants/targetPosition';
import getTargetPosition from './getTargetPosition';
import itemIsAncestor from './itemIsAncestor';
import toControlsId from './toControlsId';
import {initialDragDrop} from './useDragAndDrop';

export default function defaultComputeHover({
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
		elevationDepth,
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
		const targetIsEmpty =
			layoutDataRef.current.items[targetItem.itemId]?.children.length ===
			0;

		return (
			targetPositionWithMiddle === TARGET_POSITION.MIDDLE &&
			(targetIsEmpty || targetIsColumn) &&
			!targetIsFragment
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

	// Try to elevate to some valid ancestor
	// Using dropTargetItem parent as target and dropTargetItem as sibling
	// It will try elevate multiple levels if elevationDepth is enough and
	// there are valid ancestors

	if (elevationDepth) {
		const getElevatedTargetItem = (sibling, maximumDepth) => {
			const parent = layoutDataRef.current.items[sibling.parentId]
				? {
						...layoutDataRef.current.items[sibling.parentId],
						collectionItemIndex: sibling.collectionItemIndex,
				  }
				: null;

			if (parent) {
				const [siblingPositionWithMiddle] = getItemPosition(
					sibling,
					monitor,
					layoutDataRef,
					targetRefs
				);

				const [parentPositionWithMiddle] = getItemPosition(
					parent,
					monitor,
					layoutDataRef,
					targetRefs
				);

				if (
					(siblingPositionWithMiddle === targetPositionWithMiddle ||
						parentPositionWithMiddle ===
							targetPositionWithMiddle) &&
					checkAllowedChild(sourceItem, parent, layoutDataRef)
				) {
					if (maximumDepth > 1) {
						const [
							grandParent,
							parentSibling,
						] = getElevatedTargetItem(parent, maximumDepth - 1);

						if (grandParent) {
							return [grandParent, parentSibling];
						}
					}

					return [parent, sibling];
				}
				else {
					return getElevatedTargetItem(parent, maximumDepth);
				}
			}

			return [null, null];
		};

		const [elevatedTargetItem, siblingItem] = getElevatedTargetItem(
			targetItem,
			elevationDepth
		);

		if (elevatedTargetItem && elevatedTargetItem !== targetItem) {
			return defaultComputeHover({
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

const ELEVATION_BORDER_SIZE = 15;
const MAXIMUM_ELEVATION_STEPS = 3;

/**
 * Returns the cursor vertical position (extracted from provided dnd monitor)
 * relative to the given item, taking into account configured elevation steps
 * with ELEVATION_BORDER_SIZE and MAXIMUM_ELEVATION_STEPS.
 *
 * For each elevation step, a border on the top/bottom of the element is added.
 * The first elevation step (being the nearest to the element's center)
 * elevates two its first valid ancestor, the second to the next one, and all
 * the way up until MAXIMUM_ELEVATION_STEPS has been reached or there are no
 * more valid ancestors.
 */
function getItemPosition(item, monitor, layoutDataRef, targetRefs) {
	const targetRef = targetRefs.get(toControlsId(layoutDataRef, item));

	if (!targetRef || !targetRef.current) {
		return [null, null, 0];
	}

	const clientOffsetY = monitor.getClientOffset().y;
	const hoverBoundingRect = targetRef.current.getBoundingClientRect();

	const elevationStepSize = Math.min(
		hoverBoundingRect.height / (2 * (MAXIMUM_ELEVATION_STEPS + 1)),
		ELEVATION_BORDER_SIZE
	);

	const totalElevationBorderSize =
		elevationStepSize * MAXIMUM_ELEVATION_STEPS;

	const [
		targetPositionWithMiddle,
		targetPositionWithoutMiddle,
	] = getTargetPosition(
		clientOffsetY,
		hoverBoundingRect,
		totalElevationBorderSize
	);

	let elevationDepth = 0;

	if (targetPositionWithMiddle !== TARGET_POSITION.MIDDLE) {
		const distanceFromBorder =
			targetPositionWithMiddle === TARGET_POSITION.TOP
				? clientOffsetY - hoverBoundingRect.top
				: hoverBoundingRect.bottom - clientOffsetY;

		elevationDepth =
			MAXIMUM_ELEVATION_STEPS -
			Math.floor(
				(distanceFromBorder / totalElevationBorderSize) *
					MAXIMUM_ELEVATION_STEPS
			);
	}

	return [
		targetPositionWithMiddle,
		targetPositionWithoutMiddle,
		elevationDepth,
	];
}
