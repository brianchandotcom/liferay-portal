/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useEventListener} from 'frontend-js-react-web';
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

	return [value, newValue => updater(newValue), () => updater({}, true)];
};

const setStorage = storage => key => useStorage(storage, key);

const useLocalStorage = setStorage(localStorage);
const useSessionStorage = setStorage(sessionStorage);

export {useLocalStorage, useSessionStorage};
