/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createPortletURL, fetch, getOpener, openToast} from 'frontend-js-web';

export default function ({namespace, order, parentSiteNavigationMenuItemId}) {
	const addButton = document.getElementById(`${namespace}addButton`);

	if (addButton) {
		const onClick = () => {
			const formValidator = Liferay.Form.get(`${namespace}fm`)
				.formValidator;

			formValidator.validate();

			if (formValidator.hasErrors()) {
				return;
			}

			const form = document.getElementById(`${namespace}fm`);
			const formData = new FormData(form);

			const url = createPortletURL(form.action, {
				order,
				parentSiteNavigationMenuItemId,
			});

			fetch(url, {
				body: formData,
				method: 'POST',
			})
				.then((response) => response.json())
				.then((response) => {
					if (response.siteNavigationMenuItemId) {
						getOpener().Liferay.fire(
							'reloadSiteNavigationMenuEditor'
						);

						getOpener().Liferay.fire('closeModal', {
							id: `${namespace}addMenuItem`,
						});
					}
					else {
						openToast({
							message: response.errorMessage,
							type: 'danger',
						});
					}
				});
		};

		addButton.addEventListener('click', onClick);

		return {
			dispose() {
				addButton.removeEventListener('click', onClick);
			},
		};
	}
}
