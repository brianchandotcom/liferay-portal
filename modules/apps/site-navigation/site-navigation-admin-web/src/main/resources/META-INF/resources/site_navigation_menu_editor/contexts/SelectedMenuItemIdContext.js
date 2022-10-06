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

import React, {useCallback, useContext, useState} from 'react';

import {useConstants} from './ConstantsContext';

const SelectedMenuItemIdContext = React.createContext(null);
const SetSelectedMenuItemIdContext = React.createContext(() => {});

export function useSetSelectedMenuItemId() {
	return useContext(SetSelectedMenuItemIdContext);
}

export function useSelectedMenuItemId() {
	return useContext(SelectedMenuItemIdContext);
}

export function SelectedMenuItemIdProvider({children}) {
	const {portletNamespace} = useConstants();
	const selectedMenuItemIdKey = `${portletNamespace}_selectedMenuItemId`;

	const [selectedMenuItemId, setSelectedMenuItemId] = useState(() => {
		const persistedSelectedMenuItemId = window.sessionStorage.getItem(
			selectedMenuItemIdKey
		);

		window.sessionStorage.removeItem(selectedMenuItemIdKey);

		return persistedSelectedMenuItemId || null;
	});

	const updateSelectedMenuItemId = useCallback(
		(nextMenuItemId, {persist = false} = {}) => {
			setSelectedMenuItemId(nextMenuItemId);

			if (persist && nextMenuItemId) {
				window.sessionStorage.setItem(
					selectedMenuItemIdKey,
					nextMenuItemId
				);
			}
		},
		[selectedMenuItemIdKey]
	);

	return (
		<SetSelectedMenuItemIdContext.Provider value={updateSelectedMenuItemId}>
			<SelectedMenuItemIdContext.Provider value={selectedMenuItemId}>
				{children}
			</SelectedMenuItemIdContext.Provider>
		</SetSelectedMenuItemIdContext.Provider>
	);
}
