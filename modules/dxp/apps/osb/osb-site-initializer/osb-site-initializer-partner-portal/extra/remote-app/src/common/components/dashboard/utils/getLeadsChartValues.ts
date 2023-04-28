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

import {dealsChartStatus} from './constants/dealsChartStatus';
import getChartQuarterCount from './getDealsChartQuarterCount';

export default function getLeadsChartValues(leads: any[]) {
	const INITIAL_LEADS_CHART_VALUES = {
		rejected: [0, 0, 0, 0],
		submitted: [0, 0, 0, 0],
	};

	return leads?.reduce((accumulatedChartValues, item) => {
		if (item.leadStatus === dealsChartStatus.STATUS_CAMREJECTED) {
			accumulatedChartValues.rejected = getChartQuarterCount(
				accumulatedChartValues.rejected,
				item.dateCreated
			);
		}
		if (
			item.leadType === dealsChartStatus.TYPE_PARTNER_QUALIFIED_LEAD &&
			(item.leadStatus !==
				dealsChartStatus.STATUS_SALES_QUALIFIED_OPPORTUNITY ||
				item.leadStatus !== dealsChartStatus.STATUS_CAMREJECTED)
		) {
			accumulatedChartValues.submitted = getChartQuarterCount(
				accumulatedChartValues.submitted,
				item.dateCreated
			);
		}

		return accumulatedChartValues;
	}, INITIAL_LEADS_CHART_VALUES);
}
