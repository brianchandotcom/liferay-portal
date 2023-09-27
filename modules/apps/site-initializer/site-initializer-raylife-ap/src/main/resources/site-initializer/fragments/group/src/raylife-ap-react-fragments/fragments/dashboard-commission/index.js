/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import LineChart from '../../../common/components/line-chart';
import {getPoliciesByPeriod} from '../../../common/services';
import {CONSTANTS} from '../../../common/utils/constants';
import {
	currentDate,
	lastYearSixMonthsAgoPeriod,
	oneYearAgoDate,
	sixMonthsAgoDate,
} from '../../../common/utils/dateFormatter';

const PERIOD = {
	SIX_MONTH: '2',
	THIS_MONTH: '1',
	YTD: '3',
};

export default function () {
	const [selectedFilterDate, setSelectedFilterDate] = useState('1');
	const [currentMonthData, setCurrentMonthData] = useState([]);
	const [sixMonthData, setSixMonthData] = useState([]);
	const [firstUntilCurrentData, setFirstUntilCurrentData] = useState([]);
	const [sixMonthTotalCommission, setSixMonthTotalCommission] = useState();
	const [yoyTotalCommission, setYoyTotalCommission] = useState();
	const [yoyLastYearCommission, setYoyLastYearCommission] = useState();
	const [
		lastYearSixMonthCommission,
		setLastYearSixMonthCommission,
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
	const sixMonthsAgoFilter = getArrayOfFormatDate(getTime(new Date(), -5));
	const dateSixMonthsAgo = `${sixMonthsAgoFilter[0]} ${sixMonthsAgoFilter[2]}`;
	const dateCurrentDate = `${currentDateFilter[0]} ${currentDateFilter[2]}`;

	const currentDateArray = currentDate.split('-');

	const oneYearAgoArray = oneYearAgoDate.split('-');

	function populateCommissions(policiesResult, policiesArray) {
		policiesResult.forEach((policy) => {
			const month = new Date(policy?.startDate)
				.toGMTString()
				.split(' ')[2];

			policiesArray.forEach((policyElement) => {
				if (month in policyElement) {
					policyElement[month] += policy?.commission;
				}
			});
		});

		return policiesArray;
	}

	const getSixMonthsCommission = (response, sixMonthsAgoArray) => {
		const sixMonthsResult = response?.data?.items;
		const sixMonthsAgo = populateCommissions(
			sixMonthsResult,
			sixMonthsAgoArray
		);

		return getCommissionValues(sixMonthsAgo);
	};

	function getCommissionValues(arrayOfObjects) {
		const valuesArray = arrayOfObjects.map((values) => {
			return Object.values(values)[0];
		});

		return valuesArray;
	}

	function sumCommissionsPerMonth(comissionsArray) {
		const totalCommission = comissionsArray.reduce(
			(commissionSum, commission) => commissionSum + commission,
			0
		);

		return totalCommission;
	}

	function sumTotalCommissions(totalCommissionsArray) {
		const totalValue = totalCommissionsArray
			.map((values) => {
				return Object.values(values)[0];
			})
			.reduce(
				(commissionSum, commission) => commissionSum + commission,
				0
			);

		return totalValue;
	}

	function setCommissionName(finalArray) {
		return finalArray.unshift('Commission');
	}

	useEffect(() => {
		const sixMonthsAgoArray = [];
		const lastSixMonthsAgoArray = [];
		const firstUntilCurrentArray = [];
		const lastYearFirstUntilCurrentArray = [];

		const numberOfMonths = 12;
		const maxIndexOfMonthsArray = 11;
		const thisMonthTimePeriod = 1;
		const sixMonthsTimePeriod = 5;
		const indexOfCurrentMonth = new Date().getMonth();

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
			getPoliciesByPeriod(
				currentDateArray[0],
				currentDateArray[1],
				currentDateArray[2],
				sixMonthsAgoDate[0],
				sixMonthsAgoDate[1],
				sixMonthsAgoDate[2]
			).then((response) => {
				const sixMonthsCommission = getSixMonthsCommission(
					response,
					sixMonthsAgoArray
				);

				const sixMonthsCommissionLenght =
					sixMonthsCommission.length - 1;

				const thisMonthFilterCommission = [
					sixMonthsCommission[
						sixMonthsCommissionLenght - thisMonthTimePeriod
					],
					sixMonthsCommission[sixMonthsCommissionLenght],
				];

				setCommissionName(thisMonthFilterCommission);
				setCommissionName(sixMonthsCommission);

				setSixMonthData(sixMonthsCommission);

				setCurrentMonthData(thisMonthFilterCommission);
			});
		}

		if (selectedFilterDate === PERIOD.SIX_MONTH) {
			getPoliciesByPeriod(
				oneYearAgoArray[0],
				oneYearAgoArray[1],
				oneYearAgoArray[2],
				lastYearSixMonthsAgoPeriod[0],
				lastYearSixMonthsAgoPeriod[1],
				lastYearSixMonthsAgoPeriod[2]
			).then((response) => {
				const lastYearSixMonthsResult = response?.data?.items;

				const lastSixMonthsAgo = populateCommissions(
					lastYearSixMonthsResult,
					lastSixMonthsAgoArray
				);

				const lastYearSixMonthsCommission = sumTotalCommissions(
					lastSixMonthsAgo
				);

				setLastYearSixMonthCommission(lastYearSixMonthsCommission);

				getPoliciesByPeriod(
					currentDateArray[0],
					currentDateArray[1],
					currentDateArray[2],
					sixMonthsAgoDate[0],
					sixMonthsAgoDate[1],
					sixMonthsAgoDate[2]
				).then((response) => {
					const totalSixMonthsCommissionValue = sumCommissionsPerMonth(
						getSixMonthsCommission(response, sixMonthsAgoArray)
					);

					setSixMonthTotalCommission(totalSixMonthsCommissionValue);
				});
			});
		}

		if (selectedFilterDate === PERIOD.YTD) {
			getPoliciesByPeriod(
				currentDateArray[0],
				currentDateArray[1],
				currentDateArray[2],
				currentDateArray[0],
				'01',
				'01'
			).then((response) => {
				const firstUntilCurrentResult = response?.data?.items;

				const firstUntilCurrent = populateCommissions(
					firstUntilCurrentResult,
					firstUntilCurrentArray
				);

				const firstUntilCurrentCommission = getCommissionValues(
					firstUntilCurrent
				);

				const totalFirstCurrentCommissionValue = sumCommissionsPerMonth(
					firstUntilCurrentCommission
				);

				setCommissionName(firstUntilCurrentCommission);
				setFirstUntilCurrentData(firstUntilCurrentCommission);
				setYoyTotalCommission(totalFirstCurrentCommissionValue);

				getPoliciesByPeriod(
					oneYearAgoArray[0],
					oneYearAgoArray[1],
					oneYearAgoArray[2],
					oneYearAgoArray[0],
					'01',
					'01'
				).then((response) => {
					const firstUntilCurrentLastYearResult =
						response?.data?.items;

					const lastYearFirstUntilCurrent = populateCommissions(
						firstUntilCurrentLastYearResult,
						lastYearFirstUntilCurrentArray
					);

					const YoyCommision = sumTotalCommissions(
						lastYearFirstUntilCurrent
					);

					setYoyLastYearCommission(YoyCommision);
				});
			});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedFilterDate]);

	const options = [
		{
			label: 'This Month',
			value: PERIOD.THIS_MONTH,
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

	const percentThisMonthCommission = Math.round(
		((currentMonthData[2] - currentMonthData[1]) / currentMonthData[1]) *
			100
	);

	const percent6MoCommission = Math.round(
		((sixMonthTotalCommission - lastYearSixMonthCommission) /
			lastYearSixMonthCommission) *
			100
	);
	const percentYoyCommission = Math.round(
		((yoyTotalCommission - yoyLastYearCommission) / yoyLastYearCommission) *
			100
	);
	const loadData = [
		{
			commissionValue: currentMonthData[2],
			dataColumns: currentMonthData,
			percentcommission: percentThisMonthCommission,
			period: 1,
			periodDate: currentMonth,
		},
		{
			commissionValue: sixMonthTotalCommission,
			dataColumns: sixMonthData,
			percentcommission: percent6MoCommission,
			period: 2,
			periodDate: `${dateSixMonthsAgo} - ${dateCurrentDate}`,
		},
		{
			commissionValue: yoyTotalCommission,
			dataColumns: firstUntilCurrentData,
			percentcommission: percentYoyCommission,
			period: 3,
			periodDate: `Jan - ${currentMonth}`,
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

	const chartDataValue = chartData.columns.map((period) => period.slice(1));

	const hasData = chartDataValue[0].reduce(
		(sum, value) => Number(sum) + Number(value),
		0
	);

	const commissionPercentual = getData()[0]?.percentcommission;
	const getDataDate = getData()[0]?.periodDate;
	const commissionValue = getData()[0]?.commissionValue;
	const getPeriod = getData()[0]?.period;

	const buildLegend = () => (
		<div className="d-flex dashboard-commission-legend flex-column h-100 justify-content-end mt-5">
			<div className="font-weight-normal mb-2 text-neutral-8 text-paragraph-sm">
				{getDataDate}
			</div>

			<div className="dashboard-commission-screen font-weight-bolder h5 mb-3">
				{new Intl.NumberFormat('en-US', {
					currency: 'USD',
					style: 'currency',
				}).format(commissionValue)}
			</div>

			<div
				className={classNames('text-paragraph-sm font-weight-bolder', {
					'line-chart-icon-success-color': commissionPercentual >= 0,
					'text-danger': commissionPercentual < 0,
				})}
			>
				{commissionPercentual > 0 && <ClayIcon symbol="caret-top" />}

				{commissionPercentual === 0 && <ClayIcon symbol="hr" />}

				{commissionPercentual < 0 && <ClayIcon symbol="caret-bottom" />}

				{getPeriod === 1
					? `${
							commissionPercentual === Infinity
								? `NaN`
								: commissionPercentual
					  }% MoM`
					: `${
							commissionPercentual === Infinity
								? `NaN`
								: commissionPercentual
					  }% YoY`}
			</div>
		</div>
	);

	return (
		<div className="d-flex dashboard-commission-container flex-column flex-shrink-0 pb-4 pt-3 px-3">
			<div className="align-items-center d-flex dashboard-commission-header justify-content-between">
				<div className="dashboard-commission-title font-weight-bolder h4 mb-0">
					Commission
				</div>

				<ClaySelect
					className="dashboard-commission-select"
					onChange={({target}) => {
						setSelectedFilterDate(target.value);
					}}
					sizing="sm"
					value={selectedFilterDate}
				>
					{options.map((item) => (
						<ClaySelect.Option
							key={item.value}
							label={item.label}
							value={item.value}
						/>
					))}
				</ClaySelect>
			</div>

			{hasData > 0 ? (
				<LineChart LegendElement={buildLegend} chartData={chartData} />
			) : (
				<div className="align-items-center d-flex flex-grow-1 justify-content-center">
					<span>No Data</span>
				</div>
			)}
		</div>
	);
}
