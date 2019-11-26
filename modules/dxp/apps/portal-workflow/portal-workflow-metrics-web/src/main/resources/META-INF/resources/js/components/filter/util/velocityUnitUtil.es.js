/* eslint-disable react-hooks/exhaustive-deps */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import moment from 'moment';

const asDefault = velocityUnit => {
	return {
		...velocityUnit,
		active: true,
		defaultVelocityUnit: true
	};
};

const daysUnit = {
	key: velocityUnitConstants.days,
	name: Liferay.Language.get('inst-day')
};

const hoursUnit = {
	key: velocityUnitConstants.hours,
	name: Liferay.Language.get('inst-hour')
};

const monthsUnit = {
	key: velocityUnitConstants.months,
	name: Liferay.Language.get('inst-month')
};

const weeksUnit = {
	key: velocityUnitConstants.weeks,
	name: Liferay.Language.get('inst-week')
};

const yearsUnit = {
	key: velocityUnitConstants.years,
	name: Liferay.Language.get('inst-year')
};

const velocityUnitConstants = {
	days: 'Days',
	hours: 'Hours',
	months: 'Months',
	weeks: 'Weeks',
	years: 'Years'
};

const velocityUnitsMap = {
	1: [asDefault(hoursUnit)],
	7: [asDefault(daysUnit)],
	30: [asDefault(daysUnit), weeksUnit],
	90: [daysUnit, asDefault(weeksUnit), monthsUnit],
	180: [weeksUnit, asDefault(monthsUnit)],
	366: [weeksUnit, asDefault(monthsUnit)],
	730: [asDefault(monthsUnit), yearsUnit]
};

const getVelocityUnits = ({dateEnd, dateStart}) => {
	const daysDiff = moment.utc(dateEnd).diff(moment.utc(dateStart), 'days');

	return (
		Object.keys(velocityUnitsMap)
			.filter(key => daysDiff < key)
			.map(key => velocityUnitsMap[key])[0] || [asDefault(yearsUnit)]
	);
};

export {getVelocityUnits, velocityUnitConstants};
