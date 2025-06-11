/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {convertArrayToFormattedString} from '../../../../src/main/resources/META-INF/resources/js/main/util/convertArrayToFormattedString';

describe('convertArrayToFormattedString', () => {
	it('return an empty string for an empty array', () => {
		expect(convertArrayToFormattedString([])).toBe('');
	});

	it('return the string itself for a single-element array', () => {
		expect(convertArrayToFormattedString(['First Item'])).toBe(
			'First Item'
		);
	});

	it('return formatted string when have two items with "and" without a preceding comma', () => {
		expect(
			convertArrayToFormattedString(['First Item', 'Second Item'])
		).toBe('First Item and Second Item');
	});

	it('return formatted string when have three items with commas and "and"', () => {
		expect(
			convertArrayToFormattedString([
				'First Item',
				'Second Item',
				'Third Item',
			])
		).toBe('First Item, Second Item, and Third Item');
	});

	it('return formatted string when have multiple items with commas and "and"', () => {
		expect(
			convertArrayToFormattedString([
				'First Item',
				'Second Item',
				'Third Item',
				'Fourth Item',
			])
		).toBe('First Item, Second Item, Third Item, and Fourth Item');
	});
});
