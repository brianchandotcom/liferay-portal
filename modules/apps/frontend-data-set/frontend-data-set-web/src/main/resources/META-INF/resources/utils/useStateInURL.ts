/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useLayoutEffect, useMemo, useRef} from 'react';

import {EViewsActionTypes} from '../views/viewsReducer';
import {readStateFromURL, writeStateInURL} from './stateInURL';
import {
	EStateInURLSettings,
	IStateInURL,
	IStateInURLGetter,
	IStateInURLUpdaterThunk,
	IStateReader,
	IStateWriter,
} from './types';

function useStateInURL<K extends keyof IStateInURL>({
	additionalStateDispatchers = [],
	id,
	shouldWriteInURL = (_value: IStateInURL[K]) => true,
	stateDispatcher,
	stateInURLSettings,
	stateReader,
	stateWriter = (value: IStateInURL[K]) => value,
}: {
	additionalStateDispatchers?: {
		key: keyof IStateInURL;
		type: EViewsActionTypes;
		value: any;
	}[];
	id: string;
	shouldWriteInURL?: (value: IStateInURL[K]) => boolean;
	stateDispatcher: {
		key: K;
		type: EViewsActionTypes;
	};
	stateInURLSettings: EStateInURLSettings;
	stateReader: IStateReader<K>;
	stateWriter?: IStateWriter<K>;
}): [IStateInURLGetter<K>, IStateInURLUpdaterThunk<K>] {
	const {key, type} = stateDispatcher;

	return [
		useGetter({id, key, stateReader}),
		useUpdaterThunk({
			additionalStateDispatchers,
			id,
			key,
			shouldWriteInURL,
			stateInURLSettings,
			stateWriter,
			type,
		}),
	];
}

function useGetter<K extends keyof IStateInURL>({
	id,
	key,
	stateReader,
}: {
	id: string;
	key: K;
	stateReader: IStateReader<K>;
}): IStateInURLGetter<K> {
	return useCallback((): IStateInURL[K] | undefined => {
		const state: Partial<IStateInURL> | null = readStateFromURL(id);

		if (!state || !state[key]) {
			return undefined;
		}

		return stateReader(state[key] as IStateInURL[K]);
	}, [id, stateReader, key]);
}

function useUpdaterThunk<K extends keyof IStateInURL>({
	additionalStateDispatchers = [],
	id,
	key,
	shouldWriteInURL = (_value: IStateInURL[K]) => true,
	stateInURLSettings,
	stateWriter,
	type,
}: {
	additionalStateDispatchers?: {
		key: keyof IStateInURL;
		type: EViewsActionTypes;
		value: any;
	}[];
	id: string;
	key: K;
	shouldWriteInURL?: (value: IStateInURL[K]) => boolean;
	stateInURLSettings: EStateInURLSettings;
	stateWriter?: IStateWriter<K>;
	type: EViewsActionTypes;
}): IStateInURLUpdaterThunk<K> {
	const additionalStateDispatchersKey = JSON.stringify(
		additionalStateDispatchers
	);

	const memoizedAdditionalStateDispatchers: {
		key: keyof IStateInURL;
		type: EViewsActionTypes;
		value: any;
	}[] = useMemo(
		() => JSON.parse(additionalStateDispatchersKey),
		[additionalStateDispatchersKey]
	);

	const shouldWriteInURLRef = useRef(shouldWriteInURL);

	const stateWriterRef = useRef(stateWriter);

	useLayoutEffect(() => {
		shouldWriteInURLRef.current = shouldWriteInURL;
		stateWriterRef.current = stateWriter;
	});

	return useCallback(
		(value: IStateInURL[K]) => {
			return (viewsDispatch: Function) => {
				const newState: Partial<IStateInURL> = {
					[key]: stateWriterRef.current?.(value),
				};

				if (
					!memoizedAdditionalStateDispatchers ||
					!memoizedAdditionalStateDispatchers.length
				) {
					viewsDispatch({
						type,
						value,
					});
				}
				else {
					const stateUpdates: Array<{
						type: EViewsActionTypes;
						value: IStateInURL[keyof IStateInURL];
					}> = [];

					stateUpdates.push({
						type,
						value,
					});

					memoizedAdditionalStateDispatchers.forEach(
						(stateDispatcher) => {
							stateUpdates.push({
								type: stateDispatcher.type,
								value: stateDispatcher.value,
							});

							newState[stateDispatcher.key] =
								stateDispatcher.value;
						}
					);

					viewsDispatch({
						type: EViewsActionTypes.BATCH_UPDATE,
						value: stateUpdates,
					});
				}

				const shouldWriteInURL =
					shouldWriteInURLRef.current?.(value) ?? true;

				if (!shouldWriteInURL) {
					newState[key] = undefined;
				}

				writeStateInURL(id, newState, stateInURLSettings);
			};
		},
		[
			id,
			key,
			memoizedAdditionalStateDispatchers,
			stateInURLSettings,
			type,
			shouldWriteInURLRef,
			stateWriterRef,
		]
	);
}

export default useStateInURL;
