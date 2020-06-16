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

import React, {useMemo} from 'react';

import AuditBarChart from './components/AuditBarChart';
import EmptyAuditBarChart from './components/EmptyAuditBarChart';

export default function () {
	const categories = [
		{
			children: [
				{key: 'education', name: 'Education', value: 478},
				{key: 'selection', name: 'Selection', value: 1055},
				{key: 'selection', name: 'Selection', value: 822},
			],
			key: 'business-decision-maker',
			name: 'Business Decision Maker',
		},
		{
			children: [
				{key: 'education', name: 'Education', value: 125},
				{key: 'selection', name: 'Selection', value: 1906},
				{key: 'solution', name: 'Solution', value: 987},
			],
			key: 'business-end-user',
			name: 'Business End User',
		},
		{
			children: [
				{key: 'education', name: 'Education', value: 444},
				{key: 'selection', name: 'Selection', value: 1733},
				{key: 'solution', name: 'Solution', value: 1807},
			],
			key: 'technical-decision-maker',
			name: 'Technical Decision Maker',
		},
		{
			children: [
				{key: 'education', name: 'Education', value: 125},
				{key: 'selection', name: 'Selection', value: 317},
				{key: 'solution', name: 'Solution', value: 187},
			],
			key: 'technical-end-user',
			name: 'Technical End User',
		},
	];

	const rtl = false;

	const auditBarChartData = useMemo(() => {
		const bars = [];
		const data = [];

		categories.map((category) => {
			var dataChild = {name: category.name};

			const children = category.children;

			for (var i = 0; i < children.length; i++) {
				const barChild = {
					dataKey: children[i].key,
					name: children[i].name,
				};
				if (!bars.some((bar) => bar.dataKey === children[i].key)) {
					bars.push(barChild);
				}

				dataChild = {
					...dataChild,
					[children[i].key]: children[i].value,
				};
			}
			data.push(dataChild);
		});

		return {bars, data};
	}, [categories]);

	const {bars, data} = auditBarChartData;

	return data.length ? (
		<AuditBarChart bars={bars} data={data} rtl={rtl} />
	) : (
		<EmptyAuditBarChart />
	);
}
