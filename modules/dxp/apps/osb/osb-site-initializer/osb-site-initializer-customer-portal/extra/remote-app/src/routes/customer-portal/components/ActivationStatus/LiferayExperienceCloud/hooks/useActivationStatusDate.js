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
import {useEffect, useState} from 'react';
import {useAppPropertiesContext} from '../../../../../../common/contexts/AppPropertiesContext';
import {getCommerceOrderItems} from '../../../../../../common/services/liferay/graphql/queries';
import getActivationStatusDateRange from '../../../../../../common/utils/getActivationStatusDateRange';

export default function useActivationStatusDate(project) {
	const {client} = useAppPropertiesContext();
	const [activationStatusDate, setActivationStatusDate] = useState('');

	useEffect(() => {
		const fetchCommerceOrderItems = async () => {
			const filterAccountSubscriptionERC = `customFields/accountSubscriptionGroupERC eq '${project.accountKey}_liferay-experience-cloud'`;
			const {data} = await client.query({
				fetchPolicy: 'network-only',
				query: getCommerceOrderItems,
				variables: {
					filter: filterAccountSubscriptionERC,
				},
			});

			if (data) {
				const activationStatusDateRange = getActivationStatusDateRange(
					data?.orderItems?.items
				);
				setActivationStatusDate(activationStatusDateRange);
			}
		};

		fetchCommerceOrderItems();
	}, [client, project]);

	return {activationStatusDate};
}
