/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {navigate} from 'frontend-js-web';
import {useCallback, useContext, useEffect, useMemo} from 'react';

import {
	KeyboardNavigationContext,
	NavigationTarget,
} from '../contexts/KeyboardNavigationContext';
import {MillerColumnItem} from '../types/MillerColumnItem';
import {
	KeyboardActionType,
	clearSessionState,
	getSessionState,
	setSessionState,
} from '../utils/keyboardSessionState';

const ALLOWED_KEYS = [
	'ArrowDown',
	'ArrowLeft',
	'ArrowRight',
	'ArrowUp',
	'Home',
	'End',
	'Enter',
] as const;

type AllowedKey = (typeof ALLOWED_KEYS)[number];

function isAllowedKey(key: string): key is AllowedKey {
	return ALLOWED_KEYS.includes(key as AllowedKey);
}

export function useKeyboardNavigation({
	element,
	getItemChildren,
	item,
	rtl,
}: {
	element: HTMLLIElement;
	getItemChildren: (id: string) => Promise<void>;
	item: MillerColumnItem;
	rtl: boolean;
}) {
	const {active, columnIndex, hasChild, id, itemIndex} = item;

	const {columnSizes, setTarget, target} = useContext(
		KeyboardNavigationContext
	);

	const isTarget = useMemo(
		() =>
			columnIndex === target.columnIndex &&
			itemIndex === target.itemIndex,
		[columnIndex, itemIndex, target.columnIndex, target.itemIndex]
	);

	const onKeyDown = useCallback(
		(event) => {
			const key = getKey(event, rtl);

			if (!isAllowedKey(key)) {
				return;
			}

			// Navigate to item if pressing Enter on anchor

			if (key === 'Enter') {
				const anchor = element.querySelector('a');

				// Store item id in session so we can focus it after navigate

				if (event.target === anchor) {
					setSessionState(id, 'navigate');
					navigate(item.url);
				}

				return;
			}

			event.preventDefault();

			// Load children if pressing Arrow Right when item is active

			if (
				key === (rtl ? 'ArrowLeft' : 'ArrowRight') &&
				hasChild &&
				!active
			) {
				getItemChildren(id).then(() =>
					setTarget({
						columnIndex: columnIndex + 1,
						itemIndex: 0,
					})
				);
			}

			const nextTarget = getNextTarget({
				columnSizes,
				item,
				key,
			});

			if (nextTarget) {
				setTarget(nextTarget);
			}
		},
		[
			active,
			columnIndex,
			columnSizes,
			element,
			getItemChildren,
			hasChild,
			id,
			item,
			rtl,
			setTarget,
		]
	);

	// Focus element when it's target

	useEffect(() => {
		if (!Liferay.FeatureFlags['LPD-35220']) {
			return;
		}

		if (element && isTarget) {

			// Return if focus is prevented

			if (target.preventFocus) {
				return;
			}

			focusElement(element);
		}
	}, [element, isTarget, target.preventFocus]);

	// Focus element after navigate

	useEffect(() => {
		const {itemId, type} = getSessionState();

		if (!element || itemId !== id) {
			return;
		}

		clearSessionState();

		setTarget({columnIndex, itemIndex, preventFocus: true});

		focusElement(element, type);
	}, [columnIndex, element, id, itemIndex, setTarget]);

	return {
		isTarget,
		onKeyDown,
	};
}

function getKey(event: KeyboardEvent, rtl: boolean) {
	const {key} = event;

	if (!rtl) {
		return event.key;
	}

	return key === 'ArrowRight'
		? 'ArrowLeft'
		: key === 'ArrowLeft'
			? 'ArrowRight'
			: key;
}

function getNextTarget({
	columnSizes,
	item: {active, columnIndex, hasChild, itemIndex, parentIndex},
	key,
}: {
	columnSizes: number[];
	item: MillerColumnItem;
	key: AllowedKey;
}): NavigationTarget | null {
	const columnSize = columnSizes[columnIndex];

	// Moving first

	if (key === 'Home' && itemIndex !== 0) {
		return {columnIndex, itemIndex: 0};
	}

	// Moving vertically

	if (key === 'ArrowDown' || key === 'ArrowUp') {
		const nextItemIndex =
			key === 'ArrowDown' ? itemIndex + 1 : itemIndex - 1;

		if (nextItemIndex < 0 || nextItemIndex >= columnSize) {
			return null;
		}

		return {columnIndex, itemIndex: nextItemIndex};
	}

	// Moving last

	if (key === 'End' && itemIndex !== columnSize - 1) {
		return {columnIndex, itemIndex: columnSize - 1};
	}

	// Moving left to parent if it exists

	if (key === 'ArrowLeft' && columnIndex > 0) {
		return {columnIndex: columnIndex - 1, itemIndex: parentIndex};
	}

	// Moving right to first children

	if (key === 'ArrowRight' && hasChild && active) {
		return {columnIndex: columnIndex + 1, itemIndex: 0};
	}

	return null;
}

function focusElement(element: HTMLLIElement, type?: KeyboardActionType) {

	// Focus the element

	if (type === 'movement') {
		(element.querySelector('.drag-handler') as HTMLButtonElement)?.focus();
	}
	else {
		element.querySelector('a')?.focus();
	}

	// Scroll to column

	const column = element.closest('.miller-columns-col');

	if (column) {
		column.scrollIntoView({behavior: 'smooth', inline: 'center'});
	}
}
