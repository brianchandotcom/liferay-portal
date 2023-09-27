/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cardButtonType, dataFromType} from '../Types';

export const dataFromOptions: dataFromType[] = [
	{
		label: 'Previous Workday',
		value: 'previousWorkday',
	},
	{
		label: 'Current Workday',
		value: 'currentWorkday',
	},
];

export const dataHoursOptions: dataFromType[] = [
	{
		label: '5:00',
		value: '5:00',
	},
	{
		label: '5:30',
		value: '5:30',
	},
	{
		label: '6:00',
		value: '6:00',
	},
];

export const daysOfTheWeekButtons: cardButtonType[] = [
	{
		active: false,
		name: 'sun',
	},
	{
		active: false,
		name: 'mon',
	},
	{
		active: false,
		name: 'tue',
	},
	{
		active: false,
		name: 'wed',
	},
	{
		active: false,
		name: 'thu',
	},
	{
		active: false,
		name: 'fri',
	},
	{
		active: false,
		name: 'sat',
	},
];
