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
import {setFirstLetterUpperCase} from '../../../common/utils';
import {CONSTANTS} from '../../../common/utils/constants';

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

		const openStatus = await getApplicationsStatus(CONSTANTS.STATUS.OPEN);

		const incompleteStatus = await getApplicationsStatus(
			CONSTANTS.STATUS.INCOMPLETE
		);

		const quotedStatus = await getApplicationsStatus(
			CONSTANTS.STATUS.QUOTED
		);

		const underwritingStatus = await getApplicationsStatus(
			CONSTANTS.STATUS.UNDERWRITING
		);

		const reviewedStatus = await getApplicationsStatus(
			CONSTANTS.STATUS.REVIEWED
		);

		const rejectedStatus = await getApplicationsStatus(
			CONSTANTS.STATUS.REJECTED
		);

		const boundStatus = await getApplicationsStatus(CONSTANTS.STATUS.BOUND);

		const columns = [
			[
				CONSTANTS.STATUS.OPEN,
				openStatus?.data?.totalCount,
				setFirstLetterUpperCase(CONSTANTS.STATUS.OPEN),
			],
			[
				CONSTANTS.STATUS.INCOMPLETE,
				incompleteStatus?.data?.totalCount,
				setFirstLetterUpperCase(CONSTANTS.STATUS.INCOMPLETE),
			],
			[
				CONSTANTS.STATUS.QUOTED,
				quotedStatus?.data?.totalCount,
				setFirstLetterUpperCase(CONSTANTS.STATUS.QUOTED),
			],
			[
				CONSTANTS.STATUS.UNDERWRITING,
				underwritingStatus?.data?.totalCount,
				setFirstLetterUpperCase(CONSTANTS.STATUS.UNDERWRITING),
			],
			[
				CONSTANTS.STATUS.REVIEWED,
				reviewedStatus?.data?.totalCount,
				setFirstLetterUpperCase(CONSTANTS.STATUS.REVIEWED),
			],
			[
				CONSTANTS.STATUS.REJECTED,
				rejectedStatus?.data?.totalCount,
				setFirstLetterUpperCase(CONSTANTS.STATUS.REJECTED),
			],
			[
				CONSTANTS.STATUS.BOUND,
				boundStatus?.data?.totalCount,
				setFirstLetterUpperCase(CONSTANTS.STATUS.BOUND),
			],
		];

		const filteredColumns = columns.filter((column) => column[1] > 0);

		setChartData({...chartData, ...{colors, columns: filteredColumns}});

		const title = filteredColumns
			.map((array) => array[1])
			.reduce((sum, i) => sum + i, 0)
			.toString();

		setChartTitle(title);

		setLoadData(true);
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
