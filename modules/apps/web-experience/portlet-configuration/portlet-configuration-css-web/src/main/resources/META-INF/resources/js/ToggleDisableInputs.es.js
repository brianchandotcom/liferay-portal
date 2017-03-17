import Component from 'metal-component/src/Component';
import core from 'metal/src/core';
import Soy from 'metal-soy/src/Soy';

import templates from './ToggleDisableInputs.soy';

class ToggleDisableInputs extends Component {
	toggleSwitch() {
		this.checked = !this.checked;

		this.disableInputs_();
	}

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

ToggleDisableInputs.STATE = {
	checked: {
		validator: core.isBoolean,
		value: false
	},

	inputSelector: {
		validator: core.isString,
		value: []
	},

	label: {
		validator: core.isString
	},

	disableOnChecked: {
		validator: core.isBoolean,
		value: true
	}
}

// Register component
Soy.register(ToggleDisableInputs, templates);

export default ToggleDisableInputs;