/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getSortedFrontendTokenValues} from '../../../src/main/resources/META-INF/resources/js/style-book-editor/utils/getSortedFrontendTokenValues';

const DEFINITIONS = [
	{id: 'global', priority: 100},
	{id: 'theme', priority: 300},
];

const DEFAULT_PRIORITY = 300;

describe('getSortedFrontendTokenValues', () => {
	it('sorts global values before theme values', () => {
		const result = getSortedFrontendTokenValues(
			{
				a: {
					cssVariableMapping: 'color-1',
					tokenDefinitionId: 'theme',
					value: 'red',
				},
				b: {
					cssVariableMapping: 'color-2',
					tokenDefinitionId: 'global',
					value: 'blue',
				},
			},
			DEFINITIONS,
			DEFAULT_PRIORITY
		);

		expect(result[0].tokenDefinitionId).toBe('global');
		expect(result[1].tokenDefinitionId).toBe('theme');
	});

	it('applies defaultPriority to values without tokenDefinitionId', () => {
		const result = getSortedFrontendTokenValues(
			{
				a: {
					cssVariableMapping: 'color-1',
					tokenDefinitionId: 'global',
					value: 'blue',
				},
				b: {cssVariableMapping: 'color-2', value: 'red'},
			},
			DEFINITIONS,
			DEFAULT_PRIORITY
		);

		expect(result[0].tokenDefinitionId).toBe('global');
		expect(result[1].value).toBe('red');
	});

	it('returns empty array for empty values', () => {
		expect(
			getSortedFrontendTokenValues({}, DEFINITIONS, DEFAULT_PRIORITY)
		).toEqual([]);
	});

	it('returns all values when definitions list is empty', () => {
		const result = getSortedFrontendTokenValues(
			{
				a: {
					cssVariableMapping: 'color-1',
					tokenDefinitionId: 'theme',
					value: 'red',
				},
			},
			[],
			DEFAULT_PRIORITY
		);

		expect(result).toHaveLength(1);
	});
});
