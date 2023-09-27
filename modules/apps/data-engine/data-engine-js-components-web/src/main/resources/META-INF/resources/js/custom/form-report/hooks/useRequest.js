/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {request} from '../utils/client';

export default function useRequest(endpoint) {
	const [state, setState] = useState({
		error: null,
		isLoading: true,
		response: {},
	});

	useEffect(() => {
		if (endpoint === null) {
			return;
		}

		request({endpoint})
			.then((response) => {
				setState({
					error: null,
					isLoading: false,
					response,
				});
			})
			.catch((error) => {
				setState({
					error,
					isLoading: false,
					response: {},
				});
			});
	}, [endpoint]);

	return state;
}
