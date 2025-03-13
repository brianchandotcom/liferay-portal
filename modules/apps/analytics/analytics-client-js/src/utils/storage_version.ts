/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Analytics} from '../types';
import {setContexts} from './contexts';
import {getItem, setItem} from './storage';

export const AC_CLIENT_STORAGE_VERSION = 1.0;

const upgradeStorageSteps: [number, () => void][] = [
	[
		1.0,
		() => {
			const storedContextKvArr = getItem<Analytics.Context[][]>(
				Analytics.Keys.Contexts
			);

			if (storedContextKvArr && !Array.isArray(storedContextKvArr[0])) {
				const storedContexts = new Map();

				setContexts(storedContexts);
			}
		},
	],
];

function getStorageVersion() {
	const storageVersion = getItem<string>(Analytics.Keys.StorageVersion);

	return storageVersion ? parseFloat(storageVersion) : 0;
}

function setStorageVersion(version = AC_CLIENT_STORAGE_VERSION) {
	return setItem(Analytics.Keys.StorageVersion, version.toString());
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
