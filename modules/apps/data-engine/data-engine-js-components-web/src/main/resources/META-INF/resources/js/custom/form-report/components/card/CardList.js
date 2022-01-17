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

import toDataArray, {sumTotalEntries, toArray} from '../../utils/data';
import fieldTypes from '../../utils/fieldTypes';
import MultiBarChart from '../chart/bar/MultiBarChart';
import SimpleBarChart from '../chart/bar/SimpleBarChart';
import PieChart from '../chart/pie/PieChart';
import EmptyState from '../empty-state/EmptyState';
import List from '../list/List';
import Card from './Card';

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
		case 'address':
		case 'city':
		case 'color':
		case 'country':
		case 'date':
		case 'date_time':
		case 'place':
		case 'postal-code':
		case 'state':
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
		case 'checkbox': {
			const newValues = {};
			for (const [key, value] of Object.entries(values)) {
				const newKey =
					key === 'true'
						? Liferay.Language.get('true')
						: Liferay.Language.get('false');
				newValues[newKey] = value;
			}

			return (
				<PieChart
					data={toDataArray(options, newValues)}
					totalEntries={sumTotalValues}
				/>
			);
		}
		case 'checkbox_multiple': {
			return (
				<SimpleBarChart
					data={toDataArray(options, values)}
					totalEntries={totalEntries}
				/>
			);
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
		case 'object-relationship':
		case 'radio':
		case 'select': {
			return (
				<PieChart
					data={toDataArray(options, values)}
					totalEntries={sumTotalValues}
				/>
			);
		}
		default:
			return null;
	}
};

export default function CardList({data, fields}) {
	let hasCards = false;

	const cards = fields.map((field, index) => {
		const newData =
			data[field.parentFieldName]?.[field.name] ?? data[field.name] ?? {};
		const {
			values = {},
			structure = {},
			summary = {},
			totalEntries,
		} = newData;
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
}
