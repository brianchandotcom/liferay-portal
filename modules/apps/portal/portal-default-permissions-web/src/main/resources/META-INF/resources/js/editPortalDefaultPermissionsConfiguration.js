/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

export default function ({namespace, updateRolePermissionsURL}) {
	const saveButton = document.getElementById(`${namespace}saveButton`);

	if (saveButton) {
		saveButton.addEventListener('click', (event) => {
			event.preventDefault();

			const form = document.getElementById(`${namespace}fm`);

			if (form) {
				fetch(updateRolePermissionsURL, {
					body: new FormData(form),
					method: 'POST',
				}).then((response) => {
					response.json().then((res) => {
						if (res.success) {
							window.top.Liferay.fire('closeModal');

							window.top.Liferay.Util.openToast({
								message: Liferay.Language.get(
									'your-request-completed-successfully'
								),
								type: 'success',
							});
						}
						else {
							window.top.Liferay.Util.openToast({
								message: Liferay.Language.get(
									'an-unexpected-error-occurred'
								),
								type: 'danger',
							});
						}
					});
				});
			}
		});
	}
}
