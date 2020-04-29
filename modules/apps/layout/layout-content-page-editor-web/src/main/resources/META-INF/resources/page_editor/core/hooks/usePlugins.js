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

const {useCallback, useRef} = React;

/**
 * Maintains a registry of plugins and provides methods for adding to and
 * looking things up in the registry.
 */
export default function usePlugins() {
	const plugins = useRef(new Map());

	const getInstance = useCallback((key) => {
		return plugins.current.get(key) || Promise.resolve(null);
	}, []);

	const register = useCallback((key, promise, init) => {
		if (!plugins.current.has(key)) {
			plugins.current.set(
				key,
				promise
					.then((Plugin) => new Plugin(init))
					.catch((error) => {
						if (process.env.NODE_ENV === 'development') {
							console.error(error);

							// Reset to allow future retries.

							plugins.current.delete(key);
						}

						return null;
					})
			);
		}

		return plugins.current.get(key);
	}, []);

	return {getInstance, register};
}
