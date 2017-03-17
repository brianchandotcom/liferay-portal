import Component from 'metal-component/src/Component';
import core from 'metal/src/core';
import Soy from 'metal-soy/src/Soy';

import templates from './ColorPickerInput.soy';

class ColorPickerInput extends Component {

	/**
	 * @inheritDoc
	 */
	rendered() {
		let instance = this;

		AUI().use('aui-color-picker-popover', function(A) {
			instance.colorPickerPopover = new A.ColorPickerPopover(
				{
					constrain: true,
					color: instance.color,
					trigger: '#' + instance.id,
					zIndex: Liferay.zIndex.POPOVER
				}
			);

			instance.colorPickerPopover.render(instance.element);
			instance.colorPickerPopover.after('select', instance.setColor_, instance);
		});
	}

	setColor_(event) {
		this.color = event.color;
	}
}

ColorPickerInput.STATE = {
	disabled: {
		validator: core.isBoolean,
		value: false
	},

	id: {
		validator: core.isString
	},

	label: {
		validator: core.isString
	},

	name: {
		validator: core.isString
	},

	color: {
		validator: core.isString
	},

	wrapperCssClass: {
		validator: core.isString
	}
}

// Register component
Soy.register(ColorPickerInput, templates);

export default ColorPickerInput;