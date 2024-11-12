/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {navigate} from 'frontend-js-web';
import {useCallback, useContext, useEffect} from 'react';

import {DropPosition} from '../constants/dropPositions';
import {
	KeyboardMovementContext,
	MovementSources,
	MovementTarget,
} from '../contexts/KeyboardMovementContext';
import {MillerColumnItem} from '../types/MillerColumnItem';
import {isValidMovement} from '../utils/isValidMovement';

const ALLOWED_KEYS = [
	'ArrowDown',
	'ArrowLeft',
	'ArrowRight',
	'ArrowUp',
	'Enter',
	'Escape',
] as const;

type AllowedKey = (typeof ALLOWED_KEYS)[number];

function isAllowedKey(key: string): key is AllowedKey {
	return ALLOWED_KEYS.includes(key as AllowedKey);
}

type Items = Map<string, MillerColumnItem>;

export function useKeyboardMovement({
	isPrivateLayoutsEnabled,
	item,
	items,
	onMove,
	rtl,
}: {
	isPrivateLayoutsEnabled: boolean;
	item: MillerColumnItem;
	items: Items;
	onMove: (
		sources: MovementSources,
		target: MillerColumnItem,
		targetPosition: DropPosition
	) => {};
	rtl: boolean;
}) {
	const {columnIndex, itemIndex} = item;

	const {
		columnSizes,
		redirectURL,
		setListener,
		setRedirectURL,
		setSources,
		setTarget,
		sources,
		target,
	} = useContext(KeyboardMovementContext);

	const isTarget =
		columnIndex === target?.columnIndex && itemIndex === target?.itemIndex;

	useEffect(() => {
		if (!sources.length) {
			return;
		}

		const onKeyDown = (event: KeyboardEvent) => {
			const key = getKey(event, rtl);

			event.preventDefault();
			event.stopPropagation();

			if (!isAllowedKey(key)) {
				return;
			}

			const disableMovement = () => {
				setSources([]);
				setTarget(null);

				window.removeEventListener('keydown', onKeyDown, true);
			};

			if (key === 'Enter' && target) {
				const targetItem = getMillerColumnsItem(
					target.columnIndex,
					target.itemIndex,
					items
				);

				if (targetItem) {
					onMove(sources, targetItem, target.position);
				}

				disableMovement();
			}
			else if (key === 'Escape') {
				disableMovement();

				if (redirectURL) {
					navigate(redirectURL);
				}
			}
			else {
				const nextTarget = getNextTarget({
					columnSizes,
					isPrivateLayoutsEnabled,
					items,
					key,
					sources,
					target,
				});

				if (nextTarget) {
					setTarget(nextTarget);
				}
			}
		};

		// We send the listener to the context so we add it or remove it there,
		// this is needed because the component that is using this hook can be
		// unmounted during the movement

		setListener(() => onKeyDown);
	}, [
		columnSizes,
		isPrivateLayoutsEnabled,
		items,
		onMove,
		redirectURL,
		rtl,
		setListener,
		setSources,
		setTarget,
		sources,
		target,
	]);

	useEffect(() => {
		if (isTarget && item.active) {
			setRedirectURL(item.url);
		}
	}, [isTarget, item.active, item.url, setRedirectURL]);

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
				setSources(sources);
				setTarget(initialTarget);
			}
		},
		[
			columnSizes,
			isPrivateLayoutsEnabled,
			item,
			items,
			setSources,
			setTarget,
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

function getNextTarget({
	columnSizes,
	isPrivateLayoutsEnabled,
	items,
	key,
	sources,
	target,
}: {
	columnSizes: number[];
	isPrivateLayoutsEnabled: boolean;
	items: Items;
	key: AllowedKey;
	sources: MovementSources;
	target: MovementTarget;
}): MovementTarget {
	if (!target) {
		return null;
	}

	const {columnIndex, itemIndex, position} = target;

	if (columnIndex < 0 || columnIndex >= columnSizes.length) {
		return null;
	}

	const columnSize = columnSizes[columnIndex];

	let candidate: MovementTarget = null;

	// Moving up

	if (key === 'ArrowUp') {
		if (position === 'bottom') {
			candidate = {...target, position: 'middle'};
		}
		else if (position === 'middle') {
			candidate = {...target, position: 'top'};
		}
		else if (position === 'top' && itemIndex > 0) {
			candidate = {
				...target,
				itemIndex: itemIndex - 1,
				position: 'middle',
			};
		}
	}

	// Moving down

	if (key === 'ArrowDown') {
		if (position === 'top') {
			candidate = {
				...target,
				position: 'middle',
			};
		}
		else if (position === 'middle') {
			candidate = {
				...target,
				position: 'bottom',
			};
		}
		else if (position === 'bottom' && itemIndex < columnSize - 1) {
			candidate = {
				...target,
				itemIndex: itemIndex + 1,
				position: 'middle',
			};
		}
	}

	// Moving left

	if (key === 'ArrowLeft') {
		if (columnIndex >= 1) {
			candidate = {
				columnIndex: columnIndex - 1,
				itemIndex: 0,
				position: 'bottom',
			};
		}
	}

	// Moving right

	if (key === 'ArrowRight') {
		if (columnIndex < columnSizes.length - 1) {
			candidate = {
				columnIndex: columnIndex + 1,
				itemIndex: 0,
				position: 'bottom',
			};
		}
	}

	// If no candidate, return null

	if (!candidate) {
		return null;
	}

	// Return candidate if it's valid

	const candidateItem = getMillerColumnsItem(
		candidate.columnIndex,
		candidate.itemIndex,
		items
	);

	if (
		candidateItem &&
		isValidMovement({
			dropPosition: candidate.position,
			isPrivateLayoutsEnabled,
			sources,
			target: candidateItem,
		})
	) {
		return candidate;
	}

	// Try again

	return getNextTarget({
		columnSizes,
		isPrivateLayoutsEnabled,
		items,
		key,
		sources,
		target: candidate,
	});
}

function getMillerColumnsItem(
	columnIndex: number,
	itemIndex: number,
	items: Items
) {
	return Array.from(items.values()).find(
		(item) =>
			item.columnIndex === columnIndex && item.itemIndex === itemIndex
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
