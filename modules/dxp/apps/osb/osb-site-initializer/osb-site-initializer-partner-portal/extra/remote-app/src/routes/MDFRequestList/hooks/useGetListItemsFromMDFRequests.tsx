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

import {MDFColumnKey} from '../../../common/enums/mdfColumnKey';
import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import getMDFActivityPeriod from '../utils/getMDFActivityPeriod';
import getMDFBudgetInfos from '../utils/getMDFBudgetInfos';
import getMDFDates from '../utils/getMDFDates';
import getSummaryMDFClaims from '../utils/getSummaryMDFClaims';

export default function useGetListItemsFromMDFRequests(
	items?: MDFRequestDTO[]
) {
	return useMemo(
		() =>
			items?.map((item) => ({
				...getSummaryMDFClaims(item.currency, item.mdfReqToMDFClms),
				[MDFColumnKey.ID]: String(item.id),
				[MDFColumnKey.NAME]: item.overallCampaignName,
				...getMDFActivityPeriod(
					item.minDateActivity,
					item.maxDateActivity
				),
				[MDFColumnKey.STATUS]: item.mdfRequestStatus?.name,
				[MDFColumnKey.PARTNER]: item.companyName,
				...getMDFDates(item.dateCreated, item.dateModified),
				...getMDFBudgetInfos(
					item.totalCostOfExpense,
					item.totalMDFRequestAmount,
					item.currency,
					item.mdfRequestStatus.key
				),
			})),
		[items]
	);
}
