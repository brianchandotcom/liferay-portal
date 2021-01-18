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
import MultiBarChart from '../chart/bar/MultiBarChart.es';
import SimpleBarChart from '../chart/bar/SimpleBarChart.es';
import PieChart from '../chart/pie/PieChart.es';
import EmptyState from '../empty-state/EmptyState.es';
import List from '../list/List.es';
import Card from './Card.es';

const chartFactory = ({
	field,
	structure,
	sumTotalValues,
	summary,
	totalEntries,
	values,
}) => {
	const {options, type} = field;

	switch (type) {
		case 'checkbox_multiple':
			return (
				<SimpleBarChart
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
						summary={summary}
						totalEntries={totalEntries}
					/>
				);
			}
			else {
				return '';
			}
		}

		case 'grid': {
			return (
				<MultiBarChart
					data={values}
					field={field}
					structure={structure}
					totalEntries={sumTotalValues}
				/>
			);
		}

		case 'radio':
		case 'select':
			return (
				<PieChart
					data={toDataArray(options, values)}
					totalEntries={sumTotalValues}
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
		const {values = {}, structure = {}, summary = {}, totalEntries} =
			data[field.name] || {};

		const sumTotalValues = sumTotalEntries(values);

		field = {
			...field,
			...fieldTypes[field.type],
		};

		const chartContent = {
			field,
			structure,
			sumTotalValues,
			summary,
			totalEntries,
			values,
		};

		const chart = chartFactory(chartContent);

		if (chart === null) {
			return null;
		}
		else {
			hasCards = true;
		}

		return (
			<Card
				field={field}
				index={index}
				key={index}
				summary={summary}
				totalEntries={totalEntries ? totalEntries : sumTotalValues}
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
