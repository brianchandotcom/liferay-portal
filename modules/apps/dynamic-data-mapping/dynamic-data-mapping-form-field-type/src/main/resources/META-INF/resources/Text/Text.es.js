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

import './TextRegister.soy';

import 'clay-autocomplete';
import {normalizeFieldName} from 'dynamic-data-mapping-form-renderer';
import {debounce, cancelDebounce} from 'frontend-js-web';
import Component from 'metal-component';
import dom from 'metal-dom';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './Text.soy';

class Text extends Component {
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

	dispatchEvent(event, name, value) {
		this.emit(name, {
			fieldInstance: this,
			originalEvent: event,
			value
		});
	}

	getAutocompleteOptions() {
		const {options} = this;

		if (!options) {
			return [];
		}

		return options.map(option => {
			return option.label;
		});
	}

	prepareStateForRender(state) {
		const {options} = this;

		return {
			...state,
			options: this.getAutocompleteOptions(options)
		};
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

	_handleAutocompleteFieldChanged(event) {
		const {value} = event.data;

		this.setState(
			{
				value
			},
			() => this.dispatchEvent(event, 'fieldEdited', value)
		);
	}

	_handleAutocompleteFieldFocused(event) {
		this.dispatchEvent('fieldFocused', event, event.target.inputValue);
	}

	_handleAutocompleteFilteredItemsChanged(filteredItemsReceived) {
		const {filteredItems} = this;

		if (filteredItemsReceived.newVal.length != filteredItems.length) {
			this.setState({
				filteredItems: filteredItemsReceived.newVal
			});
		}
	}

	_handleAutocompleteSelected(event) {
		const {value} = event.data.item;

		this.setState(
			{
				filteredItems: [],
				value
			},
			() => {
				this.dispatchEvent(event, 'fieldEdited', value);
			}
		);
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

Text.STATE = {
	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	_value: Config.string()
		.internal()
		.valueFn('_internalValueFn'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	autocompleteEnabled: Config.bool(),

	/**
	 * @default 'string'
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	dataType: Config.string().value('string'),

	/**
	 * @default false
	 * @instance
	 * @memberof Text
	 * @type {?(boolean|undefined)}
	 */

	displayErrors: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	displayStyle: Config.string().value('singleline'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	errorMessage: Config.string(),

	/**
	 * @default false
	 * @instance
	 * @memberof Text
	 * @type {?bool}
	 */

	evaluable: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	fieldName: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	filteredItems: Config.array()
		.value([])
		.internal(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	name: Config.string().required(),

	/**
	 * @default []
	 * @memberof Text
	 * @type {?array<object>}
	.setter('_loadOptionsFn').
	 */

	options: Config.arrayOf(
		Config.shapeOf({
			active: Config.bool().value(false),
			disabled: Config.bool().value(false),
			id: Config.string(),
			inline: Config.bool().value(false),
			label: Config.string(),
			name: Config.string(),
			showLabel: Config.bool().value(true),
			value: Config.string()
		})
	).value([]),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	placeholder: Config.string().value(''),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	predefinedValue: Config.string().value(''),

	/**
	 * @default false
	 * @instance
	 * @memberof Text
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
	 * @memberof Text
	 * @type {?(bool|undefined)}
	 */

	required: Config.bool().value(false),

	/**
	 * @default true
	 * @instance
	 * @memberof Text
	 * @type {?(bool|undefined)}
	 */

	showLabel: Config.bool().value(true),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
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
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	type: Config.string().value('text'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	value: Config.string().value('')
};

Soy.register(Text, templates);

export default Text;
