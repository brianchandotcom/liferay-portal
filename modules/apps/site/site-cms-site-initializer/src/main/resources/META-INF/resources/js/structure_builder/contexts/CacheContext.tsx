/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	MutableRefObject,
	ReactNode,
	RefObject,
	createContext,
	useCallback,
	useContext,
	useEffect,
	useRef,
	useState,
} from 'react';

import PicklistService from '../../services/PicklistService';
import SpaceService from '../../services/SpaceService';
import {Picklist} from '../../types/Picklist';
import {Space} from '../../types/Space';
import StructureService from '../services/StructureService';
import {Structures} from '../types/Structure';

export type CacheKey = 'picklists' | 'spaces' | 'structures';
export type CacheStatus = 'idle' | 'saving' | 'saved' | 'stale';

export type Cache = {
	picklists: {
		data: Picklist[];
		fetcher: () => Promise<Picklist[]>;
		status: CacheStatus;
	};
	spaces: {
		data: Space[];
		fetcher: () => Promise<Space[]>;
		status: CacheStatus;
	};
	structures: {
		data: Structures;
		fetcher: () => Promise<Structures>;
		status: CacheStatus;
	};
};

type InitialData = Partial<{
	[K in keyof Cache]: Cache[K]['data'];
}>;

function getInitialCache(initialData: InitialData = {}): Cache {
	return {
		picklists: {
			data: initialData.picklists ?? [],
			fetcher: PicklistService.getPicklists,
			status: initialData.picklists ? 'saved' : 'idle',
		},
		spaces: {
			data: initialData.spaces ?? [],
			fetcher: SpaceService.getSpaces,
			status: initialData.spaces ? 'saved' : 'idle',
		},
		structures: {
			data: initialData.structures ?? new Map(),
			fetcher: StructureService.getStructures,
			status: initialData.structures ? 'saved' : 'idle',
		},
	};
}

const CacheContext = createContext<{
	broadcastRef: RefObject<BroadcastChannel>;
	cache: Cache;
	promisesRef: MutableRefObject<Partial<Record<CacheKey, Promise<void>>>>;
	update: <T extends CacheKey>(key: T, partial: Partial<Cache[T]>) => void;
}>({
	broadcastRef: {current: null},
	cache: getInitialCache(),
	promisesRef: {current: {}},
	update: () => {},
});

function CacheContextProvider({
	children,
	initialData,
}: {
	children: ReactNode;
	initialData: InitialData;
}) {
	const broadcastRef = useRef(new BroadcastChannel('update-cache'));

	const promisesRef = useRef({});

	const [cache, setCache] = useState(getInitialCache(initialData));

	const update = <T extends CacheKey>(key: T, partial: Partial<Cache[T]>) => {
		setCache((current) => ({
			...current,
			[key]: {
				...current[key],
				...partial,
			},
		}));
	};

	useEffect(() => {
		const broadcast = broadcastRef.current;

		return () => {
			broadcast.close();
		};
	}, []);

	return (
		<CacheContext.Provider
			value={{broadcastRef, cache, promisesRef, update}}
		>
			{children}
		</CacheContext.Provider>
	);
}

function useCache<T extends CacheKey>(
	key: T
): Cache[T] & {load: () => Promise<void>} {
	const {broadcastRef, cache, promisesRef, update} = useContext(CacheContext);

	const item = cache[key];

	const promises = promisesRef.current;

	const load = useCallback(async () => {
		const existingPromise = promises[key];

		if (existingPromise) {
			await existingPromise;

			return;
		}

		const fetchData = async () => {
			update(key, {status: 'saving'} as Partial<Cache[T]>);

			try {
				const response = await item.fetcher();

				update(key, {data: response, status: 'saved'} as Partial<
					Cache[T]
				>);
			}
			catch {
				update(key, {status: 'stale'} as Partial<Cache[T]>);
			}
			finally {
				delete promises[key];
			}
		};

		const promise = fetchData();

		promises[key] = promise;

		await promise;
	}, [item, key, promises, update]);

	useEffect(() => {
		if (item.status !== 'idle') {
			return;
		}

		load();
	}, [item, load]);

	useEffect(() => {
		const broadcast = broadcastRef.current;

		const staleCache = ({data}: MessageEvent) => {
			if (data.type !== 'staleCache' || data.key !== key) {
				return;
			}

			update(key, {status: 'stale'} as Partial<Cache[T]>);
		};

		broadcast?.addEventListener('message', staleCache);

		return () => {
			broadcast?.removeEventListener('message', staleCache);
		};
	}, [broadcastRef, item, update, key]);

	return {...item, load};
}

function useStaleCache() {
	const {broadcastRef} = useContext(CacheContext);

	return (key: CacheKey) => {
		broadcastRef.current?.postMessage({key, type: 'staleCache'});
	};
}
export default CacheContextProvider;

export {CacheContext, useCache, useStaleCache};
