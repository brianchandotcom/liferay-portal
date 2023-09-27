/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNullOrUndefined} from '@liferay/layout-js-components-web';

/**
 * @param {HTMLElement} element HTMLElement where the editor
 *  should be applied to.
 * @param {function} changeCallback Function that should be called whenever the
 *  editor produces a change. It must receive a string with
 *  the new editable value.
 * @param {function} destroyCallback Function that should be called if
 *  the editor is destroyed for any internal reason. This function does NOT need
 *  to be called if the editor is destroyed with destroyEditor function.
 * @param {Event} event that trigger the creation of the editor.
 *  This is passed only in the case of alloy editor based editors.
 */
function createEditor(element) {
	element.setAttribute('contenteditable', 'true');
	element.contentEditable = 'true';
}

/**
 * @param {HTMLElement} element HTMLElement where the editor has been created.
 */
function destroyEditor(element) {
	element.removeAttribute('contenteditable');
}

/**
 * @param {HTMLElement} element HTMLElement that should be mutated with the
 *  given value.
 * @param {string} value Any needed content to update the element. This type
 *  may vary between processors (ex. image elements may use an object for src
 *  and alt descriptions).
 * @param {object} config config Editable value's config object
 */
function render(element, value) {
	if (!isNullOrUndefined(value)) {
		element.innerHTML = value;
	}
}

export default {
	createEditor,
	destroyEditor,
	render,
};
