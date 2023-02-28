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

import {useCallback} from 'react';
import {KeyedMutator, MutatorOptions} from 'swr';
import TestrayError from '~/TestrayError';

import {APIResponse} from '../services/rest';

const useMutate = <T = any>(mutate?: KeyedMutator<T>) => {
	const mutatePartial = useCallback(
		(data: Partial<T>, options?: MutatorOptions) => {
			if (!mutate) {
				throw new TestrayError('Mutate is missing');
			}

			mutate(
				(currentData) =>
					currentData ? {...currentData, ...data} : undefined,
				{
					revalidate: false,
					...options,
				}
			);
		},
		[mutate]
	);

	const removeItemFromList = useCallback(
		(mutate: KeyedMutator<any>, id: number, options?: MutatorOptions) =>
			mutate(
				(response: APIResponse) => ({
					...response,
					items: response?.items?.filter((item) => item?.id !== id),
					totalCount: response?.totalCount - 1,
				}),
				{revalidate: false, ...options}
			),
		[]
	);

	const updateItemFromList = useCallback(
		(
			mutate: KeyedMutator<any>,
			id: number,
			data: any,
			options?: MutatorOptions
		) =>
			mutate(
				(response: APIResponse<any>) => ({
					...response,
					items: response?.items?.map((item) => {
						if (item?.id === id) {
							return {
								...item,
								...(typeof data === 'function'
									? data(item)
									: data),
							};
						}

						return item;
					}),
				}),
				{revalidate: false, ...options}
			),
		[]
	);

	return {
		mutatePartial,
		removeItemFromList,
		updateItemFromList,
	};
};

export default useMutate;
