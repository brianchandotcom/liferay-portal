import 'clay-button';
import 'clay-modal';
import {Config} from 'metal-state';
import {PagesVisitor} from '../../util/visitors.es';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './RuleEditor.soy.js';
import {makeFetch} from '../../util/fetch.es';

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
					label: Config.string(),
					target: Config.string()
				}
			)
		).internal().value([]),

		actionList: Config.arrayOf(
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

		dataProvider: Config.arrayOf(
			Config.shapeOf(
				{
					id: Config.string(),
					name: Config.string(),
					uuid: Config.string()
				}
			)
		).internal(),

		dataProviderUrl: Config.string().required(),

		/**
		 * Used for telling whether the delete conditional modal is visible or not.
		 * @default false
		 * @instance
		 * @memberof RuleEditor
		 * @type {Boolean}
		 */

		deleteActionModalVisible: Config.bool().value(false),

		/**
		 * Used for telling whether the delete conditional modal is visible or not.
		 * @default false
		 * @instance
		 * @memberof RuleEditor
		 * @type {Boolean}
		 */

		deleteConditionModalVisible: Config.bool().value(false),

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
				save: Liferay.Language.get('save'),
				show: Liferay.Language.get('show'),
				the: Liferay.Language.get('the'),
				title: Liferay.Language.get('rule'),
				user: Liferay.Language.get('user'),
				value: Liferay.Language.get('value')
			}
		},

		rolesUrl: Config.string()
	}

	created() {
		if (this.rolesUrl) {
			this._fetchRoles();
		}

		if (this.dataProviderUrl) {
			this._fetchDataProvider();
		}
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
		const {dataProviderUrl} = this;

		makeFetch(
			{
				method: 'GET',
				url: dataProviderUrl
			}
		).then(
			responseData => {
				this._pendingRequest = null;
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
		);
	}

	_fetchRoles() {
		const {rolesUrl} = this;

		makeFetch(
			{
				method: 'GET',
				rolesUrl
			}
		).then(
			responseData => {
				this._pendingRequest = null;

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

	_getConditionIndex(fieldInstance, fieldClass) {
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
		const newAction = {action: '', label: '', target: ''};

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
		const index = this._getConditionIndex(fieldInstance, '.action-type');

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

	_handleDeleteAction(event) {
		const {currentTarget} = event;
		const index = currentTarget.getAttribute('data-index');

		this.setState(
			{
				activeActionIndex: parseInt(index, 10),
				deleteActionModalVisible: true
			}
		);
	}

	_handleDeleteCondition(event) {
		const {currentTarget} = event;
		const index = currentTarget.getAttribute('data-index');

		this.setState(
			{
				activeConditionIndex: parseInt(index, 10),
				deleteConditionModalVisible: true
			}
		);
	}

	_handleFirstOperandFieldEdited(event) {
		const {fieldInstance, value} = event;
		const index = this._getConditionIndex(fieldInstance, '.condition-if');
		let {conditions} = this;

		if (value && value.length > 0 && value[0]) {
			const fieldName = value[0];
			const type = this._getFieldTypeByFieldName(fieldName);

			const firstOperand = {type, value: fieldName};

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

			this.setState(
				{
					actions,
					activeActionIndex: -1,
					activeConditionIndex: -1,
					conditions,
					deleteActionModalVisible: false,
					deleteConditionModalVisible: false
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

		const index = this._getConditionIndex(fieldInstance, '.condition-operator');

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
			index = this._getConditionIndex(fieldInstance, '.condition-type-value');
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
		const index = this._getConditionIndex(fieldInstance, '.condition-type');
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
		const index = this._getConditionIndex(fieldInstance, '.target-action');

		const {actions} = this;

		actions[index].target = value[0];
		actions[index].label = value[0];

		this.setState(
			{
				actions
			}
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