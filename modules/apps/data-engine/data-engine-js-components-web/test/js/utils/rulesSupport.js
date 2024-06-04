/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	clearFirstOperandValue,
	clearOperatorValue,
	clearSecondOperandValue,
	clearTargetValue,
	findRuleByFieldName,
	syncActions,
} from '../../../src/main/resources/META-INF/resources/js/utils/rulesSupport';
import pages from '../__mock__/mockPages.es';

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

	describe('syncActions(pages, actions)', () => {
		it('returns jump-to-page action with a valid target when there are two pages and the orignal target points to the second page', () => {
			const action = [
				{
					action: 'jump-to-page',
					source: '0',
					target: '1', // Target is zero-based, which means that we should jump to the second page.
				},
			];

			const twoPages = [pages[0], pages[0]]; // There are two pages, so we should be able to jump to the second one.

			const syncedActions = syncActions(twoPages, action);

			expect(syncedActions[0].target).toEqual('1');
		});

		it('returns empty jump-to-page action target when there is only one page', () => {
			const action = [
				{
					action: 'jump-to-page',
					source: '0',
					target: '0', // Target is zero-based, which means that we should jump to the first page.
				},
			];

			const onePage = [pages[0]]; // But target should be irrelevant if there is only one page. There is no page to jump to.

			const syncedActions = syncActions(onePage, action);

			expect(syncedActions[0].target).toEqual('');
		});

		it('returns empty jump-to-page action target when the orignal target equals the ammount of pages', () => {
			const action = [
				{
					action: 'jump-to-page',
					source: '0',
					target: '2', // Target is zero-based, which means that we should jump to the third page.
				},
			];

			const twoPages = [pages[0], pages[0]]; // There are only two pages, so we should not be able to jump to the third one.

			const syncedActions = syncActions(twoPages, action);

			expect(syncedActions[0].target).toEqual('');
		});
	});
});
