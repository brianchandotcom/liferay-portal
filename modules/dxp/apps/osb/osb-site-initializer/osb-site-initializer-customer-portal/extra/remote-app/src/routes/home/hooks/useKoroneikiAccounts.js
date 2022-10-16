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

import {NetworkStatus} from '@apollo/client';
import useSearchTerm from '../../../common/hooks/useSearchTerm';
import {useGetKoroneikiAccounts} from '../../../common/services/liferay/graphql/koroneiki-accounts';

export default function useKoroneikiAccounts() {
	const {data, fetchMore, networkStatus, refetch} = useGetKoroneikiAccounts({
		notifyOnNetworkStatusChange: true,
	});

	const search = useSearchTerm((searchTerm) =>
		refetch({
			filter: searchTerm && `contains(name, '${searchTerm}')`,
			page: 1,
		})
	);

	return {
		data,
		fetchMore,
		fetching: networkStatus === NetworkStatus.fetchMore,
		loading: networkStatus === NetworkStatus.loading,
		networkStatus,
		refetch,
		search,
		searching: networkStatus === NetworkStatus.setVariables,
	};
}
