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

import './SelectFieldDelegateTemplate.soy';
import {setIn} from '../../../../utils/FragmentsEditorUpdateUtils.es';
import templates from './SelectField.soy';

/**
 * SelectField
 */
class SelectField extends Component {
	/**
	 * @inheritdoc
	 * @review
	 */
	prepareStateForRender(state) {
		const nextState = state;

		let selectedOption = this.field.defaultValue;

		if (
			this.configurationValues &&
			this.configurationValues[this.field.name]
		) {
			selectedOption = this.configurationValues[this.field.name];
		}

		return setIn(nextState, ['selectedOption'], selectedOption);
	}

	/**
	 * Handle Select Value Change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleSelectValueChanged(event) {
		const targetElement = event.delegateTarget;

		this.emit('fieldValueChanged', {
			name: this.field.name,
			value: targetElement.options[targetElement.selectedIndex].value
		});
	}
}

SelectField.STATE = {
	/**
	 * Fragment Entry Link Configuration values
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @type {object}
	 */
	configurationValues: Config.object(),

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

Soy.register(SelectField, templates);

export {SelectField};
export default SelectField;
