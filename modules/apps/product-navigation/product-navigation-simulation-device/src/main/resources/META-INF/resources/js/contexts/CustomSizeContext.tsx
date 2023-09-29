/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	Dispatch,
	SetStateAction,
	createContext,
	useContext,
	useState,
} from 'react';

import {SIZES, ScreenSize} from '../constants/sizes';

interface State {
	customSize: ScreenSize;
	setCustomSize: Dispatch<SetStateAction<ScreenSize>>;
}

const INITIAL_STATE: State = {
	customSize: SIZES.custom.screenSize,
	setCustomSize: () => {},
};

const CustomSizeContext = createContext(INITIAL_STATE);

const useSetCustomSize = () => {
	return useContext(CustomSizeContext).setCustomSize;
};

const useCustomSize = () => {
	const {customSize} = useContext(CustomSizeContext);

	return customSize;
};

function CustomSizeContextProvider({children}: {children: React.ReactNode}) {
	const [customSize, setCustomSize] = useState<ScreenSize>(
		SIZES.custom.screenSize
	);

	return (
		<CustomSizeContext.Provider value={{customSize, setCustomSize}}>
			{children}
		</CustomSizeContext.Provider>
	);
}

export {CustomSizeContextProvider, useCustomSize, useSetCustomSize};
