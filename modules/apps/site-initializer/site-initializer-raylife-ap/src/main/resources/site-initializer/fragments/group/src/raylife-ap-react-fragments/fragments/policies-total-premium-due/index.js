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

import BarChart from '../../../common/components/bar-chart';
import ClayIconProvider from '../../../common/context/ClayIconProvider';
import {getPoliciesChartExpiringPolicies} from '../../../common/services/Policy';
import {getProducts} from '../../../common/services/Products';
import {
	currentDateString,
	nextMonthDate,
} from '../../../common/utils/dateFormatter';

export default function () {
	const [policies, setPolicies] = useState([]);
	const [expirationPoliciesList, setExpirationPoliciesList] = useState({});
	const firstDayOfTheMonth = '01';
	const policyExpirationDays = 15;

	function handleProductListFormatter() {
		getProducts().then((response) => {
			const productList = response?.data?.items;

			productList.map((product) => {
				product.productValue = 0;
			});

			const productListFormatted = {};
			productList.forEach((product) => {
				if (!productListFormatted[product?.name]) {
					productListFormatted[product?.name] = 0;
				}

				setExpirationPoliciesList({...productListFormatted});
			});
		});
	}

	function handleExpirationPolicies() {
		getPoliciesChartExpiringPolicies(
			currentDateString[0],
			currentDateString[1],
			firstDayOfTheMonth,
			nextMonthDate[0],
			nextMonthDate[1],
			nextMonthDate[2]
		).then((results) => {
			const totalFilteredPolicies = results?.data;

			const policiesList = [];

			totalFilteredPolicies?.items.map((policy) => {
				const policyEndDate = Date.parse(policy?.endDate);
				const currentDate = new Date();

				const dayInMilliseconds = 1000 * 60 * 60 * 24;
				const differenceOfDays = policyEndDate - currentDate;

				const renewalDue =
					Math.floor(differenceOfDays / dayInMilliseconds) + 1;

				if (renewalDue < policyExpirationDays) {
					policiesList.push(policy);
				}
			});

			setPolicies(policiesList);

			return policiesList;
		});
	}

	function handleValueFormatter(value) {
		const formattedValue = new Intl.NumberFormat('en-us', {
			currency: 'USD',
			style: 'currency',
		}).format(value);

		return formattedValue;
	}

	useEffect(() => {
		handleProductListFormatter();
		handleExpirationPolicies();
	}, []);

	policies?.forEach((policy) => {
		expirationPoliciesList[policy?.productName] += policy?.termPremium;

		if (!expirationPoliciesList[policy?.productName]) {
			expirationPoliciesList[policy?.productName] = policy?.termPremium;
		}
	});

	const dataColumnsFormatted = [...Object.values(expirationPoliciesList)];
	dataColumnsFormatted.unshift('data');

	const labelColumnsFormatted = [...Object.keys(expirationPoliciesList)];
	labelColumnsFormatted.unshift('x');

	const formatLabel = (labelColumnsFormatted, maxNameLenght) => {
		const productAbbrevation = labelColumnsFormatted.map((name) => {
			if (name?.length > maxNameLenght) {
				return name
					?.split(' ')
					.map((product) => product.charAt(0))
					.join('');
			}

			return name;
		});

		return productAbbrevation;
	};

	const maxNameLenght = 10;
	const formattedLabel = formatLabel(labelColumnsFormatted, maxNameLenght);

	const policiesValuesList = [...dataColumnsFormatted];
	policiesValuesList.shift();

	let sumOfPolicies = 0;
	for (let i = 0; i < policiesValuesList.length; i++) {
		sumOfPolicies += policiesValuesList[i];
	}

	const colors = [
		'#55C2FF',
		'#EC676A',
		'#7154E1',
		'#4BC286',
		'#FF9A24',
		'#1F77B4',
		'#4BC286',
		'#FF9A24',
		'#1F77B4',
	];

	return (
		<ClayIconProvider>
			<div className="d-flex flex-column px-5 total-premium-due-container">
				<div className="align-items-center d-flex font-weight-bold h4 justify-content-between mt-3 total-premium-due-title">
					<div>Total Premium Due</div>
				</div>

				<div className="d-flex total-premium-container-bar">
					<BarChart
						barRatio={0}
						barWidth={10}
						colors={colors}
						dataColumns={dataColumnsFormatted}
						format
						height={300}
						labelColumns={formattedLabel}
						titleTotal={false}
						width={800}
					/>
				</div>
			</div>

			<hr className="mx-3 my-1" />

			<div className="d-flex h4 justify-content-center py-2">
				Total:
				{sumOfPolicies ? (
					<span className="h4 px-1">
						{handleValueFormatter(sumOfPolicies)}
					</span>
				) : (
					<i>&nbsp;No data</i>
				)}
			</div>
		</ClayIconProvider>
	);
}
