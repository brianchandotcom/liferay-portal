/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
