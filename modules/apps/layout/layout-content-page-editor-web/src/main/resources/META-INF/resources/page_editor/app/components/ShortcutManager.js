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
import redoThunk from '../thunks/redo';
import switchSidebarPanel from '../thunks/switchSidebarPanel';
import undoThunk from '../thunks/undo';
import canBeDuplicated from '../utils/canBeDuplicated';
import canBeHidden from '../utils/canBeHidden';
import canBeRemoved from '../utils/canBeRemoved';
import canBeRenamed from '../utils/canBeRenamed';
import canBeSaved from '../utils/canBeSaved';
import isCtrlOrMeta from '../utils/isCtrlOrMeta';
import updateItemStyle from '../utils/updateItemStyle';
import SaveFragmentCompositionModal from './SaveFragmentCompositionModal';
import ShortcutModal from './ShortcutModal';

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
	const [openSaveModal, setOpenSaveModal] = useState(false);
	const openShortcutModal = useOpenShorcutModal();
	const selectItem = useSelectItem();
	const setEditedNodeId = useSetEditedNodeId();
	const setOpenShorcutModal = useSetOpenShorcutModal();
	const state = useSelector((state) => state);
	const sidebarHidden = state.sidebar.hidden;
	const {widgets} = state;

	const {fragmentEntryLinks, layoutData} = state;

	let activeItemId = activeItemIds;
	let multiSelection = false;

	if (Liferay.FeatureFlags['LPD-18221']) {
		multiSelection = activeItemIds.length > 1;

		[activeItemId] = activeItemIds;
	}

	const activeLayoutDataItem =
		activeItemType === ITEM_TYPES.layoutDataItem
			? layoutData.items[activeItemId]
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
				itemId: activeItemId,
				selectItem,
			})
		);
	};

	const hideShow = () => {
		updateItemStyle({
			dispatch,
			itemId: activeItemId,
			selectedViewportSize,
			styleName: 'display',
			styleValue:
				layoutData.items[activeItemId].config.styles.display === 'none'
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
				!!layoutData.items[activeItemId] &&
				canBeDuplicated(
					fragmentEntryLinks,
					layoutData.items[activeItemId],
					layoutData,
					widgets
				),
			isKeyCombination: (event) =>
				isCtrlOrMeta(event) && event.code === D_KEY_CODE,
		},
		hideShow: {
			action: hideShow,
			canBeExecuted: () =>
				canUpdatePageStructure &&
				!!layoutData.items[activeItemId] &&
				canBeHidden({
					fragmentEntryLinks,
					item: layoutData.items[activeItemId],
					layoutData,
					masterLayoutData,
					selectedViewportSize,
				}),
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
				!!layoutData.items[activeItemId] &&
				canBeRemoved(layoutData.items[activeItemId], layoutData) &&
				!isInteractiveElement(event.target),
			isKeyCombination: (event) => event.code === BACKSPACE_KEY_CODE,
		},
		rename: {
			action: () => {
				setEditedNodeId(activeItemId);
			},
			canBeExecuted: () =>
				!multiSelection &&
				canUpdatePageStructure &&
				!!layoutData.items[activeItemId] &&
				canBeRenamed(layoutData.items[activeItemId]),
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
				!!layoutData.items[activeItemId] &&
				canBeSaved(layoutData.items[activeItemId], layoutData),
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
