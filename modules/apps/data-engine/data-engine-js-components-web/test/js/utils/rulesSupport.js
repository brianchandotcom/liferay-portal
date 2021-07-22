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
	clearFirstOperandValue,
	clearOperatorValue,
	clearSecondOperandValue,
	clearTargetValue,
	findRuleByFieldName,
} from '../../../src/main/resources/META-INF/resources/js/utils/rulesSupport';

const mockActions = [
	{
		action: 'show',
		target: 'text1',
	},
	{
		action: 'enable',
		target: 'text2',
	},
];

const mockConditions = [
	{
		operands: [
			{
				type: 'show',
				value: 'text1',
			},
			{
				type: 'enable',
				value: 'text2',
			},
		],
		operator: 'isEmpty',
	},
];

describe('RulesSupport', () => {
	it('clears the action target value', () => {
		const mockArgument = [...mockActions];

		const actions = clearTargetValue(mockArgument, 0);

		expect(actions[0].target).toEqual('');
	});

	it('clears the first operand value', () => {
		const mockArgument = [...mockConditions];

		const condition = clearFirstOperandValue(mockArgument[0]);

		expect(condition.operands[0].type).toEqual('');
		expect(condition.operands[0].value).toEqual('');
	});

	it('clears the operator value', () => {
		const mockArgument = [...mockConditions];

		const condition = clearOperatorValue(mockArgument[0]);

		expect(condition.operator).toEqual('');
	});

	it('clears the second operand value', () => {
		const mockArgument = [...mockConditions];

		const condition = clearSecondOperandValue(mockArgument[0]);

		expect(condition.operands[1].type).toEqual('');
		expect(condition.operands[1].value).toEqual('');
	});

	describe('findRuleByFieldName(fieldName, pages, rules)', () => {
		it('returns false if field does not belong to rule', () => {
			const rules = [
				{
					actions: [
						{
							action: 'require',
							target: 'text1',
						},
					],
					conditions: [
						{
							operands: [
								{
									type: 'field',
									value: 'text1',
								},
								{
									type: 'field',
									value: 'text2',
								},
							],
							operator: 'equals-to',
						},
					],
				},
			];

			expect(findRuleByFieldName('text3', null, rules)).toBeFalsy();
		});

		it('returns true if field belongs to rule', () => {
			const rules = [
				{
					actions: [
						{
							action: 'enable',
							target: 'date1',
						},
					],
					conditions: [
						{
							operands: [
								{
									type: 'field',
									value: 'text1',
								},
								{
									type: 'field',
									value: 'text2',
								},
							],
							operator: 'equals-to',
						},
					],
				},
			];

			expect(findRuleByFieldName('date1', null, rules)).toBeTruthy();

			expect(findRuleByFieldName('text1', null, rules)).toBeTruthy();

			expect(findRuleByFieldName('text2', null, rules)).toBeTruthy();
		});

		it('returns true if field belongs to calculate rule', () => {
			const rules = [
				{
					actions: [
						{
							action: 'calculate',
							expression: '[num1]+([num2]*2)',
							target: 'num3',
						},
					],
					conditions: [
						{
							operands: [
								{
									type: 'field',
									value: 'text1',
								},
							],
							operator: 'is-empty',
						},
					],
				},
			];

			expect(findRuleByFieldName('num1', null, rules)).toBeTruthy();

			expect(findRuleByFieldName('num2', null, rules)).toBeTruthy();
		});

		it('returns true if field belongs to auto-fill rule', () => {
			const rules = [
				{
					actions: [
						{
							action: 'auto-fill',
							inputs: {
								key: 'text2',
							},
							outputs: {
								key: 'select1',
							},
						},
					],
					conditions: [
						{
							operands: [
								{
									type: 'field',
									value: 'text1',
								},
							],
							operator: 'is-empty',
						},
					],
				},
			];

			expect(findRuleByFieldName('select1', null, rules)).toBeTruthy();

			expect(findRuleByFieldName('text2', null, rules)).toBeTruthy();
		});
	});
});
