/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback} from 'react';

import useIsMounted from './useIsMounted';

/**
 * Hook for scheduling a repeating function call with the specified
 * interval (in milliseconds).
 */
export default function useInterval() {
	const isMounted = useIsMounted();

	return useCallback(
		function schedule(fn: () => void, ms: number | undefined) {
			const handle = setInterval(() => {
				if (isMounted()) {
					fn();
				}
				else {
					clearInterval(handle);
				}
			}, ms);

			return () => clearInterval(handle);
		},
		[isMounted]
	);
}
