/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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

export function getPoliciesByPeriod(
	currentYear,
	currentMonth,
	currentDay,
	periodYear,
	periodMonth
) {
	return axios.get(
		`${DeliveryAPI}/?filter=policyStatus ne 'declined' and userId eq '${userId}' and startDate le ${currentYear}-${currentMonth}-${currentDay} and startDate ge ${periodYear}-${periodMonth}-01&pageSize=200`
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

export function getPoliciesChartExpiringPolicies(
	currentYear,
	currentMonth,
	currentDay,
	periodYear,
	periodMonth,
	periodDay
) {
	return axios.get(
		`${DeliveryAPI}/?fields=policyStatus,endDate,totalCount,productName,termPremium&pageSize=200&filter=policyStatus eq 'executed' and endDate ge ${currentYear}-${currentMonth}-${currentDay} and endDate le ${periodYear}-${periodMonth}-${periodDay}`
	);
}

export function getPolicies() {
	return axios.get(
		`${DeliveryAPI}/?fields=endDate,totalCount,productName,termPremium,externalReferenceCode&pageSize=200`
	);
}
