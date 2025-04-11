/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	ReactNode,
	createContext,
	useContext,
	useEffect,
	useState,
} from 'react';

import PicklistService from '../services/PicklistService';
import SpaceService from '../services/SpaceService';
import {Picklist} from '../types/Picklist';
import {Space} from '../types/Space';

type CacheKey = 'picklists' | 'spaces';
type Status = 'idle' | 'saving' | 'saved' | 'stale';

export type Cache = {
	picklists: {
		data: Picklist[];
		fetcher: () => Promise<Picklist[]>;
		status: Status;
	};
	spaces: {
		data: Space[];
		fetcher: () => Promise<Space[]>;
		status: Status;
	};
};

const INITIAL_CACHE: Cache = {
	picklists: {
		data: [],
		fetcher: PicklistService.getPicklists,
		status: 'idle',
	},
	spaces: {
		data: [],
		fetcher: SpaceService.getSpaces,
		status: 'idle',
	},
};

const CacheContext = createContext<{
	broadcast: BroadcastChannel;
	cache: Cache;
	update: <T extends CacheKey>(key: T, partial: Partial<Cache[T]>) => void;
}>({
	broadcast: {} as BroadcastChannel,
	cache: INITIAL_CACHE,
	update: () => {},
});

function CacheContextProvider({children}: {children: ReactNode}) {
	const [broadcast] = useState(() => new BroadcastChannel('update-cache'));
	const [cache, setCache] = useState(INITIAL_CACHE);

	const update = <T extends CacheKey>(key: T, partial: Partial<Cache[T]>) => {
		setCache((current) => ({
			...current,
			[key]: {
				...current[key],
				...partial,
			},
		}));
	};

	return (
		<CacheContext.Provider value={{broadcast, cache, update}}>
			{children}
		</CacheContext.Provider>
	);
}

function useCache<T extends CacheKey>(key: T): Cache[T] {
	const {broadcast, cache, update} = useContext(CacheContext);

	const item = cache[key];

	useEffect(() => {
		if (item.status !== 'idle') {
			return;
		}

		update(key, {status: 'saving'} as Partial<Cache[T]>);

		item.fetcher().then((response) => {
			update(key, {data: response, status: 'saved'} as Partial<Cache[T]>);
		});
	}, [item, key, update]);

	useEffect(() => {
		const staleCache = ({data}: MessageEvent) => {
			if (data.type !== 'staleCache' || data.key !== key) {
				return;
			}

			update(key, {status: 'stale'} as Partial<Cache[T]>);
		};

		broadcast.addEventListener('message', staleCache);

		return () => {
			broadcast.removeEventListener('message', staleCache);
		};
	}, [broadcast, item, update, key]);

	return item;
}

function useStaleCache() {
	const {broadcast} = useContext(CacheContext);

	return (key: CacheKey) => {
		broadcast.postMessage({key, type: 'staleCache'});
	};
}
export default CacheContextProvider;

export {CacheContext, useCache, useStaleCache};
