/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback} from 'react';

import useIsMounted from './useIsMounted';

/**
 * Hook for delaying a function call by the specified interval (in
 * milliseconds).
 */
export default function useTimeout() {
	const isMounted = useIsMounted();

	return useCallback(
		function delay(fn: () => void, ms: number | undefined) {
			const handle = setTimeout(() => {
				if (isMounted()) {
					fn();
				}
			}, ms);

			return () => clearTimeout(handle);
		},
		[isMounted]
	);
}
