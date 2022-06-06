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

import {useIsMounted} from '@liferay/frontend-js-react-web';
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

		if (!data && cacheKey && status !== CACHE_STATUS.loading) {
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
