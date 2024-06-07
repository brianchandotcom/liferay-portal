/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEventListener} from '@liferay/frontend-js-react-web';
import {useEffect, useState} from 'react';

import {KeyboardKey} from '../types/KeyboardKey';
import {ItemTypeValues, LIST_ITEM_TYPES} from '../utils/listItemTypes';

interface Props {
	handleOpen: (key: string, editing: boolean) => void;
	key: string;
	type: ItemTypeValues;
}

export default function useKeyboardNavigation({type}: Props) {
	const [element, setElement] = useState<HTMLElement | null>(null);
	const [isTarget, setIsTarget] = useState<boolean>(false);

	useEffect(() => {
		const list = element?.closest('.panel-group');
		const listItem = element?.closest('.panel');

		const isFirstChild = list && listItem && listItem === list?.firstChild;

		setIsTarget(!!isFirstChild && element?.tagName === 'BUTTON');
	}, [element]);

	useEventListener(
		'keydown',
		(event) => {
			const key = (<KeyboardEvent>event).key as KeyboardKey;

			if ((key !== 'ArrowDown' && key !== 'ArrowUp') || !element) {
				return;
			}

			event.preventDefault();

			const nextCode = key;

			if (type === LIST_ITEM_TYPES.header) {
				onHeaderKeyDown(element, nextCode);
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
			const keyboardEvent =
				event as unknown as React.FocusEvent<HTMLElement>;

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

function onHeaderKeyDown(element: HTMLElement, keyCode: KeyboardKey) {
	if (keyCode === 'ArrowDown') {

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
	else if (keyCode === 'ArrowUp') {

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
			const previousHeader =
				previousCollapse.querySelector<HTMLElement>('button');

			previousHeader?.focus();
		}
	}
}

function onListItemKeyDown(element: HTMLElement, keyCode: KeyboardKey) {
	if (keyCode === 'ArrowUp') {

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
	else if (keyCode === 'ArrowDown') {

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
}
