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

import '../FieldBase/FieldBase.es';

import './LocalizableTextRegister.soy';

import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './LocalizableText.soy';

class LocalizableText extends Component {
	dispatchEvent(event, name, value) {
		this.emit(name, {
			fieldInstance: this,
			originalEvent: event,
			value,
		});
	}

	prepareStateForRender(state) {
		const {value} = state;

		return {
			...state,
			availableLocales: state.availableLocales.map(availableLocale => {
				return {
					...availableLocale,
					icon: availableLocale.localeId
						.replace('_', '-')
						.toLowerCase(),
					isDefault: this._isDefaultLocale(availableLocale.localeId),
					isTranslated: this._isTranslated(availableLocale.localeId),
				};
			}),
			value: this._convertValueToString(value),
		};
	}

	getEditingValue() {
		const {defaultLocale, editingLocale, value} = this;
		const valueJSON = this._convertValueToJSON(value);

		return (
			valueJSON[editingLocale.localeId] ||
			valueJSON[defaultLocale.localeId] ||
			''
		);
	}

	_convertValueToJSON(value) {
		if (value && typeof value === 'string') {
			try {
				return JSON.parse(value);
			}
			catch (e) {
				console.warn('Unable to parse JSON', value);
			}
		}

		return value;
	}

	_convertValueToString(value) {
		if (value && typeof value === 'object') {
			return JSON.stringify(value);
		}

		return value;
	}

	_handleFieldBlurred(event) {
		this.dispatchEvent(event, 'fieldBlurred', event.target.value);
	}

	_handleFieldChanged(event) {
		const {editingLocale, value} = this;
		const {target} = event;
		const valueJSON = this._convertValueToJSON(value);

		const newValue = JSON.stringify({
			...valueJSON,
			[editingLocale.localeId]: target.value,
		});

		this.setState(
			{
				_value: target.value,
				value: newValue,
			},
			() => this.dispatchEvent(event, 'fieldEdited', newValue)
		);
	}

	_handleFieldFocused(event) {
		this.dispatchEvent(event, 'fieldFocused', event.target.value);
	}

	_handleLanguageClicked({delegateTarget}) {
		const {availableLocales} = this;
		const editingLocale = availableLocales.find(
			availableLocale =>
				availableLocale.localeId === delegateTarget.dataset.localeId
		);

		this.setState({
			editingLocale: {
				...editingLocale,
				icon: editingLocale.localeId.replace('_', '-').toLowerCase(),
			},
		});

		this.setState({
			_value: this.getEditingValue(),
		});
	}

	_internalValueFn() {
		const {editingLocale, value} = this;
		const valueJSON = this._convertValueToJSON(value);

		return valueJSON[editingLocale.localeId] || '';
	}

	_isDefaultLocale(localeId) {
		const {defaultLocale} = this;

		return defaultLocale.localeId === localeId;
	}

	_isTranslated(localeId) {
		const {value} = this;
		const valueJSON = this._convertValueToJSON(value);

		return !!valueJSON[localeId];
	}
}

LocalizableText.STATE = {
	/**
	 * @default undefined
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(string|undefined)}
	 */

	_value: Config.string()
		.internal()
		.valueFn('_internalValueFn'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(array|undefined)}
	 */

	availableLocales: Config.array().value([]),

	/**
	 * @default 'string'
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(string|undefined)}
	 */

	dataType: Config.string().value('string'),

	/**
	 * @default themeDisplay.getDefaultLanguageId()
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(string|undefined)}
	 */

	defaultLocale: Config.object().value({
		icon: themeDisplay.getDefaultLanguageId(),
		localeId: themeDisplay.getDefaultLanguageId(),
	}),

	/**
	 * @default false
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(boolean|undefined)}
	 */

	displayErrors: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(string|undefined)}
	 */

	displayStyle: Config.string().value('singleline'),

	/**
	 * @default themeDisplay.getDefaultLanguageId()
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(string|undefined)}
	 */

	editingLocale: Config.object()
		.internal()
		.value({
			icon: themeDisplay
				.getDefaultLanguageId()
				.replace('_', '-')
				.toLowerCase(),
			localeId: themeDisplay.getDefaultLanguageId(),
		}),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(string|undefined)}
	 */

	errorMessage: Config.string(),

	/**
	 * @default false
	 * @instance
	 * @memberof LocalizableText
	 * @type {?bool}
	 */

	evaluable: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(string|undefined)}
	 */

	fieldName: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(string|undefined)}
	 */

	name: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(string|undefined)}
	 */

	placeholder: Config.string().value(''),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(string|undefined)}
	 */

	predefinedValue: Config.string().value(''),

	/**
	 * @default false
	 * @instance
	 * @memberof LocalizableText
	 * @type {?bool}
	 */

	readOnly: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FieldBase
	 * @type {?(bool|undefined)}
	 */

	repeatable: Config.bool(),

	/**
	 * @default false
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(bool|undefined)}
	 */

	required: Config.bool().value(false),

	/**
	 * @default true
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(bool|undefined)}
	 */

	showLabel: Config.bool().value(true),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	tooltip: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(string|undefined)}
	 */

	type: Config.string().value('localizable_text'),

	/**
	 * @default {}
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(string|undefined)}
	 */

	value: Config.oneOfType([Config.object(), Config.string()]).value({}),
};

Soy.register(LocalizableText, templates);

export default LocalizableText;
