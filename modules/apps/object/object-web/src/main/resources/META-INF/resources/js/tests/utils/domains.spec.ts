/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {domainsToItems, itemsToDomains} from '../../utils/domains';

describe('domainsToItems', () => {
	it('converts a comma-separated string into label/value items', () => {
		expect(domainsToItems('gmail.com,outlook.com')).toEqual([
			{label: 'gmail.com', value: 'gmail.com'},
			{label: 'outlook.com', value: 'outlook.com'},
		]);
	});

	it('trims whitespace around each domain', () => {
		expect(domainsToItems(' gmail.com , outlook.com ')).toEqual([
			{label: 'gmail.com', value: 'gmail.com'},
			{label: 'outlook.com', value: 'outlook.com'},
		]);
	});

	it('filters out empty segments from consecutive commas', () => {
		expect(domainsToItems('gmail.com,,outlook.com')).toEqual([
			{label: 'gmail.com', value: 'gmail.com'},
			{label: 'outlook.com', value: 'outlook.com'},
		]);
	});

	it('returns an empty array for an empty string', () => {
		expect(domainsToItems('')).toEqual([]);
	});

	it('returns an empty array for a whitespace-only string', () => {
		expect(domainsToItems('   ')).toEqual([]);
	});

	it('handles a single domain without trailing comma', () => {
		expect(domainsToItems('liferay.com')).toEqual([
			{label: 'liferay.com', value: 'liferay.com'},
		]);
	});
});

describe('itemsToDomains', () => {
	it('joins items into a comma-separated string', () => {
		const items = [
			{label: 'gmail.com', value: 'gmail.com'},
			{label: 'outlook.com', value: 'outlook.com'},
		];

		expect(itemsToDomains(items)).toBe('gmail.com,outlook.com');
	});

	it('returns an empty string for an empty array', () => {
		expect(itemsToDomains([])).toBe('');
	});

	it('returns a single value without a trailing comma', () => {
		expect(
			itemsToDomains([{label: 'liferay.com', value: 'liferay.com'}])
		).toBe('liferay.com');
	});
});
