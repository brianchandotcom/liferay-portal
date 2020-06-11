/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {useIsMounted} from 'frontend-js-react-web';
import {useRef} from 'react';

export const useThunkDispatch = (dispatch) => {
	const isMounted = useIsMounted();

	const thunkDispatch = useRef((action) => {
		if (isMounted()) {
			if (typeof action === 'function') {
				return action((payload) => {
					if (isMounted()) {
						dispatch(payload);
					}
				});
			}
			else {
				dispatch(action);
			}
		}
	});

	return thunkDispatch.current;
};

export const useThunk = ([state, dispatch]) => [
	state,
	useThunkDispatch(dispatch),
];
