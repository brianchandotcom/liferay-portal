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
export const ConfigProvider = ({children, config, initialConfig}) => {

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
};

ConfigProvider.displayName = 'ConfigProvider';

export const useConfig = () => useContext(ConfigContext);
