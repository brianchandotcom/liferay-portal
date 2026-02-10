/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSyncExternalStore} from 'react';

const subscribeVisibilityChangeEvent = (callback: () => void) => {
	document.addEventListener('visibilitychange', callback);

	return () => {
		document.removeEventListener('visibilitychange', callback);
	};
};

const getVisibilityChangeEventSnapshot = () => {
	return document.visibilityState;
};

export function useBrowserTabVisibility() {
	const visibilityState = useSyncExternalStore(

		// eslint-disable-next-line react-compiler/react-compiler
		subscribeVisibilityChangeEvent,
		getVisibilityChangeEventSnapshot
	);

	return visibilityState === 'visible';
}

export default useBrowserTabVisibility;
