/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const getYearlyTerms = ({endDate, startDate}) => {
	endDate = new Date(endDate);
	startDate = new Date(startDate);

	const endDateYear = endDate.getFullYear();
	const startDateYear = startDate.getFullYear();
	const yearlyTerms = [];

	if (startDateYear === endDateYear) {
		yearlyTerms.push({endDate, startDate});

		return yearlyTerms;
	}

	for (let year = startDateYear; year <= endDateYear; year++) {
		const startDateForYear = new Date(startDate.setFullYear(year));
		const endDateForYear = new Date(
			year + 1,
			startDate.getMonth(),
			startDate.getDate() - 1
		);

		if (year === endDateYear && endDateForYear > endDate) {
			continue;
		}

		yearlyTerms.push({
			startDate: startDateForYear,
			endDate: endDateForYear,
		});
	}

	return yearlyTerms;
};

export {getYearlyTerms};
