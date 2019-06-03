import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './FloatingToolbarColorPicker.soy';

/**
 * FloatingToolbarColorPicker
 */
class FloatingToolbarColorPicker extends Component {
	/**
	 * Continues the propagation of the color button clicked event
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleColorButtonClick(event) {
		this.emit('colorClicked', {
			color: event.delegateTarget.dataset.backgroundColorCssClass
		});
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarColorPicker.STATE = {
	/**
	 * Available colors
	 * @instance
	 * @memberof FloatingToolbarColorPicker
	 * @review
	 * @type {Array}
	 */
	colors: Config.array().required(),

	/**
	 * Selected color
	 * @instance
	 * @memberof FloatingToolbarColorPicker
	 * @review
	 * @type {string}
	 */
	selectedColor: Config.string(),

	/**
	 * Show clear button or not
	 * @instance
	 * @memberof FloatingToolbarColorPicker
	 * @review
	 * @type {boolean}
	 */
	showClearButton: Config.bool(false)
};

Soy.register(FloatingToolbarColorPicker, templates);

export {FloatingToolbarColorPicker};
export default FloatingToolbarColorPicker;
