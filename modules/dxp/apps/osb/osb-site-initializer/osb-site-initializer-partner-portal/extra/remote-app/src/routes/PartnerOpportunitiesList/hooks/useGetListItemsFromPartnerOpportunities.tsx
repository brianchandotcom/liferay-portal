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

import {PartnerOpportunitiesColumnKey} from '../../../common/enums/partnerOpportunitiesColumnKey';
import DealRegistrationDTO from '../../../common/interfaces/dto/dealRegistrationDTO';
import useGetDealRegistration from '../../../common/services/liferay/object/deal-registration/useGetDealRegistration';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import getDealAmount from '../utils/getOpportunityAmount';

export default function useGetListItemsFromPartnerOpportunities(
	getDates: (
		items: DealRegistrationDTO
	) =>
		| {
				[key in PartnerOpportunitiesColumnKey]?: string;
		  }
		| undefined,
	page: number,
	pageSize: number,
	filtersTerm: string,
	sort: string
) {
	const swrResponse = useGetDealRegistration(
		ResourceName.OPPORTUNITIES_SALESFORCE,
		page,
		pageSize,
		filtersTerm,
		sort
	);
	const listItems = useMemo(
		() =>
			swrResponse.data?.items.map((item) => ({
				[PartnerOpportunitiesColumnKey.ACCOUNT_NAME]:
					item.partnerAccountName,
				...getDates(item),
				...getDealAmount(item.amount),
				[PartnerOpportunitiesColumnKey.STAGE]: item.stage,
				[PartnerOpportunitiesColumnKey.PARTNER_REP_NAME]: `${
					item.partnerFirstName ? item.partnerFirstName : ''
				}${item.partnerLastName ? ' ' + item.partnerLastName : ''}`,
				[PartnerOpportunitiesColumnKey.PARTNER_REP_EMAIL]:
					item.partnerEmail,
				[PartnerOpportunitiesColumnKey.LIFERAY_REP]: item.ownerName,
			})),
		[getDates, swrResponse.data?.items]
	);

	return {
		...swrResponse,
		data: {
			...swrResponse.data,
			items: listItems,
		},
	};
}
