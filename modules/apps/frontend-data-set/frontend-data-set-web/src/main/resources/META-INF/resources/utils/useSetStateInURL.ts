/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {writeStateInURL} from './stateInURL';
import {EStateInURLSettings, IStateInURL} from './types';

function useSetStateInURL<K extends keyof IStateInURL>({
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
