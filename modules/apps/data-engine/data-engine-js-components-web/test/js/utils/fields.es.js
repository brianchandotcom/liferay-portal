/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
