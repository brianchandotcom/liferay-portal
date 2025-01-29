/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Autocomplete, CommerceServiceProvider} from 'commerce-frontend-js';
import {createPortletURL} from 'frontend-js-web';

export default function ({
	editCPConfigurationListRenderURL,
	namespace,
	windowState,
}) {
	const commerceCatalogIdSelect = document.getElementById(
		`${namespace}commerceCatalogId`
	);

	let commerceCatalogId = 0;

	commerceCatalogIdSelect.addEventListener('change', (event) => {
		commerceCatalogId = event.target.value;

		Autocomplete('autocomplete', 'autocomplete-root', {
			apiUrl: `/o/headless-commerce-admin-catalog/v1.0/product-configuration-lists?catalogId=${commerceCatalogId}`,
			initialValue: '',
			inputId: `${namespace}parentCPConfigurationId`,
			inputName: `${namespace}parentCPConfigurationId`,
			itemsKey: 'id',
			itemsLabel: 'name',
			required: true,
		});
	});

	Autocomplete('autocomplete', 'autocomplete-root', {
		apiUrl: `/o/headless-commerce-admin-catalog/v1.0/product-configuration-lists`,
		disabled: true,
		inputId: `${namespace}parentCPConfigurationId`,
		inputName: `${namespace}parentCPConfigurationId`,
		itemsKey: 'id',
		itemsLabel: 'name',
		required: true,
	});

	let formSubmitted = false;

	Liferay.provide(window, `${namespace}submitForm`, () => {
		if (formSubmitted) {
			return;
		}

		formSubmitted = true;

		const formattedData = {};

		formattedData.catalogId = document.querySelector(
			`#${namespace}commerceCatalogId`
		)?.value;
		formattedData.name = document.querySelector(`#${namespace}name`)?.value;
		formattedData.parentProductConfigurationListId = document.querySelector(
			`#${namespace}parentCPConfigurationId`
		)?.value;
		formattedData.priority = document.querySelector(
			`#${namespace}priority`
		)?.value;

		const AdminCatalogResource =
			CommerceServiceProvider.AdminCatalogAPI('v1');
		AdminCatalogResource.addProductConfigurationList(formattedData)
			.then((productConfigurationList) => {
				const redirectURL = createPortletURL(
					editCPConfigurationListRenderURL,
					{
						cpConfigurationListId: productConfigurationList.id,
						p_p_state: windowState,
						screenNavigationCategoryKey: 'details',
					}
				);

				window.top.location.href = redirectURL;
			})
			.catch(({message}) => {
				if (message !== 'cancel') {
					window.top.Liferay.Util.openToast({
						message:
							message ||
							Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						type: 'danger',
					});
				}
				formSubmitted = false;
			});
	});
}
