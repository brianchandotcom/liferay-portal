/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal} from 'frontend-js-web';

function showConfirmationModal({message, namespace, primary}) {
	const saveButton = document.getElementById(`${namespace}saveButton`);

	const form = document.getElementById(`${namespace}fm`);

	saveButton.addEventListener('click', (event) => {
		event.preventDefault();

		if (primary) {
			return submitForm(form);
		}

		const primaryInput = form.querySelector('#' + namespace + 'primary');

		if (!primaryInput.checked) {
			return submitForm(form);
		}

		openConfirmModal({
			message,
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					submitForm(form);
				}
			},
		});
	});
}

export default function (context) {
	showConfirmationModal(context);
}
