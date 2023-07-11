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

import {isNullOrUndefined} from '@liferay/layout-js-components-web';
import {
	COOKIE_TYPES,
	TYPE_VALUES as CookieType,
	sessionStorage,
} from 'frontend-js-web';
import {useEffect, useState} from 'react';

export function useSessionState<T>(
	key: string,
	defaultValue: T | undefined = undefined,
	type: CookieType = COOKIE_TYPES.PERSONALIZATION
) {
	const [state, setState] = useState(() => {
		const persistedState = sessionStorage.getItem(key, type) || '';

		try {
			const deserializedValue = JSON.parse(persistedState);

			if (!isNullOrUndefined(deserializedValue)) {
				return deserializedValue as T;
			}
		}
		catch (_error) {}

		return defaultValue;
	});

	useEffect(() => {
		if (isNullOrUndefined(state)) {
			sessionStorage.removeItem(key);
		}
		else {
			sessionStorage.setItem(key, JSON.stringify(state), type);
		}
	}, [key, state, type]);

	return [state, setState] as const;
}
