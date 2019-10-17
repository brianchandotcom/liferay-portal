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

import {isDef, isObject} from 'metal';

import getFormElement from './get_form_element.es';

/**
 * Sets the form elements to given values.
 * @param {!Element} form The form DOM element
 * @param {!Object} data An Object containing names and values of form
 * elements
 * @review
 */

export default function setFormValues(form, data) {
	if (!isDef(form) || form.nodeName !== 'FORM' || !isObject(data)) {
		return;
	}

	const entries = Object.entries(data);

	entries.forEach(([elementName, elementValue]) => {
		const element = getFormElement(form, elementName);

		if (element) {
			element.value = elementValue;
		}
	});
}
