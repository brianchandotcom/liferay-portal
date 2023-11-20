/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from '@liferay/frontend-js-react-web';
import {Reducer, ReducerState, useCallback, useReducer} from 'react';

const identity = <T>(a: T) => a;

export function useSafeReducer<R extends Reducer<any, any>, I>(
	reducer: R,
	initializerArg: I & ReducerState<R>,
	initializer: (arg: I & ReducerState<R>) => ReducerState<R> = identity
) {
	const [state, dispatch] = useReducer(reducer, initializerArg, initializer);
	const isMounted = useIsMounted();

	const safeDispatch = useCallback(
		(...args: Parameters<typeof dispatch>) => {
			if (isMounted()) {
				dispatch(...args);
			}
		},
		[dispatch, isMounted]
	);

	return [state, safeDispatch] as const;
}
