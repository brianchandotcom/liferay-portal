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

import {useCallback, useMemo, useState} from 'react';
import TestrayStorage, {STORAGE_KEYS} from '~/core/Storage';
import {StorageType} from '~/services/rest';
import {CONSENT_TYPE} from '~/util/enum';

type UseStorage<T> = [T, (value: T) => void];

const testrayStorage = TestrayStorage.getInstance();

type UseStorageOptions<T> = {
	consentType?: CONSENT_TYPE;
	initialValue?: T;
	storageType: StorageType;
};

const useStorage = <T = string>(
	key: STORAGE_KEYS,
	{consentType, initialValue, storageType}: UseStorageOptions<T> = {
		storageType: 'persisted',
	}
): UseStorage<T> => {
	const storage = useMemo(() => testrayStorage.getStorage(storageType), [
		storageType,
	]);

	const [storedValue, setStoredValue] = useState(() => {
		let storageValue;

		try {
			storageValue = storage.getItem(key, consentType);

			return storageValue ? JSON.parse(storageValue) : initialValue;
		}
		catch (error) {
			console.error(error);

			return storageValue || initialValue;
		}
	});

	const setStorageValue = useCallback(
		(value: T) => {
			try {
				setStoredValue(value);

				storage.setItem(key, JSON.stringify(value), consentType);
			}
			catch (error) {
				console.error(error);
			}
		},
		[key, consentType, storage]
	);

	return [storedValue, setStorageValue];
};

export default useStorage;
