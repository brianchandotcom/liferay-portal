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

import React, {useEffect, useRef, useState} from 'react';

import {CONTAINER_DISPLAY_OPTIONS} from '../config/constants/containerDisplayOptions';
import {
	ARROW_DOWN_KEYCODE,
	ARROW_LEFT_KEYCODE,
	ARROW_RIGHT_KEYCODE,
	ARROW_UP_KEYCODE,
	BACKSPACE_KEYCODE,
	D_KEYCODE,
	S_KEYCODE,
	Z_KEYCODE,
} from '../config/constants/keycodes';
import {MOVE_ITEM_DIRECTIONS} from '../config/constants/moveItemDirections';
import {useActiveItemId, useSelectItem} from '../contexts/ControlsContext';
import {useDispatch, useSelector} from '../contexts/StoreContext';
import {useWidgets} from '../contexts/WidgetsContext';
import selectCanUpdatePageStructure from '../selectors/selectCanUpdatePageStructure';
import deleteItem from '../thunks/deleteItem';
import duplicateItem from '../thunks/duplicateItem';
import moveItem from '../thunks/moveItem';
import redoThunk from '../thunks/redo';
import undoThunk from '../thunks/undo';
import canBeDuplicated from '../utils/canBeDuplicated';
import canBeRemoved from '../utils/canBeRemoved';
import canBeSaved from '../utils/canBeSaved';
import SaveFragmentCompositionModal from './SaveFragmentCompositionModal';

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
		!!element.closest('.page-editor__page-structure__item-configuration')
	);
};

const isWithinIframe = () => {
	return window.top !== window.self;
};

export default function ShortcutManager() {
	const activeItemId = useActiveItemId();
	const dispatch = useDispatch();
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const [openSaveModal, setOpenSaveModal] = useState(false);
	const selectItem = useSelectItem();
	const state = useSelector((state) => state);
	const widgets = useWidgets();

	const {fragmentEntryLinks, layoutData, segmentsExperienceId} = state;

	const activeItem = layoutData.items[activeItemId];

	const duplicate = () => {
		dispatch(
			duplicateItem({
				itemId: activeItemId,
				segmentsExperienceId,
				selectItem,
			})
		);
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

	const move = (event) => {
		const {itemId, parentId} = activeItem;

		const parentItem = layoutData.items[parentId];

		const numChildren = parentItem.children.length;

		const currentPosition = parentItem.children.indexOf(itemId);

		const direction =
			event.keyCode === ARROW_UP_KEYCODE ||
			event.keyCode === ARROW_LEFT_KEYCODE
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
				segmentsExperienceId,
			})
		);
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
				ctrlOrMeta(event) && event.keyCode === D_KEYCODE,
		},
		move: {
			action: move,
			canBeExecuted: (event) =>
				canUpdatePageStructure &&
				!!layoutData.items[activeItemId] &&
				!isEditableField(event.target) &&
				!isInteractiveElement(event.target),
			isKeyCombination: (event) => {
				if (!activeItem) {
					return false;
				}

				const {parentId} = activeItem;

				const parentItem = layoutData.items[parentId];

				if (
					parentItem.config.contentDisplay ===
					CONTAINER_DISPLAY_OPTIONS.flexRow
				) {
					return (
						event.keyCode === ARROW_RIGHT_KEYCODE ||
						event.keyCode === ARROW_LEFT_KEYCODE
					);
				}

				return (
					event.keyCode === ARROW_UP_KEYCODE ||
					event.keyCode === ARROW_DOWN_KEYCODE
				);
			},
		},
		remove: {
			action: remove,
			canBeExecuted: (event) =>
				canUpdatePageStructure &&
				!!layoutData.items[activeItemId] &&
				canBeRemoved(layoutData.items[activeItemId], layoutData) &&
				!isInteractiveElement(event.target),
			isKeyCombination: (event) => event.keyCode === BACKSPACE_KEYCODE,
		},
		save: {
			action: save,
			canBeExecuted: () =>
				canUpdatePageStructure &&
				!!layoutData.items[activeItemId] &&
				canBeSaved(layoutData.items[activeItemId], layoutData),
			isKeyCombination: (event) =>
				ctrlOrMeta(event) && event.keyCode === S_KEYCODE,
		},
		undo: {
			action: undo,
			canBeExecuted: (event) =>
				(isEditableField(event.target) ||
					!isInteractiveElement(event.target)) &&
				!isWithinIframe() &&
				!isEditingEditableField(),
			isKeyCombination: (event) =>
				ctrlOrMeta(event) &&
				event.keyCode === Z_KEYCODE &&
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
		</>
	);
}
