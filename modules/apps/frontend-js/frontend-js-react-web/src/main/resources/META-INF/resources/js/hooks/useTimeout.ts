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
