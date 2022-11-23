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
