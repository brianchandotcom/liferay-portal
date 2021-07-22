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

import fieldDeletedHandler from '../../../../src/main/resources/META-INF/resources/js/core/utils/fieldDeletedHandler';
import * as FormSupport from '../../../../src/main/resources/META-INF/resources/js/utils/FormSupport.es';
import * as RulesSupport from '../../../../src/main/resources/META-INF/resources/js/utils/rulesSupport';
import mockPages from '../../__mock__/mockPages.es';

describe('core/utils/fieldDeletedHandler', () => {
	describe('fieldDeletedHandler(state, event)', () => {
		it('calls removeEmptyRows() when row is left with no fields after delete operation', () => {
			const event = {
				activePage: 0,
				fieldName: 'radio',
				removeEmptyRows: true,
			};
			const state = {
				pages: mockPages,
				rules: [],
			};

			const removeEmptyRowsSpy = jest.spyOn(
				FormSupport,
				'removeEmptyRows'
			);

			removeEmptyRowsSpy.mockImplementation(() => []);

			fieldDeletedHandler({}, state, event);

			expect(removeEmptyRowsSpy).toHaveBeenCalled();

			removeEmptyRowsSpy.mockRestore();
		});

		it('do not call formatRules when moving a field', () => {
			const event = {
				activePage: 0,
				editRule: false,
				fieldName: 'text1',
				removeEmptyRows: true,
			};
			const state = {
				pages: mockPages,
				rules: [
					{
						actions: [],
						conditions: [
							{
								operands: [
									{
										value: 'text1',
									},
								],
							},
						],
					},
				],
			};

			const formatRulesSpy = jest.spyOn(RulesSupport, 'formatRules');

			formatRulesSpy.mockImplementation(() => []);

			fieldDeletedHandler({}, state, event);

			expect(formatRulesSpy).not.toHaveBeenCalled();

			formatRulesSpy.mockRestore();
		});
	});
});
