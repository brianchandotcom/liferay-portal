import '../../__fixtures__/MockField.es';
import dom from 'metal-dom';
import RuleBuilder from 'source/components/RuleBuilder/RuleBuilder.es';

const spritemap = 'icons.svg';
let component;

const functionsMetadata = {
	radio: [
		{name: 'contains',
			parameterTypes: ['text', 'text'],
			returnType: 'boolean',
			value: 'Contains'},
		{name: 'equals-to',
			parameterTypes: ['text', 'text'],
			returnType: 'boolean',
			value: 'Is equal to'},
		{name: 'is-empty',
			parameterTypes: ['text'],
			returnType: 'boolean',
			value: 'Is empty'},
		{name: 'not-contains',
			parameterTypes: ['text', 'text'],
			returnType: 'boolean',
			value: 'Does not contain'},
		{name: 'not-equals-to',
			parameterTypes: ['text', 'text'],
			returnType: 'boolean',
			value: 'Is not equal to'},
		{name: 'not-is-empty',
			parameterTypes: ['text'],
			returnType: 'boolean',
			value: 'Is not empty'}
	],
	text: [
		{name: 'contains',
			parameterTypes: ['text', 'text'],
			returnType: 'boolean',
			value: 'Contains'},
		{name: 'equals-to',
			parameterTypes: ['text', 'text'],
			returnType: 'boolean',
			value: 'Is equal to'},
		{name: 'is-empty',
			parameterTypes: ['text'],
			returnType: 'boolean',
			value: 'Is empty'},
		{name: 'not-contains',
			parameterTypes: ['text', 'text'],
			returnType: 'boolean',
			value: 'Does not contain'},
		{name: 'not-equals-to',
			parameterTypes: ['text', 'text'],
			returnType: 'boolean',
			value: 'Is not equal to'},
		{name: 'not-is-empty',
			parameterTypes: ['text'],
			returnType: 'boolean',
			value: 'Is not empty'}
	],
	user: [
		{name: 'belongs-to',
			parameterTypes: ['text'],
			returnType: 'boolean',
			value: 'Belongs to'}
	]
};

const baseConfig = {
	dataProviderInstanceParameterSettingsURL: '/o/dynamic-data-mapping-form-builder-data-provider-instances/',
	dataProviderInstancesURL: '/o/data-provider/',
	functionsMetadata,
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
		},
		{
			actions: [
				{
					action: 'show',
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
					operator: 'not-equals-to'
				}
			],
			['logical-operator']: 'AND'
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

				fetch.mockResponse(
					JSON.stringify(
						[]
					)
				);

				dom.enterDocument('<button id="addFieldButton" class="hide"></button>');

				fetch.mockResponse(
					JSON.stringify(
						[]
					)
				);

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
			'should change the view mode from view to edit',
			() => {
				jest.runAllTimers();

				jest.useFakeTimers();

				component.refs.RuleList.emit(
					'ruleEdited',
					{
						ruleId: 0
					}
				);

				jest.runAllTimers();

				expect(component.state.mode).toEqual('edit');
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

				spy.mockRestore();
			}
		);

		it(
			'should be able to edit a rule when more than one is available in the Rules list',
			() => {

				component = new RuleBuilder(baseConfig);

				jest.useFakeTimers();

				component.refs.RuleList.emit(
					'ruleEdited',
					{
						ruleId: 1
					}
				);

				jest.runAllTimers();

				expect(component.state.mode).toEqual('edit');

			}
		);

		it(
			'should be able to edit a rule when there\'s only one rule available in the Rules list',
			() => {

				component = new RuleBuilder(
					{
						...baseConfig,
						rules: [
							{
								...baseConfig.rules[0]
							}
						]
					}
				);

				jest.useFakeTimers();

				component.refs.RuleList.emit(
					'ruleEdited',
					{
						ruleId: 0
					}
				);

				jest.runAllTimers();

				expect(component.state.mode).toEqual('edit');

			}
		);
	}
);