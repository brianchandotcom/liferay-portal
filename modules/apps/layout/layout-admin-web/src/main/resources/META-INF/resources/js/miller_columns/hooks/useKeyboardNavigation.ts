/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useContext, useEffect, useMemo} from 'react';

import {
	KeyboardNavigationContext,
	NavigationTarget,
} from '../contexts/KeyboardNavigationContext';
import {MillerColumnItem} from '../types/MillerColumnItem';

const ALLOWED_KEYS = [
	'ArrowDown',
	'ArrowLeft',
	'ArrowRight',
	'ArrowUp',
	'Home',
	'End',
] as const;

type AllowedKey = (typeof ALLOWED_KEYS)[number];

function isAllowedKey(key: string): key is AllowedKey {
	return ALLOWED_KEYS.includes(key as AllowedKey);
}

export function useKeyboardNavigation({
	element,
	item,
	rtl,
}: {
	element: HTMLLIElement;
	item: MillerColumnItem;
	rtl: boolean;
}) {
	const {columnIndex, itemIndex} = item;

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

			event.preventDefault();

			const nextTarget = getNextTarget({
				columnSizes,
				item,
				key,
			});

			if (nextTarget) {
				setTarget(nextTarget);
			}
		},
		[columnSizes, item, rtl, setTarget]
	);

	// Add keyboard listeners when item is target

	useEffect(() => {
		if (!Liferay.FeatureFlags['LPD-35220'] || !element) {
			return;
		}

		if (isTarget) {
			element.addEventListener('keydown', onKeyDown);

			// Focus the anchor element

			element.querySelector('a')?.focus();

			// Scroll to column

			const column = element.closest('.miller-columns-col');

			if (column) {
				column.scrollIntoView({behavior: 'smooth', inline: 'center'});
			}
		}

		return () => {
			element.removeEventListener('keydown', onKeyDown);
		};
	}, [element, isTarget, onKeyDown]);

	return {
		isTarget,
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
