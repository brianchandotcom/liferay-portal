/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const DEFAULT_OPTIONS = {
	addSpaceBeforeSuffix: false,
	decimalSeparator: '.',
	denominator: 1024.0,
	suffixGB: 'GB',
	suffixKB: 'KB',
	suffixMB: 'MB',
};

/**
 * Returns storage number formatted as a String
 * @param {!Number} size Storage size to be formatted
 * @param {Object} options Object representing optional parameters for
 * formatting storage size
 * @return {String} formattedStorage Final formatted storage outputted as a String
 * @review
 */
export default function formatStorage(size, options = {}) {
	const {
		addSpaceBeforeSuffix,
		decimalSeparator,
		denominator,
		suffixGB,
		suffixKB,
		suffixMB,
	} = {
		...DEFAULT_OPTIONS,
		...options,
	};

	if (typeof size !== 'number') {
		throw new TypeError('Parameter size must be a number');
	}

	let decimalPlaces = 0;
	let suffix = suffixKB;

	size /= denominator;

	if (size >= denominator) {
		suffix = suffixMB;

		size /= denominator;
		decimalPlaces = 1;
	}

	if (size >= denominator) {
		suffix = suffixGB;

		size /= denominator;
		decimalPlaces = 1;
	}

	let fixedSize = size.toFixed(decimalPlaces);

	if (decimalSeparator !== '.') {
		fixedSize = fixedSize.replace(/\./, decimalSeparator);
	}

	return fixedSize + (addSpaceBeforeSuffix ? ' ' : '') + suffix;
}
