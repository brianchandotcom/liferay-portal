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
	const vocabularies = [
		{
			categories: [
				{
					key: 'education',
					name: 'Education',
					value: 478,
					vocabularyName: 'Stage',
				},
				{
					key: 'selection',
					name: 'Selection',
					value: 1055,
					vocabularyName: 'Stage',
				},
				{
					key: 'selection',
					name: 'Selection',
					value: 822,
					vocabularyName: 'Stage',
				},
			],
			key: 'business-decision-maker',
			name: 'Business Decision Maker',
			vocabularyName: 'Audience',
		},
		{
			categories: [
				{
					key: 'education',
					name: 'Education',
					value: 125,
					vocabularyName: 'Stage',
				},
				{
					key: 'selection',
					name: 'Selection',
					value: 1906,
					vocabularyName: 'Stage',
				},
				{
					key: 'solution',
					name: 'Solution',
					value: 987,
					vocabularyName: 'Stage',
				},
			],
			key: 'business-end-user',
			name: 'Business End User',
			vocabularyName: 'Audience',
		},
		{
			categories: [
				{
					key: 'education',
					name: 'Education',
					value: 444,
					vocabularyName: 'Stage',
				},
				{
					key: 'selection',
					name: 'Selection',
					value: 1733,
					vocabularyName: 'Stage',
				},
				{
					key: 'solution',
					name: 'Solution',
					value: 1807,
					vocabularyName: 'Stage',
				},
			],
			key: 'technical-decision-maker',
			name: 'Technical Decision Maker',
			vocabularyName: 'Audience',
		},
		{
			categories: [
				{
					key: 'education',
					name: 'Education',
					value: 125,
					vocabularyName: 'Stage',
				},
				{
					key: 'selection',
					name: 'Selection',
					value: 317,
					vocabularyName: 'Stage',
				},
				{
					key: 'solution',
					name: 'Solution',
					value: 187,
					vocabularyName: 'Stage',
				},
			],
			key: 'technical-end-user',
			name: 'Technical End User',
			vocabularyName: 'Audience',
		},
	];

	const rtl = false;

	return vocabularies.length ? (
		<AuditBarChart rtl={rtl} vocabularies={vocabularies} />
	) : (
		<EmptyAuditBarChart />
	);
}
