import 'clay-button';
import 'clay-modal';
import {Config} from 'metal-state';
import {PagesVisitor} from '../../util/visitors.es';
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
					value: 'autofill'
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
		).internal(),

		rolesURL: Config.string(),

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
				the: Liferay.Language.get('the'),
				title: Liferay.Language.get('rule'),
				user: Liferay.Language.get('user'),
				value: Liferay.Language.get('value')
			}
		}
	}

	created() {
		this._fetchDataProvider();
		this._fetchRoles();
	}

	prepareStateForRender(state) {
		const conditions = state.conditions.map(
			condition => {
				const fieldName = condition.operands[0].value;
				let firstOperandOptions = [];
				let operators = [];

				if (fieldName) {
					const type = this._getFieldTypeByFieldName(fieldName);

					operators = this._getOperatorsByFieldType(type);

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
					conditions = this._clearAllFieldValues(index);
				}

				if (!secondOperandFieldExists && secondOperand && secondOperand.type == 'field') {
					conditions = this._clearSecondOperandValue(conditions, index);
				}
			}
		);

		this.setState(
			{
				conditions,
				fieldOptions: this._fieldOptionsValueFn()
			}
		);
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
			label: '',
			target: ''
		};

		this.setState(
			{
				actions: newActions
			}
		);
	}

	_clearAllFieldValues(index) {
		let {conditions, secondOperandSelectedList} = this;

		conditions = this._clearFirstOperandValue(conditions, index);
		conditions = this._clearOperatorValue(conditions, index);
		conditions = this._clearSecondOperandValue(conditions, index);

		secondOperandSelectedList = this._clearSelectedSecondOperand(secondOperandSelectedList, index);

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
			conditions[index].operands[1].type = '';
			conditions[index].operands[1].value = '';
		}

		return conditions;
	}

	_clearSelectedSecondOperand(secondOperandSelectedList, index) {
		return secondOperandSelectedList;
	}

	_fetchDataProvider() {
		const {dataProviderInstancesURL} = this;

		makeFetch(
			{
				method: 'GET',
				url: dataProviderInstancesURL
			}
		).then(
			responseData => {
				if (!this.isDisposed()) {
					this.setState(
						{
							dataProvider: responseData.map(
								data => {
									return {
										...data,
										label: data.name,
										value: data.id
									};
								}
							)
						}
					);
				}
			}
		).catch(
			error => {
				console.log(error);
			}
		);
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
			responseData => {
				if (!this.isDisposed()) {
					actions[index].inputs = responseData.inputs;
					actions[index].outputs = responseData.outputs;

					this.setState(
						{
							actions
						}
					);
				}
			}
		).catch(
			error => {
				console.log(error);
			}
		);
	}

	_fetchRoles() {
		const {rolesURL} = this;

		makeFetch(
			{
				method: 'GET',
				url: rolesURL
			}
		).then(
			responseData => {
				if (!this.isDisposed()) {
					this.setState(
						{
							roles: responseData.map(
								data => {
									return {
										...data,
										label: data.name,
										value: data.id
									};
								}
							)
						}
					);
				}
			}
		).catch(
			error => {
				console.log(error);
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

	_getIndex(fieldInstance, fieldClass) {
		const firstOperand = fieldInstance.element.closest(fieldClass);

		return firstOperand.getAttribute(`${fieldClass.substring(1)}-index`);
	}

	_getFieldTypeByFieldName(fieldName) {
		let fieldType = '';

		if (fieldName === 'user') {
			fieldType = 'user';
		}
		else {
			const selectedField = this.fieldOptions.find(
				field => {
					return field.value === fieldName;
				}
			);

			if (selectedField) {
				fieldType = selectedField.type;
			}
		}

		return fieldType;
	}

	_getOperatorsByFieldType(fieldType) {
		if (fieldType === 'numeric') {
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
		const newAction = {action: '',
			label: '',
			target: ''};

		if (actions.length == 0) {
			actions.push(newAction);
		}

		actions.push(newAction);

		this.setState(
			{
				actions
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
					newActions[index].target = '';
					newActions[index].label = '';
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

	_handleDataProviderInputEdited(event) {
		const {fieldInstance, value} = event;
		const actionIndex = this._getIndex(fieldInstance, '.action');
		const inputIndex = this._getIndex(fieldInstance, '.container-input-field');
		const {actions} = this;

		if (value && value.length > 0 && value[0]) {
			actions[actionIndex].inputs[inputIndex].value = value[0];
			this.setState(
				{
					actions
				}
			);
		}
	}

	_handleDataProviderOutputEdited(event) {
		const {fieldInstance, value} = event;
		const actionIndex = this._getIndex(fieldInstance, '.action');
		const outputIndex = this._getIndex(fieldInstance, '.container-output-field');
		const {actions} = this;

		if (value && value.length > 0 && value[0]) {
			actions[actionIndex].outputs[outputIndex].value = value[0];
			this.setState(
				{
					actions
				}
			);
		}
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

	_handleFirstOperandFieldEdited(event) {
		const {fieldInstance, value} = event;
		const index = this._getIndex(fieldInstance, '.condition-if');
		let {conditions} = this;

		if (value && value.length > 0 && value[0]) {
			const fieldName = value[0];
			const type = this._getFieldTypeByFieldName(fieldName);

			const firstOperand = {type,
				value: fieldName};

			if (conditions.length === 0) {
				const operands = [firstOperand];

				conditions.push({operands});
			}
			else {
				const previousType = conditions[index].operands[0].type;

				if (previousType !== type) {
					conditions[index].operator = '';
				}

				conditions[index].operands[0] = firstOperand;

				this._clearSecondOperandValue(conditions, index);
			}
		}
		else {
			conditions = this._clearAllFieldValues(index);
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

	_handleSecondOperandFieldEdited(event) {
		const {conditions} = this;
		const {fieldInstance, originalEvent, value} = event;

		const {delegateTarget} = originalEvent;
		let fieldValue = '';

		if (value && value.length > 0 && value[0]) {
			fieldValue = value[0];
		}

		let index;

		if (delegateTarget.closest('.condition-type-value')) {
			index = this._getIndex(fieldInstance, '.condition-type-value');
		}

		const secondOperand = conditions[index].operands[1];

		conditions[index].operands[1] = {
			...secondOperand,
			value: fieldValue
		};

		this.setState(
			{
				conditions
			}
		);
	}

	_handleSecondOperandTypeEdited(event) {
		let {conditions} = this;
		const {fieldInstance, value} = event;
		const index = this._getIndex(fieldInstance, '.condition-type');
		const secondOperand = conditions[index].operands[1];

		if (secondOperand && secondOperand.type != value) {
			if (secondOperand.type === 'field' || value === 'field') {
				conditions = this._clearSecondOperandValue(conditions, index);
			}
		}

		if (value && value.length > 0 && value[0]) {
			let secondOperandType = 'field';
			const selectedValue = value[0];

			if (selectedValue === 'value') {
				secondOperandType = conditions[index].operands[0].type;
			}

			if (secondOperand) {
				secondOperand.type = secondOperandType;
			}
			else {
				conditions[index].operands.push(
					{
						type: secondOperandType
					}
				);
			}
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

		if (id === '') {
			actions[index].inputs = [];
			actions[index].outputs = [];
			actions[index].hasRequiredInputs = false;

			this.setState(
				{
					actions
				}
			);
		}
		else if (previousTarget !== id) {
			if (this.actions[index].action == 'autofill') {
				this._fetchDataProviderParameters(id, index)
					.then(
						() => {
							if (!this.isDisposed()) {
								actions[index] = {
									...actions[index],
									inputs: this.formatDataProviderParameter(actions[index].inputs),
									outputs: this.formatDataProviderParameter(actions[index].outputs)
								};
								actions[index].hasRequiredInputs = (actions[index].inputs)
									.some(
										input => {
											return input.required;
										}
									);
								this.setState(actions);
							}
						}
					);
			}
		}
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

	_setActions(actions) {
		if (actions.length == 0) {
			actions.push(
				{
					action: '',
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
}

Soy.register(RuleEditor, templates);

export default RuleEditor;