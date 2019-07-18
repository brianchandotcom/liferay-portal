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
import templates from './FloatingToolbarDropdown.soy';
import {Config} from 'metal-state';

/**
 * FloatingToolbarDropdown
 */
class FloatingToolbarDropdown extends Component {}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarDropdown.STATE = {
	/**
	 * @default undefined
	 * @memberof FloatingToolbarDropdown
	 * @review
	 * @type {object}
	 */
	_button: Config.object().internal(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarDropdown
	 * @review
	 * @type {object}
	 */
	_dropdown: Config.object().internal(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarDropdown
	 * @review
	 * @type {string}
	 */
	buttonId: Config.string(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarDropdown
	 * @review
	 * @type {string}
	 */
	dropdownId: Config.string()
};

Soy.register(FloatingToolbarDropdown, templates);

export {FloatingToolbarDropdown};
export default FloatingToolbarDropdown;
