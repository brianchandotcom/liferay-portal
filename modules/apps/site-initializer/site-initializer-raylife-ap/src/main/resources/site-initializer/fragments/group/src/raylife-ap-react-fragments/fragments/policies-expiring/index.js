/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelect} from '@clayui/form';
import React, {useEffect, useState} from 'react';

import DonutChart from '../../../common/components/donut-chart';
import {
	getPolicies,
	getPoliciesChartExpiringPolicies,
	getProducts,
} from '../../../common/services';
import {
	currentDateString,
	nextMonthDate,
	nextThreeMonthsDate,
} from '../../../common/utils/dateFormatter';

const PERIOD = {
	THIS_MONTH: '1',
	THREE_MONTH: '2',
};

export default function () {
	const [selectedFilterDate, setSelectedFilterDate] = useState('1');

	const [chartTitle, setChartTitle] = useState('');
	const [loadData, setLoadData] = useState(false);

	const [totalOfPolicies, setTotalOfPolicies] = useState(0);
	const [thisMonthExpiringPolicies, setThisMonthExpiringPolicies] = useState(
		0
	);
	const [
		threeMonthsExpiringPolicies,
		setThreeMonthsExpiringPolicies,
	] = useState(0);

	const [columnsLegend, setColumnsLegend] = useState([]);
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

	const colorsChart = {
		expiring: '#ec0d6b',
		remaining: '#fbcee1',
	};

	const MAX_NAME_LENGHT = 15;

	useEffect(() => {
		Promise.allSettled([getProducts(), getPolicies()]).then((results) => {
			const [productQuotesResult, allPoliciesResult] = results;

			const columnsArr = [];
			const colorsObj = {};

			const totalOfPolicies = allPoliciesResult?.value?.data;

			setTotalOfPolicies(totalOfPolicies?.totalCount);

			const handleBuildLegend = (arrayOfexpiringPolicies) => {
				productQuotesResult?.value?.data?.items?.map(
					(productQuote, index) => {
						const countExpiredPolicies = arrayOfexpiringPolicies?.filter(
							(policy) => productQuote.name === policy.productName
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

						if (countExpiredPolicies > 0) {
							columnsArr[index] = [
								fullName,
								countExpiredPolicies,
								productName,
							];
						}
					}
				);

				setChartTitle(arrayOfexpiringPolicies?.length.toString());
				setColumnsLegend(columnsArr);
				setColors(colorsObj);
			};

			if (selectedFilterDate === PERIOD.THIS_MONTH) {
				getPoliciesChartExpiringPolicies(
					currentDateString[0],
					currentDateString[1],
					currentDateString[2],
					nextMonthDate[0],
					nextMonthDate[1],
					nextMonthDate[2]
				).then((results) => {
					const expiringPoliciesResultThisMonth = results;

					const totalFilteredPolicies =
						expiringPoliciesResultThisMonth?.data;

					const arrayThisMonthExpiringPolicies = [];

					totalFilteredPolicies?.items?.forEach((policy) => {
						const policyEndDate = Date.parse(policy?.endDate);

						const currentDate = new Date();

						const differenceOfDays = policyEndDate - currentDate;

						const renewalDue =
							Math.floor(
								differenceOfDays / (1000 * 60 * 60 * 24)
							) + 1;

						if (renewalDue < 15) {
							arrayThisMonthExpiringPolicies.push(policy);
						}
					});

					setThisMonthExpiringPolicies(
						arrayThisMonthExpiringPolicies?.length
					);

					handleBuildLegend(arrayThisMonthExpiringPolicies);
				});
			}

			if (selectedFilterDate === PERIOD.THREE_MONTH) {
				getPoliciesChartExpiringPolicies(
					currentDateString[0],
					currentDateString[1],
					currentDateString[2],
					nextThreeMonthsDate[0],
					nextThreeMonthsDate[1],
					nextThreeMonthsDate[2]
				).then((results) => {
					const expiringPoliciesResultThreeMonths = results?.data;

					const arrayThreeMonthsExpiringPolicies =
						expiringPoliciesResultThreeMonths?.items;

					setThreeMonthsExpiringPolicies(
						expiringPoliciesResultThreeMonths?.totalCount
					);

					handleBuildLegend(arrayThreeMonthsExpiringPolicies);
				});
			}

			setLoadData(true);
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedFilterDate]);

	const chartLoadData = [
		{
			dataColumns: [
				['expiring', thisMonthExpiringPolicies],
				['remaining', totalOfPolicies - thisMonthExpiringPolicies],
			],
			period: 1,
		},
		{
			dataColumns: [
				['expiring', threeMonthsExpiringPolicies],
				['remaining', totalOfPolicies - threeMonthsExpiringPolicies],
			],
			period: 2,
		},
	];

	const getData = () => {
		return chartLoadData?.filter(
			(data) => data.period === Number(selectedFilterDate)
		);
	};

	const chartData = {
		colors: colorsChart,
		columns: getData()[0]?.dataColumns,
		legend: {
			colors,
			columnsLegend,
		},
		type: 'donut',
	};

	const LegendElement = () => (
		<div className="d-flex legend-container">
			{chartData?.legend?.columnsLegend?.map((column, index) => (
				<div
					className="d-flex flex-row justify-content-between legend-content pr-1"
					key={index}
				>
					<div className="align-items-center d-flex flex-row justify-content-between mr-2">
						<div
							className="flex-shrink-0 legend-color mr-2 rounded-circle"
							style={{
								backgroundColor:
									chartData?.legend?.colors[column[0]],
							}}
						></div>

						<span className="legend-title">{column[2]}</span>
					</div>

					<span className="font-weight-bolder">{column[1]}</span>
				</div>
			))}
		</div>
	);

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

			{!chartData.columns[0].includes(0) && loadData && (
				<DonutChart
					LegendElement={LegendElement}
					chartData={chartData}
					hasLegend={true}
					title={chartTitle}
				/>
			)}

			{chartData.columns[0].includes(0) && loadData && (
				<div className="align-items-center d-flex flex-grow-1 justify-content-center">
					<span>No Data</span>
				</div>
			)}
		</div>
	);
}
