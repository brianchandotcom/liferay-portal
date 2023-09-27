/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CONSTANTS} from '../../../../common/utils/constants';
import {currentYear} from '../../../../common/utils/dateFormatter';

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
	let yearAdjustment = false;

	for (let count = 0; count <= period; count++) {
		const monthsSalesFilter: any = {};
		const monthsGoalsFilter: any = {};

		if (period !== indexOfCurrentMonth) {
			if (!count) {
				month = indexBaseMonth;
			}
			if (month > maxIndexOfMonthsArray) {
				month = 0;
				yearAdjustment = true;
			}
		}

		monthsSalesFilter[CONSTANTS.MONTHS_ABREVIATIONS[month]] = 0;
		monthsGoalsFilter[CONSTANTS.MONTHS_ABREVIATIONS[month]] = 0;

		if (month > 6 && !yearAdjustment && indexOfCurrentMonth <= 5) {
			monthsLabel[count] =
				CONSTANTS.MONTHS_ABREVIATIONS[month] + ` ${currentYear - 1}`;
		}
		else {
			monthsLabel[count] =
				CONSTANTS.MONTHS_ABREVIATIONS[month] + ` ${currentYear}`;
		}

		monthsSalesArray[count] = monthsSalesFilter;
		monthsGoalsArray[count] = monthsGoalsFilter;
		month++;
	}
}
