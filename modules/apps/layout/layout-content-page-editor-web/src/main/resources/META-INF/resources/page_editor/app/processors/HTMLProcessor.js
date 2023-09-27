/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';
import {isNullOrUndefined} from '@liferay/layout-js-components-web';

import HTMLEditorModal from '../components/HTMLEditorModal';

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
