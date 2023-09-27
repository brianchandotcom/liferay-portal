/* eslint-disable react-hooks/exhaustive-deps */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moment from 'moment';

const asDefault = (velocityUnit) => {
	return {
		...velocityUnit,
		active: true,
		defaultVelocityUnit: true,
	};
};

const daysUnit = {
	key: 'Days',
	name: Liferay.Language.get('inst-day'),
};

const hoursUnit = {
	key: 'Hours',
	name: Liferay.Language.get('inst-hour'),
};

const monthsUnit = {
	key: 'Months',
	name: Liferay.Language.get('inst-month'),
};

const weeksUnit = {
	key: 'Weeks',
	name: Liferay.Language.get('inst-week'),
};

const yearsUnit = {
	key: 'Years',
	name: Liferay.Language.get('inst-year'),
};

const velocityUnitsMap = {
	1: [asDefault(hoursUnit)],
	7: [asDefault(daysUnit)],
	30: [asDefault(daysUnit), weeksUnit],
	90: [daysUnit, asDefault(weeksUnit), monthsUnit],
	180: [weeksUnit, asDefault(monthsUnit)],
	366: [weeksUnit, asDefault(monthsUnit)],
	730: [asDefault(monthsUnit), yearsUnit],
};

const getVelocityUnits = (timeRange) => {
	if (!timeRange.dateEnd || !timeRange.dateStart) {
		return [];
	}

	const dateEnd = moment(timeRange.dateEnd);
	const dateStart = moment(timeRange.dateStart);

	let daysDiff = dateEnd.diff(dateStart, 'days');

	if (daysDiff === 366 && (dateEnd.isLeapYear() || dateStart.isLeapYear())) {
		--daysDiff;
	}

	return (
		Object.keys(velocityUnitsMap)
			.filter((key) => daysDiff < key)
			.map((key) => velocityUnitsMap[key])[0] || [asDefault(yearsUnit)]
	);
};

export {getVelocityUnits};
