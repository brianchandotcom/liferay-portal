import '../../../components/RuleEditor/__tests__/__fixtures__/FieldsRuleEditor.es';
import dom from 'metal-dom';
import RuleBuilder from '../RuleBuilder.es';

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
				dom.enterDocument('<button id="addFieldButton" class="hide"></button>');

				component = new RuleBuilder(configView);

				jest.useFakeTimers();
			}
		);
		afterEach(
			() => {
				const addbutton = document.querySelector('#addFieldButton');

				if (component) {
					component.dispose();
				}

				dom.exitDocument(addbutton);
			}
		);
		it(
			'should render the list of rules when mode is set to view',
			() => {
				component.setState({mode: 'view'});

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render rule screen editor when click edit button',
			() => {
				const ruleCardEdit = document.querySelector('.rule-card-edit');

				dom.triggerEvent(ruleCardEdit, 'click', {});

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);
	}
);