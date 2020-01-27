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

import {normalizeFieldName} from 'dynamic-data-mapping-form-renderer/js/util/fields.es';
import {debounce, cancelDebounce} from 'frontend-js-web';
import Component from 'metal-component';
import dom from 'metal-dom';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './LocalizableText.soy.js';

class LocalizableText extends Component {
	created() {
		this.debouncedUpdate = debounce(_value => {
			if (this.animationFrameRequest) {
				window.cancelAnimationFrame(this.animationFrameRequest);
			}

			this.animationFrameRequest = window.requestAnimationFrame(() => {
				if (!this.isDisposed()) {
					this.setState({_value});
				}
			});
		}, 300);
	}

	attached() {
		const portalElement = dom.toElement('#clay_dropdown_portal');

		if (portalElement) {
			dom.addClasses(portalElement, 'show');
		}
	}

	// prepareStateForRender(state) {
	// 	return {
	// 		...state,
	// 		availableLanguages: this.availableLanguages.map((current) => {
	// 			return {
	// 				...current,
	// 				translated: this._isTranslated(current.code)
	// 			}
	// 		})
	// 	}
	// }

	dispatchEvent(event, name, value) {
		this.emit(name, {
			fieldInstance: this,
			originalEvent: event,
			value
		});
	}

	shouldUpdate(changes) {
		return Object.keys(changes || {}).some(key => {
			if (key === 'events' || key === 'value') {
				return false;
			}

			if (
				!Liferay.Util.isEqual(changes[key].newVal, changes[key].prevVal)
			) {
				return true;
			}
		});
	}

	willReceiveState(changes) {
		if (changes.value) {
			cancelDebounce(this.debouncedUpdate);
			this.debouncedUpdate(changes.value.newVal);
		}
	}

	_handleFieldBlurred(event) {
		this.dispatchEvent(event, 'fieldBlurred', event.target.value);
	}

	_handleFieldChanged(event) {
		const {target} = event;
		let {value} = target;
		const {fieldName} = this;

		if (fieldName === 'name') {
			value = normalizeFieldName(value);

			target.value = value;
		}

		this.setState(
			{
				value
			},
			() => this.dispatchEvent(event, 'fieldEdited', value)
		);
	}

	_handleFieldFocused(event) {
		this.dispatchEvent(event, 'fieldFocused', event.target.value);
	}

	_internalValueFn() {
		const {value} = this;

		return value;
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

	type: Config.string().value('LocalizableText'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LocalizableText
	 * @type {?(string|undefined)}
	 */

	value: Config.string().value('')
};

Soy.register(LocalizableText, templates);

export default LocalizableText;
