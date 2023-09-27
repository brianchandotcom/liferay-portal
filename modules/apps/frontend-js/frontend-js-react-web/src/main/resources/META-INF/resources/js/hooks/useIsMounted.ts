/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useLayoutEffect, useRef} from 'react';

/**
 * Hook for determining whether a component is still mounted.
 *
 * Use this to guard side-effects of asynchronous operations (fetches,
 * promises) that may complete after a component has been unmounted.
 *
 * Example:
 *
 *      const isMounted = useIsMounted();
 *      const [value, setHidden] = useHidden(true);
 *
 *      setTimeout(() => {
 *          if (isMounted()) {
 *              setHidden(true);
 *          }
 *      }, 1000);
 *
 */
export default function useIsMounted() {
	const mountedRef = useRef(false);
	const isMounted = useCallback(() => mountedRef.current, []);

	useLayoutEffect(() => {
		mountedRef.current = true;

		return () => {
			mountedRef.current = false;
		};
	}, []);

	return isMounted;
}
