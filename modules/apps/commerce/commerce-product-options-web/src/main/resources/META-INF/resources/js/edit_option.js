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

import slugify from 'commerce-frontend-js/utilities/slugify'
import {debounce} from 'frontend-js-web';

export default function ({namespace}) {
	var form = document.getElementById('#'+namespace + 'fm');

	var keyInput = form.querySelector('#'+namespace+ 'key');
	var nameInput = form.querySelector('#'+namespace + 'name');

	var handleOnNameInput = function (event) {
		keyInput.value = slugify.default(nameInput.value);
	};

	nameInput.addEventListener('input', debounce(handleOnNameInput, 200));

	document
		.getElementById( namespace +'publishButton')
		.addEventListener('click', function (e) {
			e.preventDefault();

			var form = document.getElementById(namespace+'fm');

			if (!form) {
				throw new Error('Form with id: '+namespace+'fm not found!');
			}

			submitForm(form);
		});
}