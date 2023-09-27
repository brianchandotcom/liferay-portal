/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useRef, useState} from 'react';

/**
 * Use Sync Value to synchronize the initial value with the current internal
 * value, only update the internal value with the new initial value if the
 * values are different and when the value is not changed for more than ms.
 */
export function useSyncValue(newValue, isDelay = true, forceValue) {

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

	useEffect(() => {
		if (forceValue) {
			previousValueRef.current = null;
		}
	}, [forceValue]);

	return [value, setValue];
}
