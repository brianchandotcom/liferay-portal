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

import {useEffect, useState} from 'react';

import isNullOrUndefined from '../../app/utils/isNullOrUndefined';

export function useSessionState(key, defaultValue = undefined) {
	const [state, setState] = useState(() => {
		const persistedState = window.sessionStorage.getItem(key);

		try {
			const deserializedValue = JSON.parse(persistedState);

			if (!isNullOrUndefined(deserializedValue)) {
				return deserializedValue;
			}
		}
		catch (_error) {}

		return defaultValue;
	});

	useEffect(() => {
		if (isNullOrUndefined(state)) {
			window.sessionStorage.removeItem(key);
		}
		else {
			window.sessionStorage.setItem(key, JSON.stringify(state));
		}
	}, [key, state]);

	return [state, setState];
}
