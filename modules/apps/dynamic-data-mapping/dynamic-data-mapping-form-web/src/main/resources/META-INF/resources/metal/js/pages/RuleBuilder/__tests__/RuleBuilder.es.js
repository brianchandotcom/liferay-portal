import RuleBuilder from '../RuleBuilder.es';
import {dom as MetalTestUtil} from 'metal-dom';

let component;

const configView = {
	context: [
		{
			rows: [
				{
					columns: [
						{
							fields: [
								{
									fieldName: 'text1',
									label: 'label text 1'
								}
							]
						}
					]
				}
			]
		}
	],
	rules: [
		{
			actions: [
				{
					action: 'require',
					target: 'text1',
					expression: '[x+2]'
				}
			],
			conditions: [
				{
					operands: [
						{
							type: 'field',
							value: 'value 1'
						},
						{
							type: 'field',
							value: 'value 2'
						}
					],
					operator: 'equals-to'
				}
			],
			['logical-operator']: 'OR',
		}
	],
	mode: 'view'
};

const configEdit = {
	formContext: [
		{
			rows: [
				{
					columns: [
						{
							fields: [
								{
									fieldName: 'text1',
									label: 'label text 1'
								}
							]
						}
					]
				}
			]
		}
	],
	rules: [
		{
			actions: [
				{
					action: 'require',
					target: 'text1'
				}
			],
			conditions: [
				{
					operands: [
						{
							type: 'field',
							value: 'value 1'
						},
						{
							type: 'field',
							value: 'value 2'
						}
					],
					operator: 'equals-to'
				}
			],
			['logical-operator']: 'OR',
			expression: '[x+2]'
		}
	]
};

describe(
	'RuleBuilder',
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
		it('should render the list of rules when mode is set to view',
			() => {
				component = new RuleBuilder(configView);
				component.setState({mode: 'view'});
				expect(component).toMatchSnapshot();
			}
		);

		it('should render rule screen editor when click edit button',
			() => {
				MetalTestUtil.enterDocument('<button id="addFieldButton" class="hide"></button>');

				const ruleCardEdit = document.querySelector('.rule-card-edit');

				component = new RuleBuilder(configView);

				MetalTestUtil.triggerEvent(ruleCardEdit, 'click', {});

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it('should render rule screen editor when click add rule button',
			() => {
				MetalTestUtil.enterDocument('<button id="addFieldButton" class="hide"></button>');

				const ruleCardEdit = document.querySelector('#addFieldButton');

				component = new RuleBuilder(configView);

				MetalTestUtil.triggerEvent(ruleCardEdit, 'click', {});

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);
	}
);