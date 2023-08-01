/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useQuery} from '@apollo/client';
import {CORE_KORONEIKI_ACCOUNT_FIELDS} from '../fragments/coreKoroneikiAccountFields';

const GET_KORONEIKI_ACCOUNTS = gql`
	${CORE_KORONEIKI_ACCOUNT_FIELDS}
	query getKoroneikiAccounts(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		c {
			koroneikiAccounts(
				filter: $filter
				page: $page
				pageSize: $pageSize
			) {
				items {
					...CoreKoroneikiAccountFields
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;

type OptionType = {
	filter?: string;
	notifyOnNetworkStatusChange?: boolean;
	onComplete?: (response: any) => void;
	page?: number;
	pageSize?: number;
	skip?: boolean;
};

export function useGetKoroneikiAccounts(
	options: OptionType = {
		filter: '',
		notifyOnNetworkStatusChange: false,
		onComplete: () => null,
		page: 1,
		pageSize: 20,
		skip: false,
	}
) {
	return useQuery(GET_KORONEIKI_ACCOUNTS, {
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
		onCompleted: options.onComplete,
		skip: options.skip,
		variables: {
			filter: options.filter || '',
			page: options.page || 1,
			pageSize: options.pageSize || 20,
		},
	});
}
