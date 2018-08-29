import RuleBuilder from '../RuleBuilder.es';
import {dom as MetalTestUtil} from 'metal-dom';

const spritemap = 'icons.svg';
let component;

const configView = {
	mode: 'view',
	pages: [
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
	],
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
							type: 'field',
							value: 'text2'
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
		it(
			'should render the list of rules when mode is set to view',
			() => {
				component = new RuleBuilder(configView);
				component.setState({mode: 'view'});
				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render rule screen editor when click edit button',
			() => {
				MetalTestUtil.enterDocument('<button id="addFieldButton" class="hide"></button>');

				const ruleCardEdit = document.querySelector('.rule-card-edit');

				component = new RuleBuilder(configView);

				MetalTestUtil.triggerEvent(ruleCardEdit, 'click', {});

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render rule screen editor when click add rule button',
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