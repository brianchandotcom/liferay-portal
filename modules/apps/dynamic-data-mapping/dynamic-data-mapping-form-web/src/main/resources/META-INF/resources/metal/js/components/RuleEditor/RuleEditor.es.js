import 'clay-button';
import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './RuleEditor.soy.js';
import {PagesVisitor} from '../../util/visitors.es';

/**
 * RuleEditor.
 * @extends Component
 */

class RuleEditor extends Component {
	static STATE = {
		functionsMetadata:
			Config.shapeOf(
				{
					number: Config.arrayOf(
						Config.shapeOf(
							{
								label: Config.string(),
								name: Config.string(),
								parameterTypes: Config.array(),
								returnType: Config.string()
							}
						)
					),
					text: Config.arrayOf(
						Config.shapeOf(
							{
								label: Config.string(),
								name: Config.string(),
								parameterTypes: Config.array(),
								returnType: Config.string()
							}
						)
					),
					user: Config.arrayOf(
						Config.shapeOf(
							{
								label: Config.string(),
								name: Config.string(),
								parameterTypes: Config.array(),
								returnType: Config.string()
							}
						)
					)
				}
			),

		pages: Config.array().required(),

		/**
		 * @default 0
		 * @instance
		 * @memberof RuleList
		 * @type {?array}
		 */

		actions: Config.arrayOf(
			Config.shapeOf(
				{
					action: Config.string(),
					expression: Config.string(),
					label: Config.string(),
					target: Config.string()
				}
			)
		),

		conditions: Config.arrayOf(
			Config.shapeOf(
				{
					operands: Config.arrayOf(
						Config.shapeOf(
							{
								label: Config.string(),
								repeatable: Config.bool(),
								type: Config.string(),
								value: Config.string()
							}
						)
					),
					operator: Config.string()
				}
			)
		).value([]),

		operatorsList: Config.arrayOf(
			Config.shapeOf(
				{
					label: Config.string(),
					type: Config.string()
				}
			)
		),

		firstOperandList: Config.arrayOf(
			Config.shapeOf(
				{
					dataType: Config.string(),
					name: Config.string(),
					options: Config.arrayOf(
						Config.shapeOf(
							{
								label: Config.string(),
								value: Config.string()
							}
						)
					),
					type: Config.string(),
					value: Config.string()
				}
			)
		),

		readOnly: Config.bool().value(false),

		secondOperandTypeList: Config.arrayOf(
			Config.shapeOf(
				{
					name: Config.string(),
					value: Config.string()
				}
			)
		).value(
			[
				{
					value: Liferay.Language.get('value')
				},
				{
					name: 'field',
					value: Liferay.Language.get('other-field')
				}
			]
		),

		secondOperandTypeSelectedList: Config.arrayOf(
			Config.shapeOf(
				{
					name: Config.string(),
					value: Config.string()
				}
			)
		).value([]),

		conditionTypes: Config.array(),

		logicalOperator: Config.string().value('or'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof RuleList
		 * @type {!string}
		 */

		spritemap: Config.string().required(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof RuleList
		 * @type {!string}
		 */

		strings: {
			value: {
				actions: Liferay.Language.get('actions'),
				and: Liferay.Language.get('and'),
				autofill: Liferay.Language.get('autofill'),
				calculate: Liferay.Language.get('calculate'),
				cancel: Liferay.Language.get('cancel'),
				condition: Liferay.Language.get('condition'),
				description: Liferay.Language.get('define-condition-and-action-to-change-fields-and-elements-on-the-form'),
				do: Liferay.Language.get('do'),
				enable: Liferay.Language.get('enable'),
				if: Liferay.Language.get('if'),
				jumpToPage: Liferay.Language.get('jump-to-page'),
				or: Liferay.Language.get('or'),
				otherField: Liferay.Language.get('other-field'),
				require: Liferay.Language.get('require'),
				save: Liferay.Language.get('save'),
				show: Liferay.Language.get('show'),
				the: Liferay.Language.get('the'),
				title: Liferay.Language.get('rule'),
				value: Liferay.Language.get('value')
			}
		}
	}

	_getFieldsData() {
		const fieldData = [];
		const pages = this.pages;
		const visitor = new PagesVisitor(pages);

		visitor.mapFields(
			field => {
				fieldData.push(
					{
						...field,
						name: field.fieldName,
						value: field.label
					}
				);
			}
		);

		return fieldData;
	}

	_getOperatorsByFieldType(type) {
		if (this._fieldHasOptions(type)) {
			type = 'text';
		}

		return this.functionsMetadata[type].map(
			metadata => {
				return {
					...metadata,
					value: metadata.label
				};
			}
		);
	}

	_getConditionIndex({delegateTarget}, fieldClass) {
		const firstOperand = delegateTarget.closest(fieldClass);

		return firstOperand.getAttribute(`${fieldClass.substring(1)}-index`);
	}

	_getFieldType(fieldName) {
		let fieldType = '';
		const selectedField = this.firstOperandList.filter(
			field => {
				return field.name === fieldName;
			}
		);
		if (selectedField.length > 0) {
			fieldType = selectedField[0].type;
		}

		return fieldType;
	}

	_fieldHasOptions(field) {
		return field === 'select' || field === 'radio' || field === 'checkbox';
	}

	_handleOperatorSelection(event) {
		const {originalEvent, value} = event;
		const fieldName = originalEvent.target.getAttribute('data-option-value');
		const index = this._getConditionIndex(originalEvent, '.condition-operator');

		let copyOfConditions = this.conditions;

		const copyOfsecondOperandTypeSelectedList = this.secondOperandTypeSelectedList;

		if (!fieldName) {
			copyOfConditions = this._clearSecondOperandValue(copyOfConditions, index);

			copyOfsecondOperandTypeSelectedList[index] = {name: '', value: ''};

			copyOfConditions[index].operator = '';
		}
		else {
			const copyOfSecondOperandList = this.secondOperandTypeList;

			const configUnary = {name: 'none', value: 'none'};

			if (this._isBinary(fieldName)) {
				copyOfSecondOperandList[0].name = this.conditions[index].operands[0].type;
				copyOfsecondOperandTypeSelectedList[index] = {name: '', value: ''};
			}
			else {
				copyOfsecondOperandTypeSelectedList[index] = configUnary;
			}

			this.setState(
				{
					secondOperandTypeList: copyOfSecondOperandList,
					secondOperandTypeSelectedList: copyOfsecondOperandTypeSelectedList
				}
			);

			copyOfConditions[index].operator = value;
		}

		this.setState({conditions: copyOfConditions});
	}

	_handleTypeSelection(event) {
		const {originalEvent, value} = event;
		const fieldName = originalEvent.target.getAttribute('data-option-value');

		const index = this._getConditionIndex(originalEvent, '.condition-type');

		let copyOfConditions = this.conditions;

		let copyOfsecondOperandTypeSelectedList = [];

		if (this.secondOperandTypeSelectedList) {
			copyOfsecondOperandTypeSelectedList = this.secondOperandTypeSelectedList;
		}

		const newOperandType = {name: fieldName, value};

		copyOfsecondOperandTypeSelectedList[index] = newOperandType;

		copyOfConditions = this._clearSecondOperandValue(copyOfConditions, index);

		this.setState(
			{
				conditions: copyOfConditions,
				secondOperandTypeSelectedList: copyOfsecondOperandTypeSelectedList
			}
		);
	}

	_handleFirstOperandSelection(event) {
		const {originalEvent, value} = event;

		const fieldName = originalEvent.target.getAttribute('data-option-value');

		const index = this._getConditionIndex(originalEvent, '.condition-if');

		let type = '';

		let operators = [];

		if (fieldName) {
			type = this._getFieldType(fieldName);

			if (type === 'numeric') {
				type = 'number';
			}

			operators = this._getOperatorsByFieldType(type);

		}
		else {
			this._clearAllFieldsValues(index);
		}

		const operandSelected = {type, value};

		const copyOfConditions = this.conditions;

		if (this.conditions.length === 0) {
			const operands = [];

			operands.push(operandSelected);

			copyOfConditions.push({operands});

		}
		else {
			const previousFirstOperandValue = copyOfConditions[index].operands[0].value;

			if (previousFirstOperandValue !== value) {
				copyOfConditions[index].operands[0] = operandSelected;
				copyOfConditions[index].operator = '';
			}
		}

		const copyOfsecondOperandTypeSelectedList = this.secondOperandTypeSelectedList;

		const resetedConditionType = {name: '', value: ''};

		copyOfsecondOperandTypeSelectedList[index] = resetedConditionType;

		this.setState(
			{
				conditions: copyOfConditions,
				operatorsList: operators,
				secondOperandTypeSelectedList: copyOfsecondOperandTypeSelectedList
			}
		);
	}

	_handleSecondOperandSelection(event) {
		const {originalEvent, value} = event;

		let index;

		if (!document.querySelector('.condition-type-value.hide')) {
			index = this._getConditionIndex(originalEvent, '.condition-type-value');
		}
		else if (!document.querySelector('.condition-type-value-select.hide')) {
			index = this._getConditionIndex(originalEvent, '.condition-type-value-select');
		}
		else {
			index = this._getConditionIndex(originalEvent, '.condition-type-value-select-options');
		}

		const fieldName = originalEvent.target.getAttribute('data-option-value');

		let type = '';

		if (fieldName) {
			type = this._getFieldType(fieldName);

			if (type === 'numeric') {
				type = 'number';
			}
		}

		const copyOfConditions = this.conditions;

		const operandSelected = {type, value};

		copyOfConditions[index].operands[1] = operandSelected;

		this.setState(
			{
				conditions: copyOfConditions
			}
		);
	}

	_isBinary(value) {
		return value === 'equals-to' || value === 'not-equals-to' || value === 'contains' || value === 'not-contains' || value === 'belongs-to' || value === 'greater-than' || value === 'greater-than-equals' || value === 'less-than' || value === 'less-than-equals';
	}

	_clearOperatorValue(copyOfConditions, index) {
		copyOfConditions[index].operator = '';

		return copyOfConditions;
	}

	_clearFirstOperandValue(copyOfConditions, index) {
		if (copyOfConditions[index].operands[0]) {
			copyOfConditions[index].operands[0].type = '';
			copyOfConditions[index].operands[0].value = '';
		}

		return copyOfConditions;
	}

	_clearSecondOperandValue(copyOfConditions, index) {
		if (copyOfConditions[index].operands[1]) {
			copyOfConditions[index].operands[1].type = '';
			copyOfConditions[index].operands[1].value = '';
		}

		return copyOfConditions;
	}

	_clearAllFieldsValues(index) {
		let copyOfConditions = this.conditions;

		copyOfConditions = this._clearOperatorValue(copyOfConditions, index);
		copyOfConditions = this._clearFirstOperandValue(copyOfConditions, index);
		copyOfConditions = this._clearSecondOperandValue(copyOfConditions, index);

		this.setState(
			{
				conditions: copyOfConditions
			}
		);
	}

	attached() {
		const addButton = document.querySelector('#addFieldButton');

		if (addButton) {
			addButton.classList.add('hide');
		}
	}

	created() {
		this.setState(
			{
				firstOperandList: this._getFieldsData()
			}
		);
	}
}

Soy.register(RuleEditor, templates);

export default RuleEditor;