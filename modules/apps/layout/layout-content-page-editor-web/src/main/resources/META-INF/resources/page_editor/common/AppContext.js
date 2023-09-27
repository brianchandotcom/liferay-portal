/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

/**
 * Context for shared data, intended as a mechanism for sharing between
 * a host application that uses `usePlugins()` and its hosted plugin modules.
 */
const AppContext = React.createContext({});

/**
 * Convenience function that returns a component that provides the passed
 * `value` as context.
 */
export function Component(value) {
	return ({children}) => (
		<AppContext.Provider value={value}>{children}</AppContext.Provider>
	);
}

export default AppContext;
