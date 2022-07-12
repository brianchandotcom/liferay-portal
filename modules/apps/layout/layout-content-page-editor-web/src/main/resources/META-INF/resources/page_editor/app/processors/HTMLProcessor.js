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

import {render} from '@liferay/frontend-js-react-web';

import HTMLEditorModal from '../components/HTMLEditorModal';
import isNullOrUndefined from '../utils/isNullOrUndefined';

/**
 * @param {HTMLElement} element HTMLElement where the editor
 *  should be applied to.
 * @param {function} changeCallback Function that should be called whenever the
 *  editor produces a change. It must receive a string with
 *  the new editable value.
 * @param {function} destroyCallback Function that should be called if
 *  the editor is destroyed for any internal reason. This function does NOT need
 *  to be called if the editor is destroyed with destroyEditor function.
 */
function createEditor(element, changeCallback, destroyCallback) {
	render(
		HTMLEditorModal,
		{
			initialContent: element.innerHTML,
			onClose: destroyCallback,
			onSave: (content) => {
				changeCallback(content);
				destroyCallback();
			},
		},
		document.createElement('div')
	);
}

/**
 */
function destroyEditor() {}

/**
 * @param {HTMLElement} element HTMLElement that should be mutated with the
 *  given value.
 * @param {string} value Element content
 */
function renderFn(element, value) {
	if (!isNullOrUndefined(value)) {
		element.innerHTML = value;
	}
}

export default {
	createEditor,
	destroyEditor,
	render: renderFn,
};
