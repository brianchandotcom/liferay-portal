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

import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import './TextFieldDelegateTemplate.soy';
import templates from './TextField.soy';

/**
 * TextField
 */
class TextField extends Component {
	/**
	 * @inheritdoc
	 * @review
	 */
	prepareStateForRender(state) {
		let nextState = state;

		const value = state.configurationValues[this.field.name];

		if (value) {
			nextState = {
				...state,
				configurationValues: {
					...state.configurationValues,
					[this.field.name]: value
				}
			};
		}

		return nextState;
	}

	/**
	 * Handle Text Value Change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleTextValueChanged(event) {
		const targetElement = event.delegateTarget;

		this.emit('fieldValueChanged', {
			name: this.field.name,
			value: `${targetElement.value}`
		});
	}
}

TextField.STATE = {
	/**
	 * The configuration field
	 * @review
	 * @type {object}
	 */
	field: Config.shapeOf({
		dataType: Config.string(),
		defaultValue: Config.string(),
		description: Config.string(),
		label: Config.string(),
		name: Config.string(),
		type: Config.string(),
		typeOptions: Config.object()
	})
};

Soy.register(TextField, templates);

export {TextField};
export default TextField;
