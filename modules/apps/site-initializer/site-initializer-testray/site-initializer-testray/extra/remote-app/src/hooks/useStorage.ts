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

import {useState} from 'react';

type UseStorage<T> = [T, (value: T) => void];

const useStorage = <T = string>(
	key: string,
	initialValue?: T,
	storage: Storage = localStorage
): UseStorage<T> => {
	const [storedValue, setStoredValue] = useState(() => {
		let storageValue;

		try {
			storageValue = storage.getItem(key);

			return storageValue ? JSON.parse(storageValue) : initialValue;
		} catch (error) {
			console.error(error);

			return storageValue || initialValue;
		}
	});

	const setStorageValue = (value: T) => {
		try {
			setStoredValue(value);

			storage.setItem(key, JSON.stringify(value));
		} catch (error) {
			console.error(error);
		}
	};

	return [storedValue, setStorageValue];
};

export default useStorage;
