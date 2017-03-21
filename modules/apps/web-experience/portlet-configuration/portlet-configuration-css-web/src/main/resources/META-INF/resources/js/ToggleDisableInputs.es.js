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
		let inputs = document.querySelectorAll(this.inputSelector);

		inputs.forEach(
			(input) => {
				if (this.disableOnChecked) {
					input.disabled = this.checked;
				}
				else {
					input.disabled = !this.checked;
				}
			}
		);
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
	 * CSS Selector for the inputs to enable/disable
	 * @type {String}
	 */
	inputSelector: {
		validator: core.isString,
		value: []
	},

	/**
	 * Label of the switch
	 * @type {String}
	 */
	label: {
		validator: core.isString
	},

	/**
	 * Flag to specify the logic for disabling inputs based on switch state
	 * @type {Boolean}
	 */
	disableOnChecked: {
		validator: core.isBoolean,
		value: true
	}
}

// Register component
Soy.register(ToggleDisableInputs, templates);

export default ToggleDisableInputs;