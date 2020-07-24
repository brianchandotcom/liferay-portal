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

import {closest} from 'metal-dom';
import React, {useEffect, useRef, useState} from 'react';

import {
	ARROW_DOWN_KEYCODE,
	ARROW_UP_KEYCODE,
	BACKSPACE_KEYCODE,
	D_KEYCODE,
	S_KEYCODE,
	Z_KEYCODE,
} from '../config/constants/keycodes';
import {MOVE_ITEM_DIRECTIONS} from '../config/constants/moveItemDirections';
import {useDispatch, useSelector} from '../store/index';
import deleteItem from '../thunks/deleteItem';
import duplicateItem from '../thunks/duplicateItem';
import moveItem from '../thunks/moveItem';
import redoThunk from '../thunks/redo';
import undoThunk from '../thunks/undo';
import canBeDuplicated from '../utils/canBeDuplicated';
import canBeRemoved from '../utils/canBeRemoved';
import canBeSaved from '../utils/canBeSaved';
import {useActiveItemId, useSelectItem} from './Controls';
import SaveFragmentCompositionModal from './SaveFragmentCompositionModal';

const ctrlOrMeta = (event) =>
	(event.ctrlKey && !event.metaKey) || (!event.ctrlKey && event.metaKey);

const isCommentsAlloyEditor = (element) => {
	return (
		element.classList.contains('alloy-editor') &&
		element.parentElement.classList.contains('alloy-editor-container') &&
		closest(element, '.page-editor__sidebar')
	);
};

const isEditableCKEditor = (element) => {
	return (
		element.classList.contains('cke_editable') &&
		closest(element, '.page-editor__editable')
	);
};

const isTextElement = (element) => {
	return (
		(element.tagName === 'INPUT' &&
			(element.type === 'text' ||
				element.type === 'search' ||
				element.type === 'number')) ||
		element.tagName === 'TEXTAREA'
	);
};

const isWithinIframe = () => {
	return window.top !== window.self;
};

export default function ShortcutManager() {
	const activeItemId = useActiveItemId();
	const dispatch = useDispatch();
	const selectItem = useSelectItem();

	const [openSaveModal, setOpenSaveModal] = useState(false);

	const state = useSelector((state) => state);
	const {
		fragmentEntryLinks,
		layoutData,
		segmentsExperienceId,
		widgets,
	} = state;

	const duplicate = (event) => {
		event.preventDefault();

		const item = layoutData.items[activeItemId];

		if (
			item &&
			canBeDuplicated(fragmentEntryLinks, item, layoutData, widgets) &&
			document.activeElement === document.body
		) {
			dispatch(
				duplicateItem({
					itemId: activeItemId,
					segmentsExperienceId,
					selectItem,
				})
			);
		}
	};

	const remove = (event) => {
		if (
			!isEditableCKEditor(event.target) &&
			!isTextElement(event.target) &&
			!isCommentsAlloyEditor(event.target)
		) {
			event.preventDefault();
		}

		const item = layoutData.items[activeItemId];

		if (
			item &&
			canBeRemoved(item, layoutData) &&
			document.activeElement === document.body
		) {
			dispatch(
				deleteItem({
					itemId: item.itemId,
					selectItem,
					store: state,
				})
			);
		}
	};

	const save = (event) => {
		event.preventDefault();

		const item = layoutData.items[activeItemId];

		if (
			item &&
			canBeSaved(item, layoutData) &&
			document.activeElement === document.body
		) {
			setOpenSaveModal(true);
		}
	};

	const undo = (event) => {
		if (
			!isTextElement(event.target) &&
			!isCommentsAlloyEditor(event.target) &&
			!isWithinIframe()
		) {
			event.preventDefault();

			if (event.shiftKey) {
				dispatch(redoThunk({store: state}));
			}
			else {
				dispatch(undoThunk({store: state}));
			}
		}
	};

	const move = (event) => {
		const item = layoutData.items[activeItemId];

		if (
			item &&
			!isTextElement(event.target) &&
			!isEditableCKEditor(event.target) &&
			!isCommentsAlloyEditor(event.target)
		) {
			event.preventDefault();

			const {itemId, parentId} = item;

			const parentItem = layoutData.items[parentId];

			const numChildren = parentItem.children.length;

			const currentPosition = parentItem.children.indexOf(itemId);

			const direction =
				event.keyCode === ARROW_UP_KEYCODE
					? MOVE_ITEM_DIRECTIONS.UP
					: MOVE_ITEM_DIRECTIONS.DOWN;

			if (
				(direction === MOVE_ITEM_DIRECTIONS.UP &&
					currentPosition === 0) ||
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
		}
	};

	const keymapRef = useRef(null);

	keymapRef.current = {
		duplicate: {
			action: duplicate,
			isKeyCombination: (event) =>
				ctrlOrMeta(event) && event.keyCode === D_KEYCODE,
		},
		move: {
			action: move,
			isKeyCombination: (event) =>
				event.keyCode === ARROW_UP_KEYCODE ||
				event.keyCode === ARROW_DOWN_KEYCODE,
		},
		remove: {
			action: remove,
			isKeyCombination: (event) => event.keyCode === BACKSPACE_KEYCODE,
		},
		save: {
			action: save,
			isKeyCombination: (event) =>
				ctrlOrMeta(event) && event.keyCode === S_KEYCODE,
		},
		undo: {
			action: undo,
			isKeyCombination: (event) =>
				ctrlOrMeta(event) &&
				event.keyCode === Z_KEYCODE &&
				!event.altKey,
		},
	};

	useEffect(() => {
		const onKeyDown = (event) => {
			const shortcut = Object.values(keymapRef.current).find((shortcut) =>
				shortcut.isKeyCombination(event)
			);

			if (shortcut) {
				shortcut.action(event);
			}
		};

		window.addEventListener('keydown', onKeyDown, true);

		return () => {
			window.removeEventListener('keydown', onKeyDown, true);
		};
	}, []);

	return (
		<SaveFragmentCompositionModal
			onCloseModal={() => setOpenSaveModal(false)}
			open={openSaveModal}
		/>
	);
}
