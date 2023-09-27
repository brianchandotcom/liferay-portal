/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * This code is from https://github.com/text-mask/text-mask
 * https://github.com/text-mask/text-mask/blob/master/addons/src/createAutoCorrectedDatePipe.js
 */
type MaxOrMinValue = {
	HH: number;
	MM: number;
	SS: number;
	dd: number;
	hh: number;
	mm: number;
	yy: number;
	yyyy: number;
};

type conformedValue = string | number;

type indexOfPipedChars = string | number;

const maxValueMonth = [31, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
const formatOrder = ['yyyy', 'yy', 'mm', 'dd', 'HH', 'MM', 'SS', 'hh'];
export function createAutoCorrectedDatePipe(
	dateFormat = 'mm dd yyyy',
	{maxYear = 9999, minYear = 1} = {}
) {
	const dateFormatArray = dateFormat
		.split(/[^dhmyHMS]+/)
		.sort((a, b) => formatOrder.indexOf(a) - formatOrder.indexOf(b));

	return function (conformedValue: string) {
		const indexesOfPipedChars: indexOfPipedChars[] = [];
		const maxValue: MaxOrMinValue = {
			HH: 23,
			MM: 59,
			SS: 59,
			dd: 31,
			hh: 12,
			mm: 12,
			yy: 99,
			yyyy: maxYear,
		};
		const minValue: MaxOrMinValue = {
			HH: 0,
			MM: 0,
			SS: 0,
			dd: 1,
			hh: 1,
			mm: 1,
			yy: 0,
			yyyy: minYear,
		};
		const conformedValueArr: conformedValue[] = conformedValue.split('');

		// Check first digit

		dateFormatArray.forEach((format) => {
			const position = dateFormat.indexOf(format);
			const maxFirstDigit = parseInt(
				maxValue[format as keyof MaxOrMinValue]
					.toString()
					.substring(0, 1),
				10
			);

			// Verify if its a string and convert if necessary

			const conformedValue =
				typeof conformedValueArr[position] === 'string'
					? parseInt(conformedValueArr[position] as string, 10)
					: conformedValueArr[position];

			if (conformedValue > maxFirstDigit) {
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
			const offset = position + length;
			const textValue = conformedValue
				.substring(position, offset)
				.replace(/\D/g, '');
			const value = parseInt(textValue, 10);
			if (format === 'mm') {
				month = value || 0;
			}
			const maxValueForFormat =
				format === 'dd'
					? maxValueMonth[month]
					: maxValue[format as keyof MaxOrMinValue];
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
				(textValue.length === length &&
					value < minValue[format as keyof MaxOrMinValue])
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
