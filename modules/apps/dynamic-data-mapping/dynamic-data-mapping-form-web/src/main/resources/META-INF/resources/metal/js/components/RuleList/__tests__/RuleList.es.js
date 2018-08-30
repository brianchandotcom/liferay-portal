import RuleList from '../RuleList.es';
import {dom as MetalTestUtil} from 'metal-dom';

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

				MetalTestUtil.triggerEvent(deleteButton, 'click', {});

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
			'should show add button when listing the rules',
			() => {
				MetalTestUtil.enterDocument('<button id="addFieldButton" class="hide"></button>');
				const addButton = document.querySelector('#addFieldButton');

				component = new RuleList(
					{
						pages,
						rules,
						spritemap,
						strings
					}
				);

				component.rules['logical-operator'] = 'OR';

				expect(addButton.classList.contains('hide')).toEqual(false);

				MetalTestUtil.exitDocument(addButton);
			}
		);

		it(
			'should  hide translation manager and form basic info when listing the rules',
			() => {
				MetalTestUtil.enterDocument('<div class="ddm-translation-manager"></div>');

				MetalTestUtil.enterDocument('<div class="ddm-form-basic-info"></div>');

				const translationManager = document.querySelector('.ddm-translation-manager');

				const formBasicInfo = document.querySelector('.ddm-form-basic-info');

				component = new RuleList(
					{
						pages,
						rules,
						spritemap,
						strings
					}
				);

				component.rules['logical-operator'] = 'OR';

				expect(
					translationManager.classList.contains('hide') &&
					formBasicInfo.classList.contains('hide')
				).toEqual(true);

				MetalTestUtil.exitDocument(translationManager);
				MetalTestUtil.exitDocument(formBasicInfo);
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