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

import {memo} from 'react';
import {
	Cell,
	Legend,
	Pie,
	PieChart,
	PieProps,
	ResponsiveContainer,
	Tooltip,
} from 'recharts';

import LegendContainer, {LegendItem} from './Legend';

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

type PieChartComponentProps = {
	data: {color?: string; name: string; value: number}[];
	pieProps: Omit<PieProps, 'ref'>;
};

const LegendContent: React.FC<any> = (props) => {
	return (
		<LegendContainer>
			{props?.payload?.map((payload: any, index: number) => {
				const {
					color,
					payload: {percent, value},
					value: label,
				} = payload;

				const _percent = percent * 100;
				const percentRound = Math.round(_percent);
				const percentCeil = Math.ceil(_percent);

				return (
					<LegendItem
						color={color}
						key={index}
						label={label}
						percent={
							percentRound !== percentCeil
								? `< ${percentCeil}`
								: percentRound
						}
						value={value}
					/>
				);
			})}
		</LegendContainer>
	);
};

const LegendContentMemo = memo(LegendContent);

const TooltipContent = (props: any) => {
	const [payload] = props.payload;

	if (props.active && payload) {
		const {
			name,
			payload: {color},
			value,
		} = payload;

		return (
			<LegendContainer>
				<LegendItem
					color={color}
					label={name}
					percent={12}
					value={value}
				/>
			</LegendContainer>
		);
	}

	return null;
};

const PieChartComponent: React.FC<PieChartComponentProps> = ({
	data,
	pieProps,
}) => {
	return (
		<ResponsiveContainer>
			<PieChart height={400} width={250}>
				<Legend
					align="right"
					content={(props) => <LegendContentMemo {...props} />}
					layout="centric"
					verticalAlign="top"
				/>

				<Tooltip content={(props) => <TooltipContent {...props} />} />

				<Pie
					cx={120}
					cy={120}
					data={data}
					innerRadius={60}
					legendType="circle"
					outerRadius={80}
					paddingAngle={0.5}
					{...pieProps}
				>
					{data.map((entry, index) => (
						<Cell
							fill={entry.color || COLORS[index % COLORS.length]}
							key={`cell-${index}`}
							name={entry.name}
						/>
					))}
				</Pie>
			</PieChart>
		</ResponsiveContainer>
	);
};

export default PieChartComponent;
