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
		value: 'Nome',
		type: 'text',
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
		value: 'Sobrenome',
		type: 'text',
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
		value: 'Estado civil',
		type: 'select',
	}
];

const secondOperandTypeSelectedList = [
	{
		name: '',
		value: ''
	}
];

const conditionOperatorList = [
	{value: 'Is equal to', name: 'equals-to', parameterTypes: ['text', 'text'], returnType: 'boolean'},
	{value: 'Is not equal to', name: 'not-equals-to', parameterTypes: ['text', 'text'], returnType: 'boolean'},
	{value: 'Contains', name: 'contains', parameterTypes: ['text', 'text'], returnType: 'boolean'},
	{value: 'Does not contain', name: 'not-contains', parameterTypes: ['text', 'text'], returnType: 'boolean'},
	{value: 'Is empty', name: 'is-empty', parameterTypes: ['text'], returnType: 'boolean'},
	{value: 'Is not empty', name: 'not-is-empty', parameterTypes: ['text'], returnType: 'boolean'}
];

const functionsMetadata = {text: [
	{value: 'Is equal to', name: 'equals-to', parameterTypes: ['text', 'text'], returnType: 'boolean'},
	{value: 'Is not equal to', name: 'not-equals-to', parameterTypes: ['text', 'text'], returnType: 'boolean'},
	{value: 'Contains', name: 'contains', parameterTypes: ['text', 'text'], returnType: 'boolean'},
	{value: 'Does not contain', name: 'not-contains', parameterTypes: ['text', 'text'], returnType: 'boolean'},
	{value: 'Is empty', name: 'is-empty', parameterTypes: ['text'], returnType: 'boolean'},
	{value: 'Is not empty', name: 'not-is-empty', parameterTypes: ['text'], returnType: 'boolean'}
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

				const conditions = [{
					operands: [{
						type: 'text',
						value: 'Nome'
					}],
					operator: 'Is not equal to'
				}];

				component = new RuleEditor(
					{
						conditionOperatorList,
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

				component = new RuleEditor(
					{
						conditionOperatorList,
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

				component = new RuleEditor(
					{
						conditionOperatorList,
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

				component = new RuleEditor(
					{
						conditionOperatorList,
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
			'should reset and show fourth "select" selector when first, second and third have options selected and Third selector is other-field',
			() => {

				const conditions = [
					{
						operands: [
							{
								type: 'select',
								value: 'Estado civil'
							},
							{
								type: '',
								value: 'dghdg'
							}
						],
						operator: 'Is not equal to'
					}
				];

				component = new RuleEditor(
					{
						conditionOperatorList,
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
				component.refs.type.emitFieldEdited('other-field', 'field', jest.fn());

				const type = document.querySelector('.condition-type').classList.contains('hide');
				const typeValue = document.querySelector('.condition-type-value').classList.contains('hide');
				const typeValueSelect = document.querySelector('.condition-type-value-select').classList.contains('hide');
				const typeValueSelectOptions = document.querySelector('.condition-type-value-select-options').classList.contains('hide');

				const secondOperandReseted = (conditions[0].operands[1].type === '') && (conditions[0].operands[1].value === '');

				expect(
					!type && typeValue &&
					!typeValueSelect && typeValueSelectOptions
					&& secondOperandReseted
				).toEqual(true);
			}
		);

		it(
			'should reset all fields selectors when first selector has no option is selected',
			() => {

				const conditions = [
					{
						operands: [
							{
								type: 'select',
								value: 'Estado civil'
							},
							{
								type: '',
								value: 'dghdg'
							}
						],
						operator: 'Is not equal to'
					}
				];

				component = new RuleEditor(
					{
						conditionOperatorList,
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
				component.refs.type.emitFieldEdited('other-field', 'field', jest.fn());
				component.refs.typeValueSelect.emitFieldEdited('text', 'Sobrenome', jest.fn());
				component.refs.conditionIf.emitFieldEdited('', '', jest.fn());

				const type = document.querySelector('.condition-type').classList.contains('hide');
				const typeValue = document.querySelector('.condition-type-value').classList.contains('hide');
				const typeValueSelect = document.querySelector('.condition-type-value-select').classList.contains('hide');
				const typeValueSelectOptions = document.querySelector('.condition-type-value-select-options').classList.contains('hide');

				const firstOperandReseted = (conditions[0].operands[0].type === '') && (conditions[0].operands[0].value === '');
				const secondOperandReseted = (conditions[0].operands[1].type === '') && (conditions[0].operands[1].value === '');
				const operatorReseted = (conditions[0].operator === '');

				expect(
					type && typeValue && typeValueSelect &&
					typeValueSelectOptions && firstOperandReseted &&
					secondOperandReseted && operatorReseted
				).toEqual(true);
			}
		);

		it(
			'should reset third and fourth fields selectors when second selector has no option is selected',
			() => {

				const conditions = [
					{
						operands: [
							{
								type: 'select',
								value: 'Estado civil'
							},
							{
								type: '',
								value: 'dghdg'
							}
						],
						operator: 'Is not equal to'
					}
				];

				component = new RuleEditor(
					{
						firstOperandList,
						conditions,
						conditionOperatorList,
						functionsMetadata,
						pages,
						secondOperandTypeSelectedList,
						spritemap
					}
				);

				component.refs.conditionIf.emitFieldEdited('', 'EstadoCivil', jest.fn());
				component.refs.conditionOperator.emitFieldEdited('Is not equal to', 'not-equals-to', jest.fn());
				component.refs.type.emitFieldEdited('other-field', 'field', jest.fn());
				component.refs.typeValueSelect.emitFieldEdited('text', 'Sobrenome', jest.fn());
				component.refs.conditionOperator.emitFieldEdited('', 'Choose an option', jest.fn());

				const type = document.querySelector('.condition-type').classList.contains('hide');
				const typeValue = document.querySelector('.condition-type-value').classList.contains('hide');
				const typeValueSelect = document.querySelector('.condition-type-value-select').classList.contains('hide');
				const typeValueSelectOptions = document.querySelector('.condition-type-value-select-options').classList.contains('hide');

				const firstOperandReseted = (conditions[0].operands[0].type === '') && (conditions[0].operands[0].value === '');
				const secondOperandReseted = (conditions[0].operands[1].type === '') && (conditions[0].operands[1].value === '');
				const operatorReseted = (conditions[0].operator === '');

				expect(
					type && typeValue && typeValueSelect &&
					typeValueSelectOptions && !firstOperandReseted &&
					secondOperandReseted && operatorReseted
				).toEqual(true);
			}
		);

		it(
			'should reset fourth field selector when third selector has no option is selected',
			() => {

				const conditions = [
					{
						operands: [
							{
								type: 'select',
								value: 'Estado civil'
							},
							{
								type: '',
								value: 'dghdg'
							}
						],
						operator: 'Is not equal to'
					}
				];

				component = new RuleEditor(
					{
						firstOperandList,
						conditions,
						conditionOperatorList,
						functionsMetadata,
						pages,
						secondOperandTypeSelectedList,
						spritemap
					}
				);

				component.refs.conditionIf.emitFieldEdited('', 'EstadoCivil', jest.fn());
				component.refs.conditionOperator.emitFieldEdited('Is not equal to', 'not-equals-to', jest.fn());
				component.refs.type.emitFieldEdited('other-field', 'field', jest.fn());
				component.refs.typeValueSelect.emitFieldEdited('text', 'Sobrenome', jest.fn());
				component.refs.type.emitFieldEdited('', 'Choose an option', jest.fn());

				const firstOperandReseted = (conditions[0].operands[0].type === '') && (conditions[0].operands[0].value === '');
				const secondOperandReseted = (conditions[0].operands[1].type === '') && (conditions[0].operands[1].value === '');
				const operatorReseted = (conditions[0].operator === '');

				expect(!firstOperandReseted && secondOperandReseted && !operatorReseted).toEqual(true);
			}
		);
	}

);
