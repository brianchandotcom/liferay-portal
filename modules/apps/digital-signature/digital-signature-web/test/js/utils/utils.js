/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	concatValues,
	isEqualObjects,
} from '../../../src/main/resources/META-INF/resources/js/utils/utils';

describe('Utils', () => {
	describe('concatValues', () => {
		it('call with 1 values', () => {
			const result = concatValues(['Product Menu']);

			expect(result).toBe('Product Menu');
		});

		it('call with 2 items values', () => {
			const result = concatValues(['Product Menu', 'Standalone']);

			expect(result).toBe('Product Menu and Standalone');
		});

		it('call with 2 more items values', () => {
			const result = concatValues([
				'Product Menu',
				'Standalone',
				'Widget',
			]);

			expect(result).toBe('Product Menu, Standalone and Widget');
		});
	});

	describe('isEqualObjects', () => {
		it('call without args', () => {
			expect(isEqualObjects()).toBeFalsy();
		});

		it('call with non objects', () => {
			expect(isEqualObjects(1, 2)).toBeFalsy();
		});

		it('call with different objects', () => {
			expect(isEqualObjects({propA: 1}, {propB: 2})).toBeFalsy();
		});

		it('call with equal objects', () => {
			const object = {propA: 1, propB: 2};

			expect(isEqualObjects(object, object)).toBeTruthy();
		});
	});
});
