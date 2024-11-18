/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEventListener} from '@liferay/frontend-js-react-web';
import {openToast, sub} from 'frontend-js-web';
import {useEffect, useRef} from 'react';

import {FRAGMENT_ENTRY_TYPES} from '../../config/constants/fragmentEntryTypes';
import {
	ARROW_DOWN_KEY_CODE,
	ARROW_UP_KEY_CODE,
	END_KEY_CODE,
	ENTER_KEY_CODE,
	ESCAPE_KEY_CODE,
	HOME_KEY_CODE,
} from '../../config/constants/keyboardCodes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {
	useSelectItem,
	useSelectMultipleItems,
} from '../../contexts/ControlsContext';
import {
	useDisableKeyboardMovement,
	useMovementSources,
	useMovementTarget,
	useSetMovementTarget,
	useSetMovementText,
} from '../../contexts/KeyboardMovementContext';
import {useDispatch, useSelectorRef} from '../../contexts/StoreContext';
import {useGetWidgets} from '../../contexts/WidgetsContext';
import selectLayoutDataItemLabel from '../../selectors/selectLayoutDataItemLabel';
import addFragment from '../../thunks/addFragment';
import addItem from '../../thunks/addItem';
import addStepper from '../../thunks/addStepper';
import addWidget from '../../thunks/addWidget';
import moveItems from '../../thunks/moveItems';
import moveStepper from '../../thunks/moveStepper';
import checkAllowedChild from '../../utils/drag_and_drop/checkAllowedChild';
import {TARGET_POSITIONS} from '../../utils/drag_and_drop/constants/targetPositions';
import getDropData from '../../utils/drag_and_drop/getDropData';
import itemIsAncestor from '../../utils/drag_and_drop/itemIsAncestor';
import {getFormParent} from '../../utils/getFormParent';
import {isMultistepForm} from '../../utils/isMultistepForm';
import isStepper from '../../utils/isStepper';
import {isUnmappedCollection} from '../../utils/isUnmappedCollection';
import {openFormConversionModal} from '../../utils/openFormConversionModal';

const DIRECTIONS = {
	down: 'down',
	up: 'up',
};

const ACTION_TYPES = {
	add: 'add',
	move: 'move',
};

export default function KeyboardMovementManager() {
	const sources = useMovementSources();
	const target = useMovementTarget();

	const lastSource = sources[sources.length - 1];

	const fragmentEntryLinksRef = useSelectorRef(
		(state) => state.fragmentEntryLinks
	);
	const layoutDataRef = useSelectorRef((state) => state.layoutData);
	const keymapRef = useRef({});

	const disableMovement = useDisableKeyboardMovement();
	const setTarget = useSetMovementTarget();
	const setText = useSetMovementText();
	const selectItem = useSelectItem();
	const selectMultipleItems = useSelectMultipleItems();
	const dispatch = useDispatch();

	const getWidgets = useGetWidgets();

	const selectItems = Liferay.FeatureFlags['LPD-18221']
		? selectMultipleItems
		: selectItem;

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
				const actionType = lastSource.itemId
					? ACTION_TYPES.move
					: ACTION_TYPES.add;

				const {position, targetId} = getDropData({
					isElevation: target.position !== TARGET_POSITIONS.MIDDLE,
					layoutDataRef,
					sourceItemId: lastSource.itemId,
					targetItemId: target.itemId,
					targetPosition: target.position,
				});

				let thunk;

				if (actionType === ACTION_TYPES.move) {
					if (lastSource.itemId === target.itemId) {
						setText(null);

						disableMovement();

						return;
					}

					thunk = isStepper(lastSource)
						? moveStepper({
								itemId: lastSource.itemId,
								parentItemId: targetId,
								position,
							})
						: moveItems({
								itemIds: sources.map(({itemId}) => itemId),
								parentItemIds: [targetId],
								positions: [position],
							});
				}
				else if (actionType === ACTION_TYPES.add) {
					const [source] = sources;

					if (source.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
						if (source.isWidget) {
							thunk = addWidget({
								parentItemId: targetId,
								portletId: source.portletId,
								portletItemId: source.portletItemId,
								position,
								selectItems,
							});
						}
						else if (isStepper(source)) {
							thunk = addStepper({
								fragmentEntryKey: source.fragmentEntryKey,
								groupId: source.groupId,
								parentItemId: targetId,
								position,
								selectItems,
								type: source.type,
							});
						}
						else {
							thunk = addFragment({
								fragmentEntryKey: source.fragmentEntryKey,
								groupId: source.groupId,
								parentItemId: targetId,
								position,
								selectItems,
								type: source.fragmentEntryType,
							});
						}
					}
					else {
						thunk = addItem({
							itemType: source.type,
							parentItemId: targetId,
							position,
							selectItems,
						});
					}
				}

				const executeAction = () => {
					const [source] = sources;

					dispatch(thunk);

					setText(
						sub(Liferay.Language.get('x-placed-on-x-of-x'), [
							source.name,
							target.position,
							target.name,
						])
					);

					if (actionType === ACTION_TYPES.move) {
						selectItems(sources.map(({itemId}) => itemId));
					}
				};

				const targetItem = layoutDataRef.current.items[target.itemId];
				const formParent = getFormParent(
					targetItem,
					layoutDataRef.current
				);

				if (
					formParent &&
					sources.every((source) => isStepper(source)) &&
					!isMultistepForm(formParent)
				) {
					openFormConversionModal({
						onContinue: () => executeAction(),
					});
				}
				else {
					executeAction();
				}

				setTimeout(() => setText(null), 1000);

				disableMovement();
			},
			keyCode: ENTER_KEY_CODE,
		},
		moveDown: {
			action: () => {
				const nextTarget = getNextTarget(
					lastSource,
					target,
					fragmentEntryLinksRef,
					layoutDataRef,
					getWidgets,
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
					sources,
					layoutDataRef,
					fragmentEntryLinksRef,
					getWidgets
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
					lastSource,
					{
						itemId: root.itemId,
						position: TARGET_POSITIONS.TOP,
					},
					fragmentEntryLinksRef,
					layoutDataRef,
					getWidgets,
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
					lastSource,
					target,
					fragmentEntryLinksRef,
					layoutDataRef,
					getWidgets,
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
			sources,
			layoutDataRef,
			fragmentEntryLinksRef,
			getWidgets
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

			showErrorToast(lastSource);
		}
	}, [
		disableMovement,
		fragmentEntryLinksRef,
		getWidgets,
		lastSource,
		layoutDataRef,
		selectItem,
		setTarget,
		setText,
		sources,
	]);

	return null;
}

