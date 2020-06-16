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

	return categories.length ? (
		<AuditBarChart categories={categories} rtl={rtl} />
	) : (
		<EmptyAuditBarChart />
	);
}
