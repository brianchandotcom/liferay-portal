/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

interface FetchState<Data> {
	data: Data | null;
	error: string;
	loading: boolean;
}

const useFetch = <Data, Variables>(
	fetchFn: (variables: Variables) => Promise<Response>,
	{variables}: {variables: Variables}
): FetchState<Data> => {
	const [state, setState] = useState<FetchState<Data>>({
		data: null,
		error: '',
		loading: true,
	});

	useEffect(() => {
		const fetchData = async () => {
			try {
				setState({data: null, error: '', loading: true});

				const response = await fetchFn(variables);

				if (!response?.ok) {
					throw new Error();
				}

				const data: Data & {error: string} = await response.json();

				if (data.error) {
					throw new Error(data.error);
				}

				setState({data, error: '', loading: false});
			}
			catch (error: any) {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}

				setState({
					data: null,
					error: error.message ?? 'Unknown error',
					loading: false,
				});
			}
		};

		fetchData();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [fetchFn, JSON.stringify(variables)]);

	return state;
};

export default useFetch;
