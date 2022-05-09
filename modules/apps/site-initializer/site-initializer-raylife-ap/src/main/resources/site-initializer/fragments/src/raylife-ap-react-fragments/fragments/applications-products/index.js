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

const MAX_NAME_LENGHT = 15;

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

				productQuotesResult?.value?.data?.items?.map(
					(productQuote, index) => {
						const countApplications = applicationsResult?.value?.data?.items?.filter(
							(application) =>
								Number(application.productQuote) ===
								productQuote.productId
						).length;

						const shortDescription = Object.values(
							productQuote.shortDescription
						)[0];

						const [fullName] = Object.values(productQuote.name);
						let productName = fullName;

						const productAbbrevation = productName
							.split(' ')
							.map((product) => product.charAt(0))
							.join('');

						if (productName?.length > MAX_NAME_LENGHT) {
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
					}
				);

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

			<div className="align-items-center d-flex flex-wrap justify-content-center">
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

								return `<div class="applications-products-tooltip bg-neutral-0 d-flex font-weight-bold rounded-sm p-2"><span class="d-flex mr-2 w-100">${title}</span> ${percent}%</div>`;
							},
						}}
					/>
				)}

				<div className="d-flex legend-container">
					{chartData?.columns?.map((column, index) => (
						<div
							className="d-flex flex-row justify-content-between legend-content pr-1"
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

								<span className="legend-title">
									{column[2]}
								</span>
							</div>

							<span className="font-weight-bolder">
								{column[1]}
							</span>
						</div>
					))}
				</div>
			</div>
		</div>
	);
}
