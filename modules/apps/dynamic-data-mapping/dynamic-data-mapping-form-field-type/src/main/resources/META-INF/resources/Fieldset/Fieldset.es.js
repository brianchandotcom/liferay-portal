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

import './FieldsetRegister.soy';

import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './Fieldset.soy';

class Fieldset extends Component {
	_handleFieldEdited(event) {
		this.emit('fieldEdited', event);
	}
}

Fieldset.STATE = {
	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(number|undefined)}
	 */

	columnSize: Config.number(),

	/**
	 * @default false
	 * @instance
	 * @memberof Fieldset
	 * @type {?bool}
	 */

	evaluable: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	fieldName: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	name: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(array|undefined)}
	 */

	nestedFields: Config.array(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	placeholder: Config.string(),

	/**
	 * @default false
	 * @instance
	 * @memberof Fieldset
	 * @type {?bool}
	 */

	readOnly: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(bool|undefined)}
	 */

	repeatable: Config.bool(),

	/**
	 * @default false
	 * @instance
	 * @memberof Fieldset
	 * @type {?(bool|undefined)}
	 */

	required: Config.bool().value(false),

	/**
	 * @default true
	 * @instance
	 * @memberof Fieldset
	 * @type {?(bool|undefined)}
	 */

	showLabel: Config.bool().value(true),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	tooltip: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	type: Config.string().value('fieldset'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	value: Config.string().value('')
};

Soy.register(Fieldset, templates);

export default Fieldset;
