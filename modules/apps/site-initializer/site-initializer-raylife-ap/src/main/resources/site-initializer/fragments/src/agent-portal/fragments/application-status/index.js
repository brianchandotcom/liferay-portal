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
import React from 'react';

export default function () {
	const chartData = {
		colors: {
			Bound: '#D9E4FE',
			Quoted: '#81A8FF',
			Reviewed: '#4C84FF',
			Underwriting: '#B5CDFE',
			Incomplete: '#1F77D4'
		},
		columns: [
			['Incomplete', 67],
			['Quoted', 69],
			['Underwriting', 10],
			['Reviewed', 5],
			['Bound', 240],
		],
		type: 'donut',
	};

	const title = chartData.columns
		.map((array) => array[1])
		.reduce((sum, i) => sum + i)
		.toString();

	return (
		<div className="d-flex flex-column flex-shrink-0 pb-4 pt-3 px-3">
			<div className="font-weight-bolder h4 raylife-status-chart">
				Status
			</div>

			<div className="align-items-center d-flex flex-lg-row flex-md-column flex-sm-column justify-content-between px-2">
				<ClayChart
					data={{
						colors: chartData.colors,
						columns: chartData.columns,
						type: chartData.type,
					}}
					donut={{
						label: {
							show: false,
						},
						title: title,
						width: 15,
					}}
					legend={{
						position: 'right',
						show: false,
						usePoint: true,
					}}
					point={{
						pattern: ['circle'],
					}}
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

							return `<div style='border: 1px solid lightblue; background-color: #ffffff; padding: 5px; border-radius: 4px;'>${title} ${percent}%</div>`;
						},
					}}
				/>

				<div className="d-flex flex-column">
					{chartData.columns.map((column, index) => (
						<div
							className="chart-legend d-flex flex-row justify-content-between"
							key={index}
						>
							<div className="align-items-center d-flex flex-row justify-content-between mr-2">
								<div
									className="flex-shrink-0 mr-1 rounded-circle"
									style={{
										backgroundColor:
											chartData.colors[column[0]],

										height: '8px',
										width: '8px',
									}}
								></div>

								<div
									className="d-flex flex-row lh-lg"
									style={{color: '#5C5E5E', fontSize: '14px'}}
								>
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

// import ClayChart from '@clayui/charts';
// import React from 'react';
// import './index.css';

// export default function () {
// 	const COLUMNS = [
// 		['Review', 70],
// 		['Quote', 36],
// 		['Underwriting', 17],
// 		['Bound', 8],
// 	];

// 	const title = COLUMNS.map((array) => array[1]).reduce((sum, i) => sum + i).toString();

// 	console.log('title:', title);
// 	return (
// 		<div>
// 			<ClayChart
// 				data={{
// 					colors: {
// 						Bound: '#D9E4FE',
// 						Quote: '#81A8FF',
// 						Review: '#4C84FF',
// 						Underwriting: '#B5CDFE',
// 					},
// 					columns: COLUMNS,
// 					type: 'donut',
// 				}}
// 				donut={{
// 					label: {
// 						show: false,
// 					},
// 					title: title,
// 					width: 15,
// 				}}
// 				point={{
// 					pattern: ['circle'],
// 				}}
// 				legend={{
// 					position: 'right',
// 					show: true,
// 					usePoint: true,
// 				}}
// 				size={{
// 					height: 300,
// 					width: 300,
// 				}}
// 			/>
// 		</div>
// 	);
// }
