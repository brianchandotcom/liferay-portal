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

import {useEventListener} from '@liferay/frontend-js-react-web';
import {useEffect, useRef} from 'react';

import {
	ARROW_DOWN_KEYCODE,
	ARROW_UP_KEYCODE,
	END_KEYCODE,
	ENTER_KEYCODE,
	ESCAPE_KEYCODE,
	HOME_KEYCODE,
} from '../config/constants/keycodes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {useSelectItem} from '../contexts/ControlsContext';
import {
	useDisableKeyboardMovement,
	useMovementSource,
	useMovementTarget,
	useSetMovementTarget,
} from '../contexts/KeyboardMovementContext';
import {useDispatch, useSelectorRef} from '../contexts/StoreContext';
import addFragment from '../thunks/addFragment';
import addItem from '../thunks/addItem';
import addWidget from '../thunks/addWidget';
import moveItem from '../thunks/moveItem';
import checkAllowedChild from '../utils/drag-and-drop/checkAllowedChild';
import {TARGET_POSITIONS} from '../utils/drag-and-drop/constants/targetPositions';
import getDropData from '../utils/drag-and-drop/getDropData';
import itemIsAncestor from '../utils/drag-and-drop/itemIsAncestor';
import {isUnmappedCollection} from '../utils/isUnmappedCollection';

const DIRECTIONS = {
	down: 'down',
	up: 'up',
};

const ACTION_TYPES = {
	add: 'add',
	move: 'move',
};

export default function KeyboardMovementManager() {
	const source = useMovementSource();
	const target = useMovementTarget();

	const layoutDataRef = useSelectorRef((state) => state.layoutData);
	const keymapRef = useRef({});

	const disableMovement = useDisableKeyboardMovement();
	const setTarget = useSetMovementTarget();
	const selectItem = useSelectItem();
	const dispatch = useDispatch();

	keymapRef.current = {
		disableMovement: {
			action: () => disableMovement(),
			keyCode: ESCAPE_KEYCODE,
		},
		executeAction: {
			action: () => {
				const actionType = source.itemId
					? ACTION_TYPES.move
					: ACTION_TYPES.add;

				const {dropItemId, position} = getDropData({
					isElevation: target.position !== TARGET_POSITIONS.MIDDLE,
					layoutDataRef,
					sourceItemId: source.itemId,
					targetItemId: target.itemId,
					targetPosition: target.position,
				});

				let thunk;

				if (actionType === ACTION_TYPES.move) {
					thunk = moveItem({
						itemId: source.itemId,
						parentItemId: dropItemId,
						position,
					});
				}
				else if (actionType === ACTION_TYPES.add) {
					if (source.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
						if (source.isWidget) {
							thunk = addWidget({
								parentItemId: dropItemId,
								portletId: source.portletId,
								portletItemId: source.portletItemId,
								position,
							});
						}
						else {
							thunk = addFragment({
								fragmentEntryKey: source.fragmentEntryKey,
								groupId: source.groupId,
								parentItemId: dropItemId,
								position,
								type: source.type,
							});
						}
					}
					else {
						thunk = addItem({
							itemType: source.type,
							parentItemId: dropItemId,
							position,
						});
					}
				}

				dispatch(thunk);
				disableMovement();

				if (actionType === ACTION_TYPES.move) {
					selectItem(source.itemId);
				}
			},
			keyCode: ENTER_KEYCODE,
		},
		moveDown: {
			action: () => {
				const nextTarget = getNextTarget(
					source,
					target,
					layoutDataRef,
					DIRECTIONS.down
				);

				if (nextTarget) {
					setTarget(nextTarget);
				}
			},
			keyCode: ARROW_DOWN_KEYCODE,
		},
		moveToEnd: {
			action: () => {
				setTarget(getInitialTarget(source, layoutDataRef));
			},
			keyCode: END_KEYCODE,
		},
		moveToStart: {
			action: () => {
				const root =
					layoutDataRef.current.items[
						layoutDataRef.current.rootItems.main
					];

				const nextTarget = getNextTarget(
					source,
					{
						itemId: root.itemId,
						position: TARGET_POSITIONS.TOP,
					},
					layoutDataRef,
					DIRECTIONS.down
				);

				if (nextTarget) {
					setTarget(nextTarget);
				}
			},
			keyCode: HOME_KEYCODE,
		},
		moveUp: {
			action: () => {
				const nextTarget = getNextTarget(
					source,
					target,
					layoutDataRef,
					DIRECTIONS.up
				);

				if (nextTarget) {
					setTarget(nextTarget);
				}
			},
			keyCode: ARROW_UP_KEYCODE,
		},
	};

	useEventListener(
		'keydown',
		(event) => {
			event.stopPropagation();
			event.preventDefault();

			const {keyCode} = event;

			const shortcut = Object.values(keymapRef.current).find(
				(shortcut) => shortcut.keyCode === keyCode
			);

			if (shortcut) {
				shortcut.action(event);
			}
		},
		true,
		window
	);

	useEffect(() => {
		const initialTarget = getInitialTarget(source, layoutDataRef);

		if (initialTarget) {
			setTarget(initialTarget);
			selectItem(null);
		}
		else {
			disableMovement();
		}
	}, [disableMovement, layoutDataRef, selectItem, setTarget, source]);

	return null;
}

function getInitialTarget(source, layoutDataRef) {
	const layoutData = layoutDataRef.current;

	const actionType = source.itemId ? ACTION_TYPES.move : ACTION_TYPES.add;

	if (actionType === ACTION_TYPES.add) {
		const root = layoutData.items[layoutData.rootItems.main];

		if (!checkAllowedChild(source, root, layoutDataRef)) {
			return null;
		}

		let childIndex = root.children.length - 1;

		while (childIndex >= 0) {
			const childId = root.children[childIndex];
			const child = layoutData.items[childId];

			if (!isHidden(child)) {
				return {
					itemId: child.itemId,
					position: TARGET_POSITIONS.BOTTOM,
				};
			}

			childIndex--;
		}

		return {
			itemId: root.itemId,
			position: TARGET_POSITIONS.MIDDLE,
		};
	}
	else if (actionType === ACTION_TYPES.move) {
		const target = {
			itemId: source.itemId,
			position: TARGET_POSITIONS.MIDDLE,
		};

		return (
			getNextTarget(source, target, layoutDataRef, DIRECTIONS.up) ||
			getNextTarget(source, target, layoutDataRef, DIRECTIONS.down)
		);
	}
}

function getNextTarget(source, target, layoutDataRef, direction) {
	const layoutData = layoutDataRef.current;

	const checkValidTarget = (nextTarget) => {
		const nextTargetItem = layoutData.items[nextTarget.itemId];
		const sourceItem = layoutData.items[source.itemId];

		if (
			itemIsAncestor(sourceItem, nextTargetItem, layoutDataRef) ||
			hasUnmappedCollectionAncestor(nextTargetItem, layoutData) ||
			isHidden(nextTargetItem)
		) {
			return getNextTarget(source, nextTarget, layoutDataRef, direction);
		}

		const nextTargetParent = layoutData.items[nextTargetItem.parentId];

		if (!nextTargetParent) {
			return null;
		}

		if (nextTarget.position === TARGET_POSITIONS.BOTTOM) {
			if (!checkAllowedChild(source, nextTargetParent, layoutDataRef)) {
				return getNextTarget(
					source,
					nextTarget,
					layoutDataRef,
					direction
				);
			}

			return nextTarget;
		}

		if (nextTarget.position === TARGET_POSITIONS.TOP) {
			if (
				nextTargetParent.children[0] !== nextTarget.itemId ||
				!checkAllowedChild(source, nextTargetParent, layoutDataRef)
			) {
				return getNextTarget(
					source,
					nextTarget,
					layoutDataRef,
					direction
				);
			}

			return nextTarget;
		}

		if (nextTarget.position === TARGET_POSITIONS.MIDDLE) {
			if (
				hasChildren(nextTargetItem, layoutData) ||
				!checkAllowedChild(source, nextTargetItem, layoutDataRef)
			) {
				return getNextTarget(
					source,
					nextTarget,
					layoutDataRef,
					direction
				);
			}

			return nextTarget;
		}
	};

	const {itemId: targetId, position: targetPosition} = target;
	const targetItem = layoutData.items[targetId];

	if (direction === DIRECTIONS.up) {
		if (targetPosition === TARGET_POSITIONS.BOTTOM) {
			if (!hasChildren(targetItem, layoutData)) {
				return checkValidTarget({
					itemId: targetId,
					position: TARGET_POSITIONS.MIDDLE,
				});
			}

			const lastChildId =
				targetItem.children[targetItem.children.length - 1];

			return checkValidTarget({
				itemId: lastChildId,
				position: TARGET_POSITIONS.BOTTOM,
			});
		}

		if (targetPosition === TARGET_POSITIONS.MIDDLE) {
			return checkValidTarget({
				itemId: targetId,
				position: TARGET_POSITIONS.TOP,
			});
		}

		if (targetPosition === TARGET_POSITIONS.TOP) {
			const parentItem = layoutData.items[targetItem.parentId];
			const targetIndex = parentItem.children.indexOf(targetId);

			if (targetIndex === 0) {
				return checkValidTarget({
					itemId: parentItem.itemId,
					position: TARGET_POSITIONS.TOP,
				});
			}
			else {
				const previousSiblingId = parentItem.children[targetIndex - 1];

				return checkValidTarget({
					itemId: previousSiblingId,
					position: TARGET_POSITIONS.BOTTOM,
				});
			}
		}
	}
	else if (direction === DIRECTIONS.down) {
		if (targetPosition === TARGET_POSITIONS.BOTTOM) {
			const parentItem = layoutData.items[targetItem.parentId];
			const targetIndex = parentItem.children.indexOf(targetId);

			if (targetIndex === parentItem.children.length - 1) {
				return checkValidTarget({
					itemId: parentItem.itemId,
					position: TARGET_POSITIONS.BOTTOM,
				});
			}
			else {
				const nextSiblingId = parentItem.children[targetIndex + 1];

				return checkValidTarget({
					itemId: nextSiblingId,
					position: TARGET_POSITIONS.TOP,
				});
			}
		}

		if (targetPosition === TARGET_POSITIONS.MIDDLE) {
			return checkValidTarget({
				itemId: targetId,
				position: TARGET_POSITIONS.BOTTOM,
			});
		}

		if (targetPosition === TARGET_POSITIONS.TOP) {
			if (!hasChildren(targetItem, layoutData)) {
				return checkValidTarget({
					itemId: targetId,
					position: TARGET_POSITIONS.MIDDLE,
				});
			}

			const firstChildId = targetItem.children[0];

			return checkValidTarget({
				itemId: firstChildId,
				position: TARGET_POSITIONS.TOP,
			});
		}
	}

	return null;
}

function isHidden(item) {
	return item.config.styles?.display === 'none';
}

function hasChildren(item, layoutData) {
	return item.children.some((childId) => {
		const child = layoutData.items[childId];

		return !isHidden(child);
	});
}

function hasUnmappedCollectionAncestor(item, layoutData) {
	if (isUnmappedCollection(item)) {
		return true;
	}

	const parent = layoutData?.items?.[item.parentId];

	if (!parent) {
		return false;
	}

	return hasUnmappedCollectionAncestor(parent, layoutData);
}
