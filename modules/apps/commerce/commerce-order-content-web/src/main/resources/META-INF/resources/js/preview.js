/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
