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
