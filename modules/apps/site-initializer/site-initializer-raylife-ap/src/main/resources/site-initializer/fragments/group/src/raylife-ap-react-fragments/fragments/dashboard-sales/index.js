/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import DonutChart from '../../../common/components/donut-chart';
import {getPoliciesForSalesGoal} from '../../../common/services/Policy';
import {getSalesGoal} from '../../../common/services/SalesGoal';
import {
	arrayOfMonthsWith30Days,
	arrayOfMonthsWith31Days,
	currentDateString,
	december,
	getCurrentDay,
	getCurrentMonth,
	getDayOfYear,
	january,
	sixMonthsAgoDate,
	threeMonthsAgoDate,
} from '../../../common/utils/dateFormatter';

const PERIOD = {
	SIX_MONTH: '3',
	THIS_MONTH: '1',
	THREE_MONTH: '2',
	YTD: '4',
};

export default function () {
	const [selectedFilterDate, setSelectedFilterDate] = useState('1');
	const [sumOfSalesCurrentMonth, setSumOfSalesCurrentMonth] = useState(0);
	const [sumOfGoalsCurrentMonth, setSumOfGoalsCurrentMonth] = useState(0);
	const [sumOfSalesThreeMonths, setSumOfSalesThreeMonths] = useState(0);
	const [sumOfGoalsThreeMonths, setSumOfGoalsThreeMonths] = useState(0);
	const [sumOfSalesSixMonths, setSumOfSalesSixMonths] = useState(0);
	const [sumOfGoalsSixMonths, setSumOfGoalsSixMonths] = useState(0);
	const [sumOfSalesYearToDate, setSumOfSalesYearToDate] = useState(0);
	const [sumOfGoalsYearToDate, setSumOfGoalsYearToDate] = useState(0);
	const [daysUntilGoal, setDaysUntilGoal] = useState(0);

	const options = [
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

	function getDaysUntilGoal(currentDay, currentMonth, filterOption) {
		if (filterOption === PERIOD.YTD) {
			return 365 - getDayOfYear;
		}
		else {
			if (arrayOfMonthsWith31Days.includes(currentMonth)) {
				return 31 - currentDay;
			}
			else if (arrayOfMonthsWith30Days.includes(currentMonth)) {
				return 30 - currentDay;
			}
			else {
				return 28 - currentDay;
			}
		}
	}

	function getArrayFromArrayOfObjects(arrayOfObjects) {
		const valuesArray = arrayOfObjects.map((values) => {
			return Object.values(values)[1];
		});

		return Object.values(valuesArray);
	}

	function getSumFromArrayOfValues(arrayOfValues) {
		const totalValue = arrayOfValues.reduce(
			(sumValue, values) => sumValue + values,
			0
		);

		return totalValue;
	}

	function getReachedValue(sumOfSales, sumOfGoals) {
		return (sumOfSales / sumOfGoals) * 100;
	}

	useEffect(() => {
		function getArrayWithValuesOfGoals(arrayOfGoals) {
			const arrayOfValues = arrayOfGoals?.map((salesGoal) => {
				return salesGoal.goalValue;
			});

			return arrayOfValues;
		}

		if (selectedFilterDate === PERIOD.THIS_MONTH) {
			getSalesGoal(
				currentDateString[0],
				currentDateString[1],
				currentDateString[0],
				currentDateString[1]
			).then((results) => {
				const thisMonthGoalResult = results?.data?.items;

				const arrayValuesOfGoalsThisMonth = getArrayWithValuesOfGoals(
					thisMonthGoalResult
				);

				setSumOfGoalsCurrentMonth(
					getSumFromArrayOfValues(arrayValuesOfGoalsThisMonth)
				);
			});

			getPoliciesForSalesGoal(
				currentDateString[0],
				currentDateString[1],
				currentDateString[0],
				currentDateString[1]
			).then((results) => {
				const policiesForSalesGoalThisMonthResult =
					results?.data?.items;

				const arrayValuesOfSalesThisMonth = getArrayFromArrayOfObjects(
					policiesForSalesGoalThisMonthResult
				);

				setSumOfSalesCurrentMonth(
					getSumFromArrayOfValues(arrayValuesOfSalesThisMonth)
				);
			});
		}

		if (selectedFilterDate === PERIOD.THREE_MONTH) {
			getSalesGoal(
				currentDateString[0],
				currentDateString[1],
				threeMonthsAgoDate[0],
				threeMonthsAgoDate[1]
			).then((results) => {
				const lastThreeMonthsGoalsResult = results?.data?.items;

				const arrayValuesOfGoalsThreeMonths = getArrayWithValuesOfGoals(
					lastThreeMonthsGoalsResult
				);

				setSumOfGoalsThreeMonths(
					getSumFromArrayOfValues(arrayValuesOfGoalsThreeMonths)
				);
			});

			getPoliciesForSalesGoal(
				currentDateString[0],
				currentDateString[1],
				threeMonthsAgoDate[0],
				threeMonthsAgoDate[1]
			).then((results) => {
				const policiesThreeMonthsSalesResult = results?.data?.items;

				const arrayValuesOfSalesThreeMonths = getArrayFromArrayOfObjects(
					policiesThreeMonthsSalesResult
				);

				setSumOfSalesThreeMonths(
					getSumFromArrayOfValues(arrayValuesOfSalesThreeMonths)
				);
			});
		}

		if (selectedFilterDate === PERIOD.SIX_MONTH) {
			getSalesGoal(
				currentDateString[0],
				currentDateString[1],
				sixMonthsAgoDate[0],
				sixMonthsAgoDate[1]
			).then((results) => {
				const lastSixMonthsGoalsResult = results?.data?.items;

				const arrayValuesOfGoalsSixMonths = getArrayWithValuesOfGoals(
					lastSixMonthsGoalsResult
				);

				setSumOfGoalsSixMonths(
					getSumFromArrayOfValues(arrayValuesOfGoalsSixMonths)
				);
			});

			getPoliciesForSalesGoal(
				currentDateString[0],
				currentDateString[1],
				sixMonthsAgoDate[0],
				sixMonthsAgoDate[1]
			).then((results) => {
				const policiesSixMonthsSalesResult = results?.data?.items;

				const arrayValuesOfSalesSixMonths = getArrayFromArrayOfObjects(
					policiesSixMonthsSalesResult
				);

				setSumOfSalesSixMonths(
					getSumFromArrayOfValues(arrayValuesOfSalesSixMonths)
				);
			});
		}

		if (selectedFilterDate === PERIOD.YTD) {
			getSalesGoal(
				currentDateString[0],
				december,
				currentDateString[0],
				january
			).then((results) => {
				const allYearGoalsResult = results?.data?.items;

				const arrayValuesOfGoalsAllYear = getArrayWithValuesOfGoals(
					allYearGoalsResult
				);

				setSumOfGoalsYearToDate(
					getSumFromArrayOfValues(arrayValuesOfGoalsAllYear)
				);
			});

			getPoliciesForSalesGoal(
				currentDateString[0],
				currentDateString[1],
				currentDateString[0],
				january
			).then((results) => {
				const policiesSalesUntilCurrentMonth = results?.data?.items;

				const arrayValueOfSalesUntilCurrentMonth = getArrayFromArrayOfObjects(
					policiesSalesUntilCurrentMonth
				);

				setSumOfSalesYearToDate(
					getSumFromArrayOfValues(arrayValueOfSalesUntilCurrentMonth)
				);
			});
		}

		setDaysUntilGoal(
			getDaysUntilGoal(getCurrentDay, getCurrentMonth, selectedFilterDate)
		);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedFilterDate]);

	const reachedValueThisMonth = getReachedValue(
		sumOfSalesCurrentMonth,
		sumOfGoalsCurrentMonth
	);

	const reachedValueThreeMonths = getReachedValue(
		sumOfSalesThreeMonths,
		sumOfGoalsThreeMonths
	);

	const reachedValueSixMonths = getReachedValue(
		sumOfSalesSixMonths,
		sumOfGoalsSixMonths
	);

	const reachedValueYearToDate = getReachedValue(
		sumOfSalesYearToDate,
		sumOfGoalsYearToDate
	);

	const loadData = [
		{
			dataColumns: [
				['reached', sumOfSalesCurrentMonth],
				['remaining', sumOfGoalsCurrentMonth - sumOfSalesCurrentMonth],
			],
			dateUntilGoal: `${daysUntilGoal} days to goal`,
			goalValue: sumOfGoalsCurrentMonth,
			period: 1,
			salesPercentual: reachedValueThisMonth.toFixed(0),
			salesValue: sumOfSalesCurrentMonth,
		},
		{
			dataColumns: [
				['reached', sumOfSalesThreeMonths],
				['remaining', sumOfGoalsThreeMonths - sumOfSalesThreeMonths],
			],
			dateUntilGoal: `${daysUntilGoal} days to goal`,
			goalValue: sumOfGoalsThreeMonths,
			period: 2,
			salesPercentual: reachedValueThreeMonths.toFixed(0),
			salesValue: sumOfSalesThreeMonths,
		},
		{
			dataColumns: [
				['reached', sumOfSalesSixMonths],
				['remaining', sumOfGoalsSixMonths - sumOfSalesSixMonths],
			],
			dateUntilGoal: `${daysUntilGoal} days to goal`,
			goalValue: sumOfGoalsSixMonths,
			period: 3,
			salesPercentual: reachedValueSixMonths.toFixed(0),
			salesValue: sumOfSalesSixMonths,
		},
		{
			dataColumns: [
				['reached', sumOfSalesYearToDate],
				['remaining', sumOfGoalsYearToDate - sumOfSalesYearToDate],
			],
			dateUntilGoal: `${daysUntilGoal} days to goal`,
			goalValue: sumOfGoalsYearToDate,
			period: 4,
			salesPercentual: reachedValueYearToDate.toFixed(0),
			salesValue: sumOfSalesYearToDate,
		},
	];

	const getData = () => {
		return loadData?.filter(
			(data) => data.period === Number(selectedFilterDate)
		);
	};

	const getDateUntilGoal = getData()[0]?.dateUntilGoal;
	const getSalesValue = getData()[0]?.salesValue;
	const getGoalValue = getData()[0]?.goalValue;
	const getSalesPercentual =
		getData()[0]?.salesPercentual > 100
			? `${100}%`
			: `${getData()[0]?.salesPercentual}%`;

	const isGoalRached = getSalesValue >= getGoalValue;

	const colors = {
		reached: !isGoalRached ? '#ec0d6b' : '#FD7E14',
		remaining: '#fbcee1',
	};

	const chartData = {
		colors,
		columns: getData()[0]?.dataColumns,
		type: 'donut',
	};

	const chartDataValue = chartData.columns.map((period) => period.slice(1));

	const hasData = chartDataValue
		.map((arrayValue) => arrayValue[0])
		.reduce((sum, value) => Number(sum) + Number(value));

	const LegendElement = () => (
		<div className="d-flex dashboard-sales-space-legend flex-column h-100 justify-content-end mt-5">
			<div className="dashboard-sales-screen font-weight-bolder h5">
				{new Intl.NumberFormat('en-US', {
					currency: 'USD',
					style: 'currency',
				}).format(getSalesValue)}
			</div>

			<div className="font-weight-normal mb-2 text-neutral-8 text-paragraph-sm">
				{`Goal: ${new Intl.NumberFormat('en-US', {
					currency: 'USD',
					style: 'currency',
				}).format(getGoalValue)}`}
			</div>

			<div
				className={classNames('font-weight-bolder text-paragraph-sm', {
					'text-danger': !isGoalRached,
					'text-warning': isGoalRached,
				})}
			>
				{!isGoalRached && <ClayIcon className="mr-1" symbol="time" />}

				{isGoalRached && (
					<ClayIcon className="mr-1" symbol="check-circle-full" />
				)}

				{`${isGoalRached ? 'Exceeded' : getDateUntilGoal}`}
			</div>
		</div>
	);

	return (
		<div className="d-flex dashboard-sales-container flex-column flex-shrink-0 pb-4 pt-3 px-3">
			<div className="align-items-center d-flex dashboard-sales-header justify-content-between">
				<div className="dashboard-sales-title font-weight-bolder h4">
					Sales
				</div>

				<ClaySelect
					className="dashboard-sales-select"
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

			{hasData > 0 ? (
				<DonutChart
					LegendElement={LegendElement}
					chartData={chartData}
					hasLegend={true}
					maxValue={getGoalValue}
					title={getSalesPercentual}
				/>
			) : (
				<div className="align-items-center d-flex flex-grow-1 justify-content-center">
					<span>No Data</span>
				</div>
			)}
		</div>
	);
}
