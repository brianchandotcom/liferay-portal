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

import React, {useEffect, useState} from 'react';

import DonutChart from '../../../common/components/donut-chart';
import {getApplicationsStatusTotal} from '../../../common/services/Application';
import {setFirstLetterUpperCase} from '../../../common/utils';

export default function () {
	const [chartTitle, setChartTitle] = useState('');
	const [loadData, setLoadData] = useState(false);

	const [chartData, setChartData] = useState({
		colors: {},
		columns: [],
		type: 'donut',
	});

	const loadChartData = async () => {
		const colors = {
			bound: '#D9E4FE',
			incomplete: '#1F77D4',
			open: '#FF7F0E',
			quoted: '#81A8FF',
			rejected: '#191970',
			reviewed: '#4C84FF',
			underwriting: '#B5CDFE',
		};

		getApplicationsStatusTotal().then((response) => {
			const statuses = response?.data?.facets[0]?.facetValues;

			const columns = statuses?.map((status) => {
				return [
					status.term,
					status.numberOfOccurrences,
					setFirstLetterUpperCase(status.term),
				];
			});

			const filteredColumns = columns.filter((column) => column[1] > 0);

			setChartData({...chartData, ...{colors, columns: filteredColumns}});

			const title = filteredColumns
				.map((filteredColumn) => filteredColumn[1])
				.reduce((total, currentValue) => total + currentValue, 0)
				.toString();

			setChartTitle(title);

			setLoadData(true);
		});
	};

	useEffect(() => {
		loadChartData();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<div className="applications-status-container d-flex flex-column flex-shrink-0 pb-4 pt-3 px-3 w-100">
			<div className="applications-status-title font-weight-bold h4 raylife-status-chart">
				Status
			</div>

			{!!chartData.columns.length && (
				<DonutChart chartData={chartData} title={chartTitle} />
			)}

			{!chartData.columns.length && loadData && (
				<div className="align-items-center d-flex flex-grow-1 justify-content-center">
					<span>No Data Applications</span>
				</div>
			)}
		</div>
	);
}
