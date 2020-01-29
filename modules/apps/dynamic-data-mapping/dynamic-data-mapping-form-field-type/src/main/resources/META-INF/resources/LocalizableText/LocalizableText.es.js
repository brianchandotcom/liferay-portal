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

import './LocalizableTextRegister.soy.js';

import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './LocalizableText.soy.js';

class LocalizableText extends Component {
	dispatchEvent(event, name, value) {
		this.emit(name, {
			fieldInstance: this,
			originalEvent: event,
			value
		});
	}

	prepareStateForRender(state) {
		return {
			...state,
			_value: this.getEditingValue(),
			availableLocales: state.availableLocales.map(availableLocale => {
				return {
					...availableLocale,
					icon: availableLocale.localeId
						.replace('_', '-')
						.toLowerCase(),
					isDefault: this._isDefaultLocale(availableLocale.localeId),
					isTranslated: this._isTranslated(availableLocale.localeId)
				};
			})
		};
	}

	getEditingValue() {
		const {defaultLocale, editingLocale, value} = this;

		return (
			value[editingLocale.localeId] || value[defaultLocale.localeId] || ''
		);
	}

	_handleFieldBlurred(event) {
		this.dispatchEvent(event, 'fieldBlurred', event.target.value);
	}

	_handleFieldChanged(event) {
		const {editingLocale, value} = this;
		const {target} = event;

		const newValue = {
			...value,
			[editingLocale.localeId]: target.value
		};

		this.setState(
			{
				value: newValue
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
				icon: editingLocale.localeId.replace('_', '-').toLowerCase()
			}
		});
	}

	_internalValueFn() {
		const {editingLocale, value} = this;

		return value[editingLocale.localeId] || '';
	}

	_isDefaultLocale(localeId) {
		const {defaultLocale} = this;

		return defaultLocale.localeId === localeId;
	}

	_isTranslated(localeId) {
		const {value} = this;

		return !!value[localeId];
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
		localeId: themeDisplay.getDefaultLanguageId()
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
			localeId: themeDisplay.getDefaultLanguageId()
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

	value: Config.object().value({})
};

Soy.register(LocalizableText, templates);

export default LocalizableText;
