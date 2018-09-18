import RuleEditor from '../RuleEditor.es';
import './__fixtures__/FieldsRuleEditor.es';
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
	{name: 'equals-to', parameterTypes: ['text', 'text'], returnType: 'boolean', value: 'Is equal to'},
	{name: 'not-equals-to', parameterTypes: ['text', 'text'], returnType: 'boolean', value: 'Is not equal to'},
	{name: 'contains', parameterTypes: ['text', 'text'], returnType: 'boolean', value: 'Contains'},
	{name: 'not-contains', parameterTypes: ['text', 'text'], returnType: 'boolean', value: 'Does not contain'},
	{name: 'is-empty', parameterTypes: ['text'], returnType: 'boolean', value: 'Is empty'},
	{name: 'not-is-empty', parameterTypes: ['text'], returnType: 'boolean', value: 'Is not empty'}
];

const functionsMetadata = {text: [
	{name: 'equals-to', parameterTypes: ['text', 'text'], returnType: 'boolean', value: 'Is equal to'},
	{name: 'not-equals-to', parameterTypes: ['text', 'text'], returnType: 'boolean', value: 'Is not equal to'},
	{name: 'contains', parameterTypes: ['text', 'text'], returnType: 'boolean', value: 'Contains'},
	{name: 'not-contains', parameterTypes: ['text', 'text'], returnType: 'boolean', value: 'Does not contain'},
	{name: 'is-empty', parameterTypes: ['text'], returnType: 'boolean', value: 'Is empty'},
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
						firstOperandList,
						functionsMetadata,
						pages,
						secondOperandTypeSelectedList,
						spritemap
					}
				);

				const type = document.querySelector('.condition-type').classList.contains('hide');
				const typeValue = document.querySelector('.condition-type-value').classList.contains('hide');
				const typeValueSelect = document.querySelector('.condition-type-value-select').classList.contains('hide');

				expect(type && typeValue && typeValueSelect).toEqual(true);
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

				component.refs.conditionIf.emitFieldEdited('Nome', 'Nome', jest.fn());
				component.refs.conditionOperator.emitFieldEdited('Is not equal to', 'not-equals-to', jest.fn());

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

				component.refs.conditionIf.emitFieldEdited('', 'Nome', jest.fn());
				component.refs.conditionOperator.emitFieldEdited('Is not equal to', 'not-equals-to', jest.fn());
				component.refs.type.emitFieldEdited('Value', 'text', jest.fn());

				const type = document.querySelector('.condition-type').classList.contains('hide');

				const typeValue = document.querySelector('.condition-type-value').classList.contains('hide');

				const typeValueSelect = document.querySelector('.condition-type-value-select').classList.contains('hide');

				const secondOperandReseted = (conditions[0].operands[1].type === '') && (conditions[0].operands[1].value === '');

				expect(
					!type && !typeValue &&
					typeValueSelect && secondOperandReseted
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

				component.refs.conditionIf.emitFieldEdited('', 'EstadoCivil', jest.fn());
				component.refs.conditionOperator.emitFieldEdited('Is not equal to', 'not-equals-to', jest.fn());
				component.refs.type.emitFieldEdited('Value', 'select', jest.fn());

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

				component.refs.conditionIf.emitFieldEdited('', 'Nome', jest.fn());
				component.refs.conditionOperator.emitFieldEdited('Is not equal to', 'not-equals-to', jest.fn());
				component.refs.type.emitFieldEdited('Value', 'text', jest.fn());
				component.refs.typeValueInput.emitFieldEdited('', 'Nome', jest.fn());

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

				component.refs.conditionIf.emitFieldEdited('', 'Nome', jest.fn());
				component.refs.conditionOperator.emitFieldEdited('Is not equal to', 'not-equals-to', jest.fn());
				component.refs.type.emitFieldEdited('other-field', 'field', jest.fn());
				component.refs.typeValueSelect.emitFieldEdited('', 'Nome', jest.fn());

				expect(component).toMatchSnapshot();
			}
		);
	}
);