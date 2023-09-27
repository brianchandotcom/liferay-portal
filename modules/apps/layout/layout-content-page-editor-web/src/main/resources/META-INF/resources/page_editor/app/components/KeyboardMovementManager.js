/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEventListener} from '@liferay/frontend-js-react-web';
import {openToast, sub} from 'frontend-js-web';
import {useEffect, useRef} from 'react';

import {FRAGMENT_ENTRY_TYPES} from '../config/constants/fragmentEntryTypes';
import {
	ARROW_DOWN_KEY_CODE,
	ARROW_UP_KEY_CODE,
	END_KEY_CODE,
	ENTER_KEY_CODE,
	ESCAPE_KEY_CODE,
	HOME_KEY_CODE,
} from '../config/constants/keyboardCodes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {useSelectItem} from '../contexts/ControlsContext';
import {
	useDisableKeyboardMovement,
	useMovementSource,
	useMovementTarget,
	useSetMovementTarget,
	useSetMovementText,
} from '../contexts/KeyboardMovementContext';
import {useDispatch, useSelectorRef} from '../contexts/StoreContext';
import selectLayoutDataItemLabel from '../selectors/selectLayoutDataItemLabel';
import addFragment from '../thunks/addFragment';
import addItem from '../thunks/addItem';
import addWidget from '../thunks/addWidget';
import moveItem from '../thunks/moveItem';
import checkAllowedChild from '../utils/drag_and_drop/checkAllowedChild';
import {TARGET_POSITIONS} from '../utils/drag_and_drop/constants/targetPositions';
import getDropData from '../utils/drag_and_drop/getDropData';
import itemIsAncestor from '../utils/drag_and_drop/itemIsAncestor';
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

	const fragmentEntryLinksRef = useSelectorRef(
		(state) => state.fragmentEntryLinks
	);
	const layoutDataRef = useSelectorRef((state) => state.layoutData);
	const keymapRef = useRef({});

	const disableMovement = useDisableKeyboardMovement();
	const setTarget = useSetMovementTarget();
	const setText = useSetMovementText();
	const selectItem = useSelectItem();
	const dispatch = useDispatch();

	keymapRef.current = {
		disableMovement: {
			action: () => {
				setText(null);
				disableMovement();
			},
			keyCode: ESCAPE_KEY_CODE,
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
					if (source.itemId === target.itemId) {
						setText(null);

						disableMovement();

						return;
					}

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

				setText(
					sub(Liferay.Language.get('x-placed-on-x-of-x'), [
						source.name,
						target.position,
						target.name,
					])
				);

				setTimeout(() => setText(null), 1000);

				disableMovement();

				if (actionType === ACTION_TYPES.move) {
					selectItem(source.itemId);
				}
			},
			keyCode: ENTER_KEY_CODE,
		},
		moveDown: {
			action: () => {
				const nextTarget = getNextTarget(
					source,
					target,
					fragmentEntryLinksRef.current,
					layoutDataRef,
					DIRECTIONS.down
				);

				if (nextTarget) {
					setTarget(nextTarget);

					setText(
						sub(Liferay.Language.get('targeting-x-of-x'), [
							nextTarget.position,
							nextTarget.name,
						])
					);
				}
			},
			keyCode: ARROW_DOWN_KEY_CODE,
		},
		moveToEnd: {
			action: () => {
				const nextTarget = getInitialTarget(
					source,
					layoutDataRef,
					fragmentEntryLinksRef
				);

				setTarget(nextTarget);

				setText(
					sub(Liferay.Language.get('targeting-x-of-x'), [
						nextTarget.position,
						nextTarget.name,
					])
				);
			},
			keyCode: END_KEY_CODE,
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
					fragmentEntryLinksRef,
					layoutDataRef,
					DIRECTIONS.down
				);

				if (nextTarget) {
					setTarget(nextTarget);

					setText(
						sub(Liferay.Language.get('targeting-x-of-x'), [
							nextTarget.position,
							nextTarget.name,
						])
					);
				}
			},
			keyCode: HOME_KEY_CODE,
		},
		moveUp: {
			action: () => {
				const nextTarget = getNextTarget(
					source,
					target,
					fragmentEntryLinksRef,
					layoutDataRef,
					DIRECTIONS.up
				);

				if (nextTarget) {
					setTarget(nextTarget);

					setText(
						sub(Liferay.Language.get('targeting-x-of-x'), [
							nextTarget.position,
							nextTarget.name,
						])
					);
				}
			},
			keyCode: ARROW_UP_KEY_CODE,
		},
	};

	useEventListener(
		'keydown',
		(event) => {
			event.stopPropagation();
			event.preventDefault();

			const {code} = event;

			const shortcut = Object.values(keymapRef.current).find(
				(shortcut) => shortcut.keyCode === code
			);

			if (shortcut) {
				shortcut.action(event);
			}
		},
		true,
		window
	);

	useEffect(() => {
		const initialTarget = getInitialTarget(
			source,
			layoutDataRef,
			fragmentEntryLinksRef
		);

		if (initialTarget) {
			setTarget(initialTarget);

			setText(
				sub(
					Liferay.Language.get(
						'use-up-and-down-arrows-to-move-the-fragment-and-press-enter-to-place-it-in-desired-position.-currently-targeting-x-of-x'
					),
					[initialTarget.position, initialTarget.name]
				)
			);

			selectItem(null);
		}
		else {
			disableMovement();

			showErrorToast(source);
		}
	}, [
		disableMovement,
		fragmentEntryLinksRef,
		layoutDataRef,
		selectItem,
		setTarget,
		setText,
		source,
	]);

	return null;
}

function getInitialTarget(source, layoutDataRef, fragmentEntryLinksRef) {
	const layoutData = layoutDataRef.current;
	const fragmentEntryLinks = fragmentEntryLinksRef.current;

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
				const childName = selectLayoutDataItemLabel(
					{fragmentEntryLinks},
					child
				);

				return {
					itemId: child.itemId,
					name: childName,
					position: TARGET_POSITIONS.BOTTOM,
				};
			}

			childIndex--;
		}

		return {
			itemId: root.itemId,
			name: root.type,
			position: TARGET_POSITIONS.MIDDLE,
		};
	}
	else if (actionType === ACTION_TYPES.move) {
		return {
			itemId: source.itemId,
			name: source.name,
			position: TARGET_POSITIONS.BOTTOM,
		};
	}
}

function getNextTarget(
	source,
	target,
	fragmentEntryLinks,
	layoutDataRef,
	direction
) {
	const layoutData = layoutDataRef.current;

	const checkValidTarget = (nextTarget) => {
		const nextTargetItem = layoutData.items[nextTarget.itemId];
		const sourceItem = layoutData.items[source.itemId];

		if (
			source.itemId === nextTarget.itemId &&
			nextTarget.position === TARGET_POSITIONS.BOTTOM
		) {
			return {...nextTarget, name: source.name};
		}

		if (
			itemIsAncestor(sourceItem, nextTargetItem, layoutDataRef) ||
			hasUnmappedCollectionAncestor(nextTargetItem, layoutData) ||
			isHidden(nextTargetItem)
		) {
			return getNextTarget(
				source,
				nextTarget,
				fragmentEntryLinks,
				layoutDataRef,
				direction
			);
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
					fragmentEntryLinks,
					layoutDataRef,
					direction
				);
			}
		}

		if (nextTarget.position === TARGET_POSITIONS.TOP) {
			if (
				nextTargetParent.children[0] !== nextTarget.itemId ||
				!checkAllowedChild(source, nextTargetParent, layoutDataRef)
			) {
				return getNextTarget(
					source,
					nextTarget,
					fragmentEntryLinks,
					layoutDataRef,
					direction
				);
			}
		}

		if (nextTarget.position === TARGET_POSITIONS.MIDDLE) {
			if (
				hasChildren(nextTargetItem, layoutData) ||
				!checkAllowedChild(source, nextTargetItem, layoutDataRef)
			) {
				return getNextTarget(
					source,
					nextTarget,
					fragmentEntryLinks,
					layoutDataRef,
					direction
				);
			}
		}

		const name = selectLayoutDataItemLabel(
			{fragmentEntryLinks},
			nextTargetItem
		);

		return {...nextTarget, name};
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

function showErrorToast(source) {
	let error = sub(
		Liferay.Language.get(
			'x-fragment-cannot-be-added-to-the-page-because-it-does-not-have-any-possible-drop-position'
		),
		source.name
	);

	if (source.fragmentEntryType === FRAGMENT_ENTRY_TYPES.input) {
		error = Liferay.Language.get(
			'form-components-can-only-be-placed-inside-a-mapped-form-container'
		);
	}

	openToast({
		message: error,
		type: 'danger',
	});
}
