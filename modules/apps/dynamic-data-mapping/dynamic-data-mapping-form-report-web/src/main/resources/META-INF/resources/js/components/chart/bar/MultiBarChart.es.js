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

import React, {useState} from 'react';
import {
	Bar,
	BarChart,
	Cell,
	Legend,
	ResponsiveContainer,
	Tooltip,
	XAxis,
	YAxis,
} from 'recharts';

import colors, {NAMED_COLORS} from '../../../utils/colors.es';
import ellipsize from '../../../utils/ellipsize.es';
import TooltipContent from '../TooltipContent.es';
import CustomizedAxisTick from './CustomizedAxisTick.es';

const {blueDark, gray} = NAMED_COLORS;

export default ({data, field, height, structure, width}) => {
	const [activeIndex, setActiveIndex] = useState(null);

	const getColumnLabel = (column) => {
		return field.columns[column];
	};

	const getRowLabel = (row) => {
		return field.rows[row];
	};

	const processStructure = ({columns, rows}) => {
		return {
			columns: columns.filter((column) => getColumnLabel(column)),
			rows: rows.filter((row) => getRowLabel(row)),
		};
	};

	const processedStructure = processStructure(structure);

	const {columns} = processedStructure;

	const handleOnMouseOut = () => {
		setActiveIndex(null);
	};

	const handleOnMouseOver = (activeIndex) => {
		setActiveIndex(activeIndex);
	};

	const processData = ({columns, rows}) => {
		const processedData = [];

		rows.map((row) => {
			const newData = {name: getRowLabel(row)};

			columns.map((column) => {
				newData[getColumnLabel(column)] = data[row][column]
					? data[row][column]
					: 0;
			});
			processedData.push(newData);
		});

		return processedData;
	};

	data = processedStructure ? processData(processedStructure) : data;

	const renderLegend = (props) => {
		const {payload} = props;

		return (
			<ul className="bar-legend">
				{payload.map((entry, index) => (
					<li key={`item-${index}`}>
						<svg height="12" width="12">
							<rect fill={entry.color} height="12" width="12" />
						</svg>
						<span>
							{entry.value && entry.value.length > 44
								? ellipsize(entry.value, 44)
								: entry.value}
						</span>
					</li>
				))}
			</ul>
		);
	};

	return (
		<ResponsiveContainer height={height || '99%'} width={width || '99%'}>
			<BarChart
				data={data}
				layout={'horizontal'}
				margin={{
					bottom: 20,
					left: 20,
					right: 20,
					top: 20,
				}}
			>
				<XAxis
					axisLine={{stroke: blueDark}}
					dataKey="name"
					interval={0}
					tick={
						<CustomizedAxisTick
							ticksNumber={structure.rows.length}
						/>
					}
					tickLine={false}
				/>

				<YAxis
					axisLine={{stroke: gray}}
					tickLine={false}
					type="number"
				/>

				<Tooltip
					content={<TooltipContent roundBullet={false} />}
					cursor={{fill: 'transparent'}}
				/>

				{columns.map((row, index) => {
					return (
						<Bar
							dataKey={getColumnLabel(row)}
							fill={colors(index)}
							key={`bar-${index}`}
							onMouseOut={handleOnMouseOut}
							onMouseOver={(_, index) => handleOnMouseOver(index)}
						>
							{data.map((_, index) => (
								<Cell
									fillOpacity={
										activeIndex != null &&
										activeIndex != index
											? '.5'
											: 1
									}
									key={`cell-${index}`}
								/>
							))}
						</Bar>
					);
				})}

				<Legend
					content={renderLegend}
					iconType="square"
					verticalAlign="top"
				/>
			</BarChart>
		</ResponsiveContainer>
	);
};
