/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

/**
 * Hook for providing an asynchronous function to be completed before performing
 * an action. Returns a loading state that is set to false at the end.
 * @param {function} action Function to be called after async
 * object.
 * @param {number=} timeout An optional number for timeout
 * @return {Object}
 */
function useAsyncCall(action, timeout = 2000) {
	const [loading, setLoading] = useState(true);

	const asyncCall = (timeout) => {
		return new Promise((resolve) => setTimeout(resolve, timeout));
	};

	useEffect(() => {
		asyncCall(timeout)
			.then(action)
			.finally(() => {
				setLoading(false);
			});
	}, []); //eslint-disable-line

	return [loading, setLoading];
}

export default useAsyncCall;
