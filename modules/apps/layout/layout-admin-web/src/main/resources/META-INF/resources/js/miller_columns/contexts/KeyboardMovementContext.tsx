/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ScreenReaderAnnouncer} from '@liferay/layout-js-components-web';
import {navigate} from 'frontend-js-web';
import React, {
	Dispatch,
	ReactNode,
	SetStateAction,
	useCallback,
	useEffect,
	useRef,
	useState,
} from 'react';

import {DropPosition} from '../constants/dropPositions';
import {isValidMovement} from '../utils/isValidMovement';

import type {MillerColumnItem} from '../types/MillerColumnItem';

export type MovementSources = MillerColumnItem[];

export type MovementTarget = {
	columnIndex: number;
	itemIndex: number;
	position: DropPosition;
} | null;

const KeyboardMovementContext = React.createContext<{
	columnSizes: number[];
	redirectURL: string | null;
	setRedirectURL: Dispatch<SetStateAction<string | null>>;
	setSources: Dispatch<SetStateAction<MovementSources>>;
	setTarget: Dispatch<SetStateAction<MovementTarget>>;
	setText: (text: any) => void;
	sources: MovementSources;
	target: MovementTarget;
}>({
	columnSizes: [],
	redirectURL: null,
	setRedirectURL: () => {},
	setSources: () => {},
	setTarget: () => {},
	setText: () => {},
	sources: [],
	target: null,
});

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

function KeyboardMovementProvider({
	children,
	columnSizes,
	items,
	onMove,
	rtl,
}: {
	children: ReactNode;
	columnSizes: number[];
	items: Map<string, MillerColumnItem>;
	onMove: (
		sources: MovementSources,
		target: MillerColumnItem,
		position: DropPosition
	) => void;
	rtl: boolean;
}) {
	const [sources, setSources] = useState<MovementSources>([]);
	const [target, setTarget] = useState<MovementTarget>(null);
	const [redirectURL, setRedirectURL] = useState<string | null>(null);
	const screenReaderAnnouncerRef = useRef<any>();

	const setText = useCallback((text) => {
		const ref = screenReaderAnnouncerRef;

		if (ref.current) {
			ref.current?.sendMessage(text);
		}
	}, []);

	useEffect(() => {
		const onKeyDown = (event: KeyboardEvent) => {
			if (!sources.length) {
				return;
			}

			const key = getKey(event, rtl);

			event.preventDefault();
			event.stopPropagation();

			if (!isAllowedKey(key)) {
				return;
			}

			const disableMovement = () => {
				setSources([]);
				setTarget(null);
			};

			if (key === 'Enter' && target) {
				const targetItem = getMillerColumnsItem(
					target.columnIndex,
					target.itemIndex,
					items
				);

				if (targetItem && onMove) {
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
					isPrivateLayoutsEnabled: false,
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

		window.addEventListener('keydown', onKeyDown, true);

		return () => {
			window.removeEventListener('keydown', onKeyDown, true);
		};
	}, [columnSizes, items, redirectURL, sources, onMove, rtl, target]);

	return (
		<KeyboardMovementContext.Provider
			value={{
				columnSizes,
				redirectURL,
				setRedirectURL,
				setSources,
				setTarget,
				setText,
				sources,
				target,
			}}
		>
			<ScreenReaderAnnouncer
				aria-live="assertive"
				ref={screenReaderAnnouncerRef}
			/>

			{children}
		</KeyboardMovementContext.Provider>
	);
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
	isPrivateLayoutsEnabled,
	items,
	key,
	sources,
	target,
}: {
	columnSizes: number[];
	isPrivateLayoutsEnabled: boolean;
	items: Map<string, MillerColumnItem>;
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
	items: Map<string, MillerColumnItem>
) {
	return Array.from(items.values()).find(
		(item) =>
			item.columnIndex === columnIndex && item.itemIndex === itemIndex
	);
}

export {KeyboardMovementContext, KeyboardMovementProvider, getNextTarget};
