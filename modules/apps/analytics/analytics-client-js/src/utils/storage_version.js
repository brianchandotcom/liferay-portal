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

import {STORAGE_KEY_CONTEXTS, STORAGE_KEY_STORAGE_VERSION} from './constants';
import {setContexts} from './contexts';
import {getItem, setItem} from './storage';

export const AC_CLIENT_STORAGE_VERSION = 1.0;

const upgradeStorageSteps = [
	[
		1.0,
		() => {
			const storedContextKvArr = getItem(STORAGE_KEY_CONTEXTS);

			if (storedContextKvArr && !Array.isArray(storedContextKvArr[0])) {
				const storedContexts = new Map();

				setContexts(storedContexts);
			}
		},
	],
];

function getStorageVersion() {
	const storageVersion = getItem(STORAGE_KEY_STORAGE_VERSION);

	return storageVersion ? parseFloat(storageVersion) : 0;
}

function setStorageVersion(version = AC_CLIENT_STORAGE_VERSION) {
	return setItem(STORAGE_KEY_STORAGE_VERSION, version.toString());
}

function upgradeStorage() {
	const version = getStorageVersion();

	if (version === AC_CLIENT_STORAGE_VERSION) {
		return true;
	}

	upgradeStorageSteps.forEach(([stepVersion, upgradeFn]) => {
		if (stepVersion > version) {
			upgradeFn();
		}
	});

	setStorageVersion(AC_CLIENT_STORAGE_VERSION);

	return true;
}

export {getStorageVersion, setStorageVersion, upgradeStorage};
