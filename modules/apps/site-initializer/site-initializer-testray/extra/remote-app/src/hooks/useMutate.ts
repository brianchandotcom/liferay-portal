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

import {KeyedMutator, MutatorOptions} from 'swr';

import {APIResponse} from '../graphql/queries';

const useMutate = () => {
	return {
		addItemToList: (
			mutate: KeyedMutator<any>,
			data: any,
			options?: MutatorOptions
		) => {
			mutate(
				(response: APIResponse) => ({
					...response,
					items: [data, ...response.items],
					totalCount: response.totalCount + 1,
				}),
				options
			);
		},

		removeItemFromList: (
			mutate: KeyedMutator<any>,
			id: number,
			options?: MutatorOptions
		) =>
			mutate(
				(response: APIResponse) => ({
					...response,
					items: response.items.filter((item) => item.id !== id),
					totalCount: response.totalCount - 1,
				}),
				{revalidate: false, ...options}
			),
		updateItemFromList: (
			mutate: KeyedMutator<any>,
			id: number,
			data: any,
			options?: MutatorOptions
		) =>
			mutate(
				(response: APIResponse<any>) => ({
					...response,
					items: response.items.map((item) => {
						if (item.id === id) {
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
	};
};

export default useMutate;
