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
	currentDateString,
	currentYear,
	lastYear,
	lastYearSixMonthsAgoPeriod,
	oneYearAgoDate,
	sixMonthsAgoDate,
	threeMonthsAgoDate,
} from '../utils/dateFormatter';
import {axios} from './liferay/api';

const DeliveryAPI = 'o/c/raylifepolicies';
const userId = Liferay.ThemeDisplay.getUserId();

export function getPoliciesStatus(totalCount) {
	return new Promise((resolve) => {
		resolve({data: {totalCount}});
	});
}

export function getPolicies() {
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

export function getPoliciesForSalesGoalCurrentMonth() {
	return axios.get(
		`${DeliveryAPI}/?fields=boundDate,termPremium&pageSize=200&filter=policyStatus ne 'declined' and userId eq '${userId}' and boundDate le ${currentDateString[0]}-${currentDateString[1]}-31 and boundDate ge ${currentDateString[0]}-${currentDateString[1]}-01`
	);
}

export function getPoliciesForSalesGoalThreeMonths() {
	return axios.get(
		`${DeliveryAPI}/?fields=boundDate,termPremium&pageSize=200&filter=policyStatus ne 'declined' and userId eq '${userId}' and boundDate le ${currentDateString[0]}-${currentDateString[1]}-31 and boundDate ge ${threeMonthsAgoDate[0]}-${threeMonthsAgoDate[1]}-01`
	);
}

export function getPoliciesForSalesGoalSixMonths() {
	return axios.get(
		`${DeliveryAPI}/?fields=boundDate,termPremium&pageSize=200&filter=policyStatus ne 'declined' and userId eq '${userId}' and boundDate le ${currentDateString[0]}-${currentDateString[1]}-31 and boundDate ge ${sixMonthsAgoDate[0]}-${sixMonthsAgoDate[1]}-01`
	);
}

export function getPoliciesForSalesGoalUntilCurrentMonth() {
	return axios.get(
		`${DeliveryAPI}/?fields=boundDate,termPremium&pageSize=200&filter=policyStatus ne 'declined' and userId eq '${userId}' and boundDate le ${currentDateString[0]}-${currentDateString[1]}-31 and boundDate ge ${currentYear}-01-01`
	);
}
