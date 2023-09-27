/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import LineChart from '../../../common/components/line-chart';
import {getClaimsByPeriod} from '../../../common/services/Claim';
import {CONSTANTS} from '../../../common/utils/constants';
import {
	currentDateString,
	firstDayOfLastMonthDate,
	lastDateOfLastYear,
	lastDayOfLastMonthDate,
	sixMonthsAgoDate,
	sixMonthsAgoDateLastYearFirstDay,
	sixMonthsAgoDateLastYearLastDay,
	threeMonthsAgoDate,
	threeMonthsAgoDateLastYearFirstDay,
	threeMonthsAgoDateLastYearLastDay,
	yearToDate,
	yearToDateLastYear,
} from '../../../common/utils/dateFormatter';

export default function () {
	const [selectedFilterDate, setSelectedFilterDate] = useState('1');
	const [currentMonthData, setCurrentMonthData] = useState([]);
	const [threeMonthData, setThreeMonthData] = useState([]);
	const [sixMonthData, setSixMonthData] = useState([]);
	const [firstUntilCurrentData, setFirstUntilCurrentData] = useState([]);

	const [
		threeMonthTotalSettlementPaid,
		setThreeMonthTotalSettlementPaid,
	] = useState();
	const [
		sixMonthTotalSettlementPaid,
		setSixMonthTotalSettlementPaid,
	] = useState();
	const [yoyTotalSettlementPaid, setYoyTotalSettlementPaid] = useState();

	const [
		yoyLastYearSettlementPaid,
		setYoyLastYearSettlementPaid,
	] = useState();

	const [
		lastYearThreeMonthSettlementPaid,
		setLastYearThreeMonthSettlementPaid,
	] = useState();

	const [
		lastYearSixMonthSettlementPaid,
		setLastYearSixMonthSettlementPaid,
	] = useState();

	function getTime(date, months) {
		date.setMonth(date.getMonth() + months);

		return date;
	}

	function getArrayOfFormatDate(date) {
		return date.toString().split(' ').splice(1, 3);
	}

	const currentDateFilter = getArrayOfFormatDate(getTime(new Date(), 0));
	const currentMonth = currentDateFilter[0];
	const threeMonthsAgoFilter = getArrayOfFormatDate(getTime(new Date(), -2));
	const sixMonthsAgoFilter = getArrayOfFormatDate(getTime(new Date(), -5));
	const dateThreeMonthsAgo = `${threeMonthsAgoFilter[0]} ${threeMonthsAgoFilter[2]}`;
	const dateSixMonthsAgo = `${sixMonthsAgoFilter[0]} ${sixMonthsAgoFilter[2]}`;
	const dateCurrentDate = `${currentDateFilter[0]} ${currentDateFilter[2]}`;

	function populateSettlementsPaid(claimsResult, claimsArray) {
		claimsResult.forEach((claim) => {
			const month = new Date(claim?.claimCreateDate)
				.toGMTString()
				.split(' ')[2];

			claimsArray.forEach((claimElement) => {
				if (month in claimElement) {
					claimElement[month] += claim?.claimAmount;
				}
			});
		});

		return claimsArray;
	}

	const getMonthsSettlementPaid = (response, arrayOfMonths) => {
		const monthsResult = response?.data?.items;
		const monthsAgo = populateSettlementsPaid(monthsResult, arrayOfMonths);

		return getSettlementPaidValues(monthsAgo);
	};

	function getSettlementPaidValues(arrayOfObjects) {
		const valuesArray = arrayOfObjects.map((values) => {
			return Object.values(values)[0];
		});

		return valuesArray;
	}

	function sumSettlementsPaidPerMonth(settlementsPaidArray) {
		const totalSettlementPaid = settlementsPaidArray.reduce(
			(settlementPaidSum, settlementPaid) =>
				settlementPaidSum + settlementPaid,
			0
		);

		return totalSettlementPaid;
	}

	function sumTotalSettlementsPaid(totalSettlementsPaidArray) {
		const totalValue = totalSettlementsPaidArray
			.map((values) => {
				return Object.values(values)[0];
			})
			.reduce(
				(settlementPaidSum, settlementPaid) =>
					settlementPaidSum + settlementPaid,
				0
			);

		return totalValue;
	}

	function setSettlementPaidName(finalArray) {
		return finalArray.unshift('Settlements Paid');
	}

	const PERIOD = {
		SIX_MONTH: '3',
		THIS_MONTH: '1',
		THREE_MONTH: '2',
		YTD: '4',
	};

	const periodOptions = [
		{
			label: 'This Month',
			value: PERIOD.THIS_MONTH,
		},
		{
			label: '3 MO',
			value: PERIOD.THREE_MONTH,
		},
		{
			label: '6 MO',
			value: PERIOD.SIX_MONTH,
		},
		{
			label: 'YTD',
			value: PERIOD.YTD,
		},
	];

	const patternColor = ['#4c84ff'];

	useEffect(() => {
		const thisMonthsAgoArray = [];
		const threeMonthsAgoArray = [];
		const sixMonthsAgoArray = [];
		const lastThreeMonthsAgoArray = [];
		const lastSixMonthsAgoArray = [];
		const lastThisMonthsAgoArray = [];
		const firstUntilCurrentArray = [];
		const lastYearFirstUntilCurrentArray = [];
		const numberOfMonths = 12;
		const maxIndexOfMonthsArray = 11;
		const threeMonthsTimePeriod = 2;
		const thisMonthsTimePeriod = 1;
		const sixMonthsTimePeriod = 5;
		const indexOfCurrentMonth = new Date().getMonth();

		let indexBaseThisMonth = indexOfCurrentMonth - thisMonthsTimePeriod;

		indexBaseThisMonth =
			indexBaseThisMonth < 0
				? numberOfMonths + indexBaseThisMonth
				: indexBaseThisMonth;

		let baseThisMonth = 0;

		for (let count = 0; count <= thisMonthsTimePeriod; count++) {
			const thisMonthsFilter = {};
			const thisMonthsAgoFilter = {};

			if (!count) {
				baseThisMonth = indexBaseThisMonth;
			}
			if (baseThisMonth > maxIndexOfMonthsArray) {
				baseThisMonth = 0;
			}

			thisMonthsFilter[CONSTANTS.MONTHS_ABREVIATIONS[baseThisMonth]] = 0;
			thisMonthsAgoFilter[
				CONSTANTS.MONTHS_ABREVIATIONS[baseThisMonth]
			] = 0;
			thisMonthsAgoArray[count] = thisMonthsFilter;
			lastThisMonthsAgoArray[count] = thisMonthsAgoFilter;
			baseThisMonth++;
		}

		let indexBaseMonthThree = indexOfCurrentMonth - threeMonthsTimePeriod;

		indexBaseMonthThree =
			indexBaseMonthThree < 0
				? numberOfMonths + indexBaseMonthThree
				: indexBaseMonthThree;

		let baseMonth = 0;

		for (let count = 0; count <= threeMonthsTimePeriod; count++) {
			const threeMonthsFilter = {};
			const threeMonthsAgoFilter = {};

			if (!count) {
				baseMonth = indexBaseMonthThree;
			}
			if (baseMonth > maxIndexOfMonthsArray) {
				baseMonth = 0;
			}

			threeMonthsFilter[CONSTANTS.MONTHS_ABREVIATIONS[baseMonth]] = 0;
			threeMonthsAgoFilter[CONSTANTS.MONTHS_ABREVIATIONS[baseMonth]] = 0;
			threeMonthsAgoArray[count] = threeMonthsFilter;
			lastThreeMonthsAgoArray[count] = threeMonthsAgoFilter;
			baseMonth++;
		}

		let indexBaseMonth = indexOfCurrentMonth - sixMonthsTimePeriod;

		indexBaseMonth =
			indexBaseMonth < 0
				? numberOfMonths + indexBaseMonth
				: indexBaseMonth;

		let month = 0;

		for (let count = 0; count <= sixMonthsTimePeriod; count++) {
			const sixMonthsFilter = {};
			const sixMonthsAgoFilter = {};

			if (!count) {
				month = indexBaseMonth;
			}
			if (month > maxIndexOfMonthsArray) {
				month = 0;
			}

			sixMonthsFilter[CONSTANTS.MONTHS_ABREVIATIONS[month]] = 0;
			sixMonthsAgoFilter[CONSTANTS.MONTHS_ABREVIATIONS[month]] = 0;
			sixMonthsAgoArray[count] = sixMonthsFilter;
			lastSixMonthsAgoArray[count] = sixMonthsAgoFilter;
			month++;
		}

		let monthposition = 0;

		for (let count = 0; count <= indexOfCurrentMonth; count++) {
			const firstUntilCurrentFilter = {};
			const LastsixMonthsAgoFilter = {};

			firstUntilCurrentFilter[
				CONSTANTS.MONTHS_ABREVIATIONS[monthposition]
			] = 0;
			LastsixMonthsAgoFilter[
				CONSTANTS.MONTHS_ABREVIATIONS[monthposition]
			] = 0;
			firstUntilCurrentArray[count] = firstUntilCurrentFilter;
			lastYearFirstUntilCurrentArray[count] = LastsixMonthsAgoFilter;
			monthposition++;
		}

		if (selectedFilterDate === PERIOD.THIS_MONTH) {
			getClaimsByPeriod(
				firstDayOfLastMonthDate[0],
				firstDayOfLastMonthDate[1],
				firstDayOfLastMonthDate[2],
				lastDayOfLastMonthDate[0],
				lastDayOfLastMonthDate[1],
				lastDayOfLastMonthDate[2]
			).then((response) => {
				const lastMonthResult = response?.data?.items;

				const lastMonthAgo = populateSettlementsPaid(
					lastMonthResult,
					lastThisMonthsAgoArray
				);

				sumTotalSettlementsPaid(lastMonthAgo);
			});
			getClaimsByPeriod(
				firstDayOfLastMonthDate[0],
				firstDayOfLastMonthDate[1],
				firstDayOfLastMonthDate[2],
				currentDateString[0],
				currentDateString[1],
				currentDateString[2]
			).then((response) => {
				const thisMonthsSettlementPaid = getMonthsSettlementPaid(
					response,
					thisMonthsAgoArray
				);

				sumSettlementsPaidPerMonth(thisMonthsSettlementPaid);

				setSettlementPaidName(thisMonthsSettlementPaid);
				setCurrentMonthData(thisMonthsSettlementPaid);
			});
		}

		if (selectedFilterDate === PERIOD.THREE_MONTH) {
			getClaimsByPeriod(
				threeMonthsAgoDateLastYearFirstDay[0],
				threeMonthsAgoDateLastYearFirstDay[1],
				threeMonthsAgoDateLastYearFirstDay[2],
				threeMonthsAgoDateLastYearLastDay[0],
				threeMonthsAgoDateLastYearLastDay[1],
				threeMonthsAgoDateLastYearLastDay[2]
			).then((response) => {
				const lastYearThreeMonthsResult = response?.data?.items;

				const lastThreeMonthsAgo = populateSettlementsPaid(
					lastYearThreeMonthsResult,
					lastThreeMonthsAgoArray
				);

				const lastYearThreeMonthsSettlementPaid = sumTotalSettlementsPaid(
					lastThreeMonthsAgo
				);

				setLastYearThreeMonthSettlementPaid(
					lastYearThreeMonthsSettlementPaid
				);

				getClaimsByPeriod(
					threeMonthsAgoDate[0],
					threeMonthsAgoDate[1],
					threeMonthsAgoDate[2],
					currentDateString[0],
					currentDateString[1],
					currentDateString[2]
				).then((response) => {
					const threeMonthsSettlementPaid = getMonthsSettlementPaid(
						response,
						threeMonthsAgoArray
					);
					const totalThreeMonthsSettlementPaidValue = sumSettlementsPaidPerMonth(
						threeMonthsSettlementPaid
					);
					setSettlementPaidName(threeMonthsSettlementPaid);
					setThreeMonthData(threeMonthsSettlementPaid);

					setThreeMonthTotalSettlementPaid(
						totalThreeMonthsSettlementPaidValue
					);
				});
			});
		}

		if (selectedFilterDate === PERIOD.SIX_MONTH) {
			getClaimsByPeriod(
				sixMonthsAgoDateLastYearFirstDay[0],
				sixMonthsAgoDateLastYearFirstDay[1],
				sixMonthsAgoDateLastYearFirstDay[2],
				sixMonthsAgoDateLastYearLastDay[0],
				sixMonthsAgoDateLastYearLastDay[1],
				sixMonthsAgoDateLastYearLastDay[2]
			).then((response) => {
				const lastYearSixMonthsResult = response?.data?.items;

				const lastSixMonthsAgo = populateSettlementsPaid(
					lastYearSixMonthsResult,
					lastSixMonthsAgoArray
				);

				const lastYearSixMonthsSettlementPaid = sumTotalSettlementsPaid(
					lastSixMonthsAgo
				);

				setLastYearSixMonthSettlementPaid(
					lastYearSixMonthsSettlementPaid
				);

				getClaimsByPeriod(
					sixMonthsAgoDate[0],
					sixMonthsAgoDate[1],
					sixMonthsAgoDate[2],
					currentDateString[0],
					currentDateString[1],
					currentDateString[2]
				).then((response) => {
					const sixMonthsSettlementPaid = getMonthsSettlementPaid(
						response,
						sixMonthsAgoArray
					);

					const totalSixMonthsSettlementPaidValue = sumSettlementsPaidPerMonth(
						sixMonthsSettlementPaid
					);

					setSettlementPaidName(sixMonthsSettlementPaid);
					setSixMonthData(sixMonthsSettlementPaid);

					setSixMonthTotalSettlementPaid(
						totalSixMonthsSettlementPaidValue
					);
				});
			});
		}

		if (selectedFilterDate === PERIOD.YTD) {
			getClaimsByPeriod(
				yearToDateLastYear[0],
				yearToDateLastYear[1],
				yearToDateLastYear[2],
				lastDateOfLastYear[0],
				lastDateOfLastYear[1],
				lastDateOfLastYear[2]
			).then((response) => {
				const firstUntilCurrentLastYearResult = response?.data?.items;

				const lastYearFirstUntilCurrent = populateSettlementsPaid(
					firstUntilCurrentLastYearResult,
					lastYearFirstUntilCurrentArray
				);

				const YoySettlementsPaid = sumTotalSettlementsPaid(
					lastYearFirstUntilCurrent
				);

				setYoyLastYearSettlementPaid(YoySettlementsPaid);

				getClaimsByPeriod(
					yearToDate[0],
					yearToDate[1],
					yearToDate[2],
					currentDateString[0],
					currentDateString[1],
					currentDateString[2]
				).then((response) => {
					const firstUntilCurrentResult = response?.data?.items;

					const firstUntilCurrent = populateSettlementsPaid(
						firstUntilCurrentResult,
						firstUntilCurrentArray
					);

					const firstUntilCurrentSettlementPaid = getSettlementPaidValues(
						firstUntilCurrent
					);

					const totalFirstCurrentSettlementPaidValue = sumSettlementsPaidPerMonth(
						firstUntilCurrentSettlementPaid
					);

					sumTotalSettlementsPaid(lastYearFirstUntilCurrent);

					setSettlementPaidName(firstUntilCurrentSettlementPaid);
					setFirstUntilCurrentData(firstUntilCurrentSettlementPaid);

					setYoyTotalSettlementPaid(
						totalFirstCurrentSettlementPaidValue
					);
				});
			});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedFilterDate]);

	const percentThisMonthSettlementPaid = Math.round(
		((currentMonthData[2] - currentMonthData[1]) / currentMonthData[1]) *
			100
	);

	const percent3MoSettlementPaid = Math.round(
		((threeMonthTotalSettlementPaid - lastYearThreeMonthSettlementPaid) /
			lastYearThreeMonthSettlementPaid) *
			100
	);

	const percent6MoSettlementPaid = Math.round(
		((sixMonthTotalSettlementPaid - lastYearSixMonthSettlementPaid) /
			lastYearSixMonthSettlementPaid) *
			100
	);
	const percentYoySettlementPaid = Math.round(
		((yoyTotalSettlementPaid - yoyLastYearSettlementPaid) /
			yoyLastYearSettlementPaid) *
			100
	);
	const loadData = [
		{
			dataColumns: currentMonthData,
			percentSettlementPaid: percentThisMonthSettlementPaid,
			period: 1,
			periodDate: currentMonth,
			settlementPaidValue: currentMonthData[2],
		},
		{
			dataColumns: threeMonthData,
			percentSettlementPaid: percent3MoSettlementPaid,
			period: 2,
			periodDate: `${dateThreeMonthsAgo} - ${dateCurrentDate}`,
			settlementPaidValue: threeMonthTotalSettlementPaid,
		},
		{
			dataColumns: sixMonthData,
			percentSettlementPaid: percent6MoSettlementPaid,
			period: 3,
			periodDate: `${dateSixMonthsAgo} - ${dateCurrentDate}`,
			settlementPaidValue: sixMonthTotalSettlementPaid,
		},
		{
			dataColumns: firstUntilCurrentData,
			percentSettlementPaid: percentYoySettlementPaid,
			period: 4,
			periodDate: `Jan - ${currentMonth}`,
			settlementPaidValue: yoyTotalSettlementPaid,
		},
	];

	const getData = () => {
		return loadData?.filter(
			(data) => data.period === Number(selectedFilterDate)
		);
	};

	const chartData = {
		columns: [getData()[0]?.dataColumns],
		type: 'line',
	};

	const claimPercentual = getData()[0]?.percentSettlementPaid;
	const getDataDate = getData()[0]?.periodDate;
	const claimValue = getData()[0]?.settlementPaidValue;
	const getPeriod = getData()[0]?.period;

	const buildLegend = () => (
		<div className="claims-total-settlements-legend d-flex flex-column h-100 justify-content-end mt-5">
			<div className="font-weight-normal mb-2 text-neutral-8 text-paragraph-sm">
				{getDataDate}
			</div>

			<div className="claims-total-settlements-screen font-weight-bolder h5 mb-3">
				{new Intl.NumberFormat('en-US', {
					currency: 'USD',
					style: 'currency',
				}).format(claimValue)}
			</div>

			<div
				className={classNames('text-paragraph-sm font-weight-bolder', {
					'line-chart-icon-success-color': claimPercentual >= 0,
					'text-danger': claimPercentual < 0,
				})}
			>
				{claimPercentual > 0 && <ClayIcon symbol="caret-top" />}

				{claimPercentual === 0 && <ClayIcon symbol="hr" />}

				{claimPercentual < 0 && <ClayIcon symbol="caret-bottom" />}

				{getPeriod === 4
					? `${
							claimPercentual === Infinity
								? `NaN`
								: claimPercentual
					  }% YoM`
					: `${
							claimPercentual === Infinity
								? `NaN`
								: claimPercentual
					  }% MoM`}
			</div>
		</div>
	);

	return (
		<div className="claims-total-settlements-container d-flex flex-column flex-shrink-0 pb-4 pt-3 px-3">
			<div className="align-items-center claims-total-settlements-header d-flex justify-content-between">
				<div className="claims-total-settlements-title font-weight-bolder h4 mb-0">
					Total Settlements Paid
				</div>

				<ClaySelect
					className="claims-total-settlements-select"
					onChange={({target}) => {
						setSelectedFilterDate(target.value);
					}}
					sizing="sm"
					value={selectedFilterDate}
				>
					{periodOptions.map((item) => (
						<ClaySelect.Option
							key={item.value}
							label={item.label}
							value={item.value}
						/>
					))}
				</ClaySelect>
			</div>

			{!!chartData.columns.length && (
				<LineChart
					LegendElement={buildLegend}
					chartData={chartData}
					patternColor={patternColor}
				/>
			)}

			{!chartData.columns.length && (
				<div className="align-items-center d-flex flex-grow-1 justify-content-center">
					<span>No Data</span>
				</div>
			)}
		</div>
	);
}
