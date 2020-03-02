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

import React from 'react';

/**
 * Context for shared data, intended as a mechanism for sharing between
 * a host application that uses `usePlugins()` and its hosted plugin modules.
 */
const PluginContext = React.createContext({});

/**
 * Convenience function that returns a component that provides the passed
 * `value` as context.
 */
export const Component = value => ({children}) => (
	<PluginContext.Provider value={value}>{children}</PluginContext.Provider>
);

export default PluginContext;
