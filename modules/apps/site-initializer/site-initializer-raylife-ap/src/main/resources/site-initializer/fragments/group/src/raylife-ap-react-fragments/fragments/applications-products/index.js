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
import {getApplications, getProducts} from '../../../common/services/';

const MAX_NAME_LENGHT = 15;

export default function () {
	const [columns, setColumns] = useState([]);
	const [colors, setColors] = useState({});

	const [chartTitle, setChartTitle] = useState('');
	const [loadData, setLoadData] = useState(false);

	const colorsArray = [
		'#7154E1',
		'#55C2FF',
		'#4BC286',
		'#FF9A24',
		'#EC676A',
		'#D9E4FE',
		'#1F77B4',
		'#D1D1D9',
		'#B5CDFE',
	];

	const loadChartData = async () => {
		const products = await getProducts();
		const applications = await getApplications();

		const columnsArr = [];
		const colorsObj = {};

		let totalCountApplications = 0;

		products?.data?.items?.map((productQuote, index) => {
			const countApplications = applications?.data?.items?.filter(
				(application) => application.productName === productQuote.name
			).length;

			const shortDescription = productQuote.shortDescription;

			const fullName = productQuote.name;
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

			if (countApplications > 0) {
				columnsArr[index] = [fullName, countApplications, productName];
				totalCountApplications += countApplications;
			}
		});

		setChartTitle(totalCountApplications);

		setColumns(columnsArr);
		setColors(colorsObj);

		setLoadData(true);
	};

	useEffect(() => {
		loadChartData();

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
