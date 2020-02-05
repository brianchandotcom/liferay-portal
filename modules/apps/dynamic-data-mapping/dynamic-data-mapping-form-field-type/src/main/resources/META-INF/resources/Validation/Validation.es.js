/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/* eslint-disable react/no-string-refs */

import '../Checkbox/Checkbox.es';

import '../FieldBase/FieldBase.es';

import '../Numeric/Numeric.es';

import '../Select/Select.es';

import '../Text/Text.es';

import './ValidationRegister.soy';

import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import {subWords} from '../util/strings.es';
import VALIDATIONS from '../util/validations.es';
import templates from './Validation.soy';

class Validation extends Component {
	prepareStateForRender(state) {
		const {defaultLanguageId, editingLanguageId} = this;
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
			dataType:
				state.validation && state.validation.dataType
					? state.validation.dataType
					: state.dataType,
			localizationMode: editingLanguageId !== defaultLanguageId
		};
	}

	willReceiveState({dataType, validation}) {
		if (
			(dataType && dataType.newVal !== dataType.prevVal) ||
			(validation &&
				validation.newVal.dataType !== validation.prevVal.dataType)
		) {
			this.setState({
				dataType: this._dataTypeValueFn(),
				validations: this._validationsValueFn()
			});
		}
	}

	_dataTypeValueFn() {
		return this.validation.dataType
			? this.validation.dataType
			: this.dataType;
	}

	_emitFieldEdited(value) {
		this.emit('fieldEdited', {
			fieldInstance: this,
			originalEvent: null,
			value
		});
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
		const {expression} = value;
		let parameterMessage = '';
		let selectedValidation;
		const enableValidation = !!expression.value;

		if (enableValidation) {
			selectedValidation = this._parseValidationFromExpression(
				expression
			);

			if (selectedValidation) {
				parameterMessage = selectedValidation.parameterMessage;
			}
			else {
				selectedValidation = {
					name: this.validations[0].name,
					parameterMessage: this.validations[0].parameterMessage,
					value: this.validations[0].template
				};
			}
		}

		const {defaultLanguageId, editingLanguageId} = this;

		const errorMessage =
			this.value.errorMessage[editingLanguageId] ||
			this.value.errorMessage[defaultLanguageId];

		const parameter =
			this.value.parameter[editingLanguageId] ||
			this.value.parameter[defaultLanguageId];

		return {
			enableValidation,
			errorMessage,
			expression,
			parameter,
			parameterMessage,
			selectedValidation
		};
	}

	_getValue() {
		let expression = {};
		const {
			editingLanguageId,
			validation: {fieldName: name}
		} = this;
		let errorMessage = '';
		let parameter = '';

		if (this.refs.errorMessage) {
			errorMessage = this.refs.errorMessage.value;
		}

		if (this.refs.parameter) {
			parameter = this.refs.parameter.value;
		}

		const enableValidation = this.refs.enableValidation.value;
		let selectedValidation = this._getSelectedValidation();

		if (
			enableValidation &&
			!this.value.expression &&
			this.context.validation
		) {
			selectedValidation = this.validations.find(
				validation =>
					validation.name === this.context.validation.expression.name
			);
			parameter = this.context.validation.parameterMessage;
		}

		if (enableValidation) {
			expression = {
				name: selectedValidation.name,
				value: subWords(selectedValidation.template, {
					name
				})
			};
		}

		return {
			enableValidation,
			errorMessage: {
				...this.value.errorMessage,
				[editingLanguageId]: errorMessage
			},
			expression,
			parameter: {
				...this.value.parameter,
				[editingLanguageId]: parameter
			}
		};
	}

	_normalizeValidationsOptions(validations) {
		return validations.map(validation => {
			return {
				...validation,
				checked: false,
				value: validation.name
			};
		});
	}

	_parseValidationFromExpression(expression) {
		const {validations} = this;
		let validation;

		if (!expression && this.context.validation) {
			expression = this.context.validation.expression;
		}

		if (expression) {
			validation = validations.find(
				validation => validation.name === expression.name
			);
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

Validation.STATE = {
	/**
	 * @default undefined
	 * @instance
	 * @memberof Validation
	 * @type {?(string|undefined)}
	 */

	dataType: Config.string().valueFn('_dataTypeValueFn'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Options
	 * @type {?string}
	 */

	defaultLanguageId: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Options
	 * @type {?string}
	 */

	editingLanguageId: Config.string(),

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
	 * @type {?}
	 */

	expression: Config.object(),

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

	parameterMessage: Config.string()
		.internal()
		.value(''),

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

	value: Config.shapeOf({
		errorMessage: Config.object(),
		expression: Config.object(),
		parameter: Config.object()
	}).value({})
};

Soy.register(Validation, templates);

export default Validation;
