/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {
	Bar,
	BarChart,
	CartesianGrid,
	ReferenceLine,
	ResponsiveContainer,
	XAxis,
	YAxis,
} from 'recharts';

import {
	IFrequencyChartItem,
	IFrequencyChartProps,
} from '../../../common/utils/types';
import Loader from './Loader';

const margin = {
	bottom: 5,
	left: 20,
	right: 30,
	top: 20,
};

const getFrequencyLabel = (type: string): string => {
	if (type === 'DAILY') {
		return Liferay.Language.get('daily');
	}
	if (type === 'WEEKLY') {
		return Liferay.Language.get('weekly');
	}
	if (type === 'BIWEEKLY') {
		return Liferay.Language.get('biweekly');
	}
	if (type === 'MONTHLY') {
		return Liferay.Language.get('monthly');
	}

	return type;
};

const formatData = (
	frequencyChartItems: IFrequencyChartItem[]
): IFrequencyChartItem[] => {
	if (!frequencyChartItems) {
		return [];
	}

	return frequencyChartItems.map((frequencyChartItem) => ({
		frequencyType: getFrequencyLabel(frequencyChartItem.frequencyType),
		visitCount: frequencyChartItem.visitCount || 0,
	}));
};

function FrequencyChart({
	frequencyChartItems,
	isLoading = false,
}: IFrequencyChartProps) {
	if (isLoading) {
		return <Loader />;
	}

	const formattedData = formatData(
		frequencyChartItems as IFrequencyChartItem[]
	);

	if (!formattedData?.length) {
		return (
			<p className="text-muted">
				{Liferay.Language.get('no-data-available')}
			</p>
		);
	}

	return (
		<ResponsiveContainer>
			<BarChart
				data={formattedData}
				height={300}
				margin={margin}
				width={600}
			>
				{formattedData.map(
					(
						frequencyChartItem: IFrequencyChartItem,
						index: number
					) => {
						return (
							<ReferenceLine
								key={`bg-strip-${index}`}
								stroke="#E5F1FF"
								strokeOpacity={0.3}
								strokeWidth={60}
								x={frequencyChartItem.frequencyType}
							/>
						);
					}
				)}

				<CartesianGrid
					stroke="#ccc"
					strokeDasharray="5 5"
					vertical={(props: any) => (
						<line
							key={props.key}
							stroke="none"
							x1={props.x1}
							x2={props.x2}
							y1={props.y1}
							y2={props.y2}
						/>
					)}
				/>

				<Bar
					barSize={60}
					dataKey="visitCount"
					fill="#97C5FF"
					radius={[4, 4, 0, 0]}
				/>

				<XAxis dataKey="frequencyType" />

				<YAxis />
			</BarChart>
		</ResponsiveContainer>
	);
}

export default FrequencyChart;
