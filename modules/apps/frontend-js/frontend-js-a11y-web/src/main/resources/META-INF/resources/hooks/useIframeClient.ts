/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
