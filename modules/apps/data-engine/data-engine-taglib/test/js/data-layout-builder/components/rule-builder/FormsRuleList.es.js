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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {FormsRuleList} from '../../../../../src/main/resources/META-INF/resources/data_layout_builder/js/components/rule-builder/FormsRuleList.es';

const DEFAULT_ACTIONS = {
	action: 'enable',
	target: 'Text27344751',
};

const createRule = (
	conditions = [],
	actions = [DEFAULT_ACTIONS],
	logicalOperator = 'OR'
) => {
	return {
		actions,
		conditions,
		logicalOperator,
	};
};

const defaultProps = (rules = []) => {
	return {
		fields: [
			{
				fieldName: 'Text27344750',
				label: 'Foo',
				value: 'Text27344750',
			},
			{
				fieldName: 'Text27344751',
				label: 'Bar',
				value: 'Text27344751',
			},
			{
				fieldName: 'Text27344752',
				label: 'Baz',
				value: 'Text27344752',
			},
			{
				fieldName: 'Select27344753',
				label: 'Select',
				options: [
					{
						label: 'Baz',
						reference: 'Option59797628',
						value: 'Option59797628',
					},
				],
				value: 'Select27344753',
			},
			{
				columns: [
					{
						label: 'bar',
						reference: 'Option73752233',
						value: 'Option73752233',
					},
				],
				fieldName: 'Grid27344754',
				label: 'Grid',
				rows: [
					{
						label: 'foo',
						reference: 'Option60238589',
						value: 'Option60238589',
					},
				],
				value: 'Grid27344754',
			},
		],
		keywords: '',
		onDelete: () => {},
		onEdit: () => {},
		rules,
	};
};

expect.extend({
	toRule(received, rule) {
		const ruleNormalized = rule.replace(/ /g, '');
		const pass = received === ruleNormalized;

		if (pass) {
			return {
				message: () =>
					`expected ${received} not to equal by ${ruleNormalized}`,
				pass: true,
			};
		}
		else {
			return {
				message: () =>
					`expected ${received} to be equal by ${ruleNormalized}`,
				pass: false,
			};
		}
	},
});

describe('RuleList', () => {
	describe('basic component render', () => {
		afterEach(cleanup);

		it('renders the empty state when there are no rules', () => {
			const {container} = render(<FormsRuleList />);

			expect(
				container.querySelector('.sheet-text > .text-default').innerHTML
			).toBe(
				'there-are-no-rules-yet-click-on-plus-icon-below-to-add-the-first'
			);
		});

		it('list the condition by comparing the field value with the foo value then enable Bar', () => {
			const leftOperand = {
				repeatable: false,
				source: 0,
				type: 'field',
				value: 'Text27344750',
			};
			const rightOperand = {
				type: 'string',
				value: 'foo',
			};

			const {container} = render(
				<FormsRuleList
					{...defaultProps([
						createRule([
							{
								operands: [leftOperand, rightOperand],
								operator: 'equals-to',
							},
						]),
					])}
				/>
			);

			expect(
				container.querySelector(
					'.list-group .list-group-item .autofit-col'
				).textContent
			).toRule(
				`if field Foo is-equal-to value ${rightOperand.value} enable Bar`
			);
		});

		it('list the condition by comparing the field value with the field Bar and then show Baz', () => {
			const leftOperand = {
				repeatable: false,
				source: 0,
				type: 'field',
				value: 'Text27344750',
			};

			const rightOperand = {
				repeatable: false,
				source: 0,
				type: 'field',
				value: 'Text27344751',
			};

			const {container} = render(
				<FormsRuleList
					{...defaultProps([
						createRule(
							[
								{
									operands: [leftOperand, rightOperand],
									operator: 'equals-to',
								},
							],
							[
								{
									action: 'show',
									target: 'Text27344752',
								},
							]
						),
					])}
				/>
			);

			expect(
				container.querySelector(
					'.list-group .list-group-item .autofit-col'
				).textContent
			).toRule('if field Foo is-equal-to field Bar show Baz');
		});

		it('list the condition by comparing the User value with the Guest value and then require Foo', () => {
			const leftOperand = {
				label: 'user',
				repeatable: false,
				type: 'user',
				value: 'user',
			};

			const rightOperand = {
				label: 'Guest',
				type: 'list',
				value: 'Guest',
			};

			const {container} = render(
				<FormsRuleList
					{...defaultProps([
						createRule(
							[
								{
									operands: [leftOperand, rightOperand],
									operator: 'belongs-to',
								},
							],
							[
								{
									action: 'require',
									target: 'Text27344750',
								},
							]
						),
					])}
				/>
			);

			expect(
				container.querySelector(
					'.list-group .list-group-item .autofit-col'
				).textContent
			).toRule('if user user belongs-to list Guest require Foo');
		});

		it('list the condition by comparing the Grid value with the foo:bar value and then show Foo', () => {
			const leftOperand = {
				repeatable: false,
				source: 0,
				type: 'field',
				value: 'Grid27344754',
			};

			const rightOperand = {
				type: 'json',
				value: '{"Option60238589":"Option73752233"}',
			};

			const {container} = render(
				<FormsRuleList
					{...defaultProps([
						createRule(
							[
								{
									operands: [leftOperand, rightOperand],
									operator: 'equals-to',
								},
							],
							[
								{
									action: 'show',
									target: 'Text27344750',
								},
							]
						),
					])}
				/>
			);

			expect(
				container.querySelector(
					'.list-group .list-group-item .autofit-col'
				).textContent
			).toRule('if field Grid is-equal-to value foo:bar show Foo');
		});

		it('list the condition by comparing the Select field value with the Baz selected value and then show Bar', () => {
			const leftOperand = {
				repeatable: false,
				source: 0,
				type: 'field',
				value: 'Select27344753',
			};

			const rightOperand = {
				type: 'option',
				value: 'Option59797628',
			};

			const {container} = render(
				<FormsRuleList
					{...defaultProps([
						createRule(
							[
								{
									operands: [leftOperand, rightOperand],
									operator: 'equals-to',
								},
							],
							[
								{
									action: 'show',
									target: 'Text27344751',
								},
							]
						),
					])}
				/>
			);

			expect(
				container.querySelector(
					'.list-group .list-group-item .autofit-col'
				).textContent
			).toRule('if field Select is-equal-to value Baz show Bar');
		});
	});
});
