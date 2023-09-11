/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openImageSelector} from '../../common/openImageSelector';
import {getEditableLinkValue} from '../utils/getEditableLinkValue';
import {getEditableLocalizedValue} from '../utils/getEditableLocalizedValue';

/**
 * @param {HTMLElement} element HTMLElement where the editor
 *  should be applied to.
 * @param {function} changeCallback Function that should be called whenever the
 *  editor produces a change. It must receive two parameters, the editable value
 *  and the editable config.
 * @param {function} destroyCallback Function that should be called if
 *  the editor is destroyed for any internal reason. This function does NOT need
 *  to be called if the editor is destroyed with destroyEditor function.
 */
function createEditor(element, changeCallback, destroyCallback) {
	openImageSelector((image) => {
		changeCallback(image, {alt: ''});
	}, destroyCallback);
}

/**
 */
function destroyEditor() {}

/**
 * @param {HTMLElement} element HTMLElement that should be mutated with the
 *  given value.
 * @param {string} value Image url
 * @param {object} config Editable value's config object
 * @param {string} [config.href] Image anchor url
 * @param {string} [config.target] Image anchor target
 * @param {string} languageId Language id
 */
function render(element, value, editableConfig = {}, languageId) {
	let image = null;

	if (element instanceof HTMLImageElement) {
		image = element;
	}
	else {
		image = element.querySelector('img');
	}

	if (image) {
		image.alt =
			getEditableLocalizedValue(editableConfig.alt, languageId) ||
			value?.alt ||
			'';

		const link = getEditableLinkValue(editableConfig, languageId);

		if (link.href) {
			if (image.parentElement instanceof HTMLAnchorElement) {
				image.parentElement.href = link.href;
				image.parentElement.target = link.target;
			}
			else {
				const anchorElement = document.createElement('a');

				anchorElement.href = `${editableConfig.prefix || ''}${
					link.href
				}`;
				anchorElement.rel = link.rel;
				anchorElement.target = link.target;

				image.parentElement.replaceChild(anchorElement, image);
				anchorElement.appendChild(image);
			}
		}

		const imageValue =
			value && typeof value !== 'string' ? value.url : value;

		if (imageValue) {
			image.src = imageValue;
		}

		if (editableConfig.lazyLoading) {
			image.loading = 'lazy';
		}
	}
}

export default {
	createEditor,
	destroyEditor,
	render,
};
