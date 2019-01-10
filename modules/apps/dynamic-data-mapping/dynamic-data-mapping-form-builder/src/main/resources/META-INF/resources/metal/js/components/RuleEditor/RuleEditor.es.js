import 'clay-button';
import 'clay-modal';
import {Config} from 'metal-state';
import {PagesVisitor} from '../../util/visitors.es';
import '../Calculator/Calculator.es';
import '../Page/PageRenderer.es';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './RuleEditor.soy.js';
import {makeFetch} from '../../util/fetch.es';

const fieldOptionStructure = Config.shapeOf(
	{
		dataType: Config.string(),
		name: Config.string(),
		options: Config.arrayOf(
			Config.shapeOf(
				{
					label: Config.string(),
					name: Config.string(),
					value: Config.string()
				}
			)
		),
		type: Config.string(),
		value: Config.string()
	}
);

/**
 * RuleEditor.
 * @extends Component
 */

class RuleEditor extends Component {
	static STATE = {
		actions: Config.arrayOf(
			Config.shapeOf(
				{
					action: Config.string(),
					calculatorFields: Config.arrayOf(fieldOptionStructure).value([]),
					expression: Config.string(),
					hasRequiredInputs: Config.bool(),
					inputs: Config.arrayOf(
						Config.shapeOf(
							{
								fieldOptions: Config.arrayOf(fieldOptionStructure),
								label: Config.string(),
								name: Config.string(),
								required: Config.bool(),
								type: Config.string(),
								value: Config.string()
							}
						)
					),
					label: Config.string(),
					outputs: Config.arrayOf(
						Config.shapeOf(
							{
								fieldOptions: Config.arrayOf(fieldOptionStructure),
								name: Config.string(),
								type: Config.string(),
								value: Config.string()
							}
						)
					),
					target: Config.string()
				}
			)
		).internal().setter('_setActions').value([]),

		actionTypes: Config.arrayOf(
			Config.shapeOf(
				{
					label: Config.string(),
					value: Config.string()
				}
			)
		).internal().value(
			[
				{
					label: 'Show',
					value: 'show'
				},
				{
					label: 'Enable',
					value: 'enable'
				},
				{
					label: 'Require',
					value: 'require'
				},
				{
					label: 'Autofill',
					value: 'auto-fill'
				},
				{
					label: 'Calculate',
					value: 'calculate'
				}
			]
		),

		/**
		 * Used for tracking which action we are currently focused on
		 * when trying to delete an action.
		 * @default 0
		 * @instance
		 * @memberof RuleEditor
		 * @type {Number}
		 */
		activeActionIndex: Config.number().value(-1),

		/**
		 * Used for tracking which condition we are currently focused on
		 * when trying to delete a condition.
		 * @default 0
		 * @instance
		 * @memberof RuleEditor
		 * @type {Number}
		 */

		activeConditionIndex: Config.number().value(-1),

		calculatorOptions: Config.arrayOf(
			Config.shapeOf(
				{
					label: Config.string(),
					tooltip: Config.string(),
					value: Config.string()
				}
			)
		).internal().value([]),

		calculatorResultOptions: Config.arrayOf(fieldOptionStructure).internal().valueFn('_calculatorResultOptionsValueFn'),

		/**
		 * @default 0
		 * @instance
		 * @memberof RuleEditor
		 * @type {?array}
		 */

		conditions: Config.arrayOf(
			Config.shapeOf(
				{
					operands: Config.arrayOf(
						Config.shapeOf(
							{
								dataType: Config.string(),
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
		).internal().setter('_setConditions').value([]),

		dataProvider: Config.arrayOf(
			Config.shapeOf(
				{
					id: Config.string(),
					name: Config.string(),
					uuid: Config.string()
				}
			)
		).internal(),

		dataProviderInstanceParameterSettingsURL: Config.string().required(),

		dataProviderInstancesURL: Config.string().required(),

		functionsURL: Config.string(),

		fieldOptions: Config.arrayOf(
			Config.shapeOf(
				{
					dataType: Config.string(),
					name: Config.string(),
					options: Config.arrayOf(
						Config.shapeOf(
							{
								label: Config.string(),
								name: Config.string(),
								value: Config.string()
							}
						)
					),
					type: Config.string(),
					value: Config.string()
				}
			)
		).internal().valueFn('_fieldOptionsValueFn'),

		deletedFields: Config.arrayOf(Config.string()).value([]),

		fixedOptions: Config.arrayOf(
			fieldOptionStructure
		).value(
			[
				{
					dataType: 'user',
					label: Liferay.Language.get('user'),
					name: 'user',
					value: 'user'
				}
			]
		),

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

		invalidRule: Config.bool().value(true),

		logicalOperator: Config.string().internal().value('or'),

		pages: Config.array().required(),

		readOnly: Config.bool().value(false),

		roles: Config.arrayOf(
			Config.shapeOf(
				{
					id: Config.string(),
					name: Config.string()
				}
			)
		).value([]),

		/**
		 * @default 0
		 * @instance
		 * @memberof RuleEditor
		 * @type {?array}
		 */

		rule: Config.shapeOf(
			{
				actions: Config.arrayOf(
					Config.shapeOf(
						{
							action: Config.string(),
							calculatorFields: Config.arrayOf(fieldOptionStructure).value([]),
							ddmDataProviderInstanceUUID: Config.string(),
							expression: Config.string(),
							inputs: Config.object(),
							label: Config.string(),
							outputs: Config.object(),
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
				),
				['logical-operator']: Config.string()
			}
		),

		ruleEditedIndex: Config.number(),

		secondOperandList: Config.arrayOf(
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

		/**
		 * @default undefined
		 * @instance
		 * @memberof RuleEditor
		 * @type {!string}
		 */

		spritemap: Config.string().required(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof RuleEditor
		 * @type {!string}
		 */

		strings: {
			value: {
				actions: Liferay.Language.get('actions'),
				addField: Liferay.Language.get('add-field'),
				and: Liferay.Language.get('and'),
				autofill: Liferay.Language.get('autofill'),
				calculate: Liferay.Language.get('calculate'),
				cancel: Liferay.Language.get('cancel'),
				chooseAnOption: Liferay.Language.get('choose-an-option'),
				condition: Liferay.Language.get('condition'),
				dataProviderParameterInput: Liferay.Language.get('data-provider-parameter-input'),
				dataProviderParameterInputDescription: Liferay.Language.get('data-provider-parameter-input-description'),
				dataProviderParameterOutput: Liferay.Language.get('data-provider-parameter-output'),
				dataProviderParameterOutputDescription: Liferay.Language.get('data-provider-parameter-output-description'),
				delete: Liferay.Language.get('delete'),
				deleteAction: Liferay.Language.get('delete-action'),
				deleteActionQuestion: Liferay.Language.get('are-you-sure-you-want-to-delete-this-action'),
				deleteCondition: Liferay.Language.get('delete-condition'),
				deleteConditionQuestion: Liferay.Language.get('are-you-sure-you-want-to-delete-this-condition'),
				description: Liferay.Language.get('define-condition-and-action-to-change-fields-and-elements-on-the-form'),
				dismiss: Liferay.Language.get('dismiss'),
				do: Liferay.Language.get('do'),
				enable: Liferay.Language.get('enable'),
				fromDataProvider: Liferay.Language.get('from-data-provider'),
				if: Liferay.Language.get('if'),
				jumpToPage: Liferay.Language.get('jump-to-page'),
				or: Liferay.Language.get('or'),
				otherField: Liferay.Language.get('other-field'),
				require: Liferay.Language.get('require'),
				requiredField: Liferay.Language.get('required-field'),
				save: Liferay.Language.get('save'),
				show: Liferay.Language.get('show'),
				showTheResult: Liferay.Language.get('choose-a-field-to-show-the-result'),
				the: Liferay.Language.get('the'),
				theExpressionWillBeDisplayedHere: Liferay.Language.get('the-expression-will-be-displayed-here'),
				title: Liferay.Language.get('rule'),
				user: Liferay.Language.get('user'),
				value: Liferay.Language.get('value')
			}
		}
	}

	created() {
		this._fetchFunctionsURL();

		if (this.rule) {
			this._prepareRuleEditor();
		}
	}

	_prepareRuleEditor() {
		const {rule} = this;

		const actions = this._syncActions(rule.actions);

		this.setState(
			{
				actions,
				conditions: rule.conditions,
				logicalOperator: rule['logical-operator']
			}
		);
	}

	disposed() {
		this.setState(
			{
				actions: [],
				conditions: [],
				logicalOperator: ''
			}
		);
	}

	prepareStateForRender(state) {
		const conditions = state.conditions.map(
			condition => {
				const fieldName = condition.operands[0].value;
				let firstOperandOptions = [];
				let operators = [];

				if (fieldName) {
					const {dataType} = this._getFieldTypeByFieldName(fieldName);

					operators = this._getOperatorsByFieldType(dataType);

					firstOperandOptions = this.getFieldOptions(fieldName);
				}

				return {
					...condition,
					binaryOperator: this._isBinary(condition.operator),
					firstOperandOptions,
					operators
				};
			}
		);

		return {
			...state,
			conditions
		};
	}

	willUpdate() {
		this.setState(
			{
				invalidRule: !this._validateConditionsFilling() || !this._validateActionsFilling()
			}
		);
	}

	getFieldOptions(fieldName) {
		let options = [];
		const visitor = new PagesVisitor(this.pages);

		visitor.mapFields(
			field => {
				if (field.fieldName === fieldName) {
					options = field.options;
				}
			}
		);

		return options;
	}

	getTypesByFieldType(fieldType) {
		let list = [];

		if (fieldType == 'list') {
			list = ['select', 'checkbox_multiple', 'radio'];
		}
		else if (fieldType == 'text') {
			list = ['select', 'checkbox_multiple', 'radio', 'text', 'numeric'];
		}
		else if (fieldType == 'number') {
			list = ['numeric'];
		}

		return list;
	}

	getFieldsByTypes(fields, types) {
		return fields.filter(field => types.some(fieldType => field.type == fieldType));
	}

	syncPages(pages) {
		const {actions} = this;
		let {conditions} = this;

		const visitor = new PagesVisitor(pages);

		conditions.forEach(
			(condition, index) => {
				let firstOperandFieldExists = false;
				const secondOperand = condition.operands[1];
				let secondOperandFieldExists = false;

				visitor.mapFields(
					({fieldName}) => {
						if (condition.operands[0].value === fieldName) {
							firstOperandFieldExists = true;
						}

						if (secondOperand && secondOperand.value === fieldName) {
							secondOperandFieldExists = true;
						}
					}
				);

				if (condition.operands[0].value === 'user') {
					firstOperandFieldExists = true;
				}

				if (!firstOperandFieldExists) {
					conditions = this._clearAllConditionFieldValues(index);
				}

				if (!secondOperandFieldExists && secondOperand && secondOperand.type == 'field') {
					conditions = this._clearSecondOperandValue(conditions, index);
				}
			}
		);

		this.setState(
			{
				actions: this._syncActions(actions),
				calculatorResultOptions: this._calculatorResultOptionsValueFn(),
				conditions,
				deletedFields: this._getDeletedFields(visitor),
				fieldOptions: this._fieldOptionsValueFn()
			}
		);
	}

	_syncActions(actions) {
		const {pages} = this;

		const visitor = new PagesVisitor(pages);

		actions.forEach(
			(action, index) => {
				let targetFieldExists = false;

				visitor.mapFields(
					({fieldName}) => {
						if (action.target === fieldName) {
							targetFieldExists = true;
						}
					}
				);

				if (!targetFieldExists) {
					action.target = '';
				}

				action.calculatorFields = this._updateCalculatorFields(action, action.target);
			}
		);

		return actions;
	}

	_getDeletedFields(visitor) {
		const existentFields = [];
		const {fieldOptions} = this;
		let deletedFields = [];

		fieldOptions.forEach(
			field => {
				visitor.mapFields(
					({fieldName}) => {
						if (field.fieldName === fieldName) {
							existentFields.push(fieldName);
						}
					}
				);
			}
		);

		const oldFields = fieldOptions.map(field => field.fieldName);

		deletedFields = oldFields.filter(
			field => {
				return existentFields.indexOf(field) > -1 ? false : field;
			}
		);

		return deletedFields;
	}

	syncVisible(visible) {
		const addButton = document.querySelector('#addFieldButton');

		super.syncVisible(visible);

		if (addButton && visible) {
			addButton.classList.add('hide');
		}
	}

	_clearAction(index) {
		const {actions} = this;

		const newActions = actions;

		newActions[index] = {
			action: '',
			calculatorFields: [],
			expression: '',
			inputs: [],
			label: '',
			outputs: [],
			target: ''
		};

		this.setState(
			{
				actions: newActions
			}
		);
	}

	_clearAllConditionFieldValues(index) {
		const {secondOperandSelectedList} = this;
		let {conditions} = this;

		conditions = this._clearFirstOperandValue(conditions, index);
		conditions = this._clearOperatorValue(conditions, index);
		conditions = this._clearSecondOperandValue(conditions, index);

		this.setState(
			{
				conditions,
				secondOperandSelectedList
			}
		);

		return conditions;
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

	_clearSecondOperandValue(conditions, index) {
		if (conditions[index] && conditions[index].operands[1]) {
			conditions[index].operands = [conditions[index].operands[0]];
		}

		return conditions;
	}

	_clearSelectedSecondOperand(secondOperandSelectedList, index) {
		return secondOperandSelectedList;
	}

	_fetchDataProviderParameters(id, index) {
		const {actions, dataProviderInstanceParameterSettingsURL} = this;

		const url = dataProviderInstanceParameterSettingsURL.slice(0, dataProviderInstanceParameterSettingsURL.length - 1);

		return makeFetch(
			{
				method: 'GET',
				url: `${url}?ddmDataProviderInstanceId=${id}`
			}
		).then(
			({inputs, outputs}) => {
				actions[index].inputs = [];
				actions[index].outputs = [];

				if (!this.isDisposed() && inputs.length && outputs.length) {
					const newInput = {
						...inputs[0],
						[inputs[0].name]: ''
					};
					const newOutput = {
						...outputs[0],
						[outputs[0].name]: ''
					};

					actions[index].inputs = [newInput];
					actions[index].outputs = [newOutput];
				}

				return actions;
			}
		).catch(
			error => {
				throw new Error(error);
			}
		);
	}

	_fetchFunctionsURL() {
		const {functionsURL} = this;

		makeFetch(
			{
				method: 'GET',
				url: functionsURL
			}
		).then(
			responseData => {
				if (!this.isDisposed()) {

					this.setState(
						{
							calculatorOptions: responseData
						}
					);
				}
			}
		).catch(
			error => {
				throw new Error(error);
			}
		);
	}

	_fieldOptionsValueFn() {
		const {pages} = this;
		const fields = [];
		const visitor = new PagesVisitor(pages);

		visitor.mapFields(
			field => {
				fields.push(
					{
						...field,
						options: field.options ? field.options : [],
						value: field.fieldName
					}
				);
			}
		);

		return fields;
	}

	_calculatorResultOptionsValueFn() {
		const {pages} = this;
		const fields = [];
		const visitor = new PagesVisitor(pages);

		visitor.mapFields(
			field => {
				if (field.type == 'numeric') {
					fields.push(
						{
							...field,
							options: field.options ? field.options : [],
							value: field.fieldName
						}
					);
				}
			}
		);

		return fields;
	}

	_getIndex(fieldInstance, fieldClass) {
		const firstOperand = fieldInstance.element.closest(fieldClass);

		return firstOperand.getAttribute(`${fieldClass.substring(1)}-index`);
	}

	_getFieldTypeByFieldName(fieldName) {
		let dataType = '';
		let repeatable = false;
		let type = '';

		if (fieldName === 'user') {
			dataType = 'user';
		}
		else {
			const selectedField = this.fieldOptions.find(
				field => field.value === fieldName
			);

			if (selectedField) {
				dataType = selectedField.dataType;
				repeatable = selectedField.repeatable;
				type = selectedField.type;
			}
		}

		return {dataType, repeatable, type};
	}

	_getOperatorsByFieldType(fieldType) {
		if (fieldType === 'integer' || fieldType === 'double') {
			fieldType = 'number';
		}

		if (!this.functionsMetadata.hasOwnProperty(fieldType)) {
			fieldType = 'text';
		}

		return this.functionsMetadata[fieldType].map(
			metadata => {
				return {
					...metadata,
					value: metadata.name
				};
			}
		);
	}

	_handleActionAdded() {
		const {actions} = this;
		const newAction = {action: '', calculatorFields: [], expression: '', inputs: [], label: '', outputs: [], target: ''};

		if (actions.length == 0) {
			actions.push(newAction);
		}

		actions.push(newAction);

		this.setState(
			{
				actions,
				invalidRule: true
			}
		);
	}

	_handleActionSelection(event) {
		const {fieldInstance, value} = event;
		const index = this._getIndex(fieldInstance, '.action-type');

		const {actions} = this;

		const newActions = actions;

		if (value && value.length > 0 && value[0]) {
			const fieldName = value[0];

			if (actions.length > 0) {
				const previousAction = actions[index].action;

				if (fieldName !== previousAction) {
					newActions[index].action = fieldName;
					newActions[index].calculatorFields = [];
					newActions[index].label = '';
					newActions[index].target = '';
				}
			}
			else {
				newActions.push({action: fieldName});
			}
		}
		else {
			this._clearAction(index);
		}

		this.setState(
			{
				actions: newActions
			}
		);
	}

	_handleConditionAdded() {
		const {conditions} = this;

		conditions.push(
			{
				operands: [
					{
						type: '',
						value: ''
					}
				],
				operator: ''
			}
		);

		this.setState(
			{
				conditions
			}
		);
	}

	_handleCancelRule(event) {
		this.emit(
			'ruleCancel',
			{}
		);
	}

	_handleDataProviderInputEdited(event) {
		const {fieldInstance, value} = event;
		const {actions} = this;
		const actionIndex = this._getIndex(fieldInstance, '.action');
		const inputIndex = this._getIndex(fieldInstance, '.container-input-field');

		const name = actions[actionIndex].inputs[inputIndex].name;

		actions[actionIndex].inputs[inputIndex].value = value[0];
		actions[actionIndex].inputs[inputIndex][name] = value[0];

		this.setState(
			{
				actions
			}
		);
	}

	_handleDataProviderOutputEdited(event) {
		const {fieldInstance, value} = event;
		const actionIndex = this._getIndex(fieldInstance, '.action');
		const outputIndex = this._getIndex(fieldInstance, '.container-output-field');
		const {actions} = this;

		const name = actions[actionIndex].outputs[outputIndex].name;

		actions[actionIndex].outputs[outputIndex].value = value[0];
		actions[actionIndex].outputs[outputIndex][name] = value[0];

		this.setState(
			{
				actions
			}
		);
	}

	_handleDeleteAction(event) {
		const {currentTarget} = event;
		const index = currentTarget.getAttribute('data-index');

		this.refs.confirmationModalAction.show();
		this.setState(
			{
				activeActionIndex: parseInt(index, 10)
			}
		);
	}

	_handleDeleteCondition(event) {
		const {currentTarget} = event;
		const index = currentTarget.getAttribute('data-index');

		this.refs.confirmationModalCondition.show();
		this.setState(
			{
				activeConditionIndex: parseInt(index, 10)
			}
		);
	}

	_handleEditExpression({expression, index}) {
		const {actions} = this;

		actions[index].expression = expression;
		this.setState({actions});
	}

	_getFieldLabel(fieldName) {
		const pages = this.pages;

		let fieldLabel;

		if (pages) {
			for (let page = 0; page < pages.length; page++) {
				const rows = pages[page].rows;

				for (let row = 0; row < rows.length; row++) {
					const cols = rows[row].columns;

					for (let col = 0; col < cols.length; col++) {
						const fields = cols[col].fields;

						for (let field = 0; field < fields.length; field++) {
							if (pages[page].rows[row].columns[col].fields[field].fieldName === fieldName) {
								fieldLabel = pages[page].rows[row].columns[col].fields[field].label;
								break;
							}
						}
					}
				}
			}
		}

		return fieldLabel;
	}

	_handleFirstOperandFieldEdited(event) {
		const {fieldInstance, value} = event;
		const index = this._getIndex(fieldInstance, '.condition-if');
		let {conditions} = this;

		if (value && value.length > 0 && value[0]) {
			const fieldName = value[0];
			const {dataType, repeatable} = this._getFieldTypeByFieldName(fieldName);

			const firstOperand = {
				label: this._getFieldLabel(fieldName),
				repeatable,
				type: dataType == 'user' ? 'user' : 'field',
				value: fieldName
			};

			if (conditions.length === 0) {
				const operands = [firstOperand];

				conditions.push({operands});
			}
			else {
				if (fieldName !== conditions[index].operands[0].value) {
					conditions[index].operator = '';
				}

				conditions[index].operands[0] = firstOperand;

				this._clearSecondOperandValue(conditions, index);
			}
		}
		else {
			conditions = this._clearAllConditionFieldValues(index);
		}

		this.setState(
			{
				conditions
			}
		);
	}

	_handleLogicalOperationChange(event) {
		const {target} = event;

		const logicalOperatorSelected = target.innerHTML;

		let {logicalOperator} = this;

		if (logicalOperatorSelected != logicalOperator) {
			logicalOperator = logicalOperatorSelected.toLowerCase();

			this.setState(
				{
					logicalOperator
				}
			);
		}
	}

	_handleModalButtonClicked(event) {
		event.stopPropagation();

		if (!event.target.classList.contains('close-modal')) {
			const activeActionIndex = this.activeActionIndex;
			const activeConditionIndex = this.activeConditionIndex;

			const {actions, conditions} = this;

			if (activeConditionIndex > -1) {
				conditions.splice(activeConditionIndex, 1);
			}

			if (activeActionIndex > -1) {
				actions.splice(activeActionIndex, 1);
			}

			if (this.refs.confirmationModalAction.visible) {
				this.refs.confirmationModalAction.emit('hide');
			}

			if (this.refs.confirmationModalCondition.visible) {
				this.refs.confirmationModalCondition.emit('hide');
			}

			this.setState(
				{
					actions,
					activeActionIndex: -1,
					activeConditionIndex: -1,
					conditions
				}
			);

		}
	}

	_handleOperatorEdited(event) {
		const {fieldInstance, value} = event;
		let {conditions} = this;
		let operatorValue = '';

		if (value && value.length > 0 && value[0]) {
			operatorValue = value[0];
		}

		const index = this._getIndex(fieldInstance, '.condition-operator');

		if (!operatorValue || !this._isBinary(operatorValue)) {
			conditions = this._clearSecondOperandValue(conditions, index);

			conditions[index].operator = operatorValue;
		}
		else {
			conditions[index].operator = operatorValue;
		}

		this.setState(
			{
				conditions
			}
		);
	}

	_handleRuleAdded(event) {
		const actions = this._removeActionInternalProperties();

		const conditions = this._removeConditionInternalProperties();

		const {ruleEditedIndex} = this;

		this.emit(
			'ruleAdded',
			{
				actions,
				conditions,
				['logical-operator']: this.logicalOperator,
				ruleEditedIndex
			}
		);
	}

	_handleSecondOperandFieldEdited(event) {
		const {conditions, roles} = this;
		const {fieldInstance, value} = event;
		let fieldValue = '';

		if (value && typeof (value) == 'object' && value[0]) {
			fieldValue = value[0];
		}
		else if (value && typeof (value) == 'string') {
			fieldValue = value;
		}

		let index;

		if (fieldInstance.element.closest('.condition-type-value')) {
			index = this._getIndex(fieldInstance, '.condition-type-value');
		}

		let secondOperand = conditions[index].operands[1];

		if (!secondOperand) {
			secondOperand = {
				dataType: fieldInstance.dataType,
				type: fieldInstance.type
			};
		}

		let roleLabel = '';
		let userType = '';

		if (conditions[index].operands[0].type === 'user') {
			roleLabel = roles.find(role => role.id === fieldValue);
			userType = conditions[index].operands[0].type;
		}

		conditions[index].operands[1] = {
			...secondOperand,
			dataType: fieldInstance.dataType,
			label: roleLabel ? roleLabel.label : '',
			type: userType ? userType : fieldInstance.type,
			value: fieldValue
		};

		this.setState(
			{
				conditions
			}
		);
	}

	_handleSecondOperandValueEdited(event) {
		const {conditions} = this;
		const {fieldInstance, value} = event;
		const index = this._getIndex(fieldInstance, '.condition-type-value');
		const secondOperandValue = Array.isArray(value) ? value[0] : value;

		this.setState(
			{
				conditions: conditions.map(
					(condition, conditionIndex) => {
						const operands = [...condition.operands];

						if (index == conditionIndex) {
							operands[1] = {
								...operands[1],
								value: secondOperandValue
							};
						}

						return {
							...condition,
							operands
						};
					}
				)
			}
		);
	}

	_handleSecondOperandTypeEdited(event) {
		let {conditions} = this;
		const {fieldInstance, value} = event;
		const index = this._getIndex(fieldInstance, '.condition-type');
		const {operands} = conditions[index];
		const secondOperand = operands[1];

		let secondOperandType = 'field';
		let valueType = 'field';

		if (value[0] == 'value') {
			valueType = 'string';
			secondOperandType = this._getFieldTypeByFieldName(operands[0].value).type;
		}

		if (secondOperand && ((secondOperand.type === secondOperandType)) && value[0] !== '') {
			return;
		}

		if ((value[0] == '')) {
			conditions = this._clearSecondOperandValue(conditions, index);
		}
		else if (secondOperand && secondOperand.dataType != valueType) {
			conditions[index].operands[1].type = '';
			conditions[index].operands[1].value = '';
		}

		if (secondOperand) {
			secondOperand.type = secondOperandType;
		}
		else if ((value[0] !== '')) {
			conditions[index].operands.push(
				{
					type: secondOperandType,
					value: ''
				}
			);
		}

		this.setState(
			{
				conditions
			}
		);
	}

	_handleTargetSelection(event) {
		const {fieldInstance, value} = event;
		const id = value[0];
		const index = this._getIndex(fieldInstance, '.target-action');
		const {actions} = this;

		const previousTarget = this.actions[index].target;

		actions[index].target = id;
		actions[index].label = id;

		if (id === undefined) {
			actions[index].target = '';
		}
		else if (id === '') {
			actions[index].inputs = [];
			actions[index].outputs = [];
			actions[index].hasRequiredInputs = false;
		}
		else if (previousTarget !== id) {
			if (this.actions[index].action == 'auto-fill') {
				this.getDataProviderOptions(id, index);
			}
			else if (this.actions[index].action == 'calculate') {
				actions[index].calculatorFields = this._updateCalculatorFields(this.actions[index], id);
			}
		}

		this.setState(
			{
				actions
			}
		);
	}

	getDataProviderOptions(id, index) {
		this._fetchDataProviderParameters(id, index)
			.then(
				actions => {
					if (!this.isDisposed()) {
						actions[index] = {
							...actions[index],
							inputs: this.formatDataProviderParameter(actions[index].inputs),
							outputs: this.formatDataProviderParameter(actions[index].outputs)
						};

						actions[index].ddmDataProviderInstanceUUID = this.dataProvider.find(
							data => {
								return data.id == id;
							}
						).uuid;

						actions[index].hasRequiredInputs = (actions[index].inputs)
							.some(
								input => {
									return input.required;
								}
							);

						this.setState(
							{
								actions
							}
						);
					}
				}
			);
	}

	_updateCalculatorFields(action, id) {
		const {calculatorResultOptions} = this;

		return calculatorResultOptions.reduce(
			(prev, option) => {
				return (option.fieldName === id) ? prev : [
					...prev,
					{
						...option,
						title: option.fieldName,
						type: 'item'
					}
				];
			},
			[]
		);
	}

	formatDataProviderParameter(parameters) {
		return parameters.map(
			param => (
				{
					...param,
					fieldOptions: this.getFieldsByTypes(
						this.fieldOptions,
						this.getTypesByFieldType(param.type)
					)
				}
			)
		);
	}

	_isFieldAction(fieldName) {
		return (fieldName == 'enable' || fieldName == 'show' || fieldName == 'require');
	}

	_isBinary(value) {
		return (
			value === 'equals-to' ||
			value === 'not-equals-to' ||
			value === 'contains' ||
			value === 'not-contains' ||
			value === 'belongs-to' ||
			value === 'greater-than' ||
			value === 'greater-than-equals' ||
			value === 'less-than' ||
			value === 'less-than-equals'
		);
	}

	_prepareAutofillInputs(action) {
		if (Array.isArray(action.inputs)) {
			action.inputs.forEach(
				input => {
					delete input.fieldOptions;
					delete input.label;
					delete input.name;
					delete input.required;
					delete input.type;
					delete input.value;
				}
			);
			action.inputs = Object.assign({}, ...action.inputs);
		}

		return action.inputs;
	}

	_prepareAutofillOutputs(action) {
		if (Array.isArray(action.outputs)) {
			action.outputs.forEach(
				output => {
					delete output.fieldOptions;
					delete output.name;
					delete output.type;
					delete output.value;
				}
			);
			action.outputs = Object.assign({}, ...action.outputs);
		}

		return action.outputs;
	}

	_removeConditionInternalProperties() {
		const {conditions} = this;

		conditions.forEach(
			condition => {
				if (condition.operands[0].type == 'user') {
					condition.operands[0].label = condition.operands[0].value;
					condition.operands[1].type = 'list';
				}

				if (condition.operands[1]) {
					condition.operands[1].label = condition.operands[1].value;
				}
			}
		);

		return conditions;
	}

	_removeActionInternalProperties() {
		const {actions} = this;

		return actions.map(
			action => {
				const {action: actionType, ddmDataProviderInstanceUUID, expression, label, target} = action;
				const newAction = {
					action: actionType
				};

				if (actionType == 'auto-fill') {
					newAction.inputs = this._prepareAutofillInputs(action);
					newAction.outputs = this._prepareAutofillOutputs(action);
					newAction.ddmDataProviderInstanceUUID = ddmDataProviderInstanceUUID;
				}
				else {
					newAction.target = target;
					newAction.label = label;
				}

				if (actionType == 'calculate') {
					newAction.expression = expression;
				}

				return newAction;
			}
		);
	}

	_setActions(actions) {
		if (actions.length == 0) {
			actions.push(
				{
					action: '',
					calculatorFields: [],
					expression: '',
					hasRequiredInputs: false,
					inputs: [],
					label: '',
					outputs: [],
					target: ''
				}
			);
		}

		return actions;
	}

	_setConditions(conditions) {
		if (conditions.length === 0) {
			conditions.push(
				{
					operands: [
						{
							type: '',
							value: ''
						}
					],
					operator: ''
				}
			);
		}

		return conditions;
	}

	_validateConditionsFilling() {
		const {conditions} = this;

		let allFieldsFilled = true;

		for (const condition of conditions) {
			const {operands, operator} = condition;

			if (operands[0].value == '') {
				allFieldsFilled = false;
				break;
			}
			else if (!operator) {
				allFieldsFilled = false;
				break;
			}
			else if (operator && this._isBinary(operator)) {
				allFieldsFilled = operands[1] && !!operands[1].value && operands[1].value != '';
				if (!allFieldsFilled) {
					break;
				}
			}
		}

		return allFieldsFilled;
	}

	_validateActionsFilling() {
		const {actions} = this;

		let allFieldsFilled = true;

		const autofillActions = actions.filter(
			action => {
				return action.action == 'auto-fill';
			}
		);

		const calculateActions = actions.filter(
			action => {
				return action.action == 'calculate';
			}
		);

		if (actions) {
			actions.forEach(
				currentAction => {
					const {action, target} = currentAction;

					if (action == '') {
						allFieldsFilled = false;
					}
					else if (target == '') {
						allFieldsFilled = false;
					}
				}
			);

			if (allFieldsFilled) {
				if (autofillActions && autofillActions.length > 0 && calculateActions && calculateActions.length > 0) {
					allFieldsFilled = this._validateInputOutputs(autofillActions) && this._validateActionsCalculateFilling(calculateActions);
				}
				else if (autofillActions && autofillActions.length > 0) {
					allFieldsFilled = this._validateActionsAutofillFilling(autofillActions, 'inputs') &&
							this._validateActionsAutofillFilling(autofillActions, 'outputs');
				}
				else if (calculateActions && calculateActions.length > 0) {
					allFieldsFilled = this._validateActionsCalculateFilling(calculateActions);
				}
			}
		}

		return allFieldsFilled;
	}

	_validateInputOutputs(autofillActions) {
		return this._validateActionsAutofillFilling(autofillActions, 'inputs') &&
			this._validateActionsAutofillFilling(autofillActions, 'outputs');
	}

	_validateActionsAutofillFilling(autofillActions, list) {
		let allFieldsFilled = true;

		autofillActions.forEach(
			action => {
				if (action[list].length == 0) {
					allFieldsFilled = false;
				}
				else if (action[list].length > 0) {
					action[list].forEach(
						({value}) => {
							if (!value || value == '') {
								allFieldsFilled = false;
							}
						}
					);
				}
			}
		);

		return allFieldsFilled;
	}

	_validateActionsCalculateFilling(calculateActions) {
		let allFieldsFilled = true;

		calculateActions.forEach(
			({expression}) => {
				if (expression && expression.length == 0) {
					allFieldsFilled = false;
				}
			}
		);

		return allFieldsFilled;
	}
}

Soy.register(RuleEditor, templates);

export default RuleEditor;