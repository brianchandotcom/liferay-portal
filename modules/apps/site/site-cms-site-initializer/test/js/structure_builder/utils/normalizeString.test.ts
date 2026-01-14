/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import normalizeString from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/normalizeString';

describe('normalizeString', () => {
	it('Normalizes name to camelCase when style is camel', () => {
		const result = normalizeString('hello-world', {style: 'camel'});
		expect(result).toBe('helloWorld');
	});

	it('Normalizes name to PascalCase when style is pascal', () => {
		const result = normalizeString('hello-world', {style: 'pascal'});
		expect(result).toBe('HelloWorld');
	});

	it('Only removes spaces and special characters if no style is provided', () => {
		const result = normalizeString('hello-world');
		expect(result).toBe('helloworld');
	});

	it('Handles empty name input', () => {
		const result = normalizeString('');
		expect(result).toBe('');
	});

	it('Handles names with multiple spaces and dashes', () => {
		const result = normalizeString('hello---world---test', {
			style: 'camel',
		});
		expect(result).toBe('helloWorldTest');
	});
});
