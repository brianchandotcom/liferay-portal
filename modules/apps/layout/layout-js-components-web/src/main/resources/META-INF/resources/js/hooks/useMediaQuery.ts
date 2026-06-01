/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useSyncExternalStore} from 'react';

export default function useMediaQuery(query: string) {
	const subscribe = useCallback(
		(onChange: () => void) => {
			const mediaQuery = window.matchMedia(query);

			mediaQuery.addEventListener('change', onChange);

			return () => mediaQuery.removeEventListener('change', onChange);
		},
		[query]
	);

	return useSyncExternalStore(
		subscribe,
		() => window.matchMedia(query).matches
	);
}
