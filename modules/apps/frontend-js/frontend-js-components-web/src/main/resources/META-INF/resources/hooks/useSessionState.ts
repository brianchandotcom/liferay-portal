/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	COOKIE_TYPES,
	TYPE_VALUES as CookieType,
	sessionStorage,
} from 'frontend-js-web';
import {useEffect, useState} from 'react';

export default function useSessionState<T>(
	key: string,
	defaultValue: T | undefined = undefined,
	type: CookieType = COOKIE_TYPES.PERSONALIZATION
) {
	const [state, setState] = useState(() => {
		const persistedState = sessionStorage.getItem(key, type) || '';

		try {
			const deserializedValue = JSON.parse(persistedState);

			if (
				!(deserializedValue === null || deserializedValue === undefined)
			) {
				return deserializedValue as T;
			}
		}
		catch (_error) {}

		return defaultValue;
	});

	useEffect(() => {
		if (state === null || state === undefined) {
			sessionStorage.removeItem(key);
		}
		else {
			sessionStorage.setItem(key, JSON.stringify(state), type);
		}
	}, [key, state, type]);

	return [state, setState] as const;
}
