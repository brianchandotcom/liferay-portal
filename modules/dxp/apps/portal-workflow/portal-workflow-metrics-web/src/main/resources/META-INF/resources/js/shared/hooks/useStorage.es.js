/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEventListener} from '@liferay/frontend-js-react-web';
import {sessionStorage} from 'frontend-js-web';
import {useCallback, useState} from 'react';

import {jsonStorage} from '../util/storage.es';

const useStorage = (storageType, key) => {
	const {get, remove, set} = jsonStorage(storageType);
	const [value, setValue] = useState(get(key));

	const listener = useCallback(
		({detail}) => {
			if (detail.key === key) {
				setValue(detail.newValue);
			}
		},
		[key]
	);

	useEventListener('storage_change', listener, false, window);

	const updater = (newValue, removeItem) => {
		if (removeItem) {
			remove(key);
		}
		else {
			set(key, newValue);
		}

		window.dispatchEvent(
			new CustomEvent('storage_change', {detail: {key, newValue}})
		);
	};

	return [value, (newValue) => updater(newValue), () => updater({}, true)];
};

const setStorage = (storage) => (key) => useStorage(storage, key);

const useSessionStorage = setStorage(sessionStorage);

export {useSessionStorage};
