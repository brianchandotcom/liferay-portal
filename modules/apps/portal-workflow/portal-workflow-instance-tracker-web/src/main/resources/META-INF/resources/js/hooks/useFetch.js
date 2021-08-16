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

import axios from 'axios';
import {useCallback, useState} from 'react';

axios.defaults.headers.common[
	'Accept-Language'
] = Liferay.ThemeDisplay.getBCP47LanguageId();

axios.defaults.params = {
	['p_auth']: Liferay.authToken,
};

function useFetch({callback = (data) => data, urls}) {
	const [data, setData] = useState([]);

	const client = axios.create({
		baseURL: '/o/headless-admin-workflow/v1.0',
	});

	const fetchData = useCallback(
		() => {
			const requests = urls.map((url) => client.get(url));

			axios.all(requests).then(
				axios.spread(async (...responses) => {
					setData(responses.map((response) => response.data));

					return await callback(responses, client);
				})
			);
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[client, urls]
	);

	return {
		data,
		fetchData,
	};
}

export {useFetch};
