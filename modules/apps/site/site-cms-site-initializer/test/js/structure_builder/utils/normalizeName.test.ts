/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import normalizeName from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/normalizeName';

describe('normalizeName', () => {
	it('Normalizes name to camelCase when style is camel', () => {
		const result = normalizeName('hello-world', {style: 'camel'});
		expect(result).toBe('helloWorld');
	});

	it('Normalizes name to PascalCase when style is pascal', () => {
		const result = normalizeName('hello-world', {style: 'pascal'});
		expect(result).toBe('HelloWorld');
	});

	it('Only removes spaces and special characters if no style is provided', () => {
		const result = normalizeName('hello-world');
		expect(result).toBe('helloworld');
	});

	it('Handles empty name input', () => {
		const result = normalizeName('');
		expect(result).toBe('');
	});

	it('Handles names with multiple spaces and dashes', () => {
		const result = normalizeName('hello---world---test', {style: 'camel'});
		expect(result).toBe('helloWorldTest');
	});
});
