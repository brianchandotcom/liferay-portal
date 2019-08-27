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

import RulesSupport from '../../../src/main/resources/META-INF/resources/js/components/RuleBuilder/RulesSupport.es';

const mockActions = [
	{
		action: 'show',
		target: 'text1'
	},
	{
		action: 'enable',
		target: 'text2'
	}
];

const mockConditions = [
	{
		operands: [
			{
				type: 'show',
				value: 'text1'
			},
			{
				type: 'enable',
				value: 'text2'
			}
		],
		operator: 'isEmpty'
	}
];

describe('RulesSupport', () => {
	it('clears the action target value', () => {
		const mockArgument = [...mockActions];

		const actions = RulesSupport.clearTargetValue(mockArgument, 0);

		expect(actions[0].target).toEqual('');
	});

	it('clears the first operand value', () => {
		const mockArgument = [...mockConditions];

		const condition = RulesSupport.clearFirstOperandValue(mockArgument[0]);

		expect(condition.operands[0].type).toEqual('');
		expect(condition.operands[0].value).toEqual('');
	});

	it('clears the operator value', () => {
		const mockArgument = [...mockConditions];

		const condition = RulesSupport.clearOperatorValue(mockArgument[0]);

		expect(condition.operator).toEqual('');
	});

	it('clears the second operand value', () => {
		const mockArgument = [...mockConditions];

		const condition = RulesSupport.clearSecondOperandValue(mockArgument[0]);

		expect(condition.operands[1].type).toEqual('');
		expect(condition.operands[1].value).toEqual('');
	});
});
