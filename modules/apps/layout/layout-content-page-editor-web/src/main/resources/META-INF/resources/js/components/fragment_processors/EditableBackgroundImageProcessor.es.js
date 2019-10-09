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

import {FLOATING_TOOLBAR_BUTTONS} from '../../utils/constants';
import {openImageSelector} from '../../utils/FragmentsEditorDialogUtils';

/**
 * Do nothing, as LiferayItemSelectorDialog is automatically
 * destroyed on hide.
 * @review
 */
function destroy() {}

/**
 * @param {object} editableValues
 * @return {object[]} Floating toolbar panels
 */
function getFloatingToolbarButtons(editableValues) {
	const buttons = [];

	const backgroundImageButton = {
		...FLOATING_TOOLBAR_BUTTONS.fragmentBackgroundImage
	};
	const mapButton = {...FLOATING_TOOLBAR_BUTTONS.map};

	if (editableValues.mappedField || editableValues.fieldId) {
		backgroundImageButton.cssClass =
			'disabled fragments-editor__floating-toolbar--disabled fragments-editor__floating-toolbar--mapped-field';
		mapButton.cssClass = 'fragments-editor__floating-toolbar--mapped-field';
	}

	buttons.push(backgroundImageButton, mapButton);

	return buttons;
}

/**
 * Show the image selector dialog and calls the given callback when an
 * image is selected.
 * @param {function} callback
 * @review
 */
function init(callback) {
	openImageSelector(callback);
}

/**
 * @param {HTMLElement} element
 * @param {object} [backgroundImage={}]
 */
function render(element, backgroundImage = '') {
	element.style.backgroundImage = backgroundImage
		? `url("${
				backgroundImage.url ? backgroundImage.url : backgroundImage
		  }")`
		: '';
	element.style.backgroundSize = 'cover';
}

export default {
	destroy,
	getFloatingToolbarButtons,
	init,
	render
};
