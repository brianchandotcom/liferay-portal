/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {
	currentDate,
	currentYear,
	lastYear,
	lastYearSixMonthsAgoPeriod,
	oneYearAgoDate,
	sixMonthsAgoDate,
} from '../utils/dateFormatter';
import {axios} from './liferay/api';

const DeliveryAPI = 'o/c/raylifepolicies';
const userId = Liferay.ThemeDisplay.getUserId();

export function getPoliciesStatus(totalCount) {
	return new Promise((resolve) => {
		resolve({data: {totalCount}});
	});
}

export function getActivePolicies() {
	return axios.get(
		`${DeliveryAPI}/?fields=policyStatus,productName&pageSize=200&aggregationTerms=policyStatus&filter=policyStatus ne 'expired' and policyStatus ne 'declined'`
	);
}

export function getSixMonthsAgoPolicies() {
	return axios.get(
		`${DeliveryAPI}/?filter=policyStatus ne 'declined' and (startDate le ${currentDate} and startDate ge ${sixMonthsAgoDate[0]}-${sixMonthsAgoDate[1]}-01)&pageSize=200`
	);
}

export function getLastYearSixMonthsPolicies() {
	return axios.get(
		`${DeliveryAPI}/?filter=policyStatus ne 'declined' and (startDate le ${oneYearAgoDate} and startDate ge ${lastYearSixMonthsAgoPeriod[0]}-${lastYearSixMonthsAgoPeriod[1]}-01)&pageSize=200`
	);
}

export function getPoliciesUntilCurrentMonth() {
	return axios.get(
		`${DeliveryAPI}/?filter=policyStatus ne 'declined' and (startDate le ${currentDate} and startDate ge ${currentYear}-01-01)&pageSize=200`
	);
}

export function getPoliciesUntilCurrentMonthLastYear() {
	return axios.get(
		`${DeliveryAPI}/?filter=policyStatus ne 'declined' and (startDate le ${oneYearAgoDate} and startDate ge ${lastYear}-01-01)&pageSize=200`
	);
}

export function getPoliciesForSalesGoal(
	currentYear,
	currentMonth,
	periodYear,
	periodMonth
) {
	return axios.get(
		`${DeliveryAPI}/?fields=boundDate,termPremium&pageSize=200&filter=policyStatus ne 'declined' and userId eq '${userId}' and boundDate le ${currentYear}-${currentMonth}-31 and boundDate ge ${periodYear}-${periodMonth}-01`
	);
}
