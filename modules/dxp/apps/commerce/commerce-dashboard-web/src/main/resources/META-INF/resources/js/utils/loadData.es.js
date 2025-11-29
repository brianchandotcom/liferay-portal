/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable require-jsdoc */
export const NULL_VALUE = 1.4e-45;
export const headers = {
	'Content-Type': 'application/json',
};

export function byDate(a, b) {
	return new Date(a.timestamp) - new Date(b.timestamp);
}

export function byName(a, b) {
	return a.name.localeCompare(b.name);
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

export function getDateString(ts) {
	return (ts || '').slice(0, 10);
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

export function responseToJson(response) {
	return response.json();
}

export function sortPointsByDate(points = []) {
	return points.sort(byDate);
}
