/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useRef, useState} from 'react';

import {ITEM_ACTIVATION_ORIGINS} from '../config/constants/itemActivationOrigins';
import {ITEM_TYPES} from '../config/constants/itemTypes';
import {
	BACKSPACE_KEY_CODE,
	D_KEY_CODE,
	H_KEY_CODE,
	PERIOD_KEY_CODE,
	R_KEY_CODE,
	S_KEY_CODE,
	Z_KEY_CODE,
} from '../config/constants/keyboardCodes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {
	useActiveItemIds,
	useActiveItemType,
	useSelectItem,
	useSelectMultipleItems,
} from '../contexts/ControlsContext';
import {
	useOpenShorcutModal,
	useSetEditedNodeId,
	useSetOpenShorcutModal,
} from '../contexts/ShortcutContext';
import {useDispatch, useSelector} from '../contexts/StoreContext';
import selectCanUpdatePageStructure from '../selectors/selectCanUpdatePageStructure';
import deleteItem from '../thunks/deleteItem';
import duplicateItem from '../thunks/duplicateItem';
import switchSidebarPanel from '../thunks/switchSidebarPanel';
import canBeDuplicated from '../utils/canBeDuplicated';
import canBeHidden from '../utils/canBeHidden';
import canBeRemoved from '../utils/canBeRemoved';
import canBeRenamed from '../utils/canBeRenamed';
import canBeSaved from '../utils/canBeSaved';
import isCtrlOrMeta from '../utils/isCtrlOrMeta';
import updateItemStyle from '../utils/updateItemStyle';
import SaveFragmentCompositionModal from './SaveFragmentCompositionModal';
import ShortcutModal from './ShortcutModal';
import useUndoRedoActions from './undo/useUndoRedoActions';

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
	const activeItemIds = useActiveItemIds();
	const activeItemType = useActiveItemType();
	const dispatch = useDispatch();
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const {onRedo, onUndo} = useUndoRedoActions();
	const [openSaveModal, setOpenSaveModal] = useState(false);
	const openShortcutModal = useOpenShorcutModal();
	const selectItem = useSelectItem();
	const selectMultipleItems = useSelectMultipleItems();
	const setEditedNodeId = useSetEditedNodeId();
	const setOpenShorcutModal = useSetOpenShorcutModal();
	const state = useSelector((state) => state);
	const sidebarHidden = state.sidebar.hidden;
	const {widgets} = state;

	const selectItems = Liferay.FeatureFlags['LPD-18221']
		? selectMultipleItems
		: selectItem;

	const {fragmentEntryLinks, layoutData} = state;

	const multiSelection = activeItemIds.length > 1;

	const activeLayoutDataItem =
		activeItemType === ITEM_TYPES.layoutDataItem
			? layoutData.items[activeItemIds[0]]
			: null;

	const masterLayoutData = useSelector(
		(state) => state.masterLayout?.masterLayoutData
	);

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const duplicate = () => {
		dispatch(
			duplicateItem({
				itemIds: activeItemIds,
				selectItems,
			})
		);
	};

	const hideShow = () => {
		updateItemStyle({
			dispatch,
			itemIds: activeItemIds,
			selectedViewportSize,
			styleName: 'display',
			styleValue:
				layoutData.items[activeItemIds[0]].config.styles.display ===
				'none'
					? 'block'
					: 'none',
		});
	};

	const hideSidebar = () => {
		dispatch(switchSidebarPanel({hidden: !sidebarHidden}));
	};

	const remove = () => {
		dispatch(
			deleteItem({
				itemIds: activeItemIds,
				selectItems,
			})
		);
	};

	const save = () => {
		setOpenSaveModal(true);
	};

	const undo = (event) => {
		if (event.shiftKey) {
			onRedo({selectItems});
		}
		else {
			onUndo({selectItems});
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
				origin: ITEM_ACTIVATION_ORIGINS.layout,
			});
		}
	};

	const keymapRef = useRef(null);

	keymapRef.current = {
		duplicate: {
			action: duplicate,
			canBeExecuted: () =>
				canUpdatePageStructure &&
				!!activeItemIds.length &&
				activeItemIds.every(
					(activeItemId) =>
						!!layoutData.items[activeItemId] &&
						canBeDuplicated(
							fragmentEntryLinks,
							layoutData.items[activeItemId],
							layoutData,
							widgets
						)
				),

			isKeyCombination: (event) =>
				isCtrlOrMeta(event) && event.code === D_KEY_CODE,
		},
		hideShow: {
			action: hideShow,
			canBeExecuted: () =>
				canUpdatePageStructure &&
				!!activeItemIds.length &&
				activeItemIds.every(
					(activeItemId) =>
						!!layoutData.items[activeItemId] &&
						canBeHidden({
							fragmentEntryLinks,
							item: layoutData.items[activeItemId],
							layoutData,
							masterLayoutData,
							selectedViewportSize,
						})
				),

			isKeyCombination: (event) =>
				isCtrlOrMeta(event) &&
				event.altKey &&
				event.code === H_KEY_CODE,
		},
		hideSidebar: {
			action: hideSidebar,
			canBeExecuted: (event) =>
				!isInteractiveElement(event.target) &&
				!isWithinIframe() &&
				!isEditingEditableField(),

			isKeyCombination: (event) =>
				isCtrlOrMeta(event) &&
				event.shiftKey &&
				event.code === PERIOD_KEY_CODE,
		},
		openShortcutModal: {
			action: () => setOpenShorcutModal(true),
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
				!!activeItemIds.length &&
				activeItemIds.every(
					(activeItemId) =>
						!!layoutData.items[activeItemId] &&
						canBeRemoved(
							layoutData.items[activeItemId],
							layoutData
						) &&
						!isInteractiveElement(event.target)
				),
			isKeyCombination: (event) => event.code === BACKSPACE_KEY_CODE,
		},
		rename: {
			action: () => {
				setEditedNodeId(activeItemIds[0]);
			},
			canBeExecuted: () =>
				!multiSelection &&
				canUpdatePageStructure &&
				!!layoutData.items[activeItemIds[0]] &&
				canBeRenamed(layoutData.items[activeItemIds[0]]),
			isKeyCombination: (event) =>
				isCtrlOrMeta(event) &&
				event.altKey &&
				event.code === R_KEY_CODE,
		},
		save: {
			action: save,
			canBeExecuted: () =>
				!multiSelection &&
				canUpdatePageStructure &&
				!!layoutData.items[activeItemIds[0]] &&
				canBeSaved(layoutData.items[activeItemIds[0]], layoutData),
			isKeyCombination: (event) =>
				isCtrlOrMeta(event) && event.code === S_KEY_CODE,
		},
		selectParent: {
			action: selectParent,
			canBeExecuted: (event) =>
				!multiSelection &&
				!isInteractiveElement(event.target) &&
				activeLayoutDataItem,
			isKeyCombination: (event) =>
				event.shiftKey && event.altKey && event.key === 'Enter',
		},
		undo: {
			action: undo,
			canBeExecuted: (event) =>
				(isEditableField(event.target) ||
					!isInteractiveElement(event.target)) &&
				!isWithinIframe() &&
				!isEditingEditableField(),
			isKeyCombination: (event) =>
				isCtrlOrMeta(event) &&
				event.code === Z_KEY_CODE &&
				!event.altKey,
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
