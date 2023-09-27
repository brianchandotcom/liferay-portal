/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

const {useCallback, useRef} = React;

/**
 * Maintains a registry of plugins and provides methods for adding to and
 * looking things up in the registry.
 */
export default function usePlugins() {
	const pluginsRef = useRef(new Map());

	const getInstance = useCallback((key) => {
		return pluginsRef.current.get(key) || Promise.resolve(null);
	}, []);

	const register = useCallback((key, promise, init) => {
		if (!pluginsRef.current.has(key)) {
			pluginsRef.current.set(
				key,
				promise
					.then((Plugin) => new Plugin(init))
					.catch((error) => {
						if (process.env.NODE_ENV === 'development') {
							console.error(error);

							// Reset to allow future retries.

							pluginsRef.current.delete(key);
						}

						return null;
					})
			);
		}

		return pluginsRef.current.get(key);
	}, []);

	return {getInstance, register};
}
