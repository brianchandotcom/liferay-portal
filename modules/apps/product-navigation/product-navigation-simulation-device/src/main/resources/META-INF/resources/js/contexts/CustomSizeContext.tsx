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

import {SIZES} from '../constants/sizes';

interface State {
	height: number;
	setHeight: Dispatch<SetStateAction<number>>;
	setWidth: Dispatch<SetStateAction<number>>;
	width: number;
}

const INITIAL_STATE: State = {
	height: SIZES.custom.screenSize.height,
	setHeight: () => {},
	setWidth: () => {},
	width: SIZES.custom.screenSize.width,
};

const CustomSizeContext = createContext(INITIAL_STATE);

const useSetCustomWidth = () => {
	return useContext(CustomSizeContext).setWidth;
};

const useSetCustomHeight = () => {
	return useContext(CustomSizeContext).setHeight;
};

const useCustomSize = () => {
	const {height, width} = useContext(CustomSizeContext);

	return {height, width};
};

function CustomSizeContextProvider({children}: {children: React.ReactNode}) {
	const [height, setHeight] = useState<number>(
		SIZES.custom.screenSize.height
	);
	const [width, setWidth] = useState<number>(SIZES.custom.screenSize.width);

	return (
		<CustomSizeContext.Provider
			value={{height, setHeight, setWidth, width}}
		>
			{children}
		</CustomSizeContext.Provider>
	);
}

export {
	CustomSizeContextProvider,
	useSetCustomHeight,
	useSetCustomWidth,
	useCustomSize,
};
