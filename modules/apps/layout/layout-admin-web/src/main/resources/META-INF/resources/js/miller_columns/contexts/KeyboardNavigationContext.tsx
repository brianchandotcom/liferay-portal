/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Dispatch, ReactNode, SetStateAction, useState} from 'react';

export type NavigationTarget = {
	columnIndex: number;
	itemIndex: number;
};

const KeyboardContext = React.createContext<{
	columnSizes: number[];
	setTarget: Dispatch<SetStateAction<NavigationTarget>>;
	target: NavigationTarget;
}>({
	columnSizes: [],
	setTarget: () => {},
	target: {
		columnIndex: 0,
		itemIndex: 0,
	},
});

function KeyboardNavigationProvider({
	children,
	columnSizes,
}: {
	children: ReactNode;
	columnSizes: number[];
}) {
	const [target, setTarget] = useState<NavigationTarget>({
		columnIndex: 0,
		itemIndex: 0,
	});

	return (
		<KeyboardContext.Provider
			value={{
				columnSizes,
				setTarget,
				target,
			}}
		>
			{children}
		</KeyboardContext.Provider>
	);
}

export {KeyboardContext, KeyboardNavigationProvider};
