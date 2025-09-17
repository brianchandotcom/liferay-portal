/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {readStateFromURL} from './stateInURL';
import {IStateInURL} from './types';

type StateInitializer = {
	[K in keyof IStateInURL]: (value: IStateInURL[K]) => IStateInURL[K] | null;
};

function useGetStateFromURL({
	id,
	stateInitializers,
}: {
	id: string;
	stateInitializers?: StateInitializer;
}): () => Partial<IStateInURL> {
	return (): Partial<IStateInURL> => {
		const state: Partial<IStateInURL> | null = readStateFromURL(id);

		if (!state) {
			return {};
		}

		if (!stateInitializers) {
			return state;
		}

		const initializedState = {...state};

		for (const key of Object.keys(initializedState) as Array<
			keyof IStateInURL
		>) {
			const stateInitializer = stateInitializers[key];
			const stateValue = initializedState[key];

			if (stateInitializer && stateValue !== undefined) {
				const initializedValue = (stateInitializer as any)(stateValue);

				if (initializedValue !== null) {
					initializedState[key] = initializedValue;
				}
				else {
					delete initializedState[key];
				}
			}
		}

		return initializedState;
	};
}

export default useGetStateFromURL;
