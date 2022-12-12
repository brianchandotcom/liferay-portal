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

import {PartnerOpportunitiesColumnKey} from '../../../common/enums/partnerOpportunitiesColumnKey';
import {customFormatDateOptions} from '../../../common/utils/constants/customFormatDateOptions';
import getDateCustomFormat from '../../../common/utils/getDateCustomFormat';

export default function getDealDates(
	projectSubscriptionStartDate?: Date,
	projectSubscriptionEndDate?: Date
) {
	if (projectSubscriptionStartDate && projectSubscriptionEndDate) {
		const startDate = getDateCustomFormat(
			projectSubscriptionStartDate,
			customFormatDateOptions.SHORT_MONTH
		);

		const endDate = getDateCustomFormat(
			projectSubscriptionEndDate,
			customFormatDateOptions.SHORT_MONTH
		);

		return {
			[PartnerOpportunitiesColumnKey.START_DATE]: startDate,
			[PartnerOpportunitiesColumnKey.END_DATE]: endDate,
		};
	}
}
