/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEventListener} from '@liferay/frontend-js-react-web';
import {useEffect, useState} from 'react';

import {
	ARROW_DOWN_KEY_CODE,
	ARROW_LEFT_KEY_CODE,
	ARROW_RIGHT_KEY_CODE,
	ARROW_UP_KEY_CODE,
} from '../utils/keyboardCodes';
import {ItemTypeValues, LIST_ITEM_TYPES} from '../utils/listItemTypes';

const ALLOWED_KEY_CODES = [
	ARROW_DOWN_KEY_CODE,
	ARROW_LEFT_KEY_CODE,
	ARROW_RIGHT_KEY_CODE,
	ARROW_UP_KEY_CODE,
];

interface Props {
	handleOpen: (key: string, editing: boolean) => void;
	key: string;
	type: ItemTypeValues;
}

export default function useKeyboardNavigation({handleOpen, key, type}: Props) {
	const [element, setElement] = useState<HTMLElement | null>(null);
	const [isTarget, setIsTarget] = useState<boolean>(false);

	useEffect(() => {
		const list = element?.closest('.panel-group');
		const listItem = element?.closest('.panel');

		const isFirstChild = list && listItem && listItem === list?.firstChild;

		setIsTarget(!!isFirstChild && element?.tagName === 'BUTTON');
	}, [element]);

	const rtl =
		Liferay.Language.direction?.[Liferay.ThemeDisplay.getLanguageId()] ===
		'rtl';

	useEventListener(
		'keydown',
		(event) => {
			const {code} = <KeyboardEvent>event;

			if (!ALLOWED_KEY_CODES.includes(code) || !element) {
				return;
			}

			event.preventDefault();

			let nextCode = code;

			if (rtl && code === ARROW_RIGHT_KEY_CODE) {
				nextCode = ARROW_LEFT_KEY_CODE;
			}

			if (rtl && code === ARROW_LEFT_KEY_CODE) {
				nextCode = ARROW_RIGHT_KEY_CODE;
			}

			if (type === LIST_ITEM_TYPES.header) {
				onHeaderKeyDown(element, nextCode, handleOpen, key);
			}
			else if (type === LIST_ITEM_TYPES.listItem) {
				onListItemKeyDown(element, nextCode);
			}
		},
		true,
		element as Node
	);

	useEventListener('focus', () => setIsTarget(true), true, element as Node);

	useEventListener(
		'blur',
		(event) => {
			const keyboardEvent = (event as unknown) as React.FocusEvent<
				HTMLElement
			>;

			const list = keyboardEvent.target?.closest('.panel-group');

			const nextActiveElement = <HTMLElement>keyboardEvent.relatedTarget;

			if (list && list.contains(nextActiveElement)) {
				setIsTarget(false);
			}
		},
		true,
		element as Node
	);

	return {isTarget, setElement};
}

function onHeaderKeyDown(
	element: HTMLElement,
	keyCode: string,
	handleOpen: (key: string, editing: boolean) => void,
	key: string
) {
	if (keyCode === ARROW_DOWN_KEY_CODE) {

		// Target first item of the list. If it's collapsed, target next header

		const panel = element.closest('.panel');
		const firstItem = panel?.querySelector('li');
		const isCollapsed = panel?.querySelector('.collapsed');

		if (!isCollapsed && firstItem) {
			firstItem.focus();
		}
		else {
			const nextCollapse = panel?.nextSibling as HTMLElement;
			const nextHeader = nextCollapse?.querySelector('button');

			nextHeader?.focus();
		}
	}
	else if (keyCode === ARROW_UP_KEY_CODE) {

		// Target last item of the previous list. If it's collapsed, target previous header

		const panel = element.closest('.panel');
		const previousCollapse = panel?.previousSibling as HTMLElement;

		if (!previousCollapse) {
			return;
		}

		const isPreviousCollapse = previousCollapse.querySelector('.collapsed');

		const previousList = previousCollapse.querySelector('ul');

		if (!isPreviousCollapse && previousList) {
			const lastItem = previousList.lastChild as HTMLElement;

			lastItem.focus();
		}
		else {
			const previousHeader = previousCollapse.querySelector<HTMLElement>(
				'button'
			);

			previousHeader?.focus();
		}
	}
	else if (keyCode === ARROW_RIGHT_KEY_CODE) {
		handleOpen(key, false);
	}
	else if (keyCode === ARROW_LEFT_KEY_CODE) {
		handleOpen(key, true);
	}
}

function onListItemKeyDown(element: HTMLElement, keyCode: string) {
	if (keyCode === ARROW_UP_KEY_CODE) {

		// Target previous list item. If it's the first one, target header

		if (element.previousSibling) {
			const previousSibling = element.previousSibling as HTMLElement;
			previousSibling.focus();
		}
		else {
			const panel = element.closest('.panel');
			const header = panel?.querySelector('.panel-header') as HTMLElement;

			header?.focus();
		}
	}
	else if (keyCode === ARROW_DOWN_KEY_CODE) {

		// Target next list item. If it's the last one, target next header

		if (element.nextSibling) {
			const nextSibling = element.nextSibling as HTMLElement;
			nextSibling.focus();
		}
		else {
			const panel = element.closest('.panel');
			const nextPanel = panel?.nextSibling as HTMLElement;
			const nextHeader = nextPanel?.querySelector(
				'.panel-header'
			) as HTMLElement;

			nextHeader?.focus();
		}
	}
	else if (keyCode === ARROW_RIGHT_KEY_CODE) {

		// If the active element is the list item itself, target first option button

		if (document.activeElement === element) {
			const dragSpan = element.querySelector('span');

			dragSpan?.focus();
		}
	}
	else if (keyCode === ARROW_LEFT_KEY_CODE) {

		// Focus the list element

		element.focus();
	}
}
