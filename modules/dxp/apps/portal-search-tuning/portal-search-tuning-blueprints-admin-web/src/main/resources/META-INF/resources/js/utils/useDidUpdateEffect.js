/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
