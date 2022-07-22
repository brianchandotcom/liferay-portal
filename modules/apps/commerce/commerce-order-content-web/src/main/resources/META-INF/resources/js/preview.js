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

import {getOpener} from 'frontend-js-web';

export default function ({namespace}) {
	const importButton = document.getElementById(`${namespace}importButton`);

	if (importButton) {
		importButton.addEventListener('click', (event) => {
			event.preventDefault();

			const openerWindow = getOpener();
			const form = document.getElementById(`${namespace}fm`);

			form.classList.add('hide');

			openerWindow.document.body.appendChild(form);
			openerWindow.submitForm(form);
		});
	}
}
