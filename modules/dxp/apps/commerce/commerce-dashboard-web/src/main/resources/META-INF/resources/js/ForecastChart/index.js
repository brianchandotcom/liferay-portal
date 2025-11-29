/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable require-jsdoc */
import {fetch} from 'frontend-js-web';

import {
	NULL_VALUE,
	getCategoriesArray,
	getDateString,
	getPoints,
	getPredictionDate,
	hasNoActualNorForecastValue,
	headers,
	responseToJson,
	sortPointsByDate,
} from '../utils/loadData.es';

function formatCategoriesForChart(categories) {
	const totalColors = categories.length;
	let lastHue = null;

	const coloredCategories = categories.map((c) => {
		const color = getNextHue(lastHue, totalColors);
		lastHue = color;

		return {
			color: `hsl(${Math.round(color)}, 50%, 60%)`,
			id: c.id,
			name: c.name,
		};
	});

	return coloredCategories.reduce(
		(acc, curr) =>
			Object.assign(acc, {
				[`${curr.id}`]: {
					color: curr.color,
					name: curr.name,
				},
			}),
		{}
	);
}

function formatDataForChart(points = []) {
	const dataByTimestamp = mergeDataByTimestamp(points);

	return {
		categories: formatCategoriesForChart(getCategoriesArray(points)),
		columns: Object.keys(dataByTimestamp).map((timestamp) => {
			return {
				values: dataByTimestamp[timestamp].reduce((acc, point) => {
					acc[point.category] = formatPointForForecast(point);

					return acc;
				}, {}),
				xAxisName: timestamp,
			};
		}),
		predictionDate: getPredictionDate(points),
	};
}

function formatPointForForecast(point) {
	return point.actual !== NULL_VALUE
		? {
				high: point.actual,
				low: point.actual,
				mid: point.actual,
			}
		: {
				high: point.forecastUpperBound,
				low: point.forecastLowerBound,
				mid: point.forecast,
			};
}

function getNextHue(lastHue, totalColors) {
	if (totalColors === 0) {
		return 0;
	}

	const step = 360 / totalColors;
	let newHue = '';

	if (lastHue === null) {
		newHue = Math.floor(Math.random() * 360);
	}
	else {
		newHue = (lastHue + step) % 360;
	}

	return Math.round(newHue);
}

export async function loadData(url) {
	return parseData(
		fetch(url, {
			headers,
		}).then(responseToJson)
	);
}

async function parseData(loadData) {
	return loadData
		.then(getPoints)
		.then((points) => points.filter(hasNoActualNorForecastValue))
		.then(sortPointsByDate)
		.catch(() => [])
		.then(formatDataForChart);
}

function mergeDataByTimestamp(points = []) {
	return points.reduce((acc, curr) => {
		const timestamp = getDateString(curr.timestamp);

		acc[timestamp] = [...(acc[timestamp] || []), curr];

		return acc;
	}, {});
}
