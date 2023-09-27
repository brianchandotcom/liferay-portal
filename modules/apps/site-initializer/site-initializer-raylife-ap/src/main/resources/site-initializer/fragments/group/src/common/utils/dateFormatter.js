/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CONSTANTS} from './constants';

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

export const nextMonth = getCurrentMonth + 1;
export const nextThreeMonths = getCurrentMonth + 3;

export const lastDayOfMonth = 0;
export const fistDay = 1;
export const fistDayOfMonth = 1;
export const firstMonthOfYear = 0;

export const lastMonth = getCurrentMonth - 1;
export const lastYear = currentYear - 1;
export const january = '01';
export const december = '12';

export const arrayOfMonthsWith30Days = [3, 5, 8, 10];
export const arrayOfMonthsWith31Days = [0, 2, 4, 6, 7, 9, 11];

export const lastDayOfLastMonthDate = convertDateToString(
	new Date(
		new Date(new Date().setMonth(getCurrentMonth)).setDate(lastDayOfMonth)
	)
).split('-');

export const firstDayOfLastMonthDate = convertDateToString(
	new Date(new Date(new Date().setMonth(lastMonth)).setDate(fistDayOfMonth))
).split('-');

export const lastMonthDate = convertDateToString(
	new Date(new Date().setMonth(lastMonth))
).split('-');

export const lastDateOfLastYear = convertDateToString(
	new Date(
		new Date(
			new Date(new Date().setFullYear(lastYear)).setMonth(
				getCurrentMonth + 1
			)
		).setDate(lastDayOfMonth)
	)
).split('-');

export const threeMonthsAgoDate = convertDateToString(
	new Date(
		new Date(new Date().setMonth(threeMonthsAgo)).setDate(fistDayOfMonth)
	)
).split('-');

const generateDateThroughPeriod = (period, day) => {
	const generatedDate = convertDateToString(
		new Date(
			new Date(
				new Date(new Date().setFullYear(lastYear)).setMonth(period)
			).setDate(day)
		)
	).split('-');

	return generatedDate;
};

export const threeMonthsAgoDateLastYearFirstDay = generateDateThroughPeriod(
	threeMonthsAgo,
	fistDayOfMonth
);

export const threeMonthsAgoDateLastYearLastDay = generateDateThroughPeriod(
	getCurrentMonth + 1,
	lastDayOfMonth
);

export const sixMonthsAgoDateLastYearFirstDay = generateDateThroughPeriod(
	sixMonthsAgo,
	fistDayOfMonth
);

export const sixMonthsAgoDateLastYearLastDay = generateDateThroughPeriod(
	getCurrentMonth + 1,
	lastDayOfMonth
);

export const sixMonthsAgoDate = convertDateToString(
	new Date(
		new Date(new Date().setMonth(sixMonthsAgo)).setDate(fistDayOfMonth)
	)
).split('-');

export const yearToDate = convertDateToString(
	new Date(
		new Date(new Date().setMonth(firstMonthOfYear)).setDate(fistDayOfMonth)
	)
).split('-');

export const yearToDateLastYear = convertDateToString(
	new Date(
		new Date(
			new Date(new Date().setFullYear(lastYear)).setMonth(
				firstMonthOfYear
			)
		).setDate(fistDayOfMonth)
	)
).split('-');

export const oneYearAgoDate = convertDateToString(
	new Date(new Date().setFullYear(lastYear))
);

export const lastYearSixMonthsAgoPeriod = convertDateToString(
	new Date(new Date(new Date().setFullYear(lastYear)).setMonth(sixMonthsAgo))
).split('-');

export const nextMonthDate = convertDateToString(
	new Date(new Date().setMonth(nextMonth))
).split('-');

export const nextThreeMonthsDate = convertDateToString(
	new Date(new Date().setMonth(nextThreeMonths))
).split('-');

export const lastDateCurrentMonth = convertDateToString(
	new Date(new Date(new Date().setMonth(nextMonth)).setDate(0))
).split('-');

export const lastDateNextThreeMonth = convertDateToString(
	new Date(new Date(new Date().setMonth(nextThreeMonths)).setDate(0))
).split('-');

export const getDayOfYear = Math.floor(
	(new Date() - new Date(new Date().getFullYear(), 0, 0)) /
		1000 /
		60 /
		60 /
		24
);

export default function formatDate(date, withSlash) {
	const newDate = date.toISOString().substring(0, 10).split('-');

	if (withSlash) {
		return `${newDate[1]}/${newDate[2]}/${newDate[0]}`;
	}

	return `${CONSTANTS.MONTHS_ABREVIATIONS[date.getMonth()]} ${newDate[2]}, ${
		newDate[0]
	}`;
}
