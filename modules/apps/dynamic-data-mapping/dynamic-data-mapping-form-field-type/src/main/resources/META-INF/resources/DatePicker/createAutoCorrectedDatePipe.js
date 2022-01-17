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

/**
 * This code is from https://github.com/text-mask/text-mask
 * https://github.com/text-mask/text-mask/blob/master/addons/src/createAutoCorrectedDatePipe.js
 */

const maxValueMonth = [31, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
const formatOrder = ['yyyy', 'yy', 'mm', 'dd', 'HH', 'MM', 'SS', 'hh'];
export function createAutoCorrectedDatePipe(
	dateFormat = 'mm dd yyyy',
	{maxYear = 9999, minYear = 1} = {}
) {
	const dateFormatArray = dateFormat
		.split(/[^dhmyHMS]+/)
		.sort((a, b) => formatOrder.indexOf(a) - formatOrder.indexOf(b));

	return function (conformedValue) {
		const indexesOfPipedChars = [];
		const maxValue = {
			HH: 23,
			MM: 59,
			SS: 59,
			dd: 31,
			hh: 12,
			mm: 12,
			yy: 99,
			yyyy: maxYear,
		};
		const minValue = {
			HH: 0,
			MM: 0,
			SS: 0,
			dd: 1,
			hh: 1,
			mm: 1,
			yy: 0,
			yyyy: minYear,
		};
		const conformedValueArr = conformedValue.split('');

		// Check first digit

		dateFormatArray.forEach((format) => {
			const position = dateFormat.indexOf(format);
			const maxFirstDigit = parseInt(
				maxValue[format].toString().substr(0, 1),
				10
			);

			if (parseInt(conformedValueArr[position], 10) > maxFirstDigit) {
				conformedValueArr[position + 1] = conformedValueArr[position];
				conformedValueArr[position] = 0;
				indexesOfPipedChars.push(position);
			}
		});

		// Check for invalid date

		let month = 0;
		const isInvalid = dateFormatArray.some((format) => {
			const position = dateFormat.indexOf(format);
			const length = format.length;
			const textValue = conformedValue
				.substr(position, length)
				.replace(/\D/g, '');
			const value = parseInt(textValue, 10);
			if (format === 'mm') {
				month = value || 0;
			}
			const maxValueForFormat =
				format === 'dd' ? maxValueMonth[month] : maxValue[format];
			if (format === 'yyyy' && (minYear !== 1 || maxYear !== 9999)) {
				const scopedMaxValue = parseInt(
					maxValue[format].toString().substring(0, textValue.length),
					10
				);
				const scopedMinValue = parseInt(
					minValue[format].toString().substring(0, textValue.length),
					10
				);

				return value < scopedMinValue || value > scopedMaxValue;
			}

			return (
				value > maxValueForFormat ||
				(textValue.length === length && value < minValue[format])
			);
		});

		if (isInvalid) {
			return false;
		}

		return {
			indexesOfPipedChars,
			value: conformedValueArr.join(''),
		};
	};
}
