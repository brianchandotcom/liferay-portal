import Component from 'metal-component';
import core from 'metal';
import Soy from 'metal-soy';

import templates from './ColorPickerInput.soy';

/**
 * ColorPickerInput
 *
 * This class creates an input with a ColorPickerPopover binded to it.
 */
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

	/**
	 * Sets the selected color
	 *
	 * @param  {Event} event
	 */
	setColor_(event) {
		this.color = event.color;
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
ColorPickerInput.STATE = {
	/**
	 * Input css class
	 * @type {String}
	 */
	cssClass: {
		validator: core.isString
	},

	/**
	 * Input disabled state
	 * @type {Boolean}
	 */
	disabled: {
		validator: core.isBoolean,
		value: false
	},

	/**
	 * Id for the input
	 * @type {String}
	 */
	id: {
		validator: core.isString
	},

	/**
	 * Label for the input
	 * @type {String}
	 */
	label: {
		validator: core.isString
	},

	/**
	 * Name for the input
	 * @type {String}
	 */
	name: {
		validator: core.isString
	},

	/**
	 * Current selected color
	 * @type {String}
	 */
	color: {
		validator: core.isString
	},

	/**
	 * Form group css class
	 * @type {String}
	 */
	wrapperCssClass: {
		validator: core.isString
	}
}

// Register component
Soy.register(ColorPickerInput, templates);

export default ColorPickerInput;