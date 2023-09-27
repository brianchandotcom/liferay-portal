/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import useIsMounted from './useIsMounted';

const {useCallback, useState} = React;

/**
 * Wrapper for `useState` that does an `isMounted()` check behind the scenes
 * before triggering side-effects.
 */
export default function useStateSafe<T = unknown>(initialValue: T | (() => T)) {
	const isMounted = useIsMounted();

	const [state, setState] = useState(initialValue);

	const setStateSafe = useCallback(
		(newValue: T | ((previousValue: T) => T)) => {
			if (isMounted()) {
				setState(newValue);
			}
		},
		[isMounted]
	);

	return [state, setStateSafe] as const;
}
