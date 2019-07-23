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
 * Handle item selector image changes and propagate them with an
 * "editableChanged" event.
 * @param {string} url
 * @param {HTMLElement} editableElement
 * @param {string} fragmentEntryLinkId
 * @param {function} changedCallback
 * @private
 */
function _handleImageEditorChange(
	url,
	editableElement,
	fragmentEntryLinkId,
	changedCallback
) {
	const imageElement = editableElement.querySelector('img');

	if (imageElement && url) {
		imageElement.src = url;

		changedCallback(url);
	}
}

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
	return editableValues.mappedField
		? [FLOATING_TOOLBAR_BUTTONS.imageLink, FLOATING_TOOLBAR_BUTTONS.map]
		: [
				FLOATING_TOOLBAR_BUTTONS.imageProperties,
				FLOATING_TOOLBAR_BUTTONS.map
		  ];
}

/**
 * Show the image selector dialog and calls the given callback when an
 * image is selected.
 * @param {HTMLElement} editableElement
 * @param {string} fragmentEntryLinkId
 * @param {string} portletNamespace
 * @param {{imageSelectorURL: string}} options
 * @param {function} changedCallback
 * @param {function} destroyedCallback
 * @review
 */
function init(
	editableElement,
	fragmentEntryLinkId,
	portletNamespace,
	options,
	changedCallback,
	destroyedCallback
) {
	const {imageSelectorURL} = options;

	openImageSelector({
		callback: url => {
			_handleImageEditorChange(
				url,
				editableElement,
				fragmentEntryLinkId,
				changedCallback
			);
		},
		destroyedCallback,
		imageSelectorURL,
		portletNamespace
	});
}

/**
 * @param {string} content editableField's original HTML
 * @param {string} value Translated/segmented value
 * @param {object} editableValues values of the element
 * @return {string} Transformed content
 */
function render(content, value, editableValues) {
	const wrapper = document.createElement('div');

	const config = (editableValues && editableValues.config) || {};

	wrapper.innerHTML = content;

	const image = wrapper.querySelector('img');

	if (image) {
		image.src = value;

		if (config.alt) {
			image.alt = config.alt;
		}
	}

	return wrapper.innerHTML;
}

export default {
	destroy,
	getFloatingToolbarButtons,
	init,
	render
};
