/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useContext} from 'react';

import {
	KeyboardMovementContext,
	MovementSources,
	MovementTarget,
	getNextTarget,
	setMovementText,
} from '../contexts/KeyboardMovementContext';
import {LayoutColumnsContext} from '../contexts/LayoutColumnsContext';
import {MillerColumnItem} from '../types/MillerColumnItem';

const ALLOWED_KEYS = [
	'ArrowDown',
	'ArrowLeft',
	'ArrowRight',
	'ArrowUp',
	'Enter',
	'Escape',
] as const;

type AllowedKey = (typeof ALLOWED_KEYS)[number];

type Items = Map<string, MillerColumnItem>;

export function useKeyboardMovement({
	isPrivateLayoutsEnabled,
	item,
	items,
}: {
	isPrivateLayoutsEnabled: boolean;
	item: MillerColumnItem;
	items: Items;

	rtl: boolean;
}) {
	const {columnIndex, itemIndex} = item;

	const {
		columnSizes,
		setInitialColumns,
		setSources,
		setTarget,
		setText,
		sources,
		target,
	} = useContext(KeyboardMovementContext);

	const {layoutColumns} = useContext(LayoutColumnsContext);

	const enableMovement = useCallback(
		(sources) => {
			const initialTarget = getInitialTarget({
				columnSizes,
				isPrivateLayoutsEnabled,
				item,
				items,
				sources,
			});

			if (initialTarget) {
				setInitialColumns(layoutColumns);
				setSources(sources);
				setTarget(initialTarget);
				setMovementText({
					isInitialPosition: true,
					items,
					setText,
					sources,
					target: initialTarget,
				});
			}
		},
		[
			columnSizes,
			isPrivateLayoutsEnabled,
			item,
			items,
			setInitialColumns,
			layoutColumns,
			setSources,
			setTarget,
			setText,
		]
	);

	return {
		enableMovement,
		isEnabled: !!sources.length,
		isSource: isSource(item, sources),
		isTarget:
			columnIndex === target?.columnIndex &&
			itemIndex === target?.itemIndex,
		position: target?.position,
	};
}

function getInitialTarget({
	columnSizes,
	isPrivateLayoutsEnabled,
	item,
	items,
	sources,
}: {
	columnSizes: number[];
	isPrivateLayoutsEnabled: boolean;
	item: MillerColumnItem;
	items: Items;
	sources: MovementSources;
}) {
	const props = {
		columnSizes,
		isPrivateLayoutsEnabled,
		items,
		key: 'ArrowDown' as AllowedKey,
		sources,
	};

	// Try to find a valid initial target

	return (

		// Try in current column down to up

		getNextTarget({
			...props,
			key: 'ArrowUp' as AllowedKey,
			target: {
				columnIndex: item.columnIndex,
				itemIndex: item.itemIndex,
				position: 'top',
			},
		}) ||

		// Try in current column up to down

		getNextTarget({
			...props,
			target: {
				columnIndex: item.columnIndex,
				itemIndex: item.itemIndex,
				position: 'top',
			},
		}) ||

		// Try in previous column

		getNextTarget({
			...props,
			target: {
				columnIndex: item.columnIndex - 1,
				itemIndex: 0,
				position: 'top',
			},
		}) ||

		// Try in next column

		getNextTarget({
			...props,
			target: {
				columnIndex: item.columnIndex + 1,
				itemIndex: 0,
				position: 'top',
			},
		})
	);
}

function isSource(
	item: MillerColumnItem | MovementTarget,
	sources: MovementSources
) {
	return sources.some(
		(source) =>
			source.itemIndex === item?.itemIndex &&
			source.columnIndex === item?.columnIndex
	);
}
