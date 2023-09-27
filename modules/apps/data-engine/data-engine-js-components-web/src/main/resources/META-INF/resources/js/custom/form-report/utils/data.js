/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const getColumnLabel = (column, field) => {
	return field.columns[column] ? field.columns[column].value : undefined;
};

const getPercentage = (count, totalEntries) => count / totalEntries;

const removeEmptyValues = (values) =>
	Array.isArray(values) && values.filter((value) => value);

const roundPercentage = (value) => `${parseFloat((value * 100).toFixed(1))}%`;

const sumTotalEntries = (values) =>
	Object.values(values).reduce((acc, value) => acc + value, 0);

const toArray = (values) => values.map(({value}) => value);

const toDataArray = (options, values) =>
	Object.entries(values)
		.map(([name, count]) => ({
			count,
			label: options[name] ? options[name].value : name,
		}))
		.sort((a, b) => (a.count > b.count ? -1 : b.count > a.count ? 1 : 0));

export default toDataArray;
export {
	getColumnLabel,
	getPercentage,
	removeEmptyValues,
	roundPercentage,
	sumTotalEntries,
	toArray,
};
