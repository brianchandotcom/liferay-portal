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
import {useEffect, useState} from 'react';

import {
	ARROW_DOWN_KEYCODE,
	ARROW_LEFT_KEYCODE,
	ARROW_RIGHT_KEYCODE,
	ARROW_UP_KEYCODE,
} from '../../../app/config/constants/keycodes';
import {LIST_ITEM_TYPES} from '../../../app/config/constants/listItemTypes';

const ALLOWED_KEYCODES = [
	ARROW_DOWN_KEYCODE,
	ARROW_LEFT_KEYCODE,
	ARROW_RIGHT_KEYCODE,
	ARROW_UP_KEYCODE,
];

export default function useKeyboardNavigation({handleOpen, type}) {
	const [element, setElement] = useState(null);
	const [isActive, setIsActive] = useState(false);

	useEffect(() => {
		const list = element?.closest('[role="menubar"]');
		const listItem = element?.closest('li');

		const isFirstChild = listItem === list?.firstChild;

		setIsActive(isFirstChild);
	}, [element]);

	useEventListener(
		'keydown',
		(event) => {
			if (!ALLOWED_KEYCODES.includes(event.keyCode)) {
				return;
			}

			event.preventDefault();

			if (type === LIST_ITEM_TYPES.header) {
				onHeaderKeyDown(element, event.keyCode, handleOpen);
			}
			else if (type === LIST_ITEM_TYPES.listItem) {
				onListItemKeyDown(element, event.keyCode);
			}
		},
		true,
		element
	);

	useEventListener('focus', () => setIsActive(true), true, element);

	useEventListener(
		'blur',
		(event) => {
			const list = event.target.closest('[role="menubar"]');

			const nextActiveElement = event.relatedTarget;

			if (list.contains(nextActiveElement)) {
				setIsActive(false);
			}
		},
		true,
		element
	);

	return {isActive, setElement};
}

function onHeaderKeyDown(element, keyCode, handleOpen) {
	if (keyCode === ARROW_DOWN_KEYCODE) {

		// Target first item of the list. If it's collapsed, target next header

		const list = element.nextSibling;
		const firstItem = list?.querySelector('li');

		if (firstItem) {
			firstItem.focus();
		}
		else {
			const collapse = element.parentElement;
			const nextCollapse = collapse.nextSibling;
			const nextHeader = nextCollapse?.querySelector('button');

			nextHeader?.focus();
		}
	}
	else if (keyCode === ARROW_UP_KEYCODE) {

		// Target last item of the previous list. If it's collapsed, target previous header

		const collapse = element.parentElement;
		const previousCollapse = collapse.previousSibling;

		if (!previousCollapse) {
			return;
		}

		const previousList = previousCollapse.querySelector('ul');

		if (previousList) {
			const lastItem = previousList.lastChild;

			lastItem.focus();
		}
		else {
			const previousHeader = previousCollapse.querySelector('button');

			previousHeader.focus();
		}
	}
	else if (keyCode === ARROW_RIGHT_KEYCODE) {

		// Expand

		handleOpen(true);
	}
	else if (keyCode === ARROW_LEFT_KEYCODE) {

		// Collapse

		handleOpen(false);
	}
}

function onListItemKeyDown(element, keyCode) {
	if (keyCode === ARROW_UP_KEYCODE) {

		// Target previous list item. If it's the first one, target header

		if (element.previousSibling) {
			element.previousSibling.focus();
		}
		else {
			const collapse = element.closest('.page-editor__collapse');
			const header = collapse.querySelector('button');

			header.focus();
		}
	}
	else if (keyCode === ARROW_DOWN_KEYCODE) {

		// Target next list item. If it's the last one, target next header

		if (element.nextSibling) {
			element.nextSibling.focus();
		}
		else {
			const collapse = element.closest('.page-editor__collapse');
			const nextCollapse = collapse.nextSibling;
			const nextHeader = nextCollapse?.querySelector('button');

			nextHeader?.focus();
		}
	}
	else if (keyCode === ARROW_RIGHT_KEYCODE) {

		// If the active element is the list item itself, target first option button

		// If the active element is an option button, target the next one

		if (document.activeElement === element) {
			const firstButton = element.querySelector('button');

			firstButton.focus();
		}
		else {
			const nextButton = document.activeElement.nextSibling;

			nextButton?.focus();
		}
	}
	else if (keyCode === ARROW_LEFT_KEYCODE) {

		// If the previous element is another button, target it, otherwise target the list item

		const previousSibling = document.activeElement.previousSibling;

		if (previousSibling?.tagName === 'BUTTON') {
			previousSibling.focus();
		}
		else {
			element.focus();
		}
	}
}
