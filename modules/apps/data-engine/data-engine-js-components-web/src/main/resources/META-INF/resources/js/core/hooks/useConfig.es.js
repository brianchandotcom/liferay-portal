/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useRef} from 'react';

const ConfigContext = React.createContext({});

ConfigContext.displayName = 'ConfigContext';

/**
 * A provider to store any configuration or property that has no
 * side effect during the life cycle of the application.
 *
 * Maintaining configuration properties with side effect properties
 * in the same `store` may be rendering unnecessary components
 * that use only configuration properties.
 */
export function ConfigProvider({children, config, initialConfig}) {

	// Use `useRef` to avoid causing a new rendering of components that
	// consume context data. We do not want to cause a new rendering after
	// it initializes the app, this data will not change during the life
	// cycle of the application.

	const configRef = useRef({...initialConfig, ...config});

	return (
		<ConfigContext.Provider value={configRef.current}>
			{children}
		</ConfigContext.Provider>
	);
}

ConfigProvider.displayName = 'ConfigProvider';

export function useConfig() {
	return useContext(ConfigContext);
}
