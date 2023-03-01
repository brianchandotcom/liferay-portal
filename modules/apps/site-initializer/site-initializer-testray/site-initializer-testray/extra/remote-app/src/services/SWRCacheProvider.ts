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

import TestrayStorage, {STORAGE_KEYS} from '~/core/Storage';
import {CONSENT_TYPE} from '~/util/enum';

const testrayStorage = TestrayStorage.getInstance().getStorage('temporary');

/**
 * @description When initializing, we restore the data from `STORAGE` into a map.
 * Before unloading the app, we write back all the data into `STORAGE`.
 * We still use the map for write & read for performance.
 */

const SWRCacheProvider = (): Map<any, any> => {
	const cacheMap = new Map(
		JSON.parse(
			testrayStorage.getItem(
				STORAGE_KEYS.SWR_CACHE,
				CONSENT_TYPE.PERFORMANCE
			) || '[]'
		)
	);

	window.addEventListener('beforeunload', () => {
		const appCache = JSON.stringify(Array.from(cacheMap.entries()));

		testrayStorage.setItem(
			STORAGE_KEYS.SWR_CACHE,
			appCache,
			CONSENT_TYPE.PERFORMANCE
		);
	});

	return cacheMap;
};

export default SWRCacheProvider;
