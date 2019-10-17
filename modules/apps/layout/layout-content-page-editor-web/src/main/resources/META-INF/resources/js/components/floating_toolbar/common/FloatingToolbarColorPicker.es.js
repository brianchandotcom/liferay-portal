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
