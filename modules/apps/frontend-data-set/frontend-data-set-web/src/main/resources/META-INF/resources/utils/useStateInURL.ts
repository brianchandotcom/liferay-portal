/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	MutableRefObject,
	useCallback,
	useLayoutEffect,
	useMemo,
	useRef,
} from 'react';

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

type stateWriters = Record<
	string,
	Partial<{
		[key in keyof IStateInURL]: MutableRefObject<IStateWriter<key>>;
	}>
>;

const STATE_WRITERS: stateWriters = {};

function registerStateWriter<K extends keyof IStateInURL>({
	id,
	key,
	stateWriterRef,
}: {
	id: string;
	key: K;
	stateWriterRef: MutableRefObject<IStateWriter<K>>;
}): void {
	if (!STATE_WRITERS[id]) {
		STATE_WRITERS[id] = {};
	}

	(
		STATE_WRITERS[id] as Record<
			keyof IStateInURL,
			MutableRefObject<IStateWriter<any>>
		>
	)[key] = stateWriterRef;
}

function updateState({
	id,
	state,
	stateInURLSettings,
}: {
	id: string;
	state: Partial<IStateInURL>;
	stateInURLSettings: EStateInURLSettings;
}) {
	const updatedState: Partial<IStateInURL> = {};

	Object.keys(state).forEach((key: string) => {
		const stateWriterRef = (
			STATE_WRITERS[id] as Record<
				keyof IStateInURL,
				MutableRefObject<IStateWriter<any>>
			>
		)[key as keyof IStateInURL];

		updatedState[key as keyof IStateInURL] = stateWriterRef.current?.(
			state[key as keyof IStateInURL]
		);
	});

	writeStateInURL(id, updatedState, stateInURLSettings);
}

export function useUpdateState({
	id,
	stateInURLSettings,
}: {
	id: string;
	stateInURLSettings: EStateInURLSettings;
}): Function {
	return useCallback(
		(state: Partial<IStateInURL>) =>
			updateState({id, state, stateInURLSettings}),
		[id, stateInURLSettings]
	);
}

function useStateInURL<K extends keyof IStateInURL>({
	additionalStateDispatchers = [],
	id,
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
	stateInURLSettings,
	stateWriter = (value: IStateInURL[K]) => value,
	type,
}: {
	additionalStateDispatchers?: {
		key: keyof IStateInURL;
		type: EViewsActionTypes;
		value: any;
	}[];
	id: string;
	key: K;
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

	const stateWriterRef = useRef(stateWriter);

	useLayoutEffect(() => {
		stateWriterRef.current = stateWriter;
	});

	registerStateWriter({id, key, stateWriterRef});

	const updateState = useUpdateState({id, stateInURLSettings});

	return useCallback(
		(value: IStateInURL[K]) => {
			return (viewsDispatch: Function) => {
				const newState: Partial<IStateInURL> = {
					[key]: value,
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

				updateState(newState);
			};
		},
		[key, memoizedAdditionalStateDispatchers, type, updateState]
	);
}

export default useStateInURL;
