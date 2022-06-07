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
import {getApplications, getProductQuotes} from '../../../common/services/';

const MAX_NAME_LENGHT = 15;

export default function () {
	const [columns, setColumns] = useState([]);
	const [colors, setColors] = useState({});

	const [chartTitle, setChartTitle] = useState('');
	const [loadData, setLoadData] = useState(false);

	const colorsArray = ['#7154E1', '#55C2FF', '#4BC286', '#FF9A24'];

	useEffect(() => {
		Promise.allSettled([getProductQuotes(), getApplications()]).then(
			(results) => {
				const [productQuotesResult, applicationsResult] = results;

				const columnsArr = [];
				const colorsObj = {};

				setChartTitle(applicationsResult?.value?.data?.totalCount);

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

				setLoadData(true);
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
			<div className="applications-products-title font-weight-bold h4">
				Products
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
