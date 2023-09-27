/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare type TRequestFn = (params?: any) => Promise<any>;
declare type TResult<TData> = {
	data: TData | null;
	error: boolean;
	loading: boolean;
	refetch: () => void;
};
export declare function useRequest<TData, TParams = any>(
	requestFn: TRequestFn,
	params?: TParams
): TResult<TData>;
export {};
