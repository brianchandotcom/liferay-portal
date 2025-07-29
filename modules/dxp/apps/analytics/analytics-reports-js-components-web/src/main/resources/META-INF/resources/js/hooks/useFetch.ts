/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import ApiHelper from '../apis/ApiHelper';

interface FetchState<Data> {
	data: Data | null;
	loading: boolean;
}

const useFetch = <Data>(endpointUrl: string): FetchState<Data> => {
	const [state, setState] = useState<FetchState<Data>>({
		data: null,
		loading: false,
	});

	useEffect(() => {
		const fetchData = async () => {
			try {
				setState({data: null, loading: true});

				const {data, error} = await ApiHelper.get<Data>(endpointUrl);

				if (error) {
					throw new Error(error);
				}

				setState({data, loading: false});
			}
			catch (error: any) {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}

				setState({
					data: null,
					loading: false,
				});
			}
		};

		fetchData();
	}, [endpointUrl]);

	return state;
};

export default useFetch;
