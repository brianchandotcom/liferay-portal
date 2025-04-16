/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ProcessLock from 'browser-tabs-lock';

import {Analytics} from '../types';

const getItem = <T>(key: string) => {
	const Liferay = window.Liferay;

	let data: T;

	try {
		let item;

		if (Liferay?.FeatureFlags?.['LPD-10588']) {
			item = Liferay?.Util?.LocalStorage?.getItem(
				key,
				Liferay?.Util?.LocalStorage?.TYPES?.PERFORMANCE as string
			);
		}
		else {
			item = localStorage.getItem(key);
		}

		data = JSON.parse(item as string);
	}
	catch (error) {
		return;
	}

	return data;
};

const setItem = <T>(key: string, value: T) => {
	const Liferay = window.Liferay;

	try {
		if (Liferay?.FeatureFlags?.['LPD-10588']) {
			Liferay?.Util?.LocalStorage?.setItem(
				key,
				JSON.stringify(value),
				Liferay?.Util?.LocalStorage?.TYPES?.PERFORMANCE as string
			);
		}
		else {
			localStorage.setItem(key, JSON.stringify(value));
		}
	}
	catch (error) {
		return;
	}
};

const removeItem = (key: string) => {
	const Liferay = window.Liferay;

	try {
		if (Liferay?.FeatureFlags?.['LPD-10588']) {
			Liferay?.Util?.LocalStorage?.removeItem(
				key,
				Liferay?.Util?.LocalStorage?.TYPES?.PERFORMANCE as string
			);
		}
		else {
			localStorage.removeItem(key);
		}
	}
	catch (error) {
		return;
	}
};

/**
 * Get the stringified size of a value in kilobytes.
 */
const getStorageSizeInKb = (val: any) => {
	return Number((JSON.stringify(val).length * 2) / 1024);
};

/**
 * Verify storage size and dequeue 1 item when limit is reached.
 *
 * Note: Because we are using a ProcessLock, no other process should
 * be able to acquire a lock for a particular key to run its callback
 * until the process with the active lock releases it.
 */
const verifyStorageLimitForKey = (storageKey: string, limit: number) => {
	const storedValue = getItem<Analytics.Event[]>(storageKey);

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

export {
	getItem,
	getStorageSizeInKb,
	removeItem,
	setItem,
	verifyStorageLimitForKey,
};
