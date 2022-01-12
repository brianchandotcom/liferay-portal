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

export function countCompletedFields(fields) {
	let count = 0;
	const values = Object.values(fields);
	const objKey = '_f';

	values.forEach((value) => {
		if (value.hasOwnProperty(objKey)) {
			if (
				value[objKey].required &&
				value[objKey].value &&
				value[objKey].value !== ''
			) {
				count += 1;
			}
		}
		else {
			count += countCompletedFields(value);
		}
	});

	return count;
}

/**
 * @param {number} current - Current value (represents some percentage of a total)
 * @param {number} total - Total value (represents 100%)
 * @returns {number} Percentage
 */
export function calculatePercentage(current, total) {
	if (current > total) {
		return 100;
	}

	return (current * 100) / total;
}

/**
 * @param {number} radius - Circle radius
 * @returns {number} Circumference
 */
export function calculateCircumference(radius) {
	return radius * 2 * Math.PI;
}

/**
 * @param {number} percent - Current percentage
 * @param {number} circumference - Circumference
 * @returns {number} Circumference Offset
 */
export function calculateOffset(percent, circumference) {
	return circumference - (percent / 100) * circumference;
}

export function toSlug(str) {
	str = str.replace(/^\s+|\s+$/g, '');
	str = str.toLowerCase();

	// remove accents, swap ñ for n, etc

	var from = 'àáäâèéëêìíïîòóöôùúüûñç·/_,:;';
	var to = 'aaaaeeeeiiiioooouuuunc------';
	for (var i = 0, l = from.length; i < l; i++) {
		str = str.replace(new RegExp(from.charAt(i), 'g'), to.charAt(i));
	}

	str = str
		.replace(/[^a-z0-9 -]/g, '')
		.replace(/\s+/g, '-')
		.replace(/-+/g, '-');

	return str;
}
