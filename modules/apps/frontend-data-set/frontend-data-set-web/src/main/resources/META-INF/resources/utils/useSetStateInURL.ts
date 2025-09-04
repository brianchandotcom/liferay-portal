/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {writeStateInURL} from './stateInURL';
import {EStateInURLSettings, IStateInURL, IStateInURLSetters} from './types';

function useSetStateInURL({
	id,
	setters,
	stateInURLSettings,
}: {
	id: string;
	setters: {
		key: keyof IStateInURL;
		type: string;
	}[];
	stateInURLSettings: EStateInURLSettings;
}): IStateInURLSetters {
	const stateSetters = {};

	for (const setter of setters) {
		const {key, type} = setter;

		(stateSetters as IStateInURLSetters)[key] = getSetter({
			id,
			key,
			stateInURLSettings,
			type,
		});
	}

	return stateSetters as IStateInURLSetters;
}

function getSetter<K extends keyof IStateInURL>({
	id,
	key,
	stateInURLSettings,
	type,
}: {
	id: string;
	key: K;
	stateInURLSettings: EStateInURLSettings;
	type: string;
}) {
	return (value: IStateInURL[K]) => {
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
	};
}

export default useSetStateInURL;
