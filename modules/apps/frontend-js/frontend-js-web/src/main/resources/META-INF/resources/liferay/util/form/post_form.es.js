/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isObject from './../is_object';
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
	if (typeof form === 'string') {
		form = document.querySelector(form);
	}

	if (form && form.nodeName === 'FORM') {
		form.setAttribute('method', 'post');

		if (isObject(options)) {
			const {data, url} = options;

			if (isObject(data)) {
				setFormValues(form, data);
			}
			else {
				return;
			}

			if (url === undefined) {
				submitForm(form);
			}
			else if (typeof url === 'string') {
				submitForm(form, url);
			}
		}
		else {
			submitForm(form);
		}
	}
}
