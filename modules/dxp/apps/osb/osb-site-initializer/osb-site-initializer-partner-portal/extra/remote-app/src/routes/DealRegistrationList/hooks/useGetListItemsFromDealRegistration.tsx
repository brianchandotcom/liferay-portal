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

import {useMemo} from 'react';

import {DealRegistrationColumnKey} from '../../../common/enums/dealRegistrationColumnKey';
import {Liferay} from '../../../common/services/liferay';
import useGetDealRegistration from '../../../common/services/liferay/object/deal-registration/useGetDealRegistration';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import getDealAmount from '../utils/getDealAmount';
import getDealDates from '../utils/getDealDates';
import getDoubleParagraph from '../utils/getDoubleParagraph';

export default function useGetListItemsFromDealRegistration(
	page: number,
	pageSize: number,
	filtersTerm: string
) {
	const apiOption = Liferay.FeatureFlags['LPS-164528']
		? ResourceName.OPPORTUNITIES_SALESFORCE
		: ResourceName.DEAL_REGISTRATION_DXP;

	const swrResponse = useGetDealRegistration(
		apiOption,
		page,
		pageSize,
		filtersTerm
	);
	const listItems = useMemo(
		() =>
			swrResponse.data?.items.map((item) => ({
				[DealRegistrationColumnKey.ACCOUNT_NAME]:
					item.partnerAccountName,
				...getDealDates(
					item.projectSubscriptionStartDate,
					item.projectSubscriptionEndDate
				),
				...getDealAmount(item.amount),
				[DealRegistrationColumnKey.STAGE]: item.stage,
				[DealRegistrationColumnKey.PARTNER_REP]: getDoubleParagraph(
					`${item.partnerFirstName ? item.partnerFirstName : ''} ${
						item.partnerLastName ? item.partnerLastName : ''
					}`,
					item.partnerEmail && item.partnerEmail
				),
				[DealRegistrationColumnKey.LIFERAY_REP]: item.ownerName,
			})),
		[swrResponse.data?.items]
	);

	return {
		...swrResponse,
		data: {
			...swrResponse.data,
			items: listItems,
		},
	};
}
