/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext} from 'react';

export const ConstantsContext = createContext({});

interface Props {
	children: React.ReactNode;
	constants: object;
}

export function ConstantsContextProvider({children, constants}: Props) {
	return (
		<ConstantsContext.Provider value={constants}>
			{children}
		</ConstantsContext.Provider>
	);
}
