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

import formatCurrency from './formatCurrency';

export default function getRevenueChartColumns(
	revenueCurrency: any,
	revenueData: any,
	setTitleChart: any,
	setValueChart: any,
	setColumnsRevenueChart: any
) {
	const STAGE_CLOSEDLOST = 'Closed Lost';
	const STAGE_DISQUALIFIED = 'Disqualified';
	const STAGE_ROLLED_INTO_ANOTHER_OPPORTUNITY =
		'Rolled into another opportunity';
	const STAGE_CLOSEDWON = 'Closed Won';
	const STAGE_REJECTED = 'Rejected';
	const NEW_BUSINESS = 'New Business';
	const NEW_PROJECT_EXISTING_BUSINESS = 'New Project Existing Business';

	const chartColumns = [];

	const totalGrowthRevenue = revenueData?.items?.reduce(
		(accumulator: number, currentValue: any) => {
			if (
				currentValue.stage === STAGE_CLOSEDWON &&
				(currentValue.type === NEW_BUSINESS ||
					currentValue.type === NEW_PROJECT_EXISTING_BUSINESS)
			) {
				return accumulator + currentValue.growthArr;
			}

			return accumulator;
		},
		0
	);

	chartColumns.push(['Growth Revenue', totalGrowthRevenue]);

	const totalRenewalRevenue = revenueData?.items?.reduce(
		(accumulator: number, currentValue: any) => {
			if (
				currentValue.type !== NEW_BUSINESS &&
				currentValue.type !== NEW_PROJECT_EXISTING_BUSINESS &&
				currentValue.stage !== STAGE_REJECTED &&
				currentValue.stage !== STAGE_ROLLED_INTO_ANOTHER_OPPORTUNITY &&
				currentValue.stage !== STAGE_DISQUALIFIED &&
				currentValue.stage !== STAGE_CLOSEDLOST
			) {
				return accumulator + currentValue.renewalArr;
			}

			return accumulator;
		},
		0
	);

	chartColumns.push(['Renewal Revenue', totalRenewalRevenue]);

	const totalRevenueAmount = totalGrowthRevenue + totalRenewalRevenue;

	setValueChart(formatCurrency(totalRevenueAmount));
	setTitleChart(` Total Revenue`);
	setColumnsRevenueChart(chartColumns);
}
