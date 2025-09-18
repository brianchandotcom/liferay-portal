/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback} from 'react';

import {VIEWS_ACTION_TYPES} from '../views/viewsReducer';
import {readStateFromURL, writeStateInURL} from './stateInURL';
import {
	EStateInURLSettings,
	IStateInURL,
	IStateInURLGetter,
	IStateInURLSetter,
	IStateInitializer,
} from './types';

function useStateInURL<K extends keyof IStateInURL>({
	id,
	stateDispatcher,
	stateInURLSettings,
	stateInitializer,
}: {
	id: string;
	stateDispatcher: {
		key: K;
		type: VIEWS_ACTION_TYPES;
	};
	stateInURLSettings: EStateInURLSettings;
	stateInitializer: IStateInitializer<K>;
}): [IStateInURLGetter<K>, IStateInURLSetter<K>] {
	const {key, type} = stateDispatcher;

	return [
		useGetter({id, key, stateInitializer}),
		useSetter({
			id,
			key,
			stateInURLSettings,
			type,
		}),
	];
}

function useGetter<K extends keyof IStateInURL>({
	id,
	key,
	stateInitializer,
}: {
	id: string;
	key: K;
	stateInitializer: IStateInitializer<K>;
}): IStateInURLGetter<K> {
	return useCallback((): IStateInURL[K] | undefined => {
		const state: Partial<IStateInURL> | null = readStateFromURL(id);

		if (!state || !state[key]) {
			return undefined;
		}

		return stateInitializer(state[key] as IStateInURL[K]);
	}, [id, stateInitializer, key]);
}

function useSetter<K extends keyof IStateInURL>({
	id,
	key,
	stateInURLSettings,
	type,
}: {
	id: string;
	key: K;
	stateInURLSettings: EStateInURLSettings;
	type: VIEWS_ACTION_TYPES;
}) {
	return useCallback(
		(value: IStateInURL[K]) => {
			return (viewsDispatch: Function) => {
				viewsDispatch({
					type,
					value,
				});

				const newState: Partial<IStateInURL> = {
					[key]: value,
				};

				writeStateInURL(id, newState, stateInURLSettings);
			};
		},
		[id, key, stateInURLSettings, type]
	);
}

export default useStateInURL;
