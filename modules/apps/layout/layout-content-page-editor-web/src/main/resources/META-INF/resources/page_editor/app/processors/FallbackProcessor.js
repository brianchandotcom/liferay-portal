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
	element.innerHTML = value;
}

export default {
	createEditor,
	destroyEditor,
	render
};
