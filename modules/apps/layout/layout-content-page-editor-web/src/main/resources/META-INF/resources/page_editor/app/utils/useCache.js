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

import {useEffect} from 'react';

import {useDispatch} from '../contexts/StoreContext';
import {CACHE_STATUS, getCacheItem, getCacheKey, setCacheItem} from './cache';

export default function useCache({fetcher, key}) {
	const dispatch = useDispatch();

	const cacheKey = getCacheKey(key);

	const {data, status} = getCacheItem(cacheKey);

	useEffect(() => {
		if (!data && cacheKey && status !== CACHE_STATUS.loading) {
			setCacheItem({key: cacheKey, status: CACHE_STATUS.loading});

			fetcher().then((response) => {
				setCacheItem({
					data: response,
					key: cacheKey,
					status: CACHE_STATUS.saved,
				});
			});
		}
	}, [cacheKey, dispatch, fetcher, data, status]);

	return data;
}
