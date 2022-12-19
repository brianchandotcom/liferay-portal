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

import {useCallback, useEffect, useState} from 'react';

type TRequestFn = (params?: any) => Promise<any>;

type TLazyResult<TData> = {
	data: TData | null;
	error: boolean;
	loading: boolean;
};

type TResult<TData> = {
	data: TData | null;
	error: boolean;
	loading: boolean;
	refetch: () => void;
};

function useInternalRequest<TData>(
	requestFn: TRequestFn,
	params: any
): [requestFn: () => Promise<void>, result: TLazyResult<TData>] {
	const [data, setData] = useState<TData | null>(null);
	const [error, setError] = useState(false);
	const [loading, setLoading] = useState(false);

	const _requestFn = useCallback(async () => {
		setLoading(true);

		const response = await requestFn(params);

		try {
			if (response.error) {
				throw response.error;
			}
			else {
				setData(response);
			}
		}
		catch (error) {
			console.error(error);

			setError(true);
		}

		setLoading(false);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [requestFn, JSON.stringify(params)]);

	return [_requestFn, {data, error, loading}];
}

export function useRequest<TData, TParams = any>(
	requestFn: TRequestFn,
	params?: TParams
): TResult<TData> {
	const [_requestFn, {data, error, loading}] = useInternalRequest<TData>(
		requestFn,
		params
	);

	useEffect(() => {
		_requestFn();
	}, [_requestFn]);

	return {
		data,
		error,
		loading,
		refetch: _requestFn,
	};
}
