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

import {CONSTANTS} from './constants';

export function convertDateToString(date: Date) {
	return date.toISOString().substring(0, 10);
}

export const currentDate = convertDateToString(new Date());
export const currentDateString = currentDate.split('-');

export const currentDay = new Date().getDate();
export const currentMonth = new Date().getMonth();
export const currentYear = new Date().getFullYear();

export const sixMonthsAgo = currentMonth - 5;
export const threeMonthsAgo = currentMonth - 2;
export const lastYear = currentYear - 1;

export const january = '01';
export const december = '12';

export const getCurrentMonth = new Date().getMonth();

export const arrayOfMonthsWith30Days = [3, 5, 8, 10];
export const arrayOfMonthsWith31Days = [0, 2, 4, 6, 7, 9, 11];

export const threeMonthsAgoDate = convertDateToString(
	new Date(new Date().setMonth(threeMonthsAgo))
).split('-');

export const sixMonthsAgoDate = convertDateToString(
	new Date(new Date().setMonth(sixMonthsAgo))
).split('-');

export default function formatDate(date: Date, withSlash = false) {
	const newDate = date.toISOString().substring(0, 10).split('-');

	if (withSlash) {
		return `${newDate[1]}/${newDate[2]}/${newDate[0]}`;
	}

	return `${CONSTANTS.MONTHS_ABREVIATIONS[date.getMonth()]} ${newDate[2]}, ${
		newDate[0]
	}`;
}
