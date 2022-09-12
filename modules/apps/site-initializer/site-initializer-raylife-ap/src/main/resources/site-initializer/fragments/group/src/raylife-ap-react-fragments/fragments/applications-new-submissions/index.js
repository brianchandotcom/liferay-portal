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
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import LineChart from '../../../common/components/line-chart';
import {
	getLastYearSubmissions,
	getNewSubmissions,
} from '../../../common/services/Application';
import {CONSTANTS} from '../../../common/utils/constants';
import {
	currentDateString,
	january,
	lastMonthDate,
	lastYear,
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
		applicationResult.forEach((policy) => {
			const month = new Date(policy?.applicationCreateDate)
				.toGMTString()
				.split(' ')[2];

			applicationsArray.forEach((applicationElement) => {
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

		return getSubmissionsValues(monthsValue);
	};

	function getSubmissionsValues(arrayOfObjects) {
		const valuesArray = arrayOfObjects.map((values) => {
			return Object.values(values)[0];
		});

		return valuesArray;
	}

	function setNewSubmissionsName(finalArray) {
		return finalArray.unshift('New Submissions');
	}

	function sumTotalOfSubmissions(totalSubmissionsArray) {
		const totalValue = totalSubmissionsArray
			.map((values) => {
				return Object.values(values)[0];
			})
			.reduce(
				(submissionsSum, submission) => submissionsSum + submission,
				0
			);

		return totalValue;
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

	useEffect(() => {
		const currentAndLastMonthArray = [];
		const sixMonthsArray = [];
		const lastYearSixMonthsArray = [];
		const firstUntilCurrentMonthArray = [];
		const lastYearFirstUntilCurrentMonthArray = [];

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
			getNewSubmissions(
				currentDateString[0],
				currentDateString[1],
				lastMonthDate[0],
				lastMonthDate[1]
			).then((results) => {
				const thisMonthSubmission = getSubmissionsResult(
					results,
					currentAndLastMonthArray
				);

				const currentAndLastMonthsSubmissionsLenght =
					thisMonthSubmission.length - 1;

				const thisMonthFilterSubmissions = [
					thisMonthSubmission[
						currentAndLastMonthsSubmissionsLenght -
							thisMonthTimePeriod
					],
					thisMonthSubmission[currentAndLastMonthsSubmissionsLenght],
				];

				setNewSubmissionsName(thisMonthFilterSubmissions);

				setCurrentMonthData(thisMonthFilterSubmissions);
			});
		}

		if (selectedFilterDate === PERIOD.SIX_MONTH) {
			getNewSubmissions(
				currentDateString[0],
				currentDateString[1],
				sixMonthsAgoDate[0],
				sixMonthsAgoDate[1]
			).then((response) => {
				const sixMonthsResult = getSubmissionsResult(
					response,
					sixMonthsArray
				);

				const sixMonthsAgoValues = populateSubmissions(
					sixMonthsResult,
					sixMonthsArray
				);

				const sixMonthsSubmission = sumTotalOfSubmissions(
					sixMonthsAgoValues
				);

				const sixMonthsArrayOfSubmissions = getSubmissionsValues(
					sixMonthsAgoValues
				);

				setSixMonthsTotalSubmissions(sixMonthsSubmission);

				setNewSubmissionsName(sixMonthsArrayOfSubmissions);

				setSixMonthData(sixMonthsArrayOfSubmissions);

				getLastYearSubmissions(
					lastYear.toString(),
					currentDateString[1],
					lastYear.toString(),
					sixMonthsAgoDate[1]
				).then((results) => {
					const lastYearSixMonthsDataResults = getSubmissionsResult(
						results,
						lastYearSixMonthsArray
					);

					const lastYearSixMonthsAgo = populateSubmissions(
						lastYearSixMonthsDataResults,
						lastYearSixMonthsArray
					);

					const lastYearSixMonthsSubmissions = sumTotalOfSubmissions(
						lastYearSixMonthsAgo
					);

					setLastYearSixMonthsTotalSubmissions(
						lastYearSixMonthsSubmissions
					);
				});
			});
		}

		if (selectedFilterDate === PERIOD.YTD) {
			getNewSubmissions(
				currentDateString[0],
				currentDateString[1],
				currentDateString[0],
				january
			).then((response) => {
				const yearToDateMonthsResult = getSubmissionsResult(
					response,
					firstUntilCurrentMonthArray
				);

				const yearToDateMonthsAgo = populateSubmissions(
					yearToDateMonthsResult,
					firstUntilCurrentMonthArray
				);

				const yearToDateTotalSubmissions = sumTotalOfSubmissions(
					yearToDateMonthsAgo
				);

				const yearToDateArrayOfSubmissions = getSubmissionsValues(
					yearToDateMonthsAgo
				);

				setYearToDateTotalOfSubmissions(yearToDateTotalSubmissions);

				setNewSubmissionsName(yearToDateArrayOfSubmissions);

				setYearToDateData(yearToDateArrayOfSubmissions);

				getLastYearSubmissions(
					lastYear.toString(),
					currentDateString[1],
					lastYear.toString(),
					january
				).then((results) => {
					const lastYearToDateDataResults = getSubmissionsResult(
						results,
						lastYearFirstUntilCurrentMonthArray
					);

					const lastYearToDateMonthsAgo = populateSubmissions(
						lastYearToDateDataResults,
						lastYearFirstUntilCurrentMonthArray
					);

					const lastYearToDateSubmissions = sumTotalOfSubmissions(
						lastYearToDateMonthsAgo
					);

					setLastYearToDateTotalSubmissions(
						lastYearToDateSubmissions
					);
				});
			});
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

			{!!chartData.columns.length && (
				<LineChart
					LegendElement={submissionsLegend}
					chartData={chartData}
					getPeriod={getPeriod}
					hasPersonalizedTooltip={true}
					percentual={newSubmissionsPercentual}
					value={newSubmissionsValue}
				/>
			)}

			{!chartData.columns.length && loadData && (
				<div className="align-items-center d-flex flex-grow-1 justify-content-center">
					<span>No Data Applications</span>
				</div>
			)}
		</div>
	);
}
