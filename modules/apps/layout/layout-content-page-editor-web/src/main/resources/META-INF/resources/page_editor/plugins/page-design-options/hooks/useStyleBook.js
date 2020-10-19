/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React, {useContext, useState} from 'react';

import {config} from '../../../app/config/index';

const StyleBookDispatchContext = React.createContext(() => {});
const StyleBookStateContext = React.createContext({
	styleBookEntryId: '',
	tokenValues: {},
});

export const StyleBookContextProvider = ({children}) => {
	const [styleBook, setStyleBook] = useState({
		styleBookEntryId: config.styleBookEntryId,
		tokenValues: config.frontendTokens,
	});

	return (
		<StyleBookDispatchContext.Provider value={setStyleBook}>
			<StyleBookStateContext.Provider value={styleBook}>
				{children}
			</StyleBookStateContext.Provider>
		</StyleBookDispatchContext.Provider>
	);
};

export const useSetStyleBook = () => useContext(StyleBookDispatchContext);
export const useStyleBook = () => useContext(StyleBookStateContext);
