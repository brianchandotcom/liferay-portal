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
