import './ValidationRegister.soy.js';
import 'dynamic-data-mapping-form-field-type/metal/FieldBase/index.es';
import 'dynamic-data-mapping-form-field-type/metal/Checkbox/Checkbox.es';
import 'dynamic-data-mapping-form-field-type/metal/Select/Select.es';
import 'dynamic-data-mapping-form-field-type/metal/Text/Text.es';
import 'dynamic-data-mapping-form-field-type/metal/Numeric/Numeric.es';

import autobind from 'autobind-decorator';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Validation.soy.js';
import {Config} from 'metal-state';
import {subWords} from '../util/strings.es';
import VALIDATIONS from '../util/validations.es';

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
		 * @type {!string}
		 */

		strings: {
			value: {
				chooseAnOption: Liferay.Language.get('choose-an-option'),
				email: Liferay.Language.get('email'),
				errorMessage: Liferay.Language.get('error-message'),
				ifInput: Liferay.Language.get('if-input'),
				showErrorMessage: Liferay.Language.get('show-error-message'),
				theValue: Liferay.Language.get('the-value'),
				url: Liferay.Language.get('url'),
				validationMessage: Liferay.Language.get('validation')
			}
		},

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
		const {enableValidation, value} = state;
		const {errorMessage, expression} = value;
		let parameterMessage = '';
		let selectedValidation;

		if (enableValidation) {
			selectedValidation = this._parseValidationFromExpression(expression);

			if (selectedValidation) {
				parameterMessage = this._parseParameterMessageFromExpression(expression, selectedValidation);
			}
		}

		return {
			...state,
			enableValidation,
			errorMessage,
			parameterMessage,
			selectedValidation
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

	_enableValidationValueFn() {
		const {value} = this;

		return !!value.expression;
	}

	@autobind
	_handleEnableValidationEdited({value}) {
		this.setState(
			{
				enableValidation: value
			},
			() => {
				if (!value) {
					this._emitFieldEdited(
						{
							errorMessage: '',
							expression: ''
						}
					);
				}
			}
		);
	}

	@autobind
	_handleParameterMessageEdited() {
		this._updateValue();
	}

	@autobind
	_handleErrorMessageEdited() {
		this._updateValue();
	}

	@autobind
	_handleValidationValueEdited() {
		this._updateValue();
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

	_parseParameterMessageFromExpression(expression, validation) {
		const matches = validation.regex.exec(expression);

		return matches && matches[2];
	}

	_getValue() {
		let expression = '';
		const {
			validation: {fieldName: name}
		} = this;

		let selectedValidationValue = this.refs.selectedValidation.value;

		if (Array.isArray(selectedValidationValue)) {
			selectedValidationValue = selectedValidationValue[0];
		}

		const selectedValidation = this.validations.find(
			({name}) => name === selectedValidationValue
		);

		if (selectedValidation) {
			const {template} = selectedValidation;
			let parameterMessage = '';

			if (this.refs.parameterMessage) {
				parameterMessage = this.refs.parameterMessage.value;
			}

			expression = subWords(
				template,
				{
					name,
					parameter: parameterMessage
				}
			);
		}

		return {
			errorMessage: this.refs.errorMessage.value,
			expression
		};
	}

	_parseValidationFromExpression(expression) {
		let validation;

		if (expression) {
			const {validations} = this;

			validation = validations.find(validation => validation.regex.test(expression));
		}

		return validation;
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

	_updateValue() {
		const value = this._getValue();

		this._emitFieldEdited(value);
	}

	_validationsValueFn() {
		let {dataType} = this;

		dataType = dataType == 'string' ? dataType : 'numeric';

		return this._normalizeValidationsOptions(VALIDATIONS[dataType]);
	}
}

Soy.register(Validation, templates);

export default Validation;