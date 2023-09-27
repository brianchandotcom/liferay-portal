/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useRef} from 'react';

/**
 * Hook for calling a function after the first render. Use this the same way
 * as useEffect.
 */
export default function useDidUpdateEffect(fn, inputs) {
	const didMountRef = useRef(false);

	useEffect(() => {
		if (didMountRef.current) {
			fn();
		}
		else {
			didMountRef.current = true;
		}
	}, inputs); //eslint-disable-line
}
