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

import './ColorPaletteFieldDelegateTemplate.soy';
import {getConnectedComponent} from '../../../../store/ConnectedComponent.es';
import templates from './ColorPaletteField.soy';

/**
 * ColorPaletteField
 */
class ColorPaletteField extends Component {
	/**
	 * Handle Color Value Change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleColorValueChanged(event) {
		const targetElement = event.delegateTarget;

		this.emit('fieldValueChanged', {
			name: this.field.name,
			value: {
				cssClass: targetElement.dataset.backgroundColorCssClass,
				rgbValue: getComputedStyle(targetElement).backgroundColor
			}
		});
	}
}

ColorPaletteField.STATE = {
	/**
	 * The configuration field
	 * @review
	 * @type {object}
	 */
	field: Config.shapeOf({
		dataType: Config.string(),
		defaultValue: Config.object(),
		description: Config.string(),
		label: Config.string(),
		name: Config.string(),
		type: Config.string(),
		typeOptions: Config.object()
	})
};

const ConnectedColorPaletteField = getConnectedComponent(ColorPaletteField, [
	'themeColorsCssClasses'
]);

Soy.register(ConnectedColorPaletteField, templates);

export {ConnectedColorPaletteField, ColorPaletteField};
export default ConnectedColorPaletteField;
