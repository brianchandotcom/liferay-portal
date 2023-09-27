/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

const DefinitionBuilderContext = React.createContext();

const DefinitionBuilderContextProvider = ({children, ...props}) => {
	return (
		<DefinitionBuilderContext.Provider value={props}>
			{children}
		</DefinitionBuilderContext.Provider>
	);
};

export {DefinitionBuilderContext, DefinitionBuilderContextProvider};
