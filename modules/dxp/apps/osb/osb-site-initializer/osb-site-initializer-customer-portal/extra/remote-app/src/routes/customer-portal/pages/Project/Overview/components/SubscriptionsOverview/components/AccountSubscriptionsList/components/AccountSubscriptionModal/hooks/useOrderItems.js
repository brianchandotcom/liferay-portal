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
import {useEffect, useState} from 'react';
import useGetOrderItems from '../../../../../../../../../../../../common/services/liferay/graphql/order-items/queries/useGetOrderItems';

const PAGE_SIZE = 5;
const FIRST_PAGE = 1;

export default function useOrderItems(
	accountSubscriptionExternalReferenceCode
) {
	const [activePage, setActivePage] = useState(FIRST_PAGE);

	const {data, fetchMore, networkStatus} = useGetOrderItems({
		filter: `customFields/accountSubscriptionERC eq '${accountSubscriptionExternalReferenceCode}'`,
		notifyOnNetworkStatusChange: true,
		page: activePage,
		pageSize: PAGE_SIZE,
	});

	useEffect(() => {
		if (activePage !== FIRST_PAGE) {
			fetchMore({
				variables: {
					page: activePage,
				},
			});
		}
	}, [activePage, fetchMore]);

	return [
		{activePage, setActivePage},
		PAGE_SIZE,
		{data, loading: networkStatus === NetworkStatus.loading},
	];
}
