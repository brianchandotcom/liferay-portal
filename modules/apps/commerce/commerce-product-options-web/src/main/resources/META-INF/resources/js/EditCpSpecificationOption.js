/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import slugify from 'commerce-frontend-js/utilities/slugify';
import {debounce} from 'frontend-js-web';

export default function ({namespace}) {
	const form = document.getElementById(namespace + 'fm');
	const keyInput = form.querySelector('#' + namespace + 'key');

	const handleOnKeyInput = function () {
		keyInput.value = slugify(keyInput.value);
	};

	keyInput.addEventListener('input', debounce(handleOnKeyInput, 200));
}
