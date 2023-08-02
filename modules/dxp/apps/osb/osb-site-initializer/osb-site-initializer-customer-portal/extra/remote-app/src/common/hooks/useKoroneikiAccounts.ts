/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {NetworkStatus} from '@apollo/client';
import {useEffect, useMemo, useState} from 'react';
import SearchBuilder from '../core/SearchBuilder';
import {useGetKoroneikiAccounts} from '../services/liferay/graphql/koroneiki-accounts';
import useSearchTerm from './useSearchTerm';

type UseKoroneikiAccountsProps = {
	selectedFilterCategory: {
		filter: any;
		key: string;
		label: string;
	};
};

export default function useKoroneikiAccounts({
	selectedFilterCategory,
}: UseKoroneikiAccountsProps) {
	const [
		firstKoroneikiAccountsTotal,
		setFirstKoroneikiAccountsTotal,
	] = useState<any>({[selectedFilterCategory.key]: null});

	const {data, fetchMore, networkStatus, refetch} = useGetKoroneikiAccounts({
		notifyOnNetworkStatusChange: true,
		onComplete: (response) => {
			if (!firstKoroneikiAccountsTotal[selectedFilterCategory.key]) {
				setFirstKoroneikiAccountsTotal((prevValue: any) => ({
					...prevValue,
					[selectedFilterCategory.key]:
						response?.c?.koroneikiAccounts?.totalCount,
				}));
			}
		},
	});

	const getFilter = useMemo(
		() =>
			selectedFilterCategory?.filter ??
			function () {
				return new SearchBuilder();
			},
		[selectedFilterCategory?.filter]
	);

	const filter = useMemo(() => getFilter(new SearchBuilder()).build(), [
		getFilter,
	]);

	useEffect(() => {
		refetch({
			filter,
		});
	}, [filter, refetch]);

	const [search, onSearch] = useSearchTerm((searchTerm: string) =>
		refetch({
			filter: searchTerm
				? getFilter(
						new SearchBuilder()
							.group('OPEN')
							.contains('name', searchTerm)
							.or()
							.contains('code', searchTerm)
							.group('CLOSE')
							.and()
				  ).build()
				: filter,
			page: 1,
		})
	);

	return {
		data,
		fetchMore,
		fetching: networkStatus === NetworkStatus.fetchMore,
		firstKoroneikiAccountsTotal,
		loading: networkStatus === NetworkStatus.loading,
		networkStatus,
		onSearch,
		refetch,
		search,
		searching: networkStatus === NetworkStatus.setVariables,
	};
}