export function getInitialTarget(
	sources,
	layoutDataRef,
	fragmentEntryLinksRef,
	getWidgets
) {
	const layoutData = layoutDataRef.current;
	const fragmentEntryLinks = fragmentEntryLinksRef.current;
	const lastSource = sources[sources.length - 1];

	const actionType = lastSource.itemId ? ACTION_TYPES.move : ACTION_TYPES.add;

	if (actionType === ACTION_TYPES.add) {
		const root = layoutData.items[layoutData.rootItems.main];

		const canDropInRoot = checkAllowedChild(
			lastSource,
			root,
			layoutDataRef.current,
			fragmentEntryLinksRef.current,
			getWidgets
		).valid;

		// Check root children to see if someone is targetable

		let childIndex = root.children.length - 1;

		while (childIndex >= 0) {
			const childId = root.children[childIndex];
			const child = layoutData.items[childId];

			if (!isHidden(child)) {

				// This child is targetable

				const childName = selectLayoutDataItemLabel(
					{fragmentEntryLinks, layoutData},
					child
				);

				// If source can drop in root, return this child as target

				const target = {
					itemId: child.itemId,
					name: childName,
					position: TARGET_POSITIONS.BOTTOM,
				};

				if (canDropInRoot) {
					return target;
				}

				// Otherwise, look for next valid target

				else {
					return getNextTarget(
						lastSource,
						target,
						fragmentEntryLinksRef,
						layoutDataRef,
						getWidgets,
						DIRECTIONS.up
					);
				}
			}

			childIndex--;
		}

		// Root has no targetable child, return root as target if possible

		return canDropInRoot
			? {
					itemId: root.itemId,
					name: root.type,
					position: TARGET_POSITIONS.MIDDLE,
				}
			: null;
	}
	else if (actionType === ACTION_TYPES.move) {
		return {
			itemId: lastSource.itemId,
			name:
				sources.length > 1
					? sub(Liferay.Language.get('x-items'), sources.length)
					: lastSource.name,
			position: TARGET_POSITIONS.BOTTOM,
		};
	}
}

function getNextTarget(
	source,
	target,
	fragmentEntryLinksRef,
	layoutDataRef,
	getWidgets,
	direction
) {
	const fragmentEntryLinks = fragmentEntryLinksRef.current;
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
				fragmentEntryLinksRef,
				layoutDataRef,
				getWidgets,
				direction
			);
		}

		const nextTargetParent = layoutData.items[nextTargetItem.parentId];

		if (!nextTargetParent) {
			return null;
		}

		if (nextTarget.position === TARGET_POSITIONS.BOTTOM) {
			if (
				!checkAllowedChild(
					source,
					nextTargetParent,
					layoutDataRef.current,
					fragmentEntryLinksRef.current,
					getWidgets
				).valid
			) {
				return getNextTarget(
					source,
					nextTarget,
					fragmentEntryLinksRef,
					layoutDataRef,
					getWidgets,
					direction
				);
			}
		}

		if (nextTarget.position === TARGET_POSITIONS.TOP) {
			if (
				nextTargetParent.children[0] !== nextTarget.itemId ||
				!checkAllowedChild(
					source,
					nextTargetParent,
					layoutDataRef.current,
					fragmentEntryLinksRef.current,
					getWidgets
				).valid
			) {
				return getNextTarget(
					source,
					nextTarget,
					fragmentEntryLinksRef,
					layoutDataRef,
					getWidgets,
					direction
				);
			}
		}

		if (nextTarget.position === TARGET_POSITIONS.MIDDLE) {
			if (
				hasChildren(nextTargetItem, layoutData) ||
				!checkAllowedChild(
					source,
					nextTargetItem,
					layoutDataRef.current,
					fragmentEntryLinksRef.current,
					getWidgets
				).valid
			) {
				return getNextTarget(
					source,
					nextTarget,
					fragmentEntryLinksRef,
					layoutDataRef,
					getWidgets,
					direction
				);
			}
		}

		const name = selectLayoutDataItemLabel(
			{fragmentEntryLinks, layoutData},
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
