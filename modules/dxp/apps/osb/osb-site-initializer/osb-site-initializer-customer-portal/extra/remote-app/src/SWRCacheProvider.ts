/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {storage} from './common/services/liferay/storage';

const STORAGE_KEY = '@customer-portal/swr';

/**
 * @description When initializing, we restore the data from `STORAGE` into a map.
 * Before unloading the app, we write back all the data into `STORAGE`.
 * We still use the map for write & read for performance.
 */

const SWRCacheProvider = (): Map<any, any> => {
	const cacheMap = new Map(JSON.parse(storage.getItem(STORAGE_KEY) || '[]'));

	window.addEventListener('beforeunload', () => {
		const appCache = JSON.stringify(Array.from(cacheMap.entries()));

		storage.setItem(STORAGE_KEY, appCache);
	});

	return cacheMap;
};

export default SWRCacheProvider;
