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
	CartesianGrid,
	Legend,
	Line,
	LineChart,
	ResponsiveContainer,
	Tooltip,
	XAxis,
	YAxis,
} from 'recharts';

const Chart: React.FC<{data: any[]}> = ({data}) => (
	<ResponsiveContainer height={300}>
		<LineChart
			data={data}
			height={300}
			margin={{
				bottom: 5,
				left: 20,
				right: 30,
				top: 5,
			}}
			stackOffset="expand"
			width={500}
		>
			<Legend
				align="right"
				iconSize={10}
				iconType="square"
				verticalAlign="top"
				wrapperStyle={{
					fontSize: 12,
					marginLeft: 40,
					textTransform: 'uppercase',
				}}
			/>

			<CartesianGrid strokeDasharray="3 3" />

			<XAxis dataKey="name" />

			<YAxis />

			<Tooltip />

			<Line
				dataKey="passed"
				fill="#3cd587"
				stroke="#3cd587"
				type="linear"
			/>

			<Line
				dataKey="failed"
				fill="#e73a45"
				stroke="#e73a45"
				type="linear"
			/>

			<Line
				activeDot={{r: 8}}
				dataKey="blocked"
				fill="#f8d72e"
				stroke="#f8d72e"
				strokeWidth={2}
				type="linear"
			/>

			<Line
				dataKey="test_fix"
				fill="#59bbfc"
				stroke="#59bbfc"
				type="linear"
			/>

			<Line
				dataKey="incomplete"
				fill="#acbcc7"
				stroke="#acbcc7"
				type="linear"
			/>
		</LineChart>
	</ResponsiveContainer>
);

export default Chart;
