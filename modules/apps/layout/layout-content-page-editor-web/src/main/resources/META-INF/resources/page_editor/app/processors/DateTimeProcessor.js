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
