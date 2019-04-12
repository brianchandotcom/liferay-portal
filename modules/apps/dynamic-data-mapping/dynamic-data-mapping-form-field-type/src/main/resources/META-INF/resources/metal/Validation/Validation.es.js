import '../Checkbox/Checkbox.es';
import '../FieldBase/FieldBase.es';
import '../Numeric/Numeric.es';
import '../Select/Select.es';
import '../Text/Text.es';
import './ValidationRegister.soy.js';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Validation.soy.js';
import VALIDATIONS from '../util/validations.es';
import {Config} from 'metal-state';
import {subWords} from '../util/strings.es';

class Validation extends Component {
	static STATE = {

		/**
		 * @default undefined
		 * @instance
		 * @memberof Validation
		 * @type {?(string|undefined)}
		 */

		dataType: Config.string().valueFn('_dataTypeValueFn'),

		/**
		 * @default false
		 * @instance
		 * @memberof Validation
		 * @type {bool}
		 */

		enableValidation: Config.bool()
			.internal()
			.valueFn('_enableValidationValueFn'),

		/**
		 * @default ''
		 * @instance
		 * @memberof Validation
		 * @type {String}
		 */

		errorMessage: Config.string()
			.internal()
			.value(''),

		/**
		 * @default ''
		 * @instance
		 * @memberof Validation
		 * @type {String}
		 */

		expression: Config.string()
			.internal()
			.value(''),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Validation
		 * @type {?(string|undefined)}
		 */

		fieldName: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Validation
		 * @type {?(string|undefined)}
		 */

		id: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Validation
		 * @type {?(string|undefined)}
		 */

		label: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Validation
		 * @type {?(string|undefined)}
		 */

		name: Config.string().required(),

		/**
		 * @default ''
		 * @instance
		 * @memberof Validation
		 * @type {String}
		 */

		parameterMessage: Config.string().internal().value(''),

		/**
		 * @default false
		 * @instance
		 * @memberof Validation
		 * @type {?bool}
		 */

		readOnly: Config.bool().value(false),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Validation
		 * @type {?(string|undefined)}
		 */

		spritemap: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Validation
		 * @type {?(string|undefined)}
		 */

		type: Config.string().value('validation'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Validation
		 * @type {?(object|undefined)}
		 */

		validation: Config.object(),

		/**
		 * @default array
		 * @instance
		 * @memberof Validation
		 * @type {array}
		 */

		validations: Config.array().valueFn('_validationsValueFn'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Validation
		 * @type {?(string|undefined)}
		 */

		value: Config.shapeOf(
			{
				errorMessage: Config.string(),
				expression: Config.string()
			}
		).value({})
	};

	prepareStateForRender(state) {
		const parsedState = this._getStateFromValue(state.value);

		if (parsedState.enableValidation) {
			this.context = {
				...this.context,
				validation: parsedState
			};
		}

		return {
			...state,
			...parsedState,
			dataType: state.validation ? state.validation.dataType : state.dataType
		};
	}

	willReceiveState({dataType, validation}) {
		if (
			(dataType && dataType.newVal !== dataType.prevVal) ||
			(validation && validation.newVal.dataType !== validation.prevVal.dataType)
		) {
			this.setState(
				{
					dataType: this._dataTypeValueFn(),
					validations: this._validationsValueFn()
				}
			);
		}
	}

	_dataTypeValueFn() {
		return this.validation.dataType ? this.validation.dataType : this.dataType;
	}

	_emitFieldEdited(value) {
		this.emit(
			'fieldEdited',
			{
				fieldInstance: this,
				originalEvent: null,
				value
			}
		);
	}

	_enableValidationValueFn() {
		const {value} = this;

		return !!value.expression;
	}

	_getSelectedValidation() {
		let selectedValidationValue = this.refs.selectedValidation.value;

		if (Array.isArray(selectedValidationValue)) {
			selectedValidationValue = selectedValidationValue[0];
		}

		let selectedValidation = this.validations.find(
			({name}) => name === selectedValidationValue
		);

		if (!selectedValidation) {
			selectedValidation = this.validations[0];
		}

		return selectedValidation;
	}

	_getStateFromValue(value) {
		const {errorMessage, expression} = value;
		let parameterMessage = '';
		let selectedValidation;
		const enableValidation = !!expression;

		if (enableValidation) {
			selectedValidation = this._parseValidationFromExpression(expression);

			if (selectedValidation) {
				parameterMessage = this._parseParameterMessageFromExpression(expression, selectedValidation);
			}
			else {
				selectedValidation = {
					parameterMessage: this.validations[0].parameterMessage,
					value: this.validations[0].name
				};
			}
		}

		return {
			enableValidation,
			errorMessage,
			expression,
			parameterMessage,
			selectedValidation
		};
	}

	_getValue() {
		let expression;
		const {
			validation: {fieldName: name}
		} = this;
		let parameterMessage = '';

		if (this.refs.parameterMessage) {
			parameterMessage = this.refs.parameterMessage.value;
		}

		const enableValidation = this.refs.enableValidation.value;
		let selectedValidation = this._getSelectedValidation();

		if (enableValidation && !this.value.expression && this.context.validation) {
			selectedValidation = this.validations.find(
				validation => validation.regex.test(this.context.validation.expression)
			);
			parameterMessage = this.context.validation.parameterMessage;
		}

		const {template} = selectedValidation;

		if (enableValidation) {
			expression = subWords(
				template,
				{
					name,
					parameter: parameterMessage
				}
			);
		}

		return {
			enableValidation,
			errorMessage: this.refs.errorMessage.value,
			expression
		};
	}

	_normalizeValidationsOptions(validations) {
		return validations.map(
			validation => {
				return {
					...validation,
					checked: false,
					value: validation.name
				};
			}
		);
	}

	_parseParameterMessageFromExpression(expression, validation) {
		const matches = validation.regex.exec(expression);

		return matches && matches[2];
	}

	_parseValidationFromExpression(expression) {
		const {validations} = this;
		let validation;

		if (!expression && this.context.validation) {
			expression = this.context.validation.expression;
		}

		if (expression) {
			validation = validations.find(validation => validation.regex.test(expression));
		}

		return validation;
	}

	_updateValue() {
		const value = this._getValue();

		this.setState(
			{
				value
			},
			() => this._emitFieldEdited(value)
		);
	}

	_validationsValueFn() {
		let {dataType} = this;

		dataType = dataType == 'string' ? dataType : 'numeric';

		return this._normalizeValidationsOptions(VALIDATIONS[dataType]);
	}
}

Soy.register(Validation, templates);

export default Validation;