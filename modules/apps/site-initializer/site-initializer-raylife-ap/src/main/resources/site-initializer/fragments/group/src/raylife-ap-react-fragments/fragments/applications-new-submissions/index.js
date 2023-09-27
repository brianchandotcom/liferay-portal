/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import LineChart from '../../../common/components/line-chart';
import {getNewSubmissions} from '../../../common/services/Application';
import {CONSTANTS} from '../../../common/utils/constants';
import {
	currentDateString,
	january,
	lastMonthDate,
	lastYear,
	sixMonthsAgoDate,
} from '../../../common/utils/dateFormatter';
import {
	getArrayOfValuesFromArrayOfObjects,
	sumTotalOfValuesOfArray,
} from '../../../common/utils/requestFormatter';

const PERIOD = {
	SIX_MONTH: '2',
	THIS_MONTH: '1',
	YTD: '3',
};

export default function () {
	const [selectedFilterDate, setSelectedFilterDate] = useState('1');
	const [currentMonthData, setCurrentMonthData] = useState([]);
	const [sixMonthData, setSixMonthData] = useState([]);
	const [yearToDateData, setYearToDateData] = useState([]);
	const [sixMonthsTotalSubmissions, setSixMonthsTotalSubmissions] = useState(
		0
	);
	const [
		lastYearSixMonthsTotalSubmissions,
		setLastYearSixMonthsTotalSubmissions,
	] = useState(0);
	const [
		yearToDateTotalOfSubmissions,
		setYearToDateTotalOfSubmissions,
	] = useState(0);
	const [
		lastYearToDateTotalSubmissions,
		setLastYearToDateTotalSubmissions,
	] = useState(0);

	function populateSubmissions(applicationResult, applicationsArray) {
		applicationResult.forEach((application) => {
			const month = new Date(application?.applicationCreateDate)
				.toGMTString()
				.split(' ')[2];

			applicationsArray?.forEach((applicationElement) => {
				if (month in applicationElement) {
					applicationElement[month]++;
				}
			});
		});

		return applicationsArray;
	}

	const getSubmissionsResult = (response, monthsArray) => {
		const monthsResult = response?.data?.items;
		const monthsValue = populateSubmissions(monthsResult, monthsArray);

		return getArrayOfValuesFromArrayOfObjects(monthsValue);
	};

	function setNewSubmissionsName(finalArray) {
		return finalArray?.unshift('New Submissions');
	}

	const FilterOptions = [
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

	const getCurrentYearData = async (
		dataArrayOfMonths,
		currentYearString,
		currentMonthString,
		periodYearAgoDate,
		periodMonthAgoDate
	) => {
		const currentYearApplications = await getNewSubmissions(
			currentYearString,
			currentMonthString,
			periodYearAgoDate,
			periodMonthAgoDate
		);

		const monthsRequestResult = await getSubmissionsResult(
			currentYearApplications,
			dataArrayOfMonths
		);

		const monthsAgoValues = await populateSubmissions(
			monthsRequestResult,
			dataArrayOfMonths
		);

		const monthsSubmission = await sumTotalOfValuesOfArray(monthsAgoValues);

		const dataMonthsArrayOfSubmissions = await getArrayOfValuesFromArrayOfObjects(
			monthsAgoValues
		);

		return [monthsSubmission, dataMonthsArrayOfSubmissions];
	};

	const getLastYearData = async (
		dataArrayOfMonths,
		currentMonthString,
		periodAgoDate
	) => {
		const lastYearApplications = await getNewSubmissions(
			lastYear.toString(),
			currentMonthString,
			lastYear.toString(),
			periodAgoDate
		);

		const lastYearMonthsDataResults = await getSubmissionsResult(
			lastYearApplications,
			dataArrayOfMonths
		);

		const lastYearMonthsAgo = await populateSubmissions(
			lastYearMonthsDataResults,
			dataArrayOfMonths
		);

		const lastYearSubmissions = await sumTotalOfValuesOfArray(
			lastYearMonthsAgo
		);

		return lastYearSubmissions;
	};

	const getThisMonthFilterPeriod = async (currentAndLastMonthArray) => {
		const thisMonthTimePeriod = 1;

		const thisMonthFilterApplications = await getNewSubmissions(
			currentDateString[0],
			currentDateString[1],
			lastMonthDate[0],
			lastMonthDate[1]
		);

		const thisMonthRequestResult = await getSubmissionsResult(
			thisMonthFilterApplications,
			currentAndLastMonthArray
		);

		const currentAndLastMonthsSubmissionsLenght =
			thisMonthRequestResult.length - 1;

		const thisMonthFilterSubmissions = [
			thisMonthRequestResult[
				currentAndLastMonthsSubmissionsLenght - thisMonthTimePeriod
			],
			thisMonthRequestResult[currentAndLastMonthsSubmissionsLenght],
		];

		setNewSubmissionsName(thisMonthFilterSubmissions);

		setCurrentMonthData(thisMonthFilterSubmissions);
	};

	const getSixMonthsFilterPeriod = async (
		sixMonthsArray,
		lastYearSixMonthsArray
	) => {
		const [
			sixMonthsSubmission,
			sixMonthsArrayOfSubmissions,
		] = await getCurrentYearData(
			sixMonthsArray,
			currentDateString[0],
			currentDateString[1],
			sixMonthsAgoDate[0],
			sixMonthsAgoDate[1]
		);

		const lastYearSixMonthsSubmissions = await getLastYearData(
			lastYearSixMonthsArray,
			currentDateString[1],
			sixMonthsAgoDate[1]
		);

		setSixMonthsTotalSubmissions(sixMonthsSubmission);

		setNewSubmissionsName(sixMonthsArrayOfSubmissions);

		setSixMonthData(sixMonthsArrayOfSubmissions);

		setLastYearSixMonthsTotalSubmissions(lastYearSixMonthsSubmissions);
	};

	const getYearToDateFilterPeriod = async (
		yearToDateArrayOfMonths,
		lastYearToDateMonthsArray
	) => {
		const [
			yearToDateTotalSubmissions,
			yearToDateArrayOfSubmissions,
		] = await getCurrentYearData(
			yearToDateArrayOfMonths,
			currentDateString[0],
			currentDateString[1],
			currentDateString[0],
			january
		);

		const lastYearToDateSubmissions = await getLastYearData(
			lastYearToDateMonthsArray,
			currentDateString[1],
			january
		);

		setYearToDateTotalOfSubmissions(yearToDateTotalSubmissions);

		setNewSubmissionsName(yearToDateArrayOfSubmissions);

		setYearToDateData(yearToDateArrayOfSubmissions);

		setLastYearToDateTotalSubmissions(lastYearToDateSubmissions);
	};

	useEffect(() => {
		const currentAndLastMonthArray = [];
		const sixMonthsArray = [];
		const lastYearSixMonthsArray = [];
		const firstUntilCurrentMonthArray = [];
		const lastYearFirstUntilCurrentMonthArray = [];

		const numberOfMonths = 12;
		const maxIndexOfMonthsArray = 11;

		const sixMonthsTimePeriod = 5;
		const indexOfCurrentMonth = new Date().getMonth();

		let indexBaseMonth = indexOfCurrentMonth - sixMonthsTimePeriod;

		indexBaseMonth =
			indexBaseMonth < 0
				? numberOfMonths + indexBaseMonth
				: indexBaseMonth;

		let month = 0;

		for (let count = 0; count <= sixMonthsTimePeriod; count++) {
			const currentAndLastMonthFilter = {};
			const sixMonthsAgoFilter = {};
			const lastYearSixMonthsAgoFilter = {};

			if (!count) {
				month = indexBaseMonth;
			}
			if (month > maxIndexOfMonthsArray) {
				month = 0;
			}

			currentAndLastMonthFilter[CONSTANTS.MONTHS_ABREVIATIONS[month]] = 0;
			sixMonthsAgoFilter[CONSTANTS.MONTHS_ABREVIATIONS[month]] = 0;
			lastYearSixMonthsAgoFilter[
				CONSTANTS.MONTHS_ABREVIATIONS[month]
			] = 0;

			currentAndLastMonthArray[count] = currentAndLastMonthFilter;
			sixMonthsArray[count] = sixMonthsAgoFilter;
			lastYearSixMonthsArray[count] = lastYearSixMonthsAgoFilter;
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
			firstUntilCurrentMonthArray[count] = firstUntilCurrentFilter;
			lastYearFirstUntilCurrentMonthArray[count] = LastsixMonthsAgoFilter;
			monthposition++;
		}

		if (selectedFilterDate === PERIOD.THIS_MONTH) {
			getThisMonthFilterPeriod(currentAndLastMonthArray);
		}

		if (selectedFilterDate === PERIOD.SIX_MONTH) {
			getSixMonthsFilterPeriod(sixMonthsArray, lastYearSixMonthsArray);
		}

		if (selectedFilterDate === PERIOD.YTD) {
			getYearToDateFilterPeriod(
				firstUntilCurrentMonthArray,
				lastYearFirstUntilCurrentMonthArray
			);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedFilterDate]);

	const percentThisMonthSubmissions = Math.round(
		((currentMonthData[2] - currentMonthData[1]) / currentMonthData[1]) *
			100
	);

	const percent6MoSubmissions = Math.round(
		((sixMonthsTotalSubmissions - lastYearSixMonthsTotalSubmissions) /
			lastYearSixMonthsTotalSubmissions) *
			100
	);

	const percentYearToDateSubmissions = Math.round(
		((yearToDateTotalOfSubmissions - lastYearToDateTotalSubmissions) /
			lastYearToDateTotalSubmissions) *
			100
	);

	const loadData = [
		{
			dataColumns: currentMonthData,
			newSubmissionsValue: currentMonthData[2],
			percentNewApplication: percentThisMonthSubmissions,
			period: 1,
		},
		{
			dataColumns: sixMonthData,
			newSubmissionsValue: sixMonthsTotalSubmissions,
			percentNewApplication: percent6MoSubmissions,
			period: 2,
		},
		{
			dataColumns: yearToDateData,
			newSubmissionsValue: yearToDateTotalOfSubmissions,
			percentNewApplication: percentYearToDateSubmissions,
			period: 3,
		},
	];

	const getData = () => {
		return loadData.filter(
			(data) => data.period === Number(selectedFilterDate)
		);
	};

	const chartData = {
		columns: [getData()[0]?.dataColumns],
		type: 'line',
	};

	const sumDataColumnsArray = () => {
		const newArray = chartData?.columns?.map((item) => {
			return item.slice(1);
		});

		return newArray[0].reduce((acc, item) => Number(acc) + Number(item), 0);
	};

	const hasDataOnChart = sumDataColumnsArray();

	const newSubmissionsPercentual = getData()[0]?.percentNewApplication;
	const newSubmissionsValue = getData()[0]?.newSubmissionsValue;
	const getPeriod = getData()[0]?.period;

	const submissionsLegend = () => (
		<div className="applications-new-submissions-legend d-flex flex-column h-100 justify-content-end mt-5">
			<div className="applications-new-submissions-screen font-weight-bolder h5 mb-3">
				{new Intl.NumberFormat().format(newSubmissionsValue)}
			</div>

			<div
				className={classNames('text-paragraph-sm font-weight-bolder', {
					'line-chart-icon-success-color':
						newSubmissionsPercentual >= 0,
					'text-danger': newSubmissionsPercentual < 0,
				})}
			>
				{newSubmissionsPercentual > 0 && (
					<ClayIcon symbol="caret-top" />
				)}

				{newSubmissionsPercentual === 0 && <ClayIcon symbol="hr" />}

				{newSubmissionsPercentual < 0 && (
					<ClayIcon symbol="caret-bottom" />
				)}

				{getPeriod === 1
					? `${
							newSubmissionsPercentual === Infinity
								? `NaN`
								: newSubmissionsPercentual
					  }% MoM`
					: `${
							newSubmissionsPercentual === Infinity
								? `NaN`
								: newSubmissionsPercentual
					  }% YoY`}
			</div>
		</div>
	);

	return (
		<div className="applications-new-submissions-container d-flex flex-column flex-shrink-0 pb-4 pt-3 px-3">
			<div className="align-items-center applications-new-submissions-header d-flex justify-content-between">
				<div className="applications-new-submissions-title font-weight-bolder h4">
					New Submissions
				</div>

				<ClaySelect
					className="applications-new-submissions-select p-1"
					onChange={({target}) => {
						setSelectedFilterDate(target.value);
					}}
					sizing="sm"
					value={selectedFilterDate}
				>
					{FilterOptions.map((option) => (
						<ClaySelect.Option
							key={option.value}
							label={option.label}
							value={option.value}
						/>
					))}
				</ClaySelect>
			</div>

			{hasDataOnChart > 0 ? (
				<LineChart
					LegendElement={submissionsLegend}
					chartData={chartData}
					getPeriod={getPeriod}
					hasPersonalizedTooltip={true}
					percentual={newSubmissionsPercentual}
					value={newSubmissionsValue}
				/>
			) : (
				<div className="align-items-center d-flex flex-grow-1 justify-content-center">
					<span>No Data</span>
				</div>
			)}
		</div>
	);
}
