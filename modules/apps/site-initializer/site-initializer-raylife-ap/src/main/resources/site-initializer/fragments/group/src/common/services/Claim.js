/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from './liferay/api';

const DeliveryAPI = 'o/c/raylifeclaims';

export function getTotalClaims() {
	return axios.get(`${DeliveryAPI}/`);
}

export function getActiveClaims() {
	return axios.get(`${DeliveryAPI}/?filter=claimStatus ne 'declined'`);
}

export function getClaimsStatus(status) {
	return axios.get(`${DeliveryAPI}/?filter=claimStatus eq '${status}'`);
}

export function getClaimsByPeriodSettled(
	currentYear,
	currentMonth,
	currentDay,
	periodYear,
	periodMonth,
	periodDay
) {
	return axios.get(
		`${DeliveryAPI}/?fields=claimCreateDate,claimStatus,r_policyToClaims_c_raylifePolicyERC&pageSize=200&filter=claimStatus ne 'declined' and claimCreateDate le ${currentYear}-${currentMonth}-${currentDay} and claimCreateDate ge ${periodYear}-${periodMonth}-${periodDay}`
	);
}

export function getClaimsByPeriod(
	currentYear,
	currentMonth,
	currentDay,
	periodYear,
	periodMonth,
	periodDay
) {
	return axios.get(
		`${DeliveryAPI}/?fields=claimStatus,claimCreateDate,claimAmount&pageSize=200&filter=claimCreateDate ge ${currentYear}-${currentMonth}-${currentDay} and claimCreateDate le ${periodYear}-${periodMonth}-${periodDay}`
	);
}

export function getClaims() {
	return axios.get(
		`${DeliveryAPI}/?fields=claimStatus,claimCreateDate,claimAmount,r_policyToClaims_c_raylifePolicyERC&pageSize=200`
	);
}

export function getSettledClaims(
	currentYear,
	currentMonth,
	currentDay,
	periodYear,
	periodMonth,
	periodDay
) {
	return axios.get(
		`${DeliveryAPI}/?fields=claimCreateDate,claimStatus,r_policyToClaims_c_raylifePolicyERC&pageSize=200&filter=claimStatus eq 'settled' and claimCreateDate le ${currentYear}-${currentMonth}-${currentDay} and claimCreateDate ge ${periodYear}-${periodMonth}-${periodDay}`
	);
}
