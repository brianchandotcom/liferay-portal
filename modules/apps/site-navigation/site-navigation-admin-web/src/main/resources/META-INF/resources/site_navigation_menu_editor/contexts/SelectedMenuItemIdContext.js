/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {COOKIE_TYPES, sessionStorage} from 'frontend-js-web';
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

		sessionStorage.removeItem(selectedMenuItemIdKey);

		return persistedSelectedMenuItemId || null;
	});

	const updateSelectedMenuItemId = useCallback(
		(nextMenuItemId, {persist = false} = {}) => {
			setSelectedMenuItemId(nextMenuItemId);

			if (persist && nextMenuItemId) {
				sessionStorage.setItem(
					selectedMenuItemIdKey,
					nextMenuItemId,
					COOKIE_TYPES.PERSONALIZATION
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
