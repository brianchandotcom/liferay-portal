/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelect} from '@clayui/form';
import React, {useEffect, useState} from 'react';

import DonutChart from '../../../common/components/donut-chart';
import {
	getClaimsByPeriodSettled,
	getPolicies,
	getProducts,
	getSettledClaims,
} from '../../../common/services';
import {
	currentDateString,
	january,
	lastMonthDate,
	sixMonthsAgoDate,
	threeMonthsAgoDate,
} from '../../../common/utils/dateFormatter';

const PERIOD = {
	SIX_MONTHS: '3',
	THIS_MONTH: '1',
	THREE_MONTHS: '2',
	YTD: '4',
};

export default function () {
	const [selectedFilterDate, setSelectedFilterDate] = useState(PERIOD.YTD);

	const [loadData, setLoadData] = useState(false);

	const [totalOfClaimsThisMonth, setTotalOfClaimsThisMonth] = useState(0);
	const [totalOfClaimsThreeMonths, setTotalOfClaimsThreeMonths] = useState(0);
	const [totalOfClaimsSixMonths, setTotalOfClaimsSixMonths] = useState(0);
	const [totalOfClaimsYearToDate, setTotalOfClaimsYearToDate] = useState(0);
	const [thisMonthSettledClaims, setThisMonthSettledClaims] = useState(0);
	const [threeMonthsSettledClaims, setThreeMonthsSettledClaims] = useState(0);
	const [sixMonthsSettledClaims, setSixMonthsSettledClaims] = useState(0);
	const [yearToDateSettledClaims, setYearToDateSettledClaims] = useState(0);

	const [columnsLegend, setColumnsLegend] = useState([]);
	const [colors, setColors] = useState({});

	const options = [
		{
			label: 'This Month',
			value: PERIOD.THIS_MONTH,
		},
		{
			label: '3 MO',
			value: PERIOD.THREE_MONTHS,
		},
		{
			label: '6 MO',
			value: PERIOD.SIX_MONTHS,
		},
		{
			label: 'YTD',
			value: PERIOD.YTD,
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
		remaining: '#d9e4fd',
		settled: '#4c84ff',
	};

	const statusChart = {
		remaining: 'remaining',
		settled: 'settled',
	};

	const MAX_NAME_LENGHT = 15;

	const getSettledPercentual = (totalSettledClaims, totalClaims) => {
		return (totalSettledClaims / totalClaims) * 100;
	};

	useEffect(() => {
		Promise.allSettled([getProducts(), getPolicies()]).then((results) => {
			const [productQuotesResult, policiesResult] = results;

			const columnsArr = [];
			const colorsObj = {};

			const totalPolicies = policiesResult?.value?.data?.items;

			function getProductNameOfClaims(arrayOfClaims) {
				const productNameOfClaimsTest = arrayOfClaims?.items?.map(
					(claim) => {
						const getPolicyThroughClaim = totalPolicies?.filter(
							(policy) =>
								policy.externalReferenceCode ===
								claim.r_policyToClaims_c_raylifePolicyERC
						);

						return getPolicyThroughClaim[0];
					}
				);

				return productNameOfClaimsTest;
			}

			const handleBuildLegend = (arrayOfexpiringPolicies) => {
				productQuotesResult?.value?.data?.items?.map(
					(productQuote, index) => {
						const countActiveClaims = arrayOfexpiringPolicies?.filter(
							(policy) => productQuote.name === policy.productName
						).length;

						const shortDescription = productQuote.shortDescription;
						const fullName = productQuote.name;
						let productName = fullName;

						const productAbbrevation = productName
							.split(' ')
							.map((product) => product.charAt(0))
							.join('');

						if (productName.length > MAX_NAME_LENGHT) {
							productName =
								shortDescription === ''
									? productAbbrevation
									: shortDescription;
						}

						colorsObj[fullName] = colorsArray[index];

						if (countActiveClaims > 0) {
							columnsArr[index] = [
								fullName,
								countActiveClaims,
								productName,
							];
						}
					}
				);

				setColumnsLegend(columnsArr);
				setColors(colorsObj);
			};

			if (selectedFilterDate === PERIOD.THIS_MONTH) {
				getSettledClaims(
					currentDateString[0],
					currentDateString[1],
					currentDateString[2],
					lastMonthDate[0],
					lastMonthDate[1],
					lastMonthDate[2]
				).then((results) => {
					const settledClaimsThisMonth = results?.data;

					setThisMonthSettledClaims(
						settledClaimsThisMonth?.items.length
					);

					handleBuildLegend(
						getProductNameOfClaims(settledClaimsThisMonth)
					);
				});

				getClaimsByPeriodSettled(
					currentDateString[0],
					currentDateString[1],
					currentDateString[2],
					lastMonthDate[0],
					lastMonthDate[1],
					lastMonthDate[2]
				).then((results) => {
					const activeClaimsThisMonth = results?.data?.totalCount;

					setTotalOfClaimsThisMonth(activeClaimsThisMonth);
				});
			}

			if (selectedFilterDate === PERIOD.THREE_MONTHS) {
				getSettledClaims(
					currentDateString[0],
					currentDateString[1],
					currentDateString[2],
					threeMonthsAgoDate[0],
					threeMonthsAgoDate[1],
					threeMonthsAgoDate[2]
				).then((results) => {
					const settledClaimsThreeMonths = results?.data;

					setThreeMonthsSettledClaims(
						settledClaimsThreeMonths?.items.length
					);

					handleBuildLegend(
						getProductNameOfClaims(settledClaimsThreeMonths)
					);
				});

				getClaimsByPeriodSettled(
					currentDateString[0],
					currentDateString[1],
					currentDateString[2],
					threeMonthsAgoDate[0],
					threeMonthsAgoDate[1],
					threeMonthsAgoDate[2]
				).then((results) => {
					const activeClaimsThreeMonths = results?.data?.totalCount;

					setTotalOfClaimsThreeMonths(activeClaimsThreeMonths);
				});
			}

			if (selectedFilterDate === PERIOD.SIX_MONTHS) {
				getSettledClaims(
					currentDateString[0],
					currentDateString[1],
					currentDateString[2],
					sixMonthsAgoDate[0],
					sixMonthsAgoDate[1],
					sixMonthsAgoDate[2]
				).then((results) => {
					const settledClaimsSixMonths = results?.data;

					setSixMonthsSettledClaims(
						settledClaimsSixMonths?.items.length
					);

					handleBuildLegend(
						getProductNameOfClaims(settledClaimsSixMonths)
					);
				});

				getClaimsByPeriodSettled(
					currentDateString[0],
					currentDateString[1],
					currentDateString[2],
					sixMonthsAgoDate[0],
					sixMonthsAgoDate[1],
					sixMonthsAgoDate[2]
				).then((results) => {
					const activeClaimsSixMonths = results?.data?.totalCount;

					setTotalOfClaimsSixMonths(activeClaimsSixMonths);
				});
			}

			if (selectedFilterDate === PERIOD.YTD) {
				getSettledClaims(
					currentDateString[0],
					currentDateString[1],
					currentDateString[2],
					currentDateString[0],
					january,
					january
				).then((results) => {
					const settledClaimsYearToDate = results?.data;

					setYearToDateSettledClaims(
						settledClaimsYearToDate?.items.length
					);

					handleBuildLegend(
						getProductNameOfClaims(settledClaimsYearToDate)
					);
				});

				getClaimsByPeriodSettled(
					currentDateString[0],
					currentDateString[1],
					currentDateString[2],
					currentDateString[0],
					january,
					january
				).then((results) => {
					const activeClaimsYearToDate = results?.data?.totalCount;

					setTotalOfClaimsYearToDate(activeClaimsYearToDate);
				});
			}

			setLoadData(true);
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedFilterDate]);

	const chartLoadData = [
		{
			dataColumns: [
				[statusChart.settled, thisMonthSettledClaims],
				[
					statusChart.remaining,
					totalOfClaimsThisMonth - thisMonthSettledClaims,
				],
			],
			percentual: getSettledPercentual(
				thisMonthSettledClaims,
				totalOfClaimsThisMonth
			),
			period: 1,
		},
		{
			dataColumns: [
				[statusChart.settled, threeMonthsSettledClaims],
				[
					statusChart.remaining,
					totalOfClaimsThreeMonths - threeMonthsSettledClaims,
				],
			],
			percentual: getSettledPercentual(
				threeMonthsSettledClaims,
				totalOfClaimsThreeMonths
			),
			period: 2,
		},
		{
			dataColumns: [
				[statusChart.settled, sixMonthsSettledClaims],
				[
					statusChart.remaining,
					totalOfClaimsSixMonths - sixMonthsSettledClaims,
				],
			],
			percentual: getSettledPercentual(
				sixMonthsSettledClaims,
				totalOfClaimsSixMonths
			),
			period: 3,
		},
		{
			dataColumns: [
				[statusChart.settled, yearToDateSettledClaims],
				[
					statusChart.remaining,
					totalOfClaimsYearToDate - yearToDateSettledClaims,
				],
			],
			percentual: getSettledPercentual(
				yearToDateSettledClaims,
				totalOfClaimsYearToDate
			),
			period: 4,
		},
	];

	const getData = () => {
		return chartLoadData?.filter(
			(data) => data.period === Number(selectedFilterDate)
		);
	};

	const claimsSettledPercentual = `${getData()[0]?.percentual.toFixed(0)}%`;

	const chartData = {
		colors: colorsChart,
		columns: getData()[0]?.dataColumns,
		legend: {
			colors,
			columnsLegend,
		},
		onclick: (event) => {
			const EVENT_OPTION = {
				async: true,
				fireOn: true,
			};

			const eventPublish = Liferay.publish(
				'openSettingsFilterClaimsSettledEvent',
				EVENT_OPTION
			);

			eventPublish.fire({
				eventName: event.name,
			});
		},
		type: 'donut',
	};

	const LegendElement = () => (
		<div className="d-flex legend-container">
			{getData()[0]?.percentual !== 0 ? (
				chartData?.legend?.columnsLegend?.map((column, index) => (
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
				))
			) : (
				<div className="d-flex flex-column justify-content-center text-center">
					No settled claims for selected <br />
					period
				</div>
			)}
		</div>
	);

	return (
		<div className="claims-settled-container d-flex flex-column flex-shrink-0 pb-4 pt-3 px-3">
			<div className="align-items-center claims-settled-header d-flex justify-content-between">
				<div className="claims-settled-title font-weight-bolder h4">
					Settled
				</div>

				<ClaySelect
					className="claims-settled-select"
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
				<DonutChart
					LegendElement={LegendElement}
					chartData={chartData}
					hasLegend={true}
					title={claimsSettledPercentual}
				/>
			)}

			{!chartData.columns.length && loadData && (
				<div className="align-items-center d-flex flex-grow-1 justify-content-center">
					<span>No Data</span>
				</div>
			)}
		</div>
	);
}
