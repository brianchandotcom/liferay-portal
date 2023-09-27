/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	renameFieldInsideExpression,
	updateRulesReferences,
} from '../../../../src/main/resources/META-INF/resources/js/core/utils/rules';

describe('core/utils/rules', () => {
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

	describe('updateRulesReferences(rules, oldProperties, newProperties)', () => {
		it('renames a field name used inside a rule condition operand of type "field"', () => {
			const rules = [
				{
					actions: [],
					conditions: [
						{
							operands: [
								{
									type: 'field',
									value: 'FieldName1',
								},
							],
						},
						{
							operands: [
								{
									type: 'field',
									value: 'FieldName2',
								},
								{
									type: 'value',
									value: 'FieldName1',
								},
							],
						},
					],
				},
			];

			const updatedRules = updateRulesReferences(
				rules,
				{fieldName: 'FieldName1'},
				{fieldName: 'NewFieldName'}
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
							target: 'FieldName1',
						},
						{
							target: 'FieldName2',
						},
					],
					conditions: [],
				},
			];

			const updatedRules = updateRulesReferences(
				rules,
				{fieldName: 'FieldName1'},
				{fieldName: 'NewFieldName'}
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
							target: 'FieldName1',
						},
						{
							target: 'FieldName2',
						},
					],
					conditions: [],
				},
			];

			const updatedRules = updateRulesReferences(
				rules,
				{fieldName: 'FieldName2'},
				{fieldName: 'NewFieldName'}
			);

			expect(updatedRules[0].actions[0].expression).toEqual(
				'2*[NewFieldName]'
			);
			expect(updatedRules[0].actions[1].target).toEqual('NewFieldName');
		});
	});
});
