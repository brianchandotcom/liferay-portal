/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const getYearlyTerms = ({endDate, startDate}) => {
	const endDateFull = new Date(endDate);
	const startDateFull = new Date(startDate);

	const endDateDay = endDateFull.getDate();
	const endDateMonth = endDateFull.getMonth();
	const endDateYear = endDateFull.getFullYear();

	const startDateDay = startDateFull.getDate();
	const startDateMonth = startDateFull.getMonth();
	const startDateYear = startDateFull.getFullYear();

	if (startDateYear + 1 < endDateYear) {
		let arraySize = endDateYear - startDateYear;

		if (endDateMonth > startDateMonth) {
			arraySize = arraySize + 1;
		}
		else if (
			endDateMonth === startDateMonth &&
			endDateDay > startDateDay
		) {
			arraySize = arraySize + 1;
		}

		const yearDateSplitted = new Array(arraySize)
			.fill()
			.map((_, index, array) => {
				const indexYear = startDateYear + index;
				const indexNumStartDate = new Date(startDate).setFullYear(
					indexYear
				);
				const yearNumEndDate = indexYear + 1;

				const indexEndDate = new Date(
					yearNumEndDate,
					startDateMonth,
					startDateDay - 1
				);
				const indexStartDate = new Date(indexNumStartDate);

				if (index === array.length - 1) {
					return {
						endDate: endDateFull,
						startDate: indexStartDate,
					};
				}
				else {
					return {
						endDate: indexEndDate,
						startDate: indexStartDate,
					};
				}
			})
			.filter((item) => item);

		return yearDateSplitted;
	}

	return [{endDate: endDateFull, startDate: startDateFull}];
};

export {getYearlyTerms};
