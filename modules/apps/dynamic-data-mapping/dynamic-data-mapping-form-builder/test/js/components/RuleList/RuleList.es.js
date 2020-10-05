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

import RuleList from '../../../../src/main/resources/META-INF/resources/js/components/RuleList/RuleList.es';

let component;

const spritemap = 'icons.svg';

const pages = [
	{
		rows: [
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'text1',
								label: 'label text 1',
								type: 'text',
							},
							{
								fieldName: 'text2',
								label: 'label text 2',
								type: 'text',
							},
						],
					},
				],
			},
		],
	},
];

const brokenRuleConfig = {
	pages,
	rules: [
		{
			actions: [
				{
					action: 'require',
					label: 'label text 1',
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
							type: '',
							value: '',
						},
					],
					operator: 'contains',
				},
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
			['logical-operator']: 'OR',
		},
	],
	spritemap,
};

const configDefault = {
	pages,
	rules: [
		{
			actions: [
				{
					action: 'require',
					label: 'label text 1',
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
							type: 'value',
							value: 'value 2',
						},
					],
					operator: 'contains',
				},
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
			['logical-operator']: 'OR',
		},
		{
			actions: [
				{
					action: 'show',
					label: 'label text 2',
					target: 'text2',
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
							type: 'value',
							value: 'value 3',
						},
					],
					operator: 'not-equals-to',
				},
			],
			['logical-operator']: 'AND',
		},
	],
	spritemap,
};

describe('RuleList', () => {
	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('returns the field label for each action', () => {
		component = new RuleList(configDefault);

		const contextLabel =
			component.pages[0].rows[0].columns[0].fields[0].label;

		const actionLabel = component.rules[0].actions[0].label;

		jest.runAllTimers();

		expect(actionLabel).toEqual(contextLabel);
	});

	it('shows message when rule list is empty', () => {
		component = new RuleList({
			pages,
			rules: [],
			spritemap,
			strings: {
				emptyListText:
					'there-are-no-rules-yet-click-on-plus-icon-below-to-add-the-first',
			},
		});

		expect(component).toMatchSnapshot();
	});

	it('shows rule list', () => {
		component = new RuleList(configDefault);

		expect(component).toMatchSnapshot();
	});

	it('shows the label broken rule when a rule is incomplete', () => {
		component = new RuleList(brokenRuleConfig);

		expect(component).toMatchSnapshot();
	});

	it('shows rule with json type operand', () => {
		const rule = {
			actions: [
				{
					action: 'require',
					label: 'label text 1',
					target: 'text1',
				},
			],
			conditions: [
				{
					operands: [
						{
							type: 'field',
							value: 'grid1',
						},
						{
							type: 'json',
							value: '{"value1" : "value2"}',
						},
					],
					operator: 'equals-to',
				},
			],
			['logical-operator']: 'OR',
		};

		const config = {
			pages: [
				{
					rows: [
						{
							columns: [
								{
									fields: [
										{
											fieldName: 'grid1',
											label: 'label grid 1',
										},
										{
											fieldName: 'text1',
											label: 'label text 1',
										},
									],
								},
							],
						},
					],
				},
			],
			rules: [rule],
			spritemap,
		};

		component = new RuleList(config);

		expect(component._getOperandLabel(rule.conditions[0].operands, 1)).toBe(
			'value1:value2'
		);
	});
});
