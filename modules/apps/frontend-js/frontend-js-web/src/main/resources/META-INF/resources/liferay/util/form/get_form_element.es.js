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

import {isDef, isString} from 'metal';

/**
 * Returns a DOM element or elements in a form.
 * @param {!Element} form The form DOM element
 * @param {!string} elementName The name of the DOM element
 * @return {Element|NodeList|null} The DOM element or elements in the form, with
 * the given name
 * @review
 */

export default function getFormElement(form, elementName) {
	let formElement = null;

	if (isDef(form) && form.nodeName === 'FORM' && isString(elementName)) {
		const ns = form.dataset.fmNamespace || '';

		formElement = form.elements[ns + elementName] || null;
	}

	return formElement;
}
