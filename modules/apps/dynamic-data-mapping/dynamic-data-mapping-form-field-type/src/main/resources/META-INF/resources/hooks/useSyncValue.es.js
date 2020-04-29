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

import {useEffect, useRef, useState} from 'react';

/**
 * Use Sync Value to synchronize the initial value with the current internal
 * value, only update the internal value with the new initial value if the
 * values are different and when the value is not changed for more than ms.
 */
export const useSyncValue = (newValue, isDelay = true) => {
	// Maintains the reference of the last value to check in later renderings if the
	// value is new or keeps the same, it covers cases where the value typed by
	// the user is sent to LayoutProvider but it does not descend with the new changes.

	const previousValueRef = useRef(newValue);

	const [value, setValue] = useState(newValue);

	useEffect(() => {
		const handler = setTimeout(
			() => {
				if (
					value !== newValue &&
					previousValueRef.current !== newValue
				) {
					previousValueRef.current = newValue;
					setValue(newValue);
				}
			},
			isDelay ? 300 : 0
		);

		return () => {
			clearTimeout(handler);
		};
	}, [isDelay, newValue, value]);

	return [value, setValue];
};
