/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
