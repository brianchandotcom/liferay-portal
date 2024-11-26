/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Dispatch, ReactNode, SetStateAction, useState} from 'react';

export type NavigationTarget = {
	columnIndex: number;
	itemIndex: number;
	preventFocus?: boolean;
};

const INITIAL_TARGET = {
	columnIndex: 0,
	itemIndex: 0,
	preventFocus: true,
};

const KeyboardNavigationContext = React.createContext<{
	columnSizes: number[];
	setTarget: Dispatch<SetStateAction<NavigationTarget>>;
	target: NavigationTarget;
}>({
	columnSizes: [],
	setTarget: () => {},
	target: INITIAL_TARGET,
});

function KeyboardNavigationProvider({
	children,
	columnSizes,
}: {
	children: ReactNode;
	columnSizes: number[];
}) {
	const [target, setTarget] = useState<NavigationTarget>(INITIAL_TARGET);

	return (
		<KeyboardNavigationContext.Provider
			value={{
				columnSizes,
				setTarget,
				target,
			}}
		>
			{children}
		</KeyboardNavigationContext.Provider>
	);
}

export {KeyboardNavigationContext, KeyboardNavigationProvider};
