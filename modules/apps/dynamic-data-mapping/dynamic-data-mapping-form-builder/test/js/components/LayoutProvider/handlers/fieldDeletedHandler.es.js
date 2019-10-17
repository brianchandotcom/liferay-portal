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

import mockPages from '../../../__mock__/mockPages.es';
import * as FormSupport from '../../../src/main/resources/META-INF/resources/js/components/Form/FormSupport.es';
import * as fieldDeletedHandler from '../../../src/main/resources/META-INF/resources/js/components/LayoutProvider/handlers/fieldDeletedHandler.es';
import RulesSupport from '../../../src/main/resources/META-INF/resources/js/components/RuleBuilder/RulesSupport.es';

describe('LayoutProvider/handlers/fieldDeletedHandler', () => {
	describe('handleFieldDeleted(state, event)', () => {
		it('calls removeRow() when row is left with no fields after delete operation', () => {
			const event = {
				columnIndex: 0,
				pageIndex: 0,
				rowIndex: 0
			};
			const state = {
				pages: mockPages,
				rules: []
			};

			const removeRowSpy = jest.spyOn(FormSupport, 'removeRow');

			removeRowSpy.mockImplementation(() => []);

			fieldDeletedHandler.handleFieldDeleted(state, event);

			expect(removeRowSpy).toHaveBeenCalled();

			removeRowSpy.mockRestore();
		});

		it('calls clearAllConditionFieldValues() when deleting a field used as the first operand of a condition', () => {
			const event = {
				columnIndex: 0,
				pageIndex: 0,
				rowIndex: 0
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
										value: 'radio'
									}
								]
							}
						]
					}
				]
			};

			const clearAllConditionFieldValuesSpy = jest.spyOn(
				RulesSupport,
				'clearAllConditionFieldValues'
			);

			clearAllConditionFieldValuesSpy.mockImplementation(() => []);

			fieldDeletedHandler.handleFieldDeleted(state, event);

			expect(clearAllConditionFieldValuesSpy).toHaveBeenCalled();

			clearAllConditionFieldValuesSpy.mockRestore();
		});
	});
});
