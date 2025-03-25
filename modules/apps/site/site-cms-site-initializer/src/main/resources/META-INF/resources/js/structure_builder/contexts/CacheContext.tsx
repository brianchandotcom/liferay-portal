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

export type Cache = {
	picklists: {
		data: Picklist[];
		fetcher: () => Promise<Picklist[]>;
		status: 'idle' | 'saving' | 'saved';
	};
	spaces: {
		data: Space[];
		fetcher: () => Promise<Space[]>;
		status: 'idle' | 'saving' | 'saved';
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
	cache: Cache;
	update: <T extends CacheKey>(key: T, partial: Partial<Cache[T]>) => void;
}>({
	cache: INITIAL_CACHE,
	update: () => {},
});

function CacheContextProvider({children}: {children: ReactNode}) {
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
		<CacheContext.Provider value={{cache, update}}>
			{children}
		</CacheContext.Provider>
	);
}

function useCache<T extends CacheKey>(key: T): Cache[T] {
	const {cache, update} = useContext(CacheContext);

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

	return item;
}

function useClearCache() {
	const {update} = useContext(CacheContext);

	return (key: CacheKey) => update(key, INITIAL_CACHE[key]);
}

export default CacheContextProvider;

export {CacheContext, useCache, useClearCache};
