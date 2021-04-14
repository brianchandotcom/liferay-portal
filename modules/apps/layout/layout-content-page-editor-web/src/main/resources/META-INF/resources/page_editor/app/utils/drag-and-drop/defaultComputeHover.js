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

import {CONTAINER_DISPLAY_OPTIONS} from '../../config/constants/containerDisplayOptions';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import checkAllowedChild from './checkAllowedChild';
import {DRAG_DROP_TARGET_TYPE} from './constants/dragDropTargetType';
import {ORIENTATIONS} from './constants/orientations';
import {TARGET_POSITIONS} from './constants/targetPositions';
import getDropTargetPosition from './getDropTargetPosition';
import getTargetData from './getTargetData';
import getTargetPositions from './getTargetPositions';
import itemIsAncestor from './itemIsAncestor';
import toControlsId from './toControlsId';
import {initialDragDrop} from './useDragAndDrop';

const ELEVATION_BORDER_SIZE = 15;
const MAXIMUM_ELEVATION_STEPS = 3;
const ORIENTATION_BORDER_SIZE = 80;

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

	// Apparently valid drag, calculate position and
	// nesting validation

	const orientation = getOrientation(
		siblingItem || targetItem,
		monitor,
		layoutDataRef,
		targetRefs
	);

	const [
		targetPositionWithMiddle,
		targetPositionWithoutMiddle,
		elevationDepth,
	] = getItemPosition(
		siblingItem || targetItem,
		monitor,
		layoutDataRef,
		targetRefs,
		orientation
	);

	// Drop inside target

	const validDropInsideTarget = (() => {
		const targetIsColumn =
			targetItem.type === LAYOUT_DATA_ITEM_TYPES.column;
		const targetIsContainerFlex = itemIsContainerFlex(targetItem);
		const targetIsFragment =
			targetItem.type === LAYOUT_DATA_ITEM_TYPES.fragment;
		const targetIsEmpty =
			layoutDataRef.current.items[targetItem.itemId]?.children.length ===
			0;
		const allowedChild = checkAllowedChild(
			sourceItem,
			targetItem,
			layoutDataRef
		);

		return (
			targetPositionWithMiddle === TARGET_POSITIONS.MIDDLE &&
			(targetIsEmpty || targetIsColumn || targetIsContainerFlex) &&
			!targetIsFragment &&
			allowedChild
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
	// - siblingItem should have flex parent for horizontal elevation
	//   and no-flex parent for vertical elevation

	if (
		siblingItem &&
		checkAllowedChild(sourceItem, targetItem, layoutDataRef) &&
		validElevation(siblingItem, orientation, layoutDataRef)
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
					targetRefs,
					orientation
				);

				const [parentPositionWithMiddle] = getItemPosition(
					parent,
					monitor,
					layoutDataRef,
					targetRefs,
					orientation
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

function getOrientation(item, monitor, layoutDataRef, targetRefs) {
	const targetRef = targetRefs.get(toControlsId(layoutDataRef, item));
	const targetRect = targetRef.current.getBoundingClientRect();
	const hoverMiddle = targetRect.left + targetRect.width / 2;
	const clientOffsetX = monitor.getClientOffset().x;

	const targetPosition =
		clientOffsetX < hoverMiddle
			? TARGET_POSITIONS.LEFT
			: TARGET_POSITIONS.RIGHT;

	const distanceFromBorder =
		targetPosition === TARGET_POSITIONS.LEFT
			? clientOffsetX - targetRect.left
			: targetRect.right - clientOffsetX;

	return distanceFromBorder < ORIENTATION_BORDER_SIZE
		? ORIENTATIONS.horizontal
		: ORIENTATIONS.vertical;
}

function getItemPosition(
	item,
	monitor,
	layoutDataRef,
	targetRefs,
	orientation
) {
	const targetRef = targetRefs.get(toControlsId(layoutDataRef, item));

	if (!targetRef || !targetRef.current) {
		return [null, null, 0];
	}

	const clientOffset =
		orientation === ORIENTATIONS.horizontal
			? monitor.getClientOffset().x
			: monitor.getClientOffset().y;

	const targetRect = targetRef.current.getBoundingClientRect();
	const targetPositions = getTargetPositions(orientation);
	const targetData = getTargetData(targetRect, orientation);

	const elevationStepSize = Math.min(
		targetData.length / (2 * (MAXIMUM_ELEVATION_STEPS + 1)),
		ELEVATION_BORDER_SIZE
	);

	const totalElevationBorderSize =
		elevationStepSize * MAXIMUM_ELEVATION_STEPS;

	const [
		targetPositionWithMiddle,
		targetPositionWithoutMiddle,
	] = getDropTargetPosition(
		clientOffset,
		totalElevationBorderSize,
		targetPositions,
		targetData
	);

	let elevationDepth = 0;

	if (targetPositionWithMiddle !== TARGET_POSITIONS.MIDDLE) {
		const distanceFromBorder =
			targetPositionWithMiddle === targetPositions.start
				? clientOffset - targetData.start
				: targetData.end - clientOffset;

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

function itemIsContainerFlex(item) {
	return (
		item.type === LAYOUT_DATA_ITEM_TYPES.container &&
		item.config.contentDisplay === CONTAINER_DISPLAY_OPTIONS.flexRow
	);
}

function validElevation(siblingItem, orientation, layoutDataRef) {
	const targetItemParent = layoutDataRef.current.items[siblingItem.parentId];

	return orientation === ORIENTATIONS.horizontal
		? itemIsContainerFlex(targetItemParent)
		: !itemIsContainerFlex(targetItemParent);
}
