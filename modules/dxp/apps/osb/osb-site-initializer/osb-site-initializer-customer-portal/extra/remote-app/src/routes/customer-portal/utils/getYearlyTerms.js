/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const getYearlyTerms = ({endDate, startDate}) => {
	const subscriptionStartDate = new Date(startDate);
	const subscriptionEndDate = new Date(endDate);

	if (
		subscriptionStartDate.getFullYear() + 1 <
		subscriptionEndDate.getFullYear()
	) {
		let arraySize =
			subscriptionEndDate.getFullYear() -
			subscriptionStartDate.getFullYear();

		if (subscriptionEndDate.getMonth() > subscriptionStartDate.getMonth()) {
			arraySize = arraySize + 1;
		}

		if (
			subscriptionEndDate.getMonth() === subscriptionStartDate.getMonth()
		) {
			if (
				subscriptionEndDate.getDate() > subscriptionStartDate.getDate()
			) {
				arraySize = arraySize + 1;
			}
		}

		const yearDateSplitted = new Array(arraySize)
			.fill()
			.map((_, index, array) => {
				const currentYear = subscriptionStartDate.getFullYear() + index;
				const yearNumEndDate = currentYear + 1;
				const indexNumStartDate = new Date(startDate).setFullYear(
					currentYear
				);

				const daysEndDate = subscriptionStartDate.getDate();
				const monthsEndDate = subscriptionStartDate.getMonth();

				const indexEndDate = new Date(
					yearNumEndDate,
					monthsEndDate,
					daysEndDate - 1
				);
				const indexStartDate = new Date(indexNumStartDate);

				if (index === array.length - 1) {
					return {
						endDate: subscriptionEndDate,
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

	return [{endDate: subscriptionEndDate, startDate: subscriptionStartDate}];
};

export {getYearlyTerms};
