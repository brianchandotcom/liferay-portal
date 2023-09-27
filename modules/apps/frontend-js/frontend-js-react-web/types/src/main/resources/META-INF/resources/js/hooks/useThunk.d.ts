/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

/**
 * "Middleware" hook intended to wrap `useReducer` that enables you to dispatch
 * thunks (ie. functions that dispatch actions) as well as plain actions (ie.
 * objects).
 */
export default function useThunk<R extends React.Reducer<any, any>>([
	state,
	dispatch,
]: [React.ReducerState<R>, React.Dispatch<React.ReducerAction<R>>]): (
	| React.ReducerState<R>
	| ((action: any) => any)
)[];
