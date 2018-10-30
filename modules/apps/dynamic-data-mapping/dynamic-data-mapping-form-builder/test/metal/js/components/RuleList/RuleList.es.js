import RuleList from 'source/components/RuleList/RuleList.es';
import {dom as dom} from 'metal-dom';

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
								label: 'label text 1'
							},
							{
								fieldName: 'text2',
								label: 'label text 2'
							}
						]
					}
				]
			}
		]
	}
];

const configDefault = {
	pages,
	rules: [
		{
			actions: [
				{
					action: 'require',
					expression: '[x+2]',
					target: 'text1'
				}
			],
			conditions: [
				{
					operands: [
						{
							type: 'field',
							value: 'text1'
						},
						{
							type: 'value',
							value: 'value 2'
						}
					],
					operator: 'equals-to'
				}
			],
			['logical-operator']: 'OR'
		}
	],
	spritemap
};

const rules = [
	{
		actions: [
			{
				action: 'require',
				target: 'text1'
			},
			{
				action: 'auto-fill',
				target: 'text2'
			},
			{
				action: 'enable',
				target: 'text1'
			},
			{
				action: 'show',
				target: 'text2'
			},
			{
				action: 'calculate',
				target: 'text2'
			},
			{
				action: 'jump-to-page',
				target: 'text2'
			}
		],
		conditions: [
			{
				operands: [
					{
						type: 'field',
						value: 'text1'
					},
					{
						type: 'string',
						value: 'value 2'
					}
				],
				operator: 'equals-to'
			},
			{
				operands: [
					{
						type: 'field',
						value: 'text1'
					},
					{
						type: 'field',
						value: 'text1'
					}
				],
				operator: 'not-equals-to'
			}
		],
		['logical-operator']: 'OR'
	}
];

const strings = {
	value:
		{
			emptyListText: 'there-are-no-rules-yet-click-on-plus-icon-below-to-add-the-first'
		}
};

describe(
	'RuleList',
	() => {
		beforeEach(
			() => {
				jest.useFakeTimers();
			}
		);
		afterEach(
			() => {
				if (component) {
					component.dispose();
				}
			}
		);

		it(
			'should remove one rule item when delete button gets clicked',
			() => {
				component = new RuleList(
					{
						pages,
						rules,
						spritemap,
						strings
					}
				);

				const deleteButton = document.querySelector('.rule-card-delete');

				const initialSize = component.rules.length;

				dom.triggerEvent(deleteButton, 'click', {});

				jest.runAllTimers();

				const finalSize = component.rules.length;

				expect(finalSize).toEqual(initialSize - 1);
			}
		);

		it(
			'should return the field label for each action',
			() => {
				component = new RuleList(configDefault);

				const contextLabel = component.pages[0].rows[0].columns[0].fields[0].label;

				const actionLabel = component.rules[0].actions[0].label;

				jest.runAllTimers();

				expect(actionLabel).toEqual(contextLabel);
			}
		);

		it(
			'should show message when rule list is empty',
			() => {
				component = new RuleList(
					{
						pages,
						rules: [],
						spritemap,
						strings: {
							emptyListText: 'there-are-no-rules-yet-click-on-plus-icon-below-to-add-the-first'
						}
					}
				);

				expect(component).toMatchSnapshot();

			}
		);
	}
);