import Circle from 'shared/components/Circle';
import React from 'react';
import {
	Bar,
	BarChart,
	Legend,
	ResponsiveContainer,
	XAxis,
	YAxis
} from 'recharts';
import {Text} from '@clayui/core';

type Items = {
	[key: string]: {
		color: string;
		label: string;
		value: number;
	};
};

function transformData(items: Items): {[key: string]: number}[] {
	const data = {};

	Object.keys(items).forEach(key => {
		data[key] = items[key].value;
	});

	return [data];
}

interface IUsageMetricBarChart {
	items: Items;
	showLegend?: boolean;
	total: number;
}

interface IUsageMetricLegendProps {
	legendMap: Items;
	payload?: {color: string; value: string}[];
}

export const UsageMetricBarChart: React.FC<IUsageMetricBarChart> = ({
	items,
	showLegend = true,
	total
}) => (
	<ResponsiveContainer height={24} width='100%'>
		<BarChart
			data={transformData(items)}
			layout='vertical'
			margin={{bottom: 0, left: 0, right: 0, top: 0}}
		>
			<XAxis domain={[0, total]} hide type='number' />
			<YAxis dataKey='name' hide type='category' />

			{Object.keys(items).map((key, index) => (
				<Bar
					background={index === 0}
					dataKey={key}
					fill={items[key].color}
					key={index}
					stackId='barId'
				/>
			))}

			{showLegend && (
				<Legend content={<UsageMetricLegend legendMap={items} />} />
			)}
		</BarChart>
	</ResponsiveContainer>
);

const UsageMetricLegend: React.FC<IUsageMetricLegendProps> = ({
	legendMap,
	payload
}) => (
	<div style={{position: 'absolute'}}>
		{payload.map((entry, index) => (
			<div className='d-inline-block mr-3' key={`item-${index}`}>
				<Circle
					className='d-inline-block'
					color={entry.color}
					size={8}
				/>

				<Text color='secondary' size={3}>
					{legendMap[entry.value].label}
				</Text>
			</div>
		))}
	</div>
);
