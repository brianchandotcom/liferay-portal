/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from '@liferay/frontend-js-react-web';
import {isNullOrUndefined} from '@liferay/layout-js-components-web';
import {useCallback, useEffect, useState} from 'react';

import {useDispatch} from '../contexts/StoreContext';
import {
	CACHE_STATUS,
	deleteCacheItem,
	getCacheItem,
	getCacheKey,
	setCacheItem,
} from './cache';

export default function useCache({fetcher, key}) {
	const dispatch = useDispatch();

	const cacheKey = getCacheKey(key);

	const {data: cachedData} = getCacheItem(cacheKey);

	const triggerRender = useTriggerRender();

	useEffect(() => {
		const {data, loadPromise, status} = getCacheItem(cacheKey);

		if (
			isNullOrUndefined(data) &&
			cacheKey &&
			status !== CACHE_STATUS.loading
		) {
			const nextLoadPromise = fetcher();

			setCacheItem({
				key: cacheKey,
				loadPromise: nextLoadPromise,
				status: CACHE_STATUS.loading,
			});

			nextLoadPromise
				.then((response) => {
					if (response.error) {
						deleteCacheItem(cacheKey);
					}
					else {
						setCacheItem({
							data: response,
							key: cacheKey,
							status: CACHE_STATUS.saved,
						});
					}

					triggerRender();
				})
				.catch(() => deleteCacheItem(cacheKey));
		}
		else if (status === CACHE_STATUS.loading && loadPromise) {
			loadPromise.then(triggerRender);
		}
	}, [cacheKey, dispatch, fetcher, triggerRender]);

	return cachedData;
}

function useTriggerRender() {
	const isMounted = useIsMounted();
	const [, setRenderFlag] = useState(false);

	return useCallback(() => {
		if (isMounted()) {
			setRenderFlag((flag) => !flag);
		}
	}, [isMounted]);
}
