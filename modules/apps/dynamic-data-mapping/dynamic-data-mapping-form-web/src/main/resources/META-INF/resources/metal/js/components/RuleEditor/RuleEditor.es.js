import 'clay-button';
import {Config} from 'metal-state';
import {PagesVisitor} from '../../util/visitors.es';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './RuleEditor.soy.js';

/**
 * RuleEditor.
 * @extends Component
 */

class RuleEditor extends Component {
	static STATE = {

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

		conditionTypes: Config.array().internal(),

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
		).internal().valueFn('_firstOperandListValueFn'),

		functionsMetadata: Config.shapeOf(
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

		logicalOperator: Config.string().internal().value('or'),

		operators: Config.arrayOf(
			Config.shapeOf(
				{
					label: Config.string(),
					type: Config.string()
				}
			)
		).internal(),

		pages: Config.array().required(),

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
		).internal().value([]),

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

	syncPages() {
		this.setState(
			{
				firstOperandList: this._firstOperandListValueFn()
			}
		);
	}

	_clearAllFieldsValues(index) {
		let {conditions} = this;

		conditions = this._clearFirstOperandValue(conditions, index);
		conditions = this._clearOperatorValue(conditions, index);
		conditions = this._clearSecondOperandValue(conditions, index);

		this.setState(
			{
				conditions
			}
		);
	}

	_clearFirstOperandValue(conditions, index) {
		if (conditions[index] && conditions[index].operands[0]) {
			conditions[index].operands[0].type = '';
			conditions[index].operands[0].value = '';
		}

		return conditions;
	}

	_clearOperatorValue(conditions, index) {
		if (conditions[index]) {
			conditions[index].operator = '';
		}

		return conditions;
	}

	_clearOperatorValues(index) {
		let {conditions} = this;
		const {secondOperandTypeSelectedList} = this;

		conditions = this._clearOperatorValue(conditions, index);
		conditions = this._clearSecondOperandValue(conditions, index);

		secondOperandTypeSelectedList[index] = {name: '', value: ''};

		this.setState(
			{
				conditions,
				secondOperandTypeSelectedList
			}
		);
	}

	_clearSecondOperandValue(conditions, index) {
		if (conditions[index] && conditions[index].operands[1]) {
			conditions[index].operands[1].type = '';
			conditions[index].operands[1].value = '';
		}

		return conditions;
	}

	_fieldHasOptions(field) {
		return field === 'select' || field === 'radio' || field === 'checkbox';
	}

	_firstOperandListValueFn() {
		const pages = this.pages;
		const value = [];
		const visitor = new PagesVisitor(pages);

		visitor.mapFields(
			field => {
				value.push(
					{
						...field,
						name: field.fieldName,
						value: field.label
					}
				);
			}
		);

		return value;
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

	_handleFirstOperandSelection(event) {
		const {originalEvent, value} = event;
		const fieldName = originalEvent.target.getAttribute('data-option-value');
		const index = this._getConditionIndex(originalEvent, '.condition-if');
		let operators = [];
		let type = '';

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

		const {conditions} = this;
		const operandSelected = {type, value};

		if (this.conditions.length === 0) {
			const operands = [];

			operands.push(operandSelected);

			conditions.push({operands});

		}
		else {
			const previousFirstOperandValue = conditions[index].operands[0].value;

			if (previousFirstOperandValue !== value) {
				conditions[index].operands[0] = operandSelected;
				conditions[index].operator = '';
			}
		}

		const {secondOperandTypeSelectedList} = this;
		const resetedConditionType = {name: '', value: ''};

		secondOperandTypeSelectedList[index] = resetedConditionType;

		this.setState(
			{
				conditions,
				operators,
				secondOperandTypeSelectedList
			}
		);
	}

	_handleOperatorSelection(event) {
		const {originalEvent, value} = event;
		const fieldName = originalEvent.target.getAttribute('data-option-value');
		const index = this._getConditionIndex(originalEvent, '.condition-operator');

		if (fieldName) {
			const {secondOperandTypeList, secondOperandTypeSelectedList} = this;

			if (this._isBinary(fieldName)) {
				secondOperandTypeList[0].name = this.conditions[index].operands[0].type;

				if (secondOperandTypeSelectedList[index] === null) {
					secondOperandTypeSelectedList[index] = {name: '', value: ''};
				}
			}
			else {
				secondOperandTypeSelectedList[index] = null;
			}

			const {conditions} = this;

			conditions[index].operator = value;

			this.setState(
				{
					conditions,
					secondOperandTypeList,
					secondOperandTypeSelectedList
				}
			);
		}
		else {
			this._clearOperatorValues(index);
		}
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

		const {conditions} = this;
		const operandSelected = {type, value};

		conditions[index].operands[1] = operandSelected;

		this.setState(
			{
				conditions
			}
		);
	}

	_handleTypeSelection(event) {
		const {originalEvent, value} = event;
		const fieldName = originalEvent.target.getAttribute('data-option-value');
		const index = this._getConditionIndex(originalEvent, '.condition-type');
		let {conditions} = this;
		let secondOperandTypeSelectedList = [];

		if (this.secondOperandTypeSelectedList) {
			secondOperandTypeSelectedList = this.secondOperandTypeSelectedList;
		}

		const newOperandType = {name: fieldName, value};

		secondOperandTypeSelectedList[index] = newOperandType;

		conditions = this._clearSecondOperandValue(conditions, index);

		this.setState(
			{
				conditions,
				secondOperandTypeSelectedList
			}
		);
	}

	_isBinary(value) {
		return (
			value === 'belongs-to' ||
			value === 'contains' ||
			value === 'equals-to' ||
			value === 'greater-than-equals' ||
			value === 'greater-than' ||
			value === 'less-than-equals' ||
			value === 'less-than' ||
			value === 'not-contains' ||
			value === 'not-equals-to'
		);
	}
}

Soy.register(RuleEditor, templates);

export default RuleEditor;