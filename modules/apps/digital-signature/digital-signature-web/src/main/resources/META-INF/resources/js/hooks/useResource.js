/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {usePrevious} from '@liferay/frontend-js-react-web';
import {useEffect, useState} from 'react';

import {isEqualObjects} from '../utils/utils';

export default function useResource({customFetch, endpoint, method, params}) {
	const [state, setState] = useState({
		error: null,
		isLoading: true,
		response: null,
	});

	const doFetch = (options) => {
		setState((prevState) => ({
			...prevState,
			error: null,
			isLoading: true,
		}));

		customFetch(options)
			.then((response) => {
				setState({
					error: null,
					isLoading: false,
					response,
				});
			})
			.catch((error) => {
				setState((prevState) => ({
					...prevState,
					error,
					isLoading: false,
				}));
			});
	};

	const refetch = () => doFetch({endpoint, method, params});

	const previousParams = usePrevious(params);

	useEffect(() => {
		if (!isEqualObjects(params, previousParams)) {
			refetch();
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [params]);

	return {refetch, ...state};
}
