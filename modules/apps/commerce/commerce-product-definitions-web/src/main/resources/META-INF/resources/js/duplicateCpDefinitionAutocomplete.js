/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Autocomplete, FormUtils, commerceEvents} from 'commerce-frontend-js';
import {createPortletURL} from 'frontend-js-web';

export default function ({
	editProductDefinitionURL,
	namespace,
	ppState,
	productId,
	productStatus,
	productType,
}) {
	const product = {
		active: true,
		productStatus,
		productType,
	};

	Liferay.provide(window, `${namespace}apiSubmit`, (form) => {
		const API_URL = `/o/headless-commerce-admin-catalog/v1.0/products/${productId}/clone?catalogId=${product.catalogId}`;

		FormUtils.apiSubmit(form, API_URL)
			.then((payload) => {
				const redirectURL = createPortletURL(editProductDefinitionURL, {
					cpDefinitionId: payload.id,
					p_p_state: ppState,
				});

				window.parent.Liferay.fire(commerceEvents.CLOSE_MODAL, {
					redirectURL: redirectURL.toString(),
					successNotification: {
						message: Liferay.Language.get(
							'your-request-completed-successfully'
						),
						showSuccessNotification: true,
					},
				});
			})
			.catch(() => {
				window.parent.Liferay.fire(commerceEvents.IS_LOADING_MODAL, {
					isLoading: false,
				});

				Liferay.Util.openToast({
					closeable: true,
					delay: {
						hide: 5000,
						show: 0,
					},
					duration: 500,
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					render: true,
					title: Liferay.Language.get('danger'),
					type: 'danger',
				});
			});
	});

	Autocomplete('autocomplete', 'autocomplete-root', {
		apiUrl: '/o/headless-commerce-admin-catalog/v1.0/catalogs',
		inputId: `${namespace}catalogId`,
		inputName: `${namespace}catalogId`,
		itemsKey: 'id',
		itemsLabel: 'name',
		onValueUpdated(value, catalogData) {
			if (value) {
				product.catalogId = catalogData.id;
			}
		},
		required: true,
	});
}
