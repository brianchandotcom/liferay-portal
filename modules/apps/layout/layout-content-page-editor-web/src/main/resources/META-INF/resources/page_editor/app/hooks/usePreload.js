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

import {useIsMounted} from 'frontend-js-react-web';
import React from 'react';

const {useRef} = React;

/**
 * Provides a way to preload a module on demand.
 *
 * The returned `preload()` function expects an identifying `key` for
 * the module and an entry point (ie. path to the module), and returns a
 * promise that resolves to the loaded module's default export.
 */
export default function usePreload() {
	const preloading = useRef(new Map());

	const isMounted = useIsMounted();

	return function preload(key, entryPoint) {
		if (!preloading.current.get(key)) {
			preloading.current.set(
				key,
				new Promise((resolve, reject) => {
					Liferay.Loader.require(
						[entryPoint],
						Plugin => {
							if (isMounted()) {
								resolve(Plugin.default);
							}
						},
						error => {
							if (isMounted()) {
								// Reset to allow future retries.
								preloading.current.delete(key);
								reject(error);
							}
						}
					);
				})
			);
		}

		return preloading.current.get(key);
	};
}
