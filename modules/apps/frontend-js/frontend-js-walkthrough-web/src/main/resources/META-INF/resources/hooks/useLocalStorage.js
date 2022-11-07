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

import {localStorage} from 'frontend-js-web';
import {useCallback, useState} from 'react';

export function useLocalStorage(key, initialValue) {
	const [storedValue, setStoredValue] = useState(() => {
		const value =
			typeof initialValue === 'function' ? initialValue() : initialValue;

		try {
			const item = localStorage.getItem(
				key,
				localStorage.TYPES.NECESSARY
			);

			return item ? JSON.parse(item) : value;
		}
		catch (error) {
			console.error(error);

			return value;
		}
	});

	const setValue = useCallback(
		(value) => {
			try {
				const valueToStore =
					value instanceof Function ? value(storedValue) : value;

				setStoredValue(valueToStore);

				localStorage.setItem(
					key,
					JSON.stringify(valueToStore),
					localStorage.TYPES.NECESSARY
				);
			}
			catch (error) {
				console.error(error);
			}
		},
		[storedValue, key]
	);

	return [storedValue, setValue];
}
