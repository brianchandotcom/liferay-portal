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

import {fetch, openToast} from 'frontend-js-web';

export default function ({namespace, selPortletId, selPortletIsAjaxable}) {
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

			fetch(form.action, {
				body: formData,
				method: 'POST',
			})
				.then((response) => response.json())
				.then((response) => {
					if (response.siteNavigationMenuItemId) {
						Liferay.fire('closeWindow', {
							id: `_${selPortletId}_addMenuItem`,
							portletAjaxable: selPortletIsAjaxable,
							refresh: selPortletId,
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
