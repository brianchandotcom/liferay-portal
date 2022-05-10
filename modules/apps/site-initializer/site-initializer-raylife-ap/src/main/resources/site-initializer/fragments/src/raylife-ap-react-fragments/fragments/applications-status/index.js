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
import {getApplicationsStatus} from '../../../common/services/Application';
import {CONSTANTS} from '../../../common/utils/constants';

export default function () {
	const [chartTitle, setChartTitle] = useState('');
	const [loadData, setLoadData] = useState(false);

	const [chartData, setChartData] = useState({
		colors: {},
		columns: [],
		type: 'donut',
	});

	const getTotalCount = (result) => {
		return result?.value?.data?.totalCount || 0;
	};

	const loadChartData = () => {
		Promise.allSettled([
			getApplicationsStatus(CONSTANTS.STATUS.BOUND),
			getApplicationsStatus(CONSTANTS.STATUS.INCOMPLETE),
			getApplicationsStatus(CONSTANTS.STATUS.QUOTED),
			getApplicationsStatus(CONSTANTS.STATUS.OPEN),
			getApplicationsStatus(CONSTANTS.STATUS.REJECTED),
			getApplicationsStatus(CONSTANTS.STATUS.REVIEWED),
			getApplicationsStatus(CONSTANTS.STATUS.UNDERWRITING),
		]).then((results) => {
			const [
				boundApplicationsResult,
				incompleteApplicationsResult,
				quotedApplicationsResult,
				openApplicationsResults,
				rejectedApplicationsResult,
				reviewedApplicationsResult,
				underwritingApplicationsResult,
			] = results;

			const colors = {
				bound: '#D9E4FE',
				incomplete: '#1F77D4',
				open: '#FF7F0E',
				quoted: '#81A8FF',
				rejected: '#191970',
				reviewed: '#4C84FF',
				underwriting: '#B5CDFE',
			};
			const cols = [
				[CONSTANTS.STATUS.OPEN, getTotalCount(openApplicationsResults)],
				[
					CONSTANTS.STATUS.INCOMPLETE,
					getTotalCount(incompleteApplicationsResult),
				],
				[
					CONSTANTS.STATUS.QUOTED,
					getTotalCount(quotedApplicationsResult),
				],
				[
					CONSTANTS.STATUS.UNDERWRITING,
					getTotalCount(underwritingApplicationsResult),
				],
				[
					CONSTANTS.STATUS.REVIEWED,
					getTotalCount(reviewedApplicationsResult),
				],
				[
					CONSTANTS.STATUS.REJECTED,
					getTotalCount(rejectedApplicationsResult),
				],
				[
					CONSTANTS.STATUS.BOUND,
					getTotalCount(boundApplicationsResult),
				],
			];

			const columns = cols.filter((col) => col[1] > 0);
			setChartData({...chartData, ...{colors, columns: []}});

			const title = columns
				.map((array) => array[1])
				.reduce((sum, i) => {
					return sum + i;
				})
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

			{chartData.columns.length > 0 && (
				<DonutChart chartData={chartData} title={chartTitle} />
			)}

			{chartData.columns.length === 0 && loadData && (
				<div className="align-items-center d-flex flex-grow-1 justify-content-center">
					<span>No Data Applications</span>
				</div>
			)}
		</div>
	);
}
