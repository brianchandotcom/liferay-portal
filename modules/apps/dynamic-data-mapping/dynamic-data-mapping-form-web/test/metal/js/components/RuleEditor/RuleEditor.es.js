import RuleEditor from 'source/components/RuleEditor/RuleEditor.es';
import './__fixtures__/RuleEditorMockField.es';

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
								fieldName: 'Nome',
								label: 'Nome',
								options: [
									{
										label: 'Option',
										value: 'Option'
									}
								],
								type: 'text'
							},
							{
								fieldName: 'Sobrenome',
								label: 'text',
								options: [
									{
										label: 'Option',
										value: 'Option'
									}
								],
								type: 'text'
							},
							{
								fieldName: 'EstadoCivil',
								label: 'Estado civil',
								options: [
									{
										label: 'Casado',
										value: 'Casado'
									},
									{
										label: 'Solteiro',
										value: 'Solteiro'
									}
								],
								type: 'select'
							}
						]
					}
				]
			}
		]
	}
];

const firstOperandList = [
	{
		dataType: 'string',
		name: 'Nome',
		options: [
			{
				label: 'Option',
				value: 'Option'
			}
		],
		type: 'text',
		value: 'Nome'
	},
	{
		dataType: 'string',
		name: 'text',
		options: [
			{
				label: 'Option',
				value: 'Option'
			}
		],
		type: 'text',
		value: 'Sobrenome'
	},
	{
		dataType: 'string',
		name: 'EstadoCivil',
		options: [
			{
				label: 'Casado',
				value: 'Casado'
			},
			{
				label: 'Solteiro',
				value: 'Solteiro'
			}
		],
		type: 'select',
		value: 'Estado civil'
	}
];

const secondOperandTypeSelectedList = [
	{
		name: '',
		value: ''
	}
];

const operatorsList = [
	{name: 'contains', parameterTypes: ['text', 'text'], returnType: 'boolean', value: 'Contains'},
	{name: 'equals-to', parameterTypes: ['text', 'text'], returnType: 'boolean', value: 'Is equal to'},
	{name: 'is-empty', parameterTypes: ['text'], returnType: 'boolean', value: 'Is empty'},
	{name: 'not-contains', parameterTypes: ['text', 'text'], returnType: 'boolean', value: 'Does not contain'},
	{name: 'not-equals-to', parameterTypes: ['text', 'text'], returnType: 'boolean', value: 'Is not equal to'},
	{name: 'not-is-empty', parameterTypes: ['text'], returnType: 'boolean', value: 'Is not empty'}
];

const functionsMetadata = {text: [
	{name: 'contains', parameterTypes: ['text', 'text'], returnType: 'boolean', value: 'Contains'},
	{name: 'equals-to', parameterTypes: ['text', 'text'], returnType: 'boolean', value: 'Is equal to'},
	{name: 'is-empty', parameterTypes: ['text'], returnType: 'boolean', value: 'Is empty'},
	{name: 'not-contains', parameterTypes: ['text', 'text'], returnType: 'boolean', value: 'Does not contain'},
	{name: 'not-equals-to', parameterTypes: ['text', 'text'], returnType: 'boolean', value: 'Is not equal to'},
	{name: 'not-is-empty', parameterTypes: ['text'], returnType: 'boolean', value: 'Is not empty'}
]};

