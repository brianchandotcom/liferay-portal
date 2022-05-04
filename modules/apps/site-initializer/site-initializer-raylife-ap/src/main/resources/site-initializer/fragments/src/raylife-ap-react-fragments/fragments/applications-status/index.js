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

import ClayChart from '@clayui/charts';
import React, {useEffect, useRef, useState} from 'react';

import {getApplicationsStatus} from '../../../common/services/Application';
import {CONSTANTS} from '../../../common/utils/constants';

export default function () {
	const [chartData, setChartData] = useState({
		colors: {},
		columns: [],
		type: 'donut',
	});

	const chartRef = useRef();

	const titleUpdate = (title) => {
		const titleElement = chartRef.current.element.querySelector(
			'.bb-chart-arcs-title'
		);

		titleElement.textContent = title;
	};

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
				openApplicationsResults,
				quotedApplicationsResult,
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
			setChartData({...chartData, ...{colors, columns}});

			const title = columns
				.map((array) => array[1])
				.reduce((sum, i) => {
					return sum + i;
				})
				.toString();

			titleUpdate(title);
		});
	};

	useEffect(() => {
		loadChartData();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<div className="applications-products-container d-flex flex-column flex-shrink-0 pb-4 pt-3 px-3 w-100">
			<div className="applications-products-title font-weight-bold h4 raylife-status-chart">
				Status
			</div>

			<div className="align-items-center d-flex flex-lg-row flex-md-column flex-sm-column justify-content-between px-2">
				<ClayChart
					data={chartData}
					donut={{
						label: {
							show: false,
						},
						title: '0',
						width: 15,
					}}
					legend={{
						show: false,
					}}
					ref={chartRef}
					size={{
						height: 190,
						width: 190,
					}}
					tooltip={{
						contents: (
							data,
							_defaultTitleFormat,
							_defaultValueFormat,
							_color
						) => {
							const title = Liferay.Language.get(data[0].id);
							const percent = (data[0].ratio * 100).toFixed(1);

							return `<div class="applications-products-tooltip bg-neutral-0 d-flex font-weight-bold p-2 rounded-sm text-capitalize"><span class="d-flex mr-2 w-100 text-capitalize">${title}</span> ${percent}%</div>`;
						},
					}}
				/>

				<div className="d-flex flex-column ml-3 w-50">
					{chartData.columns.map((column, index) => (
						<div
							className="chart-legend d-flex flex-row justify-content-between"
							key={index}
						>
							<div className="align-items-center d-flex flex-row justify-content-between mr-2">
								<div
									className="flex-shrink-0 legend-color mr-1 rounded-circle"
									style={{
										backgroundColor:
											chartData.colors[column[0]],
									}}
								></div>

								<div className="d-flex flex-row legend-title text-capitalize text-truncate">
									{column[0]}
								</div>
							</div>

							<div
								className="font-weight-bolder"
								style={{fontSize: '14px'}}
							>
								{column[1]}
							</div>
						</div>
					))}
				</div>
			</div>
		</div>
	);
}
