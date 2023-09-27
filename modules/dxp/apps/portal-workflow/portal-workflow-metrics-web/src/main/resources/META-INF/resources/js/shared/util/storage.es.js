/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sessionStorage} from 'frontend-js-web';

const getJsonItem = (storage, key) => {
	const item = storage.getItem(key, storage.TYPES.FUNCTIONAL) || '{}';
	let jsonItem;

	try {
		jsonItem = JSON.parse(item);
	}
	catch {
		jsonItem = item;
	}

	return jsonItem;
};

const setJsonItem = (storage, key, json = {}) => {
	let jsonString;

	try {
		jsonString = JSON.stringify(json);
	}
	catch {
		jsonString = json;
	}

	storage.setItem(key, jsonString, storage.TYPES.FUNCTIONAL);
};

const jsonStorage = (storage) => {
	return {
		get: (key) => getJsonItem(storage, key),
		remove: (key) => storage.removeItem(key),
		set: (key, value) => setJsonItem(storage, key, value),
	};
};

const jsonSessionStorage = jsonStorage(sessionStorage);

export {jsonSessionStorage, jsonStorage};
