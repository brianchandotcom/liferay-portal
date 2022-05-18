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
import React, {useState} from 'react';

import LineChart from '../../../common/components/line-chart';
import {CONSTANTS} from '../../../common/utils/constants';

export default function () {
	const currentDate = new Date();
	const month = CONSTANTS.MONTHS[currentDate.getMonth()];

	const [selectedFilterDate, setSelectedFilterDate] = useState('1');

	const options = [
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
			commissionValue: 20954.67,
			dataColumns: ['Commission', 10000, 10954.67],
			percentcommission: 15,
			period: 1,
			periodDate: month,
		},
		{
			commissionValue: 69126.95,
			dataColumns: ['Commission', 60, 20, 70, 10, 30, 50],
			percentcommission: -10,
			period: 2,
			periodDate: 'Dec 2021 - May 2022',
		},
		{
			commissionValue: 22633.29,
			dataColumns: ['Commission', 60, 20, 70, 10, 30],
			percentcommission: 0,
			period: 3,
			periodDate: 'Jan - May',
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

	const commissionPercentual = getData()[0]?.percentcommission;
	const getDataDate = getData()[0]?.periodDate;
	const commissionValue = getData()[0]?.commissionValue;
	const getPeriod = getData()[0]?.period;

	return (
		<div className="d-flex dashboard-commission-container flex-column flex-shrink-0 pb-4 pt-3 px-3">
			<div className="align-items-center d-flex dashboard-commission-header justify-content-between">
				<div className="dashboard-commission-title font-weight-bolder h4">
					Commission
				</div>

				<ClaySelect
					className="dashboard-commission-select"
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

			{chartData.columns.length > 0 && (
				<LineChart
					chartData={chartData}
					getDataDate={getDataDate}
					getPeriod={getPeriod}
					percentual={commissionPercentual}
					value={commissionValue}
				/>
			)}

			{chartData.columns.length === 0 && loadData && (
				<div className="align-items-center d-flex flex-grow-1 justify-content-center">
					<span>No Data Applications</span>
				</div>
			)}
		</div>
	);
}
