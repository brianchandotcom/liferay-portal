/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useQuery} from '@apollo/client';
import {useCallback, useEffect, useMemo, useState} from 'react';
import {getKoroneikiAccounts} from '../../../common/services/liferay/graphql/queries';
import getFilterKoroneikiAccounts from '../utils/getFilterKoroneikiAccounts';

export default function usePaginatedKoroneikiAccounts(userAccount) {
	const [initialTotalCount, setInitialTotalCount] = useState(0);
	const [currentPage, setCurrentPage] = useState(1);
	const [lastSearchTerm, setLastSearchTerm] = useState('');
	const [accumulatedItems, setAccumulatedItems] = useState([]);

	const filterKoroneikiAccounts = useMemo(
		() =>
			!userAccount?.isStaff
				? getFilterKoroneikiAccounts(userAccount?.accountBriefs)
				: '',
		[userAccount?.accountBriefs, userAccount?.isStaff]
	);

	const getFilterKoroneikiAccountsBySearch = useCallback(
		(searchTerm) => {
			const filterBySearch = `contains(name, '${searchTerm}') or contains(code, '${searchTerm}')`;

			return searchTerm
				? filterKoroneikiAccounts
					? `(${filterKoroneikiAccounts}) and ${filterBySearch}`
					: filterBySearch
				: filterKoroneikiAccounts;
		},
		[filterKoroneikiAccounts]
	);

	const {data, refetch} = useQuery(getKoroneikiAccounts, {
		skip: !userAccount,
		variables: {
			filter: filterKoroneikiAccounts,
		},
	});

	const koroneikiAccounts = data?.c?.koroneikiAccounts;

	useEffect(() => {
		const totalCount = koroneikiAccounts?.totalCount;

		if (totalCount > initialTotalCount) {
			setInitialTotalCount(totalCount);
			setAccumulatedItems(koroneikiAccounts?.items);
		}
	}, [
		koroneikiAccounts?.totalCount,
		initialTotalCount,
		koroneikiAccounts?.items,
	]);

	const fetchMore = useCallback(async () => {
		const {data} = await refetch({
			filter: getFilterKoroneikiAccountsBySearch(lastSearchTerm),
			page: currentPage + 1,
		});

		const items = data?.c?.koroneikiAccounts?.items;
		if (items) {
			setAccumulatedItems((previousAccumulatedItems) => [
				...previousAccumulatedItems,
				...items,
			]);

			setCurrentPage((previousCurrentPage) => ++previousCurrentPage);
		}
	}, [
		currentPage,
		getFilterKoroneikiAccountsBySearch,
		lastSearchTerm,
		refetch,
	]);

	const search = useCallback(
		async (searchTerm) => {
			if (searchTerm !== lastSearchTerm) {
				const {data} = await refetch({
					filter: getFilterKoroneikiAccountsBySearch(searchTerm),
					page: 1,
				});

				const items = data?.c?.koroneikiAccounts?.items;
				if (items) {
					setAccumulatedItems(items);
					setCurrentPage(1);
				}

				setLastSearchTerm(searchTerm);
			}
		},
		[getFilterKoroneikiAccountsBySearch, lastSearchTerm, refetch]
	);

	return [
		{
			initialTotalCount,
			items: accumulatedItems,
			totalCount: koroneikiAccounts?.totalCount,
		},
		{
			fetchMore,
			search,
		},
	];
}
