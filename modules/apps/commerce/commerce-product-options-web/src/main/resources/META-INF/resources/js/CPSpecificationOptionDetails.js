/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {slugify} from 'commerce-frontend-js';

export default function ({namespace}) {
	const form = document.getElementById(namespace + 'fm');
	const keyInput = form.querySelector('#' + namespace + 'key');
	const titleInput = form.querySelector('#' + namespace + 'title');

	const handleOnTitleInput = function () {
		keyInput.value = slugify(titleInput.value);
	};

	titleInput.addEventListener(
		'input',
		Liferay.Util.debounce(handleOnTitleInput, 200)
	);
}
