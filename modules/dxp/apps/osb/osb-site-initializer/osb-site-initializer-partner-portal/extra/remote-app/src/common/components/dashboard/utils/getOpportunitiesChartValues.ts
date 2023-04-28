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
import isNotOpportunity from './getIsNotOpportunityDeals';

const INITIAL_OPPORTUNITIES_CHART_VALUES = {
	approved: [0, 0, 0, 0],
	closedWon: [0, 0, 0, 0],
	rejected: [0, 0, 0, 0],
};

export function getOpportunitiesChartValues(
	opportunities: any[]
): typeof INITIAL_OPPORTUNITIES_CHART_VALUES {
	return opportunities?.reduce(
		(accumulatedChartValues, currentOpportunity) => {
			if (!isNotOpportunity(currentOpportunity)) {
				accumulatedChartValues.approved = getChartQuarterCount(
					accumulatedChartValues.approved,
					currentOpportunity.dateCreated
				);
			}

			if (currentOpportunity.stage === dealsChartStatus.STAGE_CLOSEDWON) {
				accumulatedChartValues.closedWon = getChartQuarterCount(
					accumulatedChartValues.closedWon,
					currentOpportunity.dateCreated
				);
			}
			if (currentOpportunity.stage === dealsChartStatus.STAGE_REJECTED) {
				accumulatedChartValues.rejected = getChartQuarterCount(
					accumulatedChartValues.rejected,
					currentOpportunity.dateCreated
				);
			}

			return accumulatedChartValues;
		},
		INITIAL_OPPORTUNITIES_CHART_VALUES
	);
}
