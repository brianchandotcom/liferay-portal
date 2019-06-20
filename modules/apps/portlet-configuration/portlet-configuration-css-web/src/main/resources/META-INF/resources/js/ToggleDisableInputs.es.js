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
import core from 'metal';
import Soy from 'metal-soy';

import templates from './ToggleDisableInputs.soy';

/**
 * ToggleDisableInputs
 *
 * This class creates a switch button that enable/disable different inputs
 * based on its state.
 */

class ToggleDisableInputs extends Component {
	/**
	 * Toggles the state of the switch
	 */

	toggleSwitch() {
		this.checked = !this.checked;

		this.disableInputs_();
	}

	/**
	 * Disables the inputs based on switch state and disableOnChecked logic
	 */

	disableInputs_() {
		const {checked, disableOnChecked, inputSelector} = this;

		const inputs = document.querySelectorAll(inputSelector);

		for (let i = 0; i < inputs.length; i++) {
			const input = inputs[i];

			input.disabled = disableOnChecked ? checked : !checked;
		}
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */

ToggleDisableInputs.STATE = {
	/**
	 * Switch state
	 * @type {Boolean}
	 */

	checked: {
		validator: core.isBoolean,
		value: false
	},

	/**
	 * Flag to specify the logic for disabling inputs based on switch state
	 * @type {Boolean}
	 */

	disableOnChecked: {
		validator: core.isBoolean,
		value: true
	},

	/**
	 * CSS Selector for the inputs to enable/disable
	 * @type {String}
	 */

	inputSelector: {
		validator: core.isString
	},

	/**
	 * Label of the switch
	 * @type {String}
	 */

	label: {
		validator: core.isString
	},

	/**
	 * Label of the "off" state
	 * @type {String}
	 */

	labelOff: {
		validator: core.isString
	},

	/**
	 * Label of the "on" state
	 * @type {String}
	 */

	labelOn: {
		validator: core.isString
	}
};

Soy.register(ToggleDisableInputs, templates);

export default ToggleDisableInputs;
