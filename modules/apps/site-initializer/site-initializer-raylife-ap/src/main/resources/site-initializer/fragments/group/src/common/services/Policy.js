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

import {axios} from './liferay/api';

const DeliveryAPI = 'o/c/raylifepolicies';

function convertDateToString(date) {
	const newDate = date.toISOString().substring(0, 10);

	return newDate;
}

const currentDate = convertDateToString(new Date());

const currentYear = new Date().getFullYear();

const lastYear = currentYear - 1;

const sixMonthsAgo = new Date().getMonth() - 5;

const oneYearAgoDate = convertDateToString(
	new Date(new Date().setFullYear(lastYear))
);

const sixMonthsAgoDate = convertDateToString(
	new Date(new Date().setMonth(sixMonthsAgo))
).split('-');

const lastYearSixMonthsAgoPeriod = convertDateToString(
	new Date(new Date(new Date().setFullYear(lastYear)).setMonth(sixMonthsAgo))
).split('-');

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
