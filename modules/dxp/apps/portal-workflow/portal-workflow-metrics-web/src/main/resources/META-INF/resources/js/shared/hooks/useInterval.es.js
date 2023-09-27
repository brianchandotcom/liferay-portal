/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from '@liferay/frontend-js-react-web';
import {useCallback} from 'react';

const useInterval = (cancelOnDestroy) => {
	const isMounted = useIsMounted();

	return useCallback(
		(callback, milliseconds) => {
			const interval = setInterval(() => {
				if (!cancelOnDestroy || isMounted()) {
					callback();
				}
				else {
					clearInterval(interval);
				}
			}, milliseconds);

			return () => clearInterval(interval);
		},
		[cancelOnDestroy, isMounted]
	);
};

export {useInterval};
