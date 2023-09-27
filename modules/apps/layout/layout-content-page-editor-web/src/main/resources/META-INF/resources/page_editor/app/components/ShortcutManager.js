/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useRef, useState} from 'react';

import {CONTENT_DISPLAY_OPTIONS} from '../config/constants/contentDisplayOptions';
import {ITEM_ACTIVATION_ORIGINS} from '../config/constants/itemActivationOrigins';
import {ITEM_TYPES} from '../config/constants/itemTypes';
import {
	ARROW_DOWN_KEY_CODE,
	ARROW_LEFT_KEY_CODE,
	ARROW_RIGHT_KEY_CODE,
	ARROW_UP_KEY_CODE,
	BACKSPACE_KEY_CODE,
	D_KEY_CODE,
	PERIOD_KEY_CODE,
	S_KEY_CODE,
	Z_KEY_CODE,
} from '../config/constants/keyboardCodes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {MOVE_ITEM_DIRECTIONS} from '../config/constants/moveItemDirections';
import {
	useActiveItemId,
	useActiveItemType,
	useSelectItem,
} from '../contexts/ControlsContext';
import {useDispatch, useSelector} from '../contexts/StoreContext';
import selectCanUpdatePageStructure from '../selectors/selectCanUpdatePageStructure';
import deleteItem from '../thunks/deleteItem';
import duplicateItem from '../thunks/duplicateItem';
import moveItem from '../thunks/moveItem';
import redoThunk from '../thunks/redo';
import switchSidebarPanel from '../thunks/switchSidebarPanel';
import undoThunk from '../thunks/undo';
import canBeDuplicated from '../utils/canBeDuplicated';
import canBeRemoved from '../utils/canBeRemoved';
import canBeSaved from '../utils/canBeSaved';
import SaveFragmentCompositionModal from './SaveFragmentCompositionModal';
import ShortcutModal from './ShortcutModal';

const ctrlOrMeta = (event) =>
	(event.ctrlKey && !event.metaKey) || (!event.ctrlKey && event.metaKey);

const isEditableField = (element) =>
	!!element.closest('.page-editor__editable');

const isEditingEditableField = () =>
	!!document.activeElement.getAttribute('contenteditable');

const isInteractiveElement = (element) => {
	return (
		['INPUT', 'OPTION', 'SELECT', 'TEXTAREA'].includes(element.tagName) ||
		!!element.closest('.alloy-editor-container') ||
		!!element.closest('.cke_editable') ||
		!!element.closest('.dropdown-menu') ||
		!!element.closest('.page-editor__page-structure__item-configuration') ||
		!!element.closest('.page-editor__allowed-fragment__tree')
	);
};

const isWithinIframe = () => {
	return window.top !== window.self;
};

