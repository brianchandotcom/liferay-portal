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

import React from 'react';

import AuditBarChart from './components/AuditBarChart';
import EmptyAuditBarChart from './components/EmptyAuditBarChart';

const bars = [
	{
		dataKey: 'education',
		fill: '#4B9BFF',
		name: Liferay.Language.get('education'),
	},
	{
		dataKey: 'selection',
		fill: '#50D2A0',
		name: Liferay.Language.get('selection'),
	},
	{
		dataKey: 'solution',
		fill: '#FFB46E',
		name: Liferay.Language.get('solution'),
	},
];

const data = [
	{
		education: 4000,
		name: 'Business Decision Maker',
		selection: 2400,
		solution: 3100,
	},
	{
		education: 3000,
		name: 'Business End User',
		selection: 1398,
		solution: 2111,
	},
	{
		education: 2000,
		name: 'Technical Decision Maker',
		selection: 4800,
		solution: 1070,
	},
	{
		education: 2780,
		name: 'Technical End User',
		selection: 3908,
		solution: 1234,
	},
];

const rtl = false;

export default function () {
	return data.length ? (
		<AuditBarChart bars={bars} data={data} rtl={rtl} />
	) : (
		<EmptyAuditBarChart />
	);
}
