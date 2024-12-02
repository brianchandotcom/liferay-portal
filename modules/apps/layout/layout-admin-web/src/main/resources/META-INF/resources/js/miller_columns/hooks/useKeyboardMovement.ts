/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useContext, useEffect} from 'react';

import {
	KeyboardMovementContext,
	MovementSources,
	MovementTarget,
	setMovementText,
} from '../contexts/KeyboardMovementContext';
import {LayoutColumnsContext} from '../contexts/LayoutColumnsContext';
import {MillerColumnItem} from '../types/MillerColumnItem';
import {setSessionState} from '../utils/keyboardSessionState';

type Items = Map<string, MillerColumnItem>;

export function useKeyboardMovement({
	element,
	item,
	items,
}: {
	element: HTMLLIElement;
	item: MillerColumnItem;
	items: Items;
}) {
	const {columnIndex, itemIndex} = item;

	const {setInitialColumns, setSources, setTarget, setText, sources, target} =
		useContext(KeyboardMovementContext);

	const {layoutColumns} = useContext(LayoutColumnsContext);

	const isTarget =
		columnIndex === target?.columnIndex && itemIndex === target?.itemIndex;

	const enableMovement = useCallback(
		(sources) => {
			const initialTarget: MovementTarget = {
				columnIndex,
				itemIndex,
				position: 'bottom',
			};

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

			setSessionState(item.id, 'movement');
		},
		[
			columnIndex,
			item.id,
			itemIndex,
			setInitialColumns,
			layoutColumns,
			setSources,
			setTarget,
			items,
			setText,
		]
	);

	// Scroll to column if it's target

	useEffect(() => {
		if (element && isTarget) {
			const column = element.closest('.miller-columns-col');

			if (column) {
				column.scrollIntoView({behavior: 'smooth', inline: 'center'});
			}
		}
	}, [element, isTarget]);

	return {
		enableMovement,
		isEnabled: !!sources.length,
		isSource: isSource(item, sources),
		isTarget,
		position: target?.position,
	};
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
