import RuleList from 'source/components/RuleList/RuleList.es';

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
					label: 'label text 1',
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