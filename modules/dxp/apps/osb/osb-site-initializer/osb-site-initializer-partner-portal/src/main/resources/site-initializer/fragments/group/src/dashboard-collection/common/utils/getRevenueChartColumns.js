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

import {currencyFormat} from '.';

export default function getRevenueChartColumns(
	revenueData,
	setTitleChart,
	setValueChart,
	setColumnsRevenueChart
) {
	const chartColumns = [];

	const totalRevenueAmount = revenueData?.items?.reduce(
		(accumulator, currentValue) =>
			accumulator + currentValue.netSubscriptionArr || 0,
		0
	);
	setValueChart(currencyFormat(totalRevenueAmount));
	setTitleChart(` Total Revenue`);

	const totalNewBusiness = revenueData?.items?.reduce(
		(accumulator, currentValue) =>
			accumulator + currentValue.growthArr || 0,
		0
	);
	chartColumns.push(['Growth Revenue', totalNewBusiness]);

	const totalRenewal = revenueData?.items?.reduce(
		(accumulator, currentValue) =>
			accumulator + currentValue.renewalArr || 0,
		0
	);

	chartColumns.push(['Renewal Revenue', totalRenewal]);

	setColumnsRevenueChart(chartColumns);
}
