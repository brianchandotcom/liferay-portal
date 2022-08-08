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

export function convertDateToString(date) {
	const newDate = date.toISOString().substring(0, 10);

	return newDate;
}

export const getCurrentDay = new Date().getDate();
export const currentYear = new Date().getFullYear();
export const currentDate = convertDateToString(new Date());
export const currentDateString = currentDate.split('-');
export const getCurrentMonth = new Date().getMonth();
export const sixMonthsAgo = getCurrentMonth - 5;
export const threeMonthsAgo = getCurrentMonth - 2;
export const lastYear = currentYear - 1;
export const january = '01';
export const december = '12';

export const arrayOfMonthsWith30Days = [3, 5, 8, 10];
export const arrayOfMonthsWith31Days = [0, 2, 4, 6, 7, 9, 11];

export const threeMonthsAgoDate = convertDateToString(
	new Date(new Date().setMonth(threeMonthsAgo))
).split('-');

export const sixMonthsAgoDate = convertDateToString(
	new Date(new Date().setMonth(sixMonthsAgo))
).split('-');

export const oneYearAgoDate = convertDateToString(
	new Date(new Date().setFullYear(lastYear))
);

export const lastYearSixMonthsAgoPeriod = convertDateToString(
	new Date(new Date(new Date().setFullYear(lastYear)).setMonth(sixMonthsAgo))
).split('-');

export const getDayOfYear = Math.floor(
	(new Date() - new Date(new Date().getFullYear(), 0, 0)) /
		1000 /
		60 /
		60 /
		24
);
