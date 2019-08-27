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

import {
	renameFieldInsideExpression,
	updateRulesFieldName
} from '../../../src/main/resources/META-INF/resources/js/components/LayoutProvider/util/rules.es';

describe('LayoutProvider/util/rules', () => {
	describe('renameFieldInsideExpression(expression, fieldName, newFieldName)', () => {
		it('renames a field name used inside an expression', () => {
			const expression = '2*[FieldName1]+sum([FieldName2])';

			expect(
				renameFieldInsideExpression(
					expression,
					'FieldName1',
					'NewFieldName'
				)
			).toEqual('2*[NewFieldName]+sum([FieldName2])');
			expect(
				renameFieldInsideExpression(
					expression,
					'FieldName2',
					'NewFieldName'
				)
			).toEqual('2*[FieldName1]+sum([NewFieldName])');
		});
	});

	describe('updateRulesFieldName(rules, fieldName, newFieldName)', () => {
		it('renames a field name used inside a rule condition operand of type "field"', () => {
			const rules = [
				{
					actions: [],
					conditions: [
						{
							operands: [
								{
									type: 'field',
									value: 'FieldName1'
								}
							]
						},
						{
							operands: [
								{
									type: 'field',
									value: 'FieldName2'
								},
								{
									type: 'value',
									value: 'FieldName1'
								}
							]
						}
					]
				}
			];

			const updatedRules = updateRulesFieldName(
				rules,
				'FieldName1',
				'NewFieldName'
			);

			expect(updatedRules[0].conditions[0].operands[0].value).toEqual(
				'NewFieldName'
			);
			expect(updatedRules[0].conditions[1].operands[1].value).toEqual(
				'FieldName1'
			);
		});

		it('renames a field name used inside a rule action target', () => {
			const rules = [
				{
					actions: [
						{
							target: 'FieldName1'
						},
						{
							target: 'FieldName2'
						}
					],
					conditions: []
				}
			];

			const updatedRules = updateRulesFieldName(
				rules,
				'FieldName1',
				'NewFieldName'
			);

			expect(updatedRules[0].actions[0].target).toEqual('NewFieldName');
			expect(updatedRules[0].actions[1].target).toEqual('FieldName2');
		});

		it('renames a field name used inside an expression of rule of type "calculate"', () => {
			const rules = [
				{
					actions: [
						{
							action: 'calculate',
							expression: '2*[FieldName2]',
							target: 'FieldName1'
						},
						{
							target: 'FieldName2'
						}
					],
					conditions: []
				}
			];

			const updatedRules = updateRulesFieldName(
				rules,
				'FieldName2',
				'NewFieldName'
			);

			expect(updatedRules[0].actions[0].expression).toEqual(
				'2*[NewFieldName]'
			);
			expect(updatedRules[0].actions[1].target).toEqual('NewFieldName');
		});
	});
});
