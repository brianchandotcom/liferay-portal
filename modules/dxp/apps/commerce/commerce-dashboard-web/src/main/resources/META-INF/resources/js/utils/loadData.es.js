/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

/* eslint-disable require-jsdoc */
import {fetch} from 'frontend-js-web';

export const NULL_VALUE = 1.4e-45;
const headers = {
	'Content-Type': 'application/json',
};

function byDate(a, b) {
	return new Date(a.timestamp) - new Date(b.timestamp);
}

function byName(a, b) {
	return a.name.localeCompare(b.name);
}

export function formatCategoriesForChart(categories) {
	return categories.reduce(
		(a, c) => Object.assign(a, {[`${c.id}`]: c.name}),
		{}
	);
}

export function formatDataForChart(points = []) {
	const categories = getCategoriesArray(points);

	return {
		axis: {
			x: {
				type: 'timeseries',
			},
		},
		data: {
			columns: formatPointsForChart(points, categories),
			names: formatCategoriesForChart(categories),
			type: 'predictive',
			types: getLineTypesForChart(categories),
			x: 'x',
		},
		predictionDate: getPredictionDate(points),
	};
}

export function formatPointForForecast(point) {
	return point.actual !== NULL_VALUE
		? point.actual
		: {
				high: point.forecastUpperBound,
				low: point.forecastLowerBound,
				mid: point.forecast,
		  };
}

export function formatPointsForChart(points, categories) {
	return !points.length
		? []
		: [
				['x', ...getDates(points)],
				...categories.map((c) => [
					`${c.id}`,
					...getValuesForCategory(points, c),
				]),
		  ];
}

export function getCategoriesArray(items = []) {
	return [
		...items
			.reduce((a, c) => {
				a.set(c.category, c.categoryTitle);

				return a;
			}, new Map())
			.entries(),
	]
		.map(([id, name]) => ({
			id,
			name,
		}))
		.sort(byName);
}

export function getDates(points) {
	return points
		.filter(isPartOfCategory(points[0].category))
		.map((d) => getDateString(d.timestamp));
}

export function getDateString(ts) {
	return (ts || '').slice(0, 10);
}

export function getLineTypesForChart(categories) {
	return categories.reduce(
		(a, c) => Object.assign(a, {[`${c.id}`]: 'area-line-range'}),
		{}
	);
}

export function getPoints({items}) {
	return items;
}

export function getPredictionDate(points) {
	return !points.length
		? null
		: getDateString(
				points
					.slice()
					.reverse()
					.find((d) => d.actual !== NULL_VALUE).timestamp
		  );
}

export function hasNoActualNorForecastValue({actual, forecast}) {
	return !(actual === NULL_VALUE && forecast === NULL_VALUE);
}

export function getValuesForCategory(points, category) {
	return points
		.filter(isPartOfCategory(category.id))
		.filter(hasNoActualNorForecastValue)
		.map(formatPointForForecast);
}

export function isPartOfCategory(id) {
	return ({category}) => category === id;
}

export function loadData(url) {
	return parseData(
		fetch(url, {
			headers,
		}).then(responseToJson)
	);
}

export function parseData(loadData) {
	return loadData
		.then(getPoints)
		.then(sortPointsByDate)
		.then(formatDataForChart);
}

function responseToJson(response) {
	return response.json();
}

function sortPointsByDate(points = []) {
	return points.sort(byDate);
}
