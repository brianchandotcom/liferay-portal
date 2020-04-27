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

import ProcessLock from 'browser-tabs-lock';

const getItem = (key) => {
	let data;
	const item = localStorage.getItem(key);
	try {
		data = JSON.parse(item);
	}
	catch (e) {
		return;
	}

	return data;
};

const setItem = (key, value) => {
	try {
		localStorage.setItem(key, JSON.stringify(value));
	}
	catch (e) {
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
 * Note: Because we are using a ProcessLock, no other process should
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

export {getItem, getStorageSizeInKb, setItem, verifyStorageLimitForKey};
