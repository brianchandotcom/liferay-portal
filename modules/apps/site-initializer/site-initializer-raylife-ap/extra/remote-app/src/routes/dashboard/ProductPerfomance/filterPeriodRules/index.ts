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

import {CONSTANTS} from '../../../../common/utils/constants';

const numberOfMonths = 12;
const maxIndexOfMonthsArray = 11;
const indexOfCurrentMonth = new Date().getMonth();

export function populateDataByPeriod(
	period: number,
	monthsLabel: string[],
	monthsSalesArray: string[],
	monthsGoalsArray: string[]
) {
	let indexBaseMonth = indexOfCurrentMonth - period;

	indexBaseMonth =
		indexBaseMonth < 0 ? numberOfMonths + indexBaseMonth : indexBaseMonth;

	let month = 0;

	for (let count = 0; count <= period; count++) {
		const monthsSalesFilter: any = {};
		const monthsGoalsFilter: any = {};

		if (period !== indexOfCurrentMonth) {
			if (!count) {
				month = indexBaseMonth;
			}
			if (month > maxIndexOfMonthsArray) {
				month = 0;
			}
		}

		monthsSalesFilter[CONSTANTS.MONTHS_ABREVIATIONS[month]] = 0;
		monthsGoalsFilter[CONSTANTS.MONTHS_ABREVIATIONS[month]] = 0;

		monthsLabel[count] = CONSTANTS.MONTHS_ABREVIATIONS[month];
		monthsSalesArray[count] = monthsSalesFilter;
		monthsGoalsArray[count] = monthsGoalsFilter;
		month++;
	}
}
