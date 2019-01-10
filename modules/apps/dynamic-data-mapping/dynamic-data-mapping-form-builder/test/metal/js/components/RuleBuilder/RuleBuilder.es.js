import '../../components/RuleEditor/__fixtures__/RuleEditorMockField.es';
import dom from 'metal-dom';
import RuleBuilder from 'source/components/RuleBuilder/RuleBuilder.es';

const spritemap = 'icons.svg';
let component;

const baseConfig = {
	dataProviderInstanceParameterSettingsURL: '/o/dynamic-data-mapping-form-builder-data-provider-instances/',
	dataProviderInstancesURL: '/o/data-provider/',
	functionsURL: '/o/dynamic-data-mapping-form-builder-functions/',
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
	spritemap,

	rolesURL: '/o/dynamic-data-mapping-form-builder-roles/',

	url: '/o/dynamic-data-mapping-form-builder-roles/'
};

describe(
	'RuleBuilder',
	() => {
		beforeEach(
			() => {
				jest.useFakeTimers();

				dom.enterDocument('<button id="addFieldButton" class="hide"></button>');

				component = new RuleBuilder(baseConfig);
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
			'should render rule screen creator when click add button',
			() => {
				const addbutton = document.querySelector('#addFieldButton');

				dom.triggerEvent(addbutton, 'click', {});

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should receive ruleAdded event to save a rule',
			() => {
				jest.runAllTimers();

				const spy = jest.spyOn(component, 'emit');

				component.setState({mode: 'create'});

				jest.runAllTimers();

				jest.useFakeTimers();

				component.refs.RuleEditor.emit('ruleAdded', {});

				jest.runAllTimers();

				expect(spy).toHaveBeenCalledWith('ruleAdded', {});
			}
		);

		it(
			'should receive ruleCancel event to discard a rule creation',
			() => {
				jest.runAllTimers();

				const totalRules = component.props.rules.length;

				component.setState({mode: 'create'});

				jest.runAllTimers();

				jest.useFakeTimers();

				component.refs.RuleEditor.emit('ruleCancel', {});

				jest.runAllTimers();

				expect(component.state.rules).toEqual(component.props.rules);

				expect(component.state.rules.length).toEqual(totalRules);
			}
		);

		it(
			'should fetch roles when rendered',
			() => {
				const spy = jest.spyOn(window, 'fetch');

				component = new RuleBuilder(baseConfig);

				expect(spy).toHaveBeenCalledWith(component.props.url, expect.anything());
			}
		);
	}
);