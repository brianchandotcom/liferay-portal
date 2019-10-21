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

import {isDef, isObject, isString} from 'metal';
import dom from 'metal-dom';

import setFormValues from './set_form_values.es';

/**
 * Submits the form, with optional setting of form elements.
 * @param {!Element|!string} form The form DOM element or the selector
 * @param {Object=} options An object containing optional settings:
 * - `url` : a string containing form action url
 * - `data` : an object containing form elements keys and values, to be set
 * before submission
 * @review
 */

export default function postForm(form, options) {
	form = dom.toElement(form);

	if (form && form.nodeName === 'FORM') {
		form.setAttribute('method', 'post');

		if (isObject(options)) {
			const {data, url} = options;

			if (isObject(data)) {
				setFormValues(form, data);
			} else {
				return;
			}

			if (!isDef(url)) {
				submitForm(form);
			} else if (isString(url)) {
				submitForm(form, url);
			}
		} else {
			submitForm(form);
		}
	}
}
