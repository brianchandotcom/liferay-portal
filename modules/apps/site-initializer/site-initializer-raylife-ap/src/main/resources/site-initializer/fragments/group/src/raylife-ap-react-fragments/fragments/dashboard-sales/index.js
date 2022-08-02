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
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import DonutChart from '../../../common/components/donut-chart';

export default function () {
	const [selectedFilterDate, setSelectedFilterDate] = useState('1');

	const colors = {
		reached: '#ec0d6b',
		remaining: '#fbcee1',
	};

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
				['reached', 20],
				['remaining', 100],
			],
			dateUntilGoal: '156 days to goal',
			goalValue: '$72,500.00',
			period: 1,
			salesPercentual: '17%',
			salesValue: '$5,012.55',
		},
		{
			dataColumns: [
				['reached', 30],
				['remaining', 80],
			],
			dateUntilGoal: '84 days to goal',
			goalValue: '$111,500.00',
			period: 2,
			salesPercentual: '27%',
			salesValue: '$12,012.55',
		},
		{
			dataColumns: [
				['reached', 60],
				['remaining', 100],
			],
			dateUntilGoal: '110 days to goal',
			goalValue: '$12,500.00',
			period: 3,
			salesPercentual: '37%',
			salesValue: '$3,012.55',
		},
		{
			dataColumns: [
				['reached', 30],
				['remaining', 90],
			],
			dateUntilGoal: '02 days to goal',
			goalValue: '$6,500.00',
			period: 4,
			salesPercentual: '25%',
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
	const getSalesPercentual = getData()[0]?.salesPercentual;

	const LegendElement = () => (
		<div className="d-flex donut-chart-legend flex-column h-100 justify-content-end ml-5 mt-5">
			<div className="donut-chart-screen font-weight-bolder h5">
				{getSalesValue}
			</div>

			<div className="font-weight-normal mb-2 text-neutral-8 text-paragraph-sm">
				{`Goal ${getGoalValue}`}
			</div>

			<div className="font-weight-bolder text-danger text-paragraph-sm">
				<ClayIcon className="mr-1" symbol="time" />

				{getDateUntilGoal}
			</div>
		</div>
	);

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
					{options.map((option) => (
						<ClaySelect.Option
							key={option.value}
							label={option.label}
							value={option.value}
						/>
					))}
				</ClaySelect>
			</div>

			{!!chartData.columns.length && (
				<DonutChart
					LegendElement={LegendElement}
					chartData={chartData}
					hasLegend={true}
					title={getSalesPercentual}
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
