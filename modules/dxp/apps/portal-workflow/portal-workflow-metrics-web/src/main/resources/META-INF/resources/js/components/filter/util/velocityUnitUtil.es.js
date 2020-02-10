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
	key: 'Days',
	name: Liferay.Language.get('inst-day')
};

const hoursUnit = {
	key: 'Hours',
	name: Liferay.Language.get('inst-hour')
};

const monthsUnit = {
	key: 'Months',
	name: Liferay.Language.get('inst-month')
};

const weeksUnit = {
	key: 'Weeks',
	name: Liferay.Language.get('inst-week')
};

const yearsUnit = {
	key: 'Years',
	name: Liferay.Language.get('inst-year')
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
	if (!dateEnd || !dateStart) {
		return [];
	}

	const daysDiff = moment.utc(dateEnd).diff(moment.utc(dateStart), 'days');

	return (
		Object.keys(velocityUnitsMap)
			.filter(key => daysDiff < key)
			.map(key => velocityUnitsMap[key])[0] || [asDefault(yearsUnit)]
	);
};

export {getVelocityUnits};
