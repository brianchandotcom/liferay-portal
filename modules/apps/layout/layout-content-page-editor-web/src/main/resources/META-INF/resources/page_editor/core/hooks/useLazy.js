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

const {lazy, useCallback, useRef} = React;

/**
 * Returns a component that takes a `pluginId` and a `getInstance`
 * function (for obtaining a promise of a plugin instance) and wraps it
 * in a `React.lazy` wrapper.
 *
 * The supplied callback will be called with the plugin instance once the
 * promise resolves, and should return something to be rendered.
 */
export default function useLazy(callback) {
	const components = useRef(new Map());

	return useCallback(
		({getInstance, pluginId}) => {
			if (!components.current.has(pluginId)) {
				const plugin = getInstance(pluginId);

				const Component = lazy(() => {
					return plugin.then(instance => {
						return {
							default: () => {
								if (instance) {
									return callback({instance});
								}
								else {
									return null;
								}
							}
						};
					});
				});

				components.current.set(pluginId, Component);
			}
			const Component = components.current.get(pluginId);

			return <Component />;
		},
		[callback]
	);
}
