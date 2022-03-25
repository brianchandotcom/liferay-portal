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

import {fetchData} from '../utils/fetch';

/**
 * Hook for fetching data from a resource and providing the function to refetch.
 * @param {!string|!Request} resource The URL to the resource, or a Resource
 * object.
 * @param {Object=} init An optional object containing custom configuration.
 * @param {function=} getData An optional function to get the desired data from
 * response.
 * @param {Object=} defaultValue An optional value for the data to be
 * initially and if fetchData errors.
 * @return {Object}
 */
function useFetchData({
	resource,
	init,
	getData = (responseContent) => responseContent,
	defaultValue = [],
}) {
	const [data, setData] = useState(defaultValue);

	const _handleFetch = () => {
		fetchData(resource, init)
			.then((responseContent) => setData(getData(responseContent)))
			.catch(() => setData(defaultValue));
	};

	useEffect(() => {
		_handleFetch();
	}, []); //eslint-disable-line

	return {data, refetch: _handleFetch};
}

export default useFetchData;
