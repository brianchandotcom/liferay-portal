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

import './RadioRegister.soy';

import 'clay-radio';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import {setJSONArrayValue} from '../util/setters.es';
import templates from './Radio.soy';

/**
 * Radio.
 * @extends Component
 */

class Radio extends Component {
	prepareStateForRender(state) {
		const {predefinedValue} = state;
		const predefinedValueArray = this._getArrayValue(predefinedValue);

		return {
			...state,
			predefinedValue: predefinedValueArray[0] || ''
		};
	}

	_getArrayValue(value) {
		let newValue = value || '';

		if (!Array.isArray(newValue)) {
			newValue = [newValue];
		}

		return newValue;
	}

	_handleFieldBlurred() {
		this.emit('fieldBlurred', {
			fieldInstance: this,
			originalEvent: window.event,
			value: window.event.target.value
		});
	}

	_handleFieldFocused(event) {
		this.emit('fieldFocused', {
			fieldInstance: this,
			originalEvent: event
		});
	}

	_handleValueChanged(event) {
		this.emit('fieldEdited', {
			fieldInstance: this,
			originalEvent: event,
			value: event.target.value
		});
	}
}

Radio.STATE = {
	/**
	 * @default 'string'
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	dataType: Config.string().value('string'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FieldBase
	 * @type {?bool}
	 */

	evaluable: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Radio
	 * @type {?(string|undefined)}
	 */

	fieldName: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Radio
	 * @type {?(string|undefined)}
	 */

	inline: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Radio
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Radio
	 * @type {?(string|undefined)}
	 */

	options: Config.arrayOf(
		Config.shapeOf({
			label: Config.string(),
			name: Config.string(),
			value: Config.string()
		})
	).value([
		{
			label: 'Option 1'
		},
		{
			label: 'Option 2'
		}
	]),

	/**
	 * @default Choose an Option
	 * @instance
	 * @memberof Radio
	 * @type {?string}
	 */

	predefinedValue: Config.oneOfType([
		Config.array(),
		Config.object(),
		Config.string()
	])
		.setter(setJSONArrayValue)
		.value([]),

	/**
	 * @default false
	 * @instance
	 * @memberof Radio
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
	 * @memberof Radio
	 * @type {?bool}
	 */

	required: Config.bool().value(false),

	/**
	 * @default true
	 * @instance
	 * @memberof Radio
	 * @type {?bool}
	 */

	showLabel: Config.bool().value(true),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Radio
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Radio
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	type: Config.string().value('radio'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Radio
	 * @type {?(string|undefined)}
	 */

	value: Config.string()
};

Soy.register(Radio, templates);

export default Radio;
