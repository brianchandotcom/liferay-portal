/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNullOrUndefined} from '@liferay/layout-js-components-web';

function createEditor(element) {
	element.setAttribute('contenteditable', 'true');
	element.contentEditable = 'true';
}

function destroyEditor() {}

/**
 * @param {HTMLElement} element HTMLElement that should be mutated with the
 *  given value.
 * @param {string} value Element content
 */
async function renderFn(
	element,
	value,
	_editableConfig,
	_languageId,
	withinCollection
) {
	if (isNullOrUndefined(value) || withinCollection) {
		return;
	}

	element.innerHTML = value;
}

export default {
	createEditor,
	destroyEditor,
	render: renderFn,
};
