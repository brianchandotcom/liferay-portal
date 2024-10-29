/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getMinQuantity(
	minQuantity = 1,
	multipleQuantity = 1,
	precision = 0
) {
	const minDifference = minQuantity % multipleQuantity;

	if (!minDifference) {
		if (multipleQuantity <= minQuantity) {
			return minQuantity.toFixed(precision);
		}

		return multipleQuantity.toFixed(precision);
	}

	return Number(minQuantity + multipleQuantity - minDifference).toFixed(
		precision
	);
}

export function getMultipleQuantity(
	incrementalOrderQuantity = 1,
	multipleQuantity = 1,
	precision = 0
) {
	let precisionAdjustment = 0;

	if (!Number.isInteger(incrementalOrderQuantity)) {
		precisionAdjustment = incrementalOrderQuantity
			.toString()
			.split('.')[1].length;
	}

	if (!Number.isInteger(multipleQuantity)) {
		const multipleAdjustment = multipleQuantity
			.toString()
			.split('.')[1].length;
		precisionAdjustment = Math.max(precisionAdjustment, multipleAdjustment);
	}

	if (precisionAdjustment > 0) {
		const scale = Math.pow(10, precisionAdjustment);
		incrementalOrderQuantity *= scale;
		multipleQuantity *= scale;
	}

	const small = Math.min(incrementalOrderQuantity, multipleQuantity);
	const large = Math.max(incrementalOrderQuantity, multipleQuantity);

	let multiple = large;
	while (multiple % small !== 0) {
		multiple += large;
	}

	const result = multiple / Math.pow(10, precisionAdjustment);

	return Number(result.toFixed(precision));
}

export function getProductMaxQuantity(
	maxQuantity,
	multipleQuantity = 1,
	precision = 0
) {
	if (!maxQuantity) {
		return '';
	}

	const maxDifference = maxQuantity % multipleQuantity;

	if (!maxDifference) {
		return maxQuantity.toFixed(precision);
	}

	return Number(maxQuantity - maxDifference).toFixed(precision);
}

export function getProductMinQuantity({
	allowedOrderQuantities,
	minOrderQuantity,
	multipleOrderQuantity,
}) {
	let minQuantity;

	if (allowedOrderQuantities.length) {
		minQuantity = Math.min(...allowedOrderQuantities);
	}
	else {
		minQuantity = getMinQuantity(minOrderQuantity, multipleOrderQuantity);
	}

	return minQuantity;
}

export function getNumberOfDecimals(value) {
	if (value && Math.floor(value) !== Math.ceil(value)) {
		return value.toString().split('.')[1].length || 0;
	}

	return 0;
}

export function isMultiple(value1, value2, precision = 0) {
	return (
		(Math.round(value1 / value2) / (1 / value2)).toFixed(precision) ===
		Number(value1).toFixed(precision)
	);
}
