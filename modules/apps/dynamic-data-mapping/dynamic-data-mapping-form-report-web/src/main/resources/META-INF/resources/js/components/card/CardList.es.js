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

import toDataArray, {sumTotalEntries, toArray} from '../../utils/data.es';
import fieldTypes from '../../utils/fieldTypes.es';
import BarChart from '../chart/bar/BarChart.es';
import PieChart from '../chart/pie/PieChart.es';
import EmptyState from '../empty-state/EmptyState.es';
import List from '../list/List.es';
import Card from './Card.es';

const chartFactory = (field, values, totalEntries) => {
	const {options, type} = field;

	switch (type) {
		case 'checkbox_multiple':
			return (
				<BarChart
					data={toDataArray(options, values)}
					totalEntries={totalEntries}
				/>
			);

		case 'numeric': {
			if (Array.isArray(values)) {
				return (
					<List
						data={toArray(values)}
						field={field}
						totalEntries={totalEntries}
					/>
				);
			}
			else {
				return '';
			}
		}
		case 'radio':
		case 'select':
			return (
				<PieChart
					data={toDataArray(options, values)}
					totalEntries={totalEntries}
				/>
			);
		case 'color':
		case 'date':
		case 'text': {
			if (Array.isArray(values)) {
				return (
					<List
						data={toArray(values)}
						field={field}
						totalEntries={totalEntries}
						type={type}
					/>
				);
			}
			else {
				return '';
			}
		}

		default:
			return null;
	}
};

export default ({data, fields}) => {
	let hasCards = false;

	const cards = fields.map((field, index) => {
		const {
			values = {},
			summary = {},
			totalEntries = sumTotalEntries(values),
		} = data[field.name] || {};
		field = {
			...field,
			...fieldTypes[field.type],
		};

		const chart = chartFactory(field, values, totalEntries);

		if (chart === null) {
			return null;
		}
		else {
			hasCards = true;
		}

		return (
			<Card
				field={field}
				key={index}
				summary={summary}
				totalEntries={totalEntries}
			>
				{chart}
			</Card>
		);
	});

	if (!hasCards) {
		return <EmptyState />;
	}

	return cards;
};
