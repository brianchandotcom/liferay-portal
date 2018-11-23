import './ValidationRegister.soy.js';
import 'dynamic-data-mapping-form-field-type/metal/FieldBase/index.es';
import 'dynamic-data-mapping-form-field-type/metal/Checkbox/Checkbox.es';
import 'dynamic-data-mapping-form-field-type/metal/Select/Select.es';
import 'dynamic-data-mapping-form-field-type/metal/Text/Text.es';
import 'dynamic-data-mapping-form-field-type/metal/Numeric/Numeric.es';
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
			.value(false),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Validation
		 * @type {?(string|undefined)}
		 */

		errorMessage: Config.string().internal(),

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

		parameterMessage: Config.string().internal(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Validation
		 * @type {?(string|undefined)}
		 */

		selectedValidation: Config.shapeOf(
			{
				checked: Config.bool(),
				label: Config.string(),
				name: Config.string(),
				parameterMessage: Config.string(),
				regex: Config.any(),
				template: Config.string(),
				value: Config.string()
			}
		).internal(),

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
		)
	};

	attached() {
		const {value} = this;

		if (value) {
			this._handleValue(this.value);
		}
	}

	willReceiveState({value, dataType, validation}) {
		if (value) {
			this._handleValue(value.newVal);
		}

		if (
			(dataType && dataType.newVal !== dataType.prevVal) ||
			(validation && validation.newVal.dataType !== validation.prevVal.dataType)
		) {
			this.dataType = this._dataTypeValueFn();
			this.validations = this._validationsValueFn();
		}
	}

	handleEnableValidation() {
		this.setState({enableValidation: !this.enableValidation});
	}

	handleParameterMessage({value}) {
		this.setState(
			{
				parameterMessage: value
			},
			this._updateValue
		);
	}

	handleErrorMessage({value}) {
		this.setState(
			{
				errorMessage: value
			},
			this._updateValue
		);
	}

	handleValidationValue({value: [newVal]}) {
		this.setState(
			{
				selectedValidation: this.validations.find(({value}) => value == newVal)
			}
		);
	}

	_dataTypeValueFn() {
		return this.validation.dataType ? this.validation.dataType : this.dataType;
	}

	_extractParameterMessage(validation, expression) {
		const matches = validation.regex.exec(expression);

		return matches && matches[2];
	}

	_getValue() {
		const {
			errorMessage,
			parameterMessage,
			validation: {fieldName: name}
		} = this;

		const expression = subWords(
			this.selectedValidation.template,
			{
				name,
				parameter: parameterMessage
			}
		);

		return {
			errorMessage,
			expression
		};
	}

	_getValidation(expression) {
		let validation = false;

		if (expression) {
			const {validations} = this;

			validation = validations.find(validation => validation.regex.test(expression));
		}

		return validation;
	}

	_getValidationsByDataType() {
		let {dataType} = this;

		dataType = dataType == 'string' ? dataType : 'numeric';
		return VALIDATIONS[dataType];
	}

	_handleValue({expression, errorMessage}) {
		const selectedValidation = this.selectedValidation ?
			this.selectedValidation :
			this._getValidation(expression);

		if (selectedValidation) {
			this.setState(
				{
					enableValidation: true,
					errorMessage,
					parameterMessage: this._extractParameterMessage(
						selectedValidation,
						expression
					),
					selectedValidation
				}
			);
		}
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

	_sendEmit(value) {
		this.emit(
			'fieldEdited',
			{
				fieldInstance: this,
				originalEvent: null,
				value
			}
		);
	}

	_updateValue() {
		const value = this._getValue();

		this.value = value;

		this._sendEmit(value);
	}

	_validationsValueFn() {
		const validationsArray = this._getValidationsByDataType();

		return this._normalizeValidationsOptions(validationsArray);
	}
}

Soy.register(Validation, templates);

export default Validation;