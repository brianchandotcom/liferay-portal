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

const getJsonItem = (storage, key) => {
	const item = storage.getItem(key) || '{}';
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

	storage.setItem(key, jsonString);
};

const jsonStorage = storage => {
	return {
		get: key => getJsonItem(storage, key),
		remove: key => storage.removeItem(key),
		set: (key, value) => setJsonItem(storage, key, value)
	};
};

const jsonSessionStorage = jsonStorage(sessionStorage);
const jsonLocalStorage = jsonStorage(localStorage);

export {jsonLocalStorage, jsonSessionStorage, jsonStorage};
