/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {useChannel} from '../hooks/useChannel';

export const AppContext = React.createContext({
	inputChannel: null,
	portletNamespace: null,
	propertiesViewURL: null,
	templateVariableGroups: [],
});

export function AppContextProvider({
	children,
	portletNamespace,
	propertiesViewURL,
	templateVariableGroups,
}) {
	const inputChannel = useChannel();

	return (
		<AppContext.Provider
			value={{
				inputChannel,
				portletNamespace,
				propertiesViewURL,
				templateVariableGroups,
			}}
		>
			{children}
		</AppContext.Provider>
	);
}
