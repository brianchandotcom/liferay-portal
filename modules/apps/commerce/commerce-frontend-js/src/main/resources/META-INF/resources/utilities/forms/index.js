/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../AJAX/index';
import {isFormElement, toJSON} from './formsHelper';

export function apiSubmit(formElement, API_URL = null) {
	if (isFormElement(formElement)) {
		const jsonData = toJSON(new FormData(formElement));

		return AJAX.POST(API_URL, jsonData);
	}

	return Promise.reject(new Error('Not a form.'));
}

export default {apiSubmit};
