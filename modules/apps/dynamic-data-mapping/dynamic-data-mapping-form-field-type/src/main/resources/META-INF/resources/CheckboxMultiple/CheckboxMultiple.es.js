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

import './CheckboxMultipleRegister.soy';

import 'clay-checkbox';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import {setJSONArrayValue} from '../util/setters.es';
import templates from './CheckboxMultiple.soy';

/**
 * CheckboxMultiple.
 * @extends Component
 */

class CheckboxMultiple extends Component {
	_handleFieldBlurred(event) {
		this.emit('fieldBlurred', {
			fieldInstance: this,
			originalEvent: event,
			value: event.target.value
		});
	}

	_handleFieldChanged(event) {
		const {target} = event;
		const value = this.value.filter(
			currentValue => currentValue !== target.value
		);

		if (target.checked) {
			value.push(target.value);
		}

		this.setState(
			{
				value
			},
			() => {
				this.emit('fieldEdited', {
					fieldInstance: this,
					originalEvent: event,
					value
				});
			}
		);
	}

	_handleFieldFocused(event) {
		this.emit('fieldFocused', {
			fieldInstance: this,
			originalEvent: event
		});
	}
}

Soy.register(CheckboxMultiple, templates);

CheckboxMultiple.STATE = {
	/**
	 * @default 'string'
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	dataType: Config.string().value('boolean'),

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
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	fieldName: Config.string(),

	/**
	 * @default false
	 * @instance
	 * @memberof Checkbox
	 * @type {?bool}
	 */

	inline: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Checkbox
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Checkbox
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
	 * @default undefined
	 * @instance
	 * @memberof Select
	 * @type {?string}
	 */

	predefinedValue: Config.setter(setJSONArrayValue).value([]),

	/**
	 * @default false
	 * @instance
	 * @memberof Checkbox
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
	 * @memberof Checkbox
	 * @type {?bool}
	 */

	required: Config.bool().value(false),

	/**
	 * @default true
	 * @instance
	 * @memberof Checkbox
	 * @type {?bool}
	 */

	showAsSwitcher: Config.bool().value(true),

	/**
	 * @default true
	 * @instance
	 * @memberof Checkbox
	 * @type {?bool}
	 */

	showLabel: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Checkbox
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	type: Config.string().value('checkbox'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Checkbox
	 * @type {?(bool)}
	 */

	value: Config.oneOfType([Config.array(), Config.object()]).setter(
		setJSONArrayValue
	)
};

export default CheckboxMultiple;
