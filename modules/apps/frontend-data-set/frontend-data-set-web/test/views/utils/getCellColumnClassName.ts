/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCellColumnClassName from '../../../src/main/resources/META-INF/resources/views/utils/getCellColumnClassName';

describe('getCellColumnClassName utility', () => {
	it('returns a string composed by a prefix and the cell name when it receives a string', () => {
		const given = 'name';
		const expected = `cell-${given}`;

		expect(getCellColumnClassName(given)).toEqual(expected);
	});

	it('returns a string composed by a prefix and the cell name when it receives an array of string plus "LANG"', () => {
		const given = ['name', 'LANG'];
		const expected = `cell-${given[0]}`;

		expect(getCellColumnClassName(given)).toEqual(expected);
	});

	it('returns a string composed by a prefix and the cell name separated by hyphens when it receives an array of strings', () => {
		const given = ['catalog', 'name'];
		const expected = `cell-${given[0]}-${given[1]}`;

		expect(getCellColumnClassName(given)).toEqual(expected);
	});

	it('returns a string composed by a prefix and the cell name, replacing non alphanumeric characters by hyphens', () => {
		const given = ['catalog/name'];
		const expected = `cell-catalog-name`;

		expect(getCellColumnClassName(given)).toEqual(expected);
	});
});
