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

const removeEmptyValues = (values) =>
	Array.isArray(values) && values.filter((value) => value);

const roundPercentage = (value) => `${Math.trunc(value * 1000) / 10}%`;

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
export {removeEmptyValues, roundPercentage, sumTotalEntries, toArray};
