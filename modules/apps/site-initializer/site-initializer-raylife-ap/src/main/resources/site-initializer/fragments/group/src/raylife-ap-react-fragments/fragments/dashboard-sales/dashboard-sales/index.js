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

import {ClaySelect} from '@clayui/form';
import React, {useEffect, useState} from 'react';

import DonutChart from '../../../../common/components/donut-chart';

export default function () {
	const [chartTitle, setChartTitle] = useState('');
	const [selectedFilterDate, setSelectedFilterDate] = useState('1');

	const colors = {
		a: '#ec0d6b',
		b: '#fbcee1',
	};

	useEffect(() => {
		setChartTitle('50%');
	}, []);

	const options = [
		{
			label: 'This Month',
			value: '1',
		},
		{
			label: '3 MO',
			value: '2',
		},
		{
			label: '6 MO',
			value: '3',
		},
		{
			label: 'YTD',
			value: '4',
		},
	];

	const loadData = [
		{
			dataColumns: [
				['data1', 100],
				['data2', 20],
			],
			dateUntilGoal: '156 days to goal',
			goalValue: '$72,500.00',
			period: 1,
			salesValue: '$5,012.55',
		},
		{
			dataColumns: [
				['data1', 30],
				['data2', 50],
			],
			dateUntilGoal: '84 days to goal',
			goalValue: '$111,500.00',
			period: 2,
			salesValue: '$12,012.55',
		},
		{
			dataColumns: [
				['data1', 60],
				['data2', 100],
			],
			dateUntilGoal: '110 days to goal',
			goalValue: '$12,500.00',
			period: 3,
			salesValue: '$3,012.55',
		},
		{
			dataColumns: [
				['data1', 30],
				['data2', 60],
			],
			dateUntilGoal: '02 days to goal',
			goalValue: '$6,500.00',
			period: 4,
			salesValue: '$1,012.55',
		},
	];

	const getData = () => {
		return loadData?.filter(
			(data) => data.period === Number(selectedFilterDate)
		);
	};

	const chartData = {
		colors,
		columns: getData()[0]?.dataColumns,
		type: 'donut',
	};

	const getDateUntilGoal = getData()[0]?.dateUntilGoal;
	const getSalesValue = getData()[0]?.salesValue;
	const getGoalValue = getData()[0]?.goalValue;

	return (
		<div className="d-flex dashboard-sales-container flex-column flex-shrink-0 pb-4 pt-3 px-3">
			<div className="align-items-center d-flex dashboard-sales-header justify-content-between">
				<div className="dashboard-sales-title font-weight-bolder h4">
					Sales
				</div>

				<ClaySelect
					className="dashboard-sales-select"
					onChange={({target}) => {
						setSelectedFilterDate(target.value);
					}}
					sizing="sm"
					value={selectedFilterDate}
				>
					{options.map((item) => (
						<ClaySelect.Option
							key={item.value}
							label={item.label}
							value={item.value}
						/>
					))}
				</ClaySelect>
			</div>

			{!!chartData.columns.length && (
				<DonutChart
					chartData={chartData}
					dateUntilGoal={getDateUntilGoal}
					goalValue={getGoalValue}
					hasLegend={true}
					salesValue={getSalesValue}
					title={chartTitle}
				/>
			)}

			{!chartData.columns.length && (
				<div className="align-items-center d-flex flex-grow-1 justify-content-center">
					<span>No Data Applications</span>
				</div>
			)}
		</div>
	);
}
