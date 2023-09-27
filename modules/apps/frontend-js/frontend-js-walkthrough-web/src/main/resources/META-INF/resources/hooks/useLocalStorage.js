/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
