/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	Dispatch,
	ReactNode,
	SetStateAction,
	useEffect,
	useState,
} from 'react';

import {DropPosition} from '../constants/dropPositions';

import type {MillerColumnItem} from '../types/MillerColumnItem';

export type MovementSources = MillerColumnItem[];

export type MovementTarget = {
	columnIndex: number;
	itemIndex: number;
	position: DropPosition;
} | null;

type KeyDownListener = ((event: KeyboardEvent) => void) | null;

const KeyboardMovementContext = React.createContext<{
	columnSizes: number[];
	redirectURL: string | null;
	setListener: Dispatch<SetStateAction<KeyDownListener>>;
	setRedirectURL: Dispatch<SetStateAction<string | null>>;
	setSources: Dispatch<SetStateAction<MovementSources>>;
	setTarget: Dispatch<SetStateAction<MovementTarget>>;
	sources: MovementSources;
	target: MovementTarget;
}>({
	columnSizes: [],
	redirectURL: null,
	setListener: () => {},
	setRedirectURL: () => {},
	setSources: () => {},
	setTarget: () => {},
	sources: [],
	target: null,
});

function KeyboardMovementProvider({
	children,
	columnSizes,
}: {
	children: ReactNode;
	columnSizes: number[];
}) {
	const [sources, setSources] = useState<MovementSources>([]);
	const [target, setTarget] = useState<MovementTarget>(null);
	const [listener, setListener] = useState<KeyDownListener>(null);
	const [redirectURL, setRedirectURL] = useState<string | null>(null);

	useEffect(() => {
		if (listener) {
			window.addEventListener('keydown', listener, true);
		}

		return () => {
			if (listener) {
				window.removeEventListener('keydown', listener, true);
			}
		};
	}, [listener]);

	return (
		<KeyboardMovementContext.Provider
			value={{
				columnSizes,
				redirectURL,
				setListener,
				setRedirectURL,
				setSources,
				setTarget,
				sources,
				target,
			}}
		>
			{children}
		</KeyboardMovementContext.Provider>
	);
}

export {KeyboardMovementContext, KeyboardMovementProvider};
