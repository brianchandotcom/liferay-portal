/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ProcessLock from 'browser-tabs-lock';

const getCookieFromDocument = (key) => {
	const name = key + '=';
	const decodedCookie = decodeURIComponent(document.cookie);
	const cookieArray = decodedCookie.split(';');

	for (let i = 0; i < cookieArray.length; i++) {
		let cookie = cookieArray[i];

		while (cookie.charAt(0) === ' ') {
			cookie = cookie.substring(1);
		}

		if (cookie.indexOf(name) === 0) {
			const jsonStr = cookie.substring(name.length, cookie.length);

			return JSON.parse(jsonStr);
		}
	}

	return null;
};

const getItem = (key) => {
	const Liferay = window.Liferay;
	let data;

	try {
		if (Liferay?.Util?.Cookie) {
			const cookie = Liferay.Util.Cookie.get(
				key,
				Liferay.Util.Cookie.TYPES.PERFORMANCE
			);

			data = JSON.parse(decodeURIComponent(cookie));
		}
		else {
			data = getCookieFromDocument(key);
		}
	}
	catch (error) {
		return;
	}

	return data;
};

const getItemFromLocalStorage = (key) => {
	const Liferay = window.Liferay;
	let data;

	try {
		let item;

		if (Liferay?.Util?.LocalStorage) {
			item = Liferay.Util.LocalStorage.getItem(
				key,
				Liferay.Util.LocalStorage.TYPES.PERSONALIZATION
			);
		}
		else {
			item = localStorage.getItem(key);
		}

		data = JSON.parse(item);
	}
	catch (error) {
		return;
	}

	return data;
};

const setItem = (key, value, encode = true) => {
	const Liferay = window.Liferay;
	const expires = new Date();

	expires.setDate(expires.getDate() + 365);

	try {
		const jsonStr = JSON.stringify(value);
		const data = encode ? encodeURIComponent(jsonStr) : jsonStr;

		if (Liferay?.Util?.Cookie) {
			Liferay.Util.Cookie.set(
				key,
				data,
				Liferay.Util.Cookie.TYPES.PERFORMANCE,
				{
					expires,
					secure: true,
				}
			);
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
	const Liferay = window.Liferay;

	try {
		if (Liferay?.Util?.Cookie) {
			Liferay.Util.Cookie.remove(
				key,
				Liferay.Util.Cookie.TYPES.PERFORMANCE
			);
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

	if (!storedValue?.length) {
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
const getItemFromCookiesOrLocalStorage = (key) => {
	let item = getItem(key);

	if (!item) {
		const itemFromLocalStorage = getItemFromLocalStorage(key);

		if (itemFromLocalStorage) {
			setItem(key, itemFromLocalStorage);

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
