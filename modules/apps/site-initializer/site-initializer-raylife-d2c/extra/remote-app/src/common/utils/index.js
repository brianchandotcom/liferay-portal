/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	const from = 'àáäâèéëêìíïîòóöôùúüûñç·/_,:;';
	const to = 'aaaaeeeeiiiioooouuuunc------';
	for (let i = 0, l = from.length; i < l; i++) {
		str = str.replace(new RegExp(from.charAt(i), 'g'), to.charAt(i));
	}

	str = str
		.replace(/[^a-z0-9 -]/g, '')
		.replace(/\s+/g, '-')
		.replace(/-+/g, '-');

	return str;
}
