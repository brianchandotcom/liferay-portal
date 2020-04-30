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

const {useCallback, useRef} = React;

/**
 * Provides a way to load a module on demand.
 *
 * The returned `load()` function expects an identifying `key` for
 * the module and an entry point (ie. path to the module), and returns a
 * promise that resolves to the loaded module's default export.
 */
export default function useLoad() {
	const modules = useRef(new Map());

	const isMounted = useIsMounted();

	return useCallback(
		(key, entryPoint) => {
			if (!modules.current.get(key)) {
				modules.current.set(
					key,
					new Promise((resolve, reject) => {
						Liferay.Loader.require(
							[entryPoint],
							(Plugin) => {
								if (isMounted()) {
									resolve(Plugin.default);
								}
							},
							(error) => {
								if (isMounted()) {

									// Reset to allow future retries.

									modules.current.delete(key);
									reject(error);
								}
							}
						);
					})
				);
			}

			return modules.current.get(key);
		},
		[isMounted]
	);
}
