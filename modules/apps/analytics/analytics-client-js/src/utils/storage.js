/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ProcessLock from 'browser-tabs-lock';

import {ENV} from '../analytics';

const getItem = (key) => {
	const cookieManager = ENV.Analytics.getCookieManager();

	let data;

	try {
		if (cookieManager?.actions) {
			data = cookieManager.actions.getItem(key);
		}
		else {
			const match = document.cookie.match(
				new RegExp('(^| )' + key + '=([^;]+)')
			);

			if (match) {
				data = JSON.parse(decodeURIComponent(match[2]));
			}
		}
	}
	catch (error) {
		return;
	}

	return data;
};

const getItemFromLocalStorage = (key) => {
	const cookieManager = ENV.Analytics.getCookieManager();

	let data;

	try {
		let item;

		if (cookieManager?.actions) {
			data = cookieManager.actions.getItemFromLocalStorage(key);
		}
		else {
			item = localStorage.getItem(key);

			data = JSON.parse(item);
		}
	}
	catch (error) {
		return;
	}

	return data;
};

const setItem = (key, value, encode = true) => {
	const cookieManager = ENV.Analytics.getCookieManager();

	const expires = new Date();

	expires.setDate(expires.getDate() + 365);

	try {
		const jsonStr = JSON.stringify(value);
		const data = encode ? encodeURIComponent(jsonStr) : jsonStr;

		if (cookieManager?.actions) {
			cookieManager.actions.setItem(key, value, encode);
		}
		else {
			document.cookie = `${key}=${data}; expires=${expires.toUTCString()}; path=/; Secure`;
		}
	}
	catch (error) {
		return;
	}
};

const removeItem = (key) => {
	const cookieManager = ENV.Analytics.getCookieManager();

	try {
		if (cookieManager?.actions) {
			cookieManager.actions.removeItem(key);
		}
		else {
			const expirationDate = new Date();

			expirationDate.setFullYear(expirationDate.getFullYear() - 1);

			document.cookie = `${key}=; expires=${expirationDate.toUTCString()}; path=/;`;
		}
	}
	catch (error) {
		return;
	}
};

/**
 * Get the stringified size of a value in kilobytes.
 *
 * @param {String} val - Stringifiable value.
 * @returns {Number} - Storage size in of value.
 */
const getStorageSizeInKb = (val) => {
	return Number((JSON.stringify(val).length * 2) / 1024);
};

/**
 * Verify storage size and dequeue 1 item when limit is reached.
 *
 * @description Because we are using a ProcessLock, no other process should
 * be able to acquire a lock for a particular key to run its callback
 * until the process with the active lock releases it.
 *
 * @param {string} storageKey - The storage key to verify size for.
 * @param {Number} limit - Limit of storage size for given storageKey.
 * @returns {Promise}
 */
const verifyStorageLimitForKey = (storageKey, limit) => {
	const storedValue = getItem(storageKey);

	if (!storedValue.length) {
		return Promise.resolve();
	}

	const lock = new ProcessLock();

	return lock.acquireLock(storageKey).then((success) => {
		if (success) {
			const totalSize = getStorageSizeInKb(storedValue);

			if (totalSize > limit) {
				setItem(storageKey, storedValue.slice(1));
			}

			return lock.releaseLock(storageKey);
		}
	});
};

/**
 * Get storaged item from cookies or localStorage
 *
 * @description Maintain compatibility between browsers that already have data saved
 * in local storage but now need to save it in cookies and therefore, we obtain the data
 * from localStorage, update the cookies with it to make the data consistent and avoid
 * sending a new identity and return the value.
 *
 * @param {String} key
 * @returns {String | undefined}
 */
const getItemFromCookiesOrLocalStorage = (key, encode = true) => {
	let item = getItem(key);

	if (!item) {
		const itemFromLocalStorage = getItemFromLocalStorage(key);

		if (itemFromLocalStorage) {
			localStorage.removeItem(key);

			setItem(key, itemFromLocalStorage, encode);

			item = itemFromLocalStorage;
		}
	}

	return item;
};

export {
	getItem,
	getItemFromCookiesOrLocalStorage,
	getStorageSizeInKb,
	removeItem,
	setItem,
	verifyStorageLimitForKey,
};
