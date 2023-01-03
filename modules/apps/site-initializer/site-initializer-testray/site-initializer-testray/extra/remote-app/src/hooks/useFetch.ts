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

import {useMemo} from 'react';
import useSWR from 'swr';

import Rest, {APIParametersOptions} from '../services/rest/Rest';

type FetchOptions<Data> = {
	transformData?: (data: Data) => Data;
} & APIParametersOptions;

const getBaseURL = (url: string | null, options: APIParametersOptions) => {
	if (!url) {
		return null;
	}

	const searchParams = Rest.getPageParameter(options, url);

	let baseURL = url;

	if (url.includes('?')) {
		baseURL = url.slice(0, url.indexOf('?'));
	}

	if (searchParams.length) {
		baseURL += `?${searchParams}`;
	}

	return baseURL;
};

export function useFetch<Data = any, Error = any>(
	url: string | null,
	fetchParameters?: FetchOptions<Data>
) {
	const {transformData, ...options} = fetchParameters ?? {};

	const {data, error, isValidating, mutate} = useSWR<Data, Error>(() =>
		getBaseURL(url, options)
	);

	const memoizedData = useMemo(() => {
		if (data && transformData) {
			return transformData(data || ({} as Data));
		}

		return data;
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [data]);

	return {
		called: data && url,
		data: memoizedData,
		error,
		isValidating,
		loading: !data,
		mutate,
		revalidate: () => mutate((response) => response, {revalidate: true}),
	};
}
