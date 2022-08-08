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
	currentDateString,
	currentYear,
	sixMonthsAgoDate,
	threeMonthsAgoDate,
} from '../utils/dateFormatter';
import {axios} from './liferay/api';

const DeliveryAPI = 'o/c/raylifesalesgoals';
const userId = Liferay.ThemeDisplay.getUserId();

export function getSalesGoalCurrentMonth() {
	return axios.get(
		`${DeliveryAPI}/?fields=finalReferenceDate,goalValue,initialReferenceDate&pageSize=200&filter=finalReferenceDate le ${currentDateString[0]}-${currentDateString[1]}-31 and finalReferenceDate ge ${currentDateString[0]}-${currentDateString[1]}-01 and userId eq '${userId}'`
	);
}

export function getSalesGoalLastThreeMonths() {
	return axios.get(
		`${DeliveryAPI}/?fields=finalReferenceDate,goalValue,initialReferenceDate&pageSize=200&filter=finalReferenceDate le ${currentDateString[0]}-${currentDateString[1]}-31 and finalReferenceDate ge ${threeMonthsAgoDate[0]}-${threeMonthsAgoDate[1]}-01 and userId eq '${userId}'`
	);
}

export function getSalesGoalLastSixMonths() {
	return axios.get(
		`${DeliveryAPI}/?fields=finalReferenceDate,goalValue,initialReferenceDate&pageSize=200&filter=finalReferenceDate le ${currentDateString[0]}-${currentDateString[1]}-31 and finalReferenceDate ge ${sixMonthsAgoDate[0]}-${sixMonthsAgoDate[1]}-01 and userId eq '${userId}'`
	);
}

export function getSalesGoalAllYear() {
	return axios.get(
		`${DeliveryAPI}/?fields=finalReferenceDate,goalValue,initialReferenceDate&pageSize=200&filter=finalReferenceDate le ${currentYear}-12-31 and finalReferenceDate ge ${currentYear}-01-01 and userId eq '${userId}'`
	);
}
