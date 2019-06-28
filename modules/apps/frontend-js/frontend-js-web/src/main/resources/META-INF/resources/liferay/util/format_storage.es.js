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

import {isNumber} from 'metal';

const DEFAULT_OPTIONS = {
	addSpaceBeforeSuffix: false,
	decimalSeparator: '.',
	denominator: 1024.0,
	suffixGB: 'GB',
	suffixKB: 'KB',
	suffixMB: 'MB'
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
		suffixMB
	} = {
		...DEFAULT_OPTIONS,
		...options
	};

	if (!isNumber(size)) {
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
