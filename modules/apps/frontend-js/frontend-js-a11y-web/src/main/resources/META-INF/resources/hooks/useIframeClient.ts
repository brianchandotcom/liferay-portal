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

import {useCallback, useEffect, useRef, useState} from 'react';

import {SDK} from '../SDK';

export enum Kind {
	Click = 'a11y:popover:click',
}

export default function useIframeClient<T>(initialState: T) {
	const [state, setState] = useState<T>(initialState);

	const sdkRef = useRef(new SDK<T, unknown>(setState, true));

	const postMessage = useCallback(
		<P>(kind: Kind, payload: P) => {
			sdkRef.current.channel.tx(window.parent, payload, kind);
		},
		[sdkRef]
	);

	useEffect(() => {
		const sdk = sdkRef.current;

		sdk.observe();

		return () => {
			sdk.unobserve();
		};
	}, [sdkRef]);

	return [state, postMessage] as const;
}
