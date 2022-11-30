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
	const claimValue = 2000;
	const claimPercentual = 50;

	const buildLegend = () => (
		<div className="claims-total-settlements-legend d-flex flex-column h-100 justify-content-end mt-5">
			<div className="font-weight-normal mb-2 text-neutral-8 text-paragraph-sm"></div>

			<div className="claims-total-settlements-screen font-weight-bolder h5 mb-3">
				{new Intl.NumberFormat('en-US', {
					currency: 'USD',
					style: 'currency',
				}).format(claimValue)}
			</div>

			<div
				className={classNames('text-paragraph-sm font-weight-bolder', {
					'line-chart-icon-success-color': claimPercentual >= 0,
					'text-danger': claimPercentual < 0,
				})}
			>
				{claimPercentual > 0 && <ClayIcon symbol="caret-top" />}

				{claimPercentual === 0 && <ClayIcon symbol="hr" />}

				{claimPercentual < 0 && <ClayIcon symbol="caret-bottom" />}

				{PERIOD === '1'
					? `${
							claimPercentual === Infinity
								? `NaN`
								: claimPercentual
					  }% MoM`
					: `${
							claimPercentual === Infinity
								? `NaN`
								: claimPercentual
					  }% YoY`}
			</div>
		</div>
	);

	const PERIOD = {
		SIX_MONTH: '2',
		THIS_MONTH: '1',
		YTD: '3',
	};

	const options = [
		{
			label: 'This Month',
			value: PERIOD.THIS_MONTH,
		},
		{
			label: '6 MO',
			value: PERIOD.SIX_MONTH,
		},
		{
			label: 'YTD',
			value: PERIOD.YTD,
		},
	];
	const chartData = {
		columns: [['Settlements Paid', 30, 200, 100, 400, 150, 250]],
		type: 'line',
	};

	const patternColor = ['#4c84ff'];

	return (
		<div className="claims-total-settlements-container d-flex flex-column flex-shrink-0 pb-4 pt-3 px-3">
			<div className="align-items-center claims-total-settlements-header d-flex justify-content-between">
				<div className="claims-total-settlements-title font-weight-bolder h4 mb-0">
					Total Settlements Paid
				</div>

				<ClaySelect
					className="claims-total-settlements-select"
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
				<LineChart
					LegendElement={buildLegend}
					chartData={chartData}
					patternColor={patternColor}
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