describe(
	'RuleEditor',
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
			'should hide third and fourth selectors by default',
			() => {
				const conditions = [];

				component = new RuleEditor(
					{
						conditions,
						functionsMetadata,
						pages,
						secondOperandTypeSelectedList,
						spritemap
					}
				);

				const type = document.querySelector('.condition-type').classList.contains('hide');
				const typeValue = document.querySelector('.condition-type-value');
				const typeValueSelect = document.querySelector('.condition-type-value-select');

				expect(type && !typeValue && !typeValueSelect).toEqual(true);
			}
		);

		it(
			'should show third selector when first and second selectors have options selected',
			() => {
				const conditions = [
					{
						operands: [
							{
								type: 'text',
								value: 'Nome'
							}
						],
						operator: 'Is not equal to'
					}
				];

				const secondOperandTypeList = [
					{
						name: 'text',
						value: 'Value'
					},
					{
						name: 'field',
						value: 'other-field'
					}
				];

				component = new RuleEditor(
					{
						conditions,
						firstOperandList,
						functionsMetadata,
						logicalOperator: 'or',
						operatorsList,
						pages,
						secondOperandTypeList,
						secondOperandTypeSelectedList,
						spritemap
					}
				);

				component.refs.conditionIf.emitFieldEdited('Nome', 'Nome');
				component.refs.conditionOperator.emitFieldEdited('Is not equal to', 'not-equals-to');

				const type = document.querySelector('.condition-type').classList.contains('hide');
				const typeValue = document.querySelector('.condition-type-value').classList.contains('hide');
				const typeValueSelect = document.querySelector('.condition-type-value-select').classList.contains('hide');

				expect(!type && typeValue && typeValueSelect).toEqual(true);
			}
		);

		it(
			'should reset and show fourth input selector when first, second and third have options selected and first selector is text field',
			() => {
				const conditions = [
					{
						operands: [
							{
								type: 'text',
								value: 'Nome'
							},
							{
								type: 'text',
								value: 'Nome'
							}
						],
						operator: 'Is not equal to'
					}
				];

				const secondOperandTypeList = [
					{
						name: 'text',
						value: 'Value'
					},
					{
						name: 'field',
						value: 'other-field'
					}
				];

				const secondOperandTypeSelectedList = [
					{
						name: 'text',
						value: 'Value'
					}
				];

				component = new RuleEditor(
					{
						conditions,
						firstOperandList,
						functionsMetadata,
						pages,
						secondOperandTypeList,
						secondOperandTypeSelectedList,
						spritemap
					}
				);

				component.refs.conditionIf.emitFieldEdited('', 'Nome');
				component.refs.conditionOperator.emitFieldEdited('Is not equal to', 'not-equals-to');
				component.refs.type.emitFieldEdited('Value', 'text');

				const secondOperandEmpty = (conditions[0].operands[1].type === '') && (conditions[0].operands[1].value === '');
				const type = document.querySelector('.condition-type').classList.contains('hide');
				const typeValue = document.querySelector('.condition-type-value').classList.contains('hide');
				const typeValueSelect = document.querySelector('.condition-type-value-select').classList.contains('hide');

				expect(
					!type && !typeValue &&
					typeValueSelect && secondOperandEmpty
				).toEqual(true);
			}
		);

		it(
			'should show fourth "select" selector when first, second and third have options selected and first selector is select field',
			() => {
				const conditions = [
					{
						operands: [
							{
								type: 'select',
								value: 'Estado civil'
							}
						],
						operator: 'Is not equal to'
					}
				];

				const secondOperandTypeSelectedList = [
					{
						name: 'select',
						value: 'Value'
					}
				];

				component = new RuleEditor(
					{
						conditions,
						firstOperandList,
						functionsMetadata,
						pages,
						secondOperandTypeSelectedList,
						spritemap
					}
				);

				component.refs.conditionIf.emitFieldEdited('', 'EstadoCivil');
				component.refs.conditionOperator.emitFieldEdited('Is not equal to', 'not-equals-to');
				component.refs.type.emitFieldEdited('Value', 'select');

				const type = document.querySelector('.condition-type').classList.contains('hide');
				const typeValue = document.querySelector('.condition-type-value').classList.contains('hide');
				const typeValueSelect = document.querySelector('.condition-type-value-select').classList.contains('hide');
				const typeValueSelectOptions = document.querySelector('.condition-type-value-select-options').classList.contains('hide');

				expect(
					!type && typeValue &&
					typeValueSelect && !typeValueSelectOptions
				).toEqual(true);
			}
		);

		it(
			'should show all fields when fourth field is a text input',
			() => {
				const conditions = [
					{
						operands: [
							{
								type: 'text',
								value: 'Nome'
							},
							{
								type: '',
								value: 'Any text'
							}
						],
						operator: 'Is not equal to'
					}
				];

				const secondOperandTypeSelectedList = [
					{
						name: 'text',
						value: 'Value'
					}
				];

				component = new RuleEditor(
					{
						conditions,
						firstOperandList,
						functionsMetadata,
						pages,
						secondOperandTypeSelectedList,
						spritemap
					}
				);

				component.refs.conditionIf.emitFieldEdited('', 'Nome');
				component.refs.conditionOperator.emitFieldEdited('Is not equal to', 'not-equals-to');
				component.refs.type.emitFieldEdited('Value', 'text');
				component.refs.typeValueInput.emitFieldEdited('', 'Nome');

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should show all fields when fourth field is a select field',
			() => {
				const conditions = [
					{
						operands: [
							{
								type: 'text',
								value: 'Nome'
							},
							{
								type: 'text',
								value: 'Nome'
							}
						],
						operator: 'Is not equal to'
					}
				];

				const secondOperandTypeSelectedList = [
					{
						name: 'field',
						value: 'other-field'
					}
				];

				component = new RuleEditor(
					{
						conditions,
						firstOperandList,
						functionsMetadata,
						pages,
						secondOperandTypeSelectedList,
						spritemap
					}
				);

				component.refs.conditionIf.emitFieldEdited('', 'Nome');
				component.refs.conditionOperator.emitFieldEdited('Is not equal to', 'not-equals-to');
				component.refs.type.emitFieldEdited('other-field', 'field');
				component.refs.typeValueSelect.emitFieldEdited('', 'Nome');

				expect(component).toMatchSnapshot();
			}
		);
	}
);

describe(
	'Regression',
	() => {
		it(
			'LPS-85642 Should hide second operand and value when operator is reset',
			() => {
				component = new RuleEditor(
					{
						functionsMetadata,
						pages,
						spritemap
					}
				);

				component.refs.conditionIf.emitFieldEdited('', 'Nome');
				component.refs.conditionOperator.emitFieldEdited('Is not equal to', 'not-equals-to');
				component.refs.type.emitFieldEdited('other-field', 'field');

				jest.runAllTimers();

				component.refs.typeValueSelect.emitFieldEdited('', 'Nome');
				component.refs.conditionOperator.emitFieldEdited('', '');

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'LPS-85644 Should clear first operand when field is removed from pages',
			() => {
				component = new RuleEditor(
					{
						functionsMetadata,
						pages,
						spritemap
					}
				);

				component.refs.conditionIf.emitFieldEdited('Nome', 'Nome');
				component.refs.conditionOperator.emitFieldEdited('Is not equal to', 'not-equals-to');
				component.refs.type.emitFieldEdited('other-field', 'field');

				component.pages = [];

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);
	}
);