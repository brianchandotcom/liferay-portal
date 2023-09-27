/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import fetchData from '../utils/fetch/fetch_data';

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
