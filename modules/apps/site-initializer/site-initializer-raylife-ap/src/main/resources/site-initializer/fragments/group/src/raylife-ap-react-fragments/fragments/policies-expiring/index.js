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
import React, {useEffect, useState} from 'react';

import DonutChart from '../../../common/components/donut-chart';
import {getActivePolicies, getProducts} from '../../../common/services';

const PERIOD = {
	THIS_MONTH: '1',
	THREE_MONTH: '2',
};

export default function () {
	const [selectedFilterDate, setSelectedFilterDate] = useState('1');

	const [chartTitle, setChartTitle] = useState('');
	const [loadData, setLoadData] = useState(false);

	const [columns, setColumns] = useState([]);
	const [colors, setColors] = useState({});

	const options = [
		{
			label: 'This Month',
			value: PERIOD.THIS_MONTH,
		},
		{
			label: '3 MO',
			value: PERIOD.THREE_MONTH,
		},
	];

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

	const MAX_NAME_LENGHT = 15;

	useEffect(() => {
		Promise.allSettled([getProducts(), getActivePolicies()]).then(
			(results) => {
				const [productQuotesResult, policiesResult] = results;

				const columnsArr = [];
				const colorsObj = {};

				const activePolicies = policiesResult?.value?.data;

				setChartTitle(activePolicies?.totalCount);

				productQuotesResult?.value?.data?.items?.map(
					(productQuote, index) => {
						const countActivePolicies = activePolicies?.items.filter(
							(application) =>
								productQuote.name === application.productName
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

						if (countActivePolicies > 0) {
							columnsArr[index] = [
								fullName,
								countActivePolicies,
								productName,
							];
						}
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
		<div className="d-flex flex-column flex-shrink-0 pb-4 policies-expiring-container pt-3 px-3">
			<div className="align-items-center d-flex justify-content-between policies-expiring-header">
				<div className="font-weight-bolder h4 policies-expiring-title">
					Expiring
				</div>

				<ClaySelect
					className="policies-expiring-select"
					onChange={({target}) => {
						setSelectedFilterDate(target.value);
					}}
					sizing="sm"
					value={selectedFilterDate}
				>
					{options.map((option) => (
						<ClaySelect.Option
							key={option.value}
							label={option.label}
							value={option.value}
						/>
					))}
				</ClaySelect>
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
