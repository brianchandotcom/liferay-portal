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

import './MockFieldRegister.soy.js';

import Component from 'metal-component';
import Soy from 'metal-soy';
import Config from 'metal-state/lib/Config';

import templates from './MockField.soy.js';

class MockField extends Component {
	emitFieldEdited(value, fieldName) {
		this.value = value;

		this.emit('fieldEdited', {
			fieldInstance: this,
			originalEvent: {
				delegateTarget: this.element.querySelector('p'),
				target: {
					getAttribute: () => {
						return fieldName;
					}
				}
			},
			value
		});
	}
}

MockField.STATE = {
	fieldName: Config.string(),
	label: Config.string(),
	options: Config.array(),
	readOnly: Config.bool(),
	type: Config.string(),
	value: Config.any()
};

Soy.register(MockField, templates);

export default MockField;
