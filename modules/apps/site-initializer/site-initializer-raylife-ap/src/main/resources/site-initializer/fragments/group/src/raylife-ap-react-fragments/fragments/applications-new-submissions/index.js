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
import classNames from 'classnames';
import React, {useState} from 'react';

import LineChart from '../../../common/components/line-chart';

export default function () {
	const [selectedFilterDate, setSelectedFilterDate] = useState('1');

	const FilterOptions = [
		{
			label: 'This Month',
			value: '1',
		},
		{
			label: '6 MO',
			value: '2',
		},
		{
			label: 'YTD',
			value: '3',
		},
	];

	const loadData = [
		{
			dataColumns: ['New Application', 13, 1],
			newApplicationsValue: 2,
			percentNewApplication: 15,
			period: 1,
			periodDate: 'Sep 2022',
		},
		{
			dataColumns: ['New Application', 60, 20, 70, 10, 30, 50],
			newApplicationsValue: 15,
			percentNewApplication: -10,
			period: 2,
			periodDate: 'Mar 2022 - Sep 2022',
		},
		{
			dataColumns: [
				'New Application',
				60,
				20,
				70,
				10,
				14,
				21,
				30,
				14,
				21,
			],
			newApplicationsValue: 32,
			percentNewApplication: 10,
			period: 3,
			periodDate: 'Jan - Sep',
		},
	];

	const getData = () => {
		return loadData.filter(
			(data) => data.period === Number(selectedFilterDate)
		);
	};

	const chartData = {
		columns: [getData()[0]?.dataColumns],
		type: 'line',
	};

	const newSubmissionsPercentual = getData()[0]?.percentNewApplication;
	const getDataDate = getData()[0]?.periodDate;
	const newSubmissionsValue = getData()[0]?.newApplicationsValue;
	const getPeriod = getData()[0]?.period;

	const submissionsLegend = () => (
		<div className="applications-new-submissions-legend d-flex flex-column h-100 justify-content-end mt-5">
			<div className="font-weight-normal mb-2 text-neutral-8 text-paragraph-sm">
				{getDataDate}
			</div>

			<div className="applications-new-submissions-screen font-weight-bolder h5 mb-3">
				{new Intl.NumberFormat().format(newSubmissionsValue)}
			</div>

			<div
				className={classNames('text-paragraph-sm font-weight-bolder', {
					'line-chart-icon-success-color':
						newSubmissionsPercentual >= 0,
					'text-danger': newSubmissionsPercentual < 0,
				})}
			>
				{newSubmissionsPercentual > 0 && (
					<ClayIcon symbol="caret-top" />
				)}

				{newSubmissionsPercentual === 0 && <ClayIcon symbol="hr" />}

				{newSubmissionsPercentual < 0 && (
					<ClayIcon symbol="caret-bottom" />
				)}

				{getPeriod === 1
					? `${
							newSubmissionsPercentual === Infinity
								? `NaN`
								: newSubmissionsPercentual
					  }% MoM`
					: `${
							newSubmissionsPercentual === Infinity
								? `NaN`
								: newSubmissionsPercentual
					  }% YoY`}
			</div>
		</div>
	);

	return (
		<div className="applications-new-submissions-container d-flex flex-column flex-shrink-0 pb-4 pt-3 px-3">
			<div className="align-items-center applications-new-submissions-header d-flex justify-content-between">
				<div className="applications-new-submissions-title font-weight-bolder h4">
					New Submissions
				</div>

				<ClaySelect
					className="applications-new-submissions-select p-1"
					onChange={({target}) => {
						setSelectedFilterDate(target.value);
					}}
					sizing="sm"
					value={selectedFilterDate}
				>
					{FilterOptions.map((option) => (
						<ClaySelect.Option
							key={option.value}
							label={option.label}
							value={option.value}
						/>
					))}
				</ClaySelect>
			</div>

			{!!chartData.columns.length && (
				<LineChart
					LegendElement={submissionsLegend}
					chartData={chartData}
					getDataDate={getDataDate}
					getPeriod={getPeriod}
					hasPersonalizedTooltip={true}
					percentual={newSubmissionsPercentual}
					value={newSubmissionsValue}
				/>
			)}

			{!chartData.columns.length && loadData && (
				<div className="align-items-center d-flex flex-grow-1 justify-content-center">
					<span>No Data Applications</span>
				</div>
			)}
		</div>
	);
}
