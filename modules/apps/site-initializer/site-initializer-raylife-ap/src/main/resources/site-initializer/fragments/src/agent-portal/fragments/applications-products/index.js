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

import {getApplications, getProductQuotes} from '../../../common/services/';

export default function () {
	const chartRef = useRef();
	const [columns, setColumns] = useState([]);
	const [colors, setColors] = useState({});

	const colorsArray = ['#7154E1', '#55C2FF', '#4BC286', '#FF9A24'];

	const titleUpdate = (title, classNames = '') => {
		const titleElement = chartRef.current.element.querySelector(
			'.bb-chart-arcs-title'
		);

		titleElement.textContent = title;

		if (classNames !== '') {
			titleElement.classList.add(...classNames);
		}
	};

	useEffect(() => {
		Promise.allSettled([getProductQuotes(), getApplications()]).then(
			(results) => {
				const [productQuotesResult, applicationsResult] = results;

				const columnsArr = [];
				const colorsObj = {};

				titleUpdate(applicationsResult?.value?.data?.totalCount, [
					'h2',
				]);

				productQuotesResult?.value?.data?.items?.map((item, index) => {
					const countApplications = applicationsResult?.value?.data?.items?.filter(
						(application) =>
							Number(application.productQuote) === item.productId
					).length;

					const shortDescription = Object.values(
						item.shortDescription
					)[0];
					const [fullName] = Object.values(item.name);

					let [productName] = Object.values(item.name);

					const productAbbrevation = productName
						.split(' ')
						.map((product) => product.charAt(0))
						.join('');

					if (productName?.length > 15) {
						productName =
							shortDescription === ''
								? productAbbrevation
								: shortDescription;
					}

					colorsObj[fullName] = colorsArray[index];
					columnsArr[index] = [
						fullName,
						countApplications,
						productName,
					];
				});

				setColumns(columnsArr);
				setColors(colorsObj);
			}
		);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const chartData = {
		colors,
		columns,
		type: 'donut',
	};

	return (
		<div className="applications-products-container d-flex flex-column flex-shrink-0 pb-4 pt-3 px-3">
			<div className="applications-products-title font-weight-bolder h4">
				Products
			</div>

			<div className="align-items-center d-flex flex-row justify-content-between">
				{chartData && (
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
								const percent = (data[0].ratio * 100).toFixed(
									1
								);

								return `<div class="d-flex applications-products-tooltip p-2"'><spam class="d-flex w-100 mr-2">${title}</spam> <spam>${percent}%</spam></div>`;
							},
						}}
					/>
				)}

				<div className="d-flex flex-column">
					{chartData?.columns?.map((column, index) => (
						<div
							className="d-flex flex-row justify-content-between"
							key={index}
						>
							<div className="align-items-center d-flex flex-row justify-content-between mr-2">
								<div
									className="flex-shrink-0 legend-color mr-2 rounded-circle"
									style={{
										backgroundColor:
											chartData?.colors[column[0]],
									}}
								></div>

								<span
									className="legend-title"
									style={{color: '#5C5E5E', fontSize: '14px'}}
								>
									{column[2]}
								</span>
							</div>

							<span
								className="font-weight-bolder legend-value"
								style={{fontSize: '14px'}}
							>
								{column[1]}
							</span>
						</div>
					))}
				</div>
			</div>
		</div>
	);
}
