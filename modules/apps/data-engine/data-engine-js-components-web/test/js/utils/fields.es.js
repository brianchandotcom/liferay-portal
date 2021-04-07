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

import {normalizeFieldName} from '../../../src/main/resources/META-INF/resources/js/utils/fields.es';

describe('Fields', () => {
	describe('normalizeFieldName', () => {
		it('adds underline at the beginning of the string if it starts with a number', () => {
			const normalizedFieldName = normalizeFieldName('123FieldName');

			expect(normalizedFieldName).toEqual('_123FieldName');
		});

		it('removes invalids characters', () => {
			const normalizedFieldName = normalizeFieldName('Field¿êName_');

			expect(normalizedFieldName).toEqual('FieldName_');
		});

		it('removes the space character', () => {
			const normalizedFieldName = normalizeFieldName('Field name');

			expect(normalizedFieldName).toEqual('FieldName');
		});
	});
});
