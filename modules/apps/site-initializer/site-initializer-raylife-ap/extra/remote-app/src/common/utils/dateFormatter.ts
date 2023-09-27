/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
export const getCurrentDate = new Date().toISOString().slice(0, 10);

export const sixMonthsAgo = currentMonth - 5;
export const threeMonthsAgo = currentMonth - 2;
export const lastYear = currentYear - 1;

export const nowDate = currentDate;
export const policyCreateDate = nowDate;
export const getDataToAddYear = new Date();
getDataToAddYear.setFullYear(getDataToAddYear.getFullYear() + 1);

export const yearToYear = new Date(getDataToAddYear);
export const endDate = new Date(getDataToAddYear);
export const paymentDueDate = new Date(
	new Date().getTime() + 5 * 24 * 60 * 60 * 1000
);
export const getLastPaymentDate = new Date();
getLastPaymentDate.setMonth(getLastPaymentDate.getMonth() + 11);
export const lastPaymentDate = new Date(getLastPaymentDate);

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

export function dateFormatter(date?: string) {
	if (date) {
		return new Date(`${date}`)
			.toLocaleTimeString('en-US', {
				day: 'numeric',
				hour: 'numeric',
				hour12: true,
				minute: 'numeric',
				month: 'numeric',
				timeZone: 'UTC',
				year: 'numeric',
			})
			.replace(',', '');
	}
}

export function dateFormatterLocalString(date?: string) {
	if (date) {
		return new Date(date).toLocaleString('en-US', {
			day: 'numeric',
			month: 'short',
			timeZone: 'UTC',
			year: 'numeric',
		});
	}
}
