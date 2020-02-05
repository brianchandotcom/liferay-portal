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

import {openImageSelector} from '../../utils/FragmentsEditorDialogUtils';
import {FLOATING_TOOLBAR_BUTTONS} from '../../utils/constants';

/**
 * Handle item selector image changes and propagate them with an
 * "editableChanged" event.
 * @param {object} image
 * @param {HTMLElement} editableElement
 * @param {string} fragmentEntryLinkId
 * @param {function} changedCallback
 * @private
 */
function _handleImageEditorChange(
	image,
	editableElement,
	fragmentEntryLinkId,
	changedCallback
) {
	const imageElement = editableElement.querySelector('img');

	if (imageElement && image.url) {
		imageElement.src = image.url;

		changedCallback(image);
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
	const buttons = [];

	const linkButton = {...FLOATING_TOOLBAR_BUTTONS.link};

	if (
		editableValues.config &&
		(editableValues.config.fieldId ||
			editableValues.config.href ||
			editableValues.config.mappedField)
	) {
		linkButton.cssClass =
			'fragments-editor__floating-toolbar--linked-field';
	}

	buttons.push(linkButton);

	if (!editableValues.mappedField && !editableValues.fieldId) {
		buttons.push(FLOATING_TOOLBAR_BUTTONS.imageProperties);
	}

	const mapButton = {...FLOATING_TOOLBAR_BUTTONS.map};

	if (editableValues.fieldId || editableValues.mappedField) {
		mapButton.cssClass = 'fragments-editor__floating-toolbar--mapped-field';
	}

	buttons.push(mapButton);

	return buttons;
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
	openImageSelector(image => {
		_handleImageEditorChange(
			image,
			editableElement,
			fragmentEntryLinkId,
			changedCallback
		);
	}, destroyedCallback);
}

/**
 * @param {string} content editableField's original HTML
 * @param {object} value Translated/segmented value
 * @param {object} editableValues values of the element
 * @return {string} Transformed content
 */
function render(content, value, editableValues) {
	const wrapper = document.createElement('div');

	const config = (editableValues && editableValues.config) || {};

	wrapper.innerHTML = content;

	const image = wrapper.querySelector('img');

	if (image) {
		image.src = value.url || value || editableValues.defaultValue;

		if (config.alt) {
			image.alt = config.alt;
		}
	}

	if (editableValues && editableValues.config && editableValues.config.href) {
		const link = document.createElement('a');
		const {config} = editableValues;

		link.href = config.href;

		if (config.target) {
			link.target = config.target;
		}

		link.appendChild(image);

		return link.outerHTML;
	}
	else {
		return wrapper.innerHTML;
	}
}

export default {
	destroy,
	getFloatingToolbarButtons,
	init,
	render
};
