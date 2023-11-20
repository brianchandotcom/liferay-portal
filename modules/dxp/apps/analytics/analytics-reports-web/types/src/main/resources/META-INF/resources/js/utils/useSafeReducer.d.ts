/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Reducer, ReducerState} from 'react';
export declare function useSafeReducer<R extends Reducer<any, any>, I>(
	reducer: R,
	initializerArg: I & ReducerState<R>,
	initializer?: (arg: I & ReducerState<R>) => ReducerState<R>
): readonly [
	ReducerState<R>,
	(value: import('react').ReducerAction<R>) => void
];
