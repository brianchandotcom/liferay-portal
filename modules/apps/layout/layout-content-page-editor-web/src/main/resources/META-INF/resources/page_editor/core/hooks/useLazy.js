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

const {lazy, useCallback} = React;

/**
 * Returns a component that takes a promise of a plugin instance and
 * wraps it in a `React.lazy` wrapper. The supplied callback will be
 * called with the plugin instance once it resolves, and should return
 * something to be rendered.
 */
export default function useLazy(callback) {
	return useCallback(
		({plugin}) => {
			const Component = lazy(() => {
				return plugin.then(instance => {
					return {
						default: () => {
							if (instance) {
								return callback({instance});
							} else {
								return null;
							}
						}
					};
				});
			});

			return <Component />;
		},
		[callback]
	);
}