export default function ShortcutManager() {
	const activeItemId = useActiveItemId();
	const activeItemType = useActiveItemType();
	const dispatch = useDispatch();
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const [openSaveModal, setOpenSaveModal] = useState(false);
	const [openShortcutModal, setOpenShorcutModal] = useState(false);
	const selectItem = useSelectItem();
	const state = useSelector((state) => state);
	const sidebarHidden = state.sidebar.hidden;
	const {widgets} = state;

	const {fragmentEntryLinks, layoutData} = state;

	const activeLayoutDataItem =
		activeItemType === ITEM_TYPES.layoutDataItem
			? layoutData.items[activeItemId]
			: null;

	const duplicate = () => {
		dispatch(
			duplicateItem({
				itemId: activeItemId,
				selectItem,
			})
		);
	};

	const hideSidebar = () => {
		dispatch(switchSidebarPanel({hidden: !sidebarHidden}));
	};

	const move = (event) => {
		const {itemId, parentId} = activeLayoutDataItem;

		const parentItem = layoutData.items[parentId];

		const numChildren = parentItem.children.length;

		const currentPosition = parentItem.children.indexOf(itemId);

		const direction =
			event.code === ARROW_UP_KEY_CODE ||
			event.code === ARROW_LEFT_KEY_CODE
				? MOVE_ITEM_DIRECTIONS.UP
				: MOVE_ITEM_DIRECTIONS.DOWN;

		if (
			(direction === MOVE_ITEM_DIRECTIONS.UP && currentPosition === 0) ||
			(direction === MOVE_ITEM_DIRECTIONS.DOWN &&
				currentPosition === numChildren - 1)
		) {
			return;
		}

		let position;

		if (direction === MOVE_ITEM_DIRECTIONS.UP) {
			position = currentPosition - 1;
		}
		else if (direction === MOVE_ITEM_DIRECTIONS.DOWN) {
			position = currentPosition + 1;
		}

		dispatch(
			moveItem({
				itemId,
				parentItemId: parentId,
				position,
			})
		);
	};

	const openShortcutModalAction = () => {
		setOpenShorcutModal(true);
	};

	const remove = () => {
		dispatch(
			deleteItem({
				itemId: activeItemId,
				selectItem,
			})
		);
	};

	const save = () => {
		setOpenSaveModal(true);
	};

	const undo = (event) => {
		if (event.shiftKey) {
			dispatch(redoThunk({store: state}));
		}
		else {
			dispatch(undoThunk({store: state}));
		}
	};

	const selectParent = () => {
		const getSelectableParent = (layoutDataItem) => {
			if (!layoutDataItem) {
				return null;
			}

			const parentItem = state.layoutData.items[layoutDataItem.parentId];

			if (!parentItem) {
				return null;
			}

			if (
				parentItem.type !== LAYOUT_DATA_ITEM_TYPES.column &&
				parentItem.type !== LAYOUT_DATA_ITEM_TYPES.collectionItem &&
				parentItem.type !== LAYOUT_DATA_ITEM_TYPES.fragmentDropZone &&
				parentItem.type !== LAYOUT_DATA_ITEM_TYPES.root
			) {
				return parentItem;
			}

			return getSelectableParent(parentItem);
		};

		const selectableParent = getSelectableParent(activeLayoutDataItem);

		if (selectableParent) {
			selectItem(selectableParent.itemId, {
				itemType: ITEM_TYPES.layoutDataItem,
				origin: ITEM_ACTIVATION_ORIGINS.pageEditor,
			});
		}
	};

	const keymapRef = useRef(null);

	keymapRef.current = {
		duplicate: {
			action: duplicate,
			canBeExecuted: () =>
				canUpdatePageStructure &&
				!!layoutData.items[activeItemId] &&
				canBeDuplicated(
					fragmentEntryLinks,
					layoutData.items[activeItemId],
					layoutData,
					widgets
				),
			isKeyCombination: (event) =>
				ctrlOrMeta(event) && event.code === D_KEY_CODE,
		},
		hideSidebar: {
			action: hideSidebar,
			canBeExecuted: (event) =>
				!isInteractiveElement(event.target) &&
				!isWithinIframe() &&
				!isEditingEditableField(),

			isKeyCombination: (event) =>
				ctrlOrMeta(event) &&
				event.shiftKey &&
				event.code === PERIOD_KEY_CODE,
		},
		move: {
			action: move,
			canBeExecuted: (event) =>
				canUpdatePageStructure &&
				!!layoutData.items[activeItemId] &&
				!isEditableField(event.target) &&
				!isInteractiveElement(event.target),
			isKeyCombination: (event) => {
				if (!activeLayoutDataItem || !event.altKey || !event.shiftKey) {
					return false;
				}

				const {parentId} = activeLayoutDataItem;

				const parentItem = layoutData.items[parentId];

				if (
					parentItem.config.contentDisplay ===
					CONTENT_DISPLAY_OPTIONS.flexRow
				) {
					return (
						event.code === ARROW_RIGHT_KEY_CODE ||
						event.code === ARROW_LEFT_KEY_CODE
					);
				}

				return (
					event.code === ARROW_UP_KEY_CODE ||
					event.code === ARROW_DOWN_KEY_CODE
				);
			},
		},
		openShortcutModal: {
			action: openShortcutModalAction,
			canBeExecuted: (event) =>
				!isInteractiveElement(event.target) &&
				!isWithinIframe() &&
				!isEditingEditableField(),
			isKeyCombination: (event) => event.shiftKey && event.key === '?',
		},
		remove: {
			action: remove,
			canBeExecuted: (event) =>
				canUpdatePageStructure &&
				!!layoutData.items[activeItemId] &&
				canBeRemoved(layoutData.items[activeItemId], layoutData) &&
				!isInteractiveElement(event.target),
			isKeyCombination: (event) => event.code === BACKSPACE_KEY_CODE,
		},
		save: {
			action: save,
			canBeExecuted: () =>
				canUpdatePageStructure &&
				!!layoutData.items[activeItemId] &&
				canBeSaved(layoutData.items[activeItemId], layoutData),
			isKeyCombination: (event) =>
				ctrlOrMeta(event) && event.code === S_KEY_CODE,
		},
		selectParent: {
			action: selectParent,
			canBeExecuted: (event) =>
				!isInteractiveElement(event.target) && activeLayoutDataItem,
			isKeyCombination: (event) =>
				event.shiftKey && event.key === 'Enter',
		},
		undo: {
			action: undo,
			canBeExecuted: (event) =>
				(isEditableField(event.target) ||
					!isInteractiveElement(event.target)) &&
				!isWithinIframe() &&
				!isEditingEditableField(),
			isKeyCombination: (event) =>
				ctrlOrMeta(event) && event.code === Z_KEY_CODE && !event.altKey,
		},
	};

	useEffect(() => {
		const onKeyDown = (event) => {
			const shortcut = Object.values(keymapRef.current).find(
				(shortcut) =>
					shortcut.isKeyCombination(event) &&
					shortcut.canBeExecuted(event)
			);

			if (shortcut) {
				event.stopPropagation();
				event.preventDefault();

				shortcut.action(event);
			}
		};

		window.addEventListener('keydown', onKeyDown, true);

		return () => {
			window.removeEventListener('keydown', onKeyDown, true);
		};
	}, []);

	return (
		<>
			{openSaveModal && (
				<SaveFragmentCompositionModal
					onCloseModal={() => setOpenSaveModal(false)}
				/>
			)}

			{openShortcutModal && (
				<ShortcutModal
					onCloseModal={() => setOpenShorcutModal(false)}
				/>
			)}
		</>
	);
}
