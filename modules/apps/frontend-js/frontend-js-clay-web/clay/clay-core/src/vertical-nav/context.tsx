/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode, createContext, useContext} from 'react';

import type {ChildrenFunction} from '../collection';

type VerticalNavContextProps = {
	activeKey?: React.Key | null;
	ariaCurrent?: 'page' | null;
	childrenRoot: React.MutableRefObject<
		React.ReactNode | ChildrenFunction<Object, null>
	>;
	close: (key: React.Key) => void;
	expandedKeys: Set<React.Key>;
	firstKey: React.Key;
	open: (key: React.Key) => void;
	spritemap?: string;
	toggle: (key: React.Key) => void;
};

const VerticalNavContext = createContext<VerticalNavContextProps>({
	activeKey: null,
	ariaCurrent: null,
	childrenRoot: {current: null},
	close: () => false,
	expandedKeys: new Set(),
	firstKey: '',
	open: () => false,
	spritemap: '',
	toggle: () => null,
});

function VerticalNavContextProvider({
	activeKey,
	ariaCurrent,
	children,
	childrenRoot,
	close,
	expandedKeys,
	firstKey,
	open,
	spritemap,
	toggle,
}: VerticalNavContextProps & {children: ReactNode}) {
	return (
		<VerticalNavContext.Provider
			value={{
				activeKey,
				ariaCurrent,
				childrenRoot,
				close,
				expandedKeys,
				firstKey,
				open,
				spritemap,
				toggle,
			}}
		>
			{children}
		</VerticalNavContext.Provider>
	);
}

function useVerticalNavContext() {
	return useContext(VerticalNavContext);
}

export {VerticalNavContextProvider, useVerticalNavContext};
