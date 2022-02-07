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

import {
	Bar,
	BarChart,
	BarProps,
	CartesianGrid,
	Legend,
	ResponsiveContainer,
	Tooltip,
	XAxis,
	YAxis,
} from 'recharts';

type BarChartComponentProps = {
	bars: Omit<BarProps, 'ref'>[];
	data: any[];
};

const defaultColors = ['#8884d8', '#82ca9d'];

const BarChartComponent: React.FC<BarChartComponentProps> = ({bars, data}) => {
	return (
		<ResponsiveContainer height={300} width="100%">
			<BarChart
				data={data}
				height={300}
				margin={{
					bottom: 5,
					left: 20,
					right: 30,
					top: 20,
				}}
				width={500}
			>
				<CartesianGrid strokeDasharray="3 3" />

				<XAxis dataKey="name" />

				<YAxis />

				<Tooltip />

				<Legend />

				{bars.map(({fill, ...bar}, index) => (
					<Bar
						fill={fill || defaultColors[index]}
						key={index}
						stackId="a"
						{...bar}
					/>
				))}
			</BarChart>
		</ResponsiveContainer>
	);
};

export default BarChartComponent;
