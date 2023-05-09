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
import getOpportunityAmount from '../utils/getOpportunityAmount';
import getOpportunityDates from '../utils/getOpportunityDates';

export default function useGetListItemsFromPartnerOpportunities(
	getDates: (
		item: DealRegistrationDTO
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
				[PartnerOpportunitiesColumnKey.PARTNER_ACCOUNT_NAME]: item.partnerAccountName
					? item.partnerAccountName
					: ' - ',
				[PartnerOpportunitiesColumnKey.PARTNER_NAME]: `${
					item.partnerFirstName ? item.partnerFirstName : ''
				}${item.partnerLastName ? ' ' + item.partnerLastName : ''}`,
				...(item.projectSubscriptionStartDate
					? getOpportunityDates(
							item.projectSubscriptionStartDate,
							item.projectSubscriptionEndDate
					  )
					: {
							[PartnerOpportunitiesColumnKey.START_DATE]: ' - ',
							[PartnerOpportunitiesColumnKey.END_DATE]: ' - ',
					  }),
				[PartnerOpportunitiesColumnKey.ACCOUNT_NAME]: ' - ',
				...(item.amount
					? getOpportunityAmount(item.amount)
					: {[PartnerOpportunitiesColumnKey.DEAL_AMOUNT]: ' - '}),
				[PartnerOpportunitiesColumnKey.PARTNER_REP_NAME]: `${
					item.primaryContactFirstName
						? item.primaryContactFirstName
						: ' - '
				}${
					item.primaryContactLastName
						? ' ' + item.primaryContactLastName
						: ' '
				}`,
				[PartnerOpportunitiesColumnKey.PARTNER_REP_EMAIL]: item.primaryContactEmail
					? item.primaryContactEmail
					: ' - ',
				[PartnerOpportunitiesColumnKey.LIFERAY_REP]: item.ownerName
					? item.ownerName
					: ' - ',
				[PartnerOpportunitiesColumnKey.STAGE]: item.stage
					? item.stage
					: '- ',
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
