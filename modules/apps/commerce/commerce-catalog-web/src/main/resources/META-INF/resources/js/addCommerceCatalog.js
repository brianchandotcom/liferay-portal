/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormUtils, commerceEvents} from 'commerce-frontend-js';
import {createPortletURL} from 'frontend-js-web';

export default function addCommerceCatalog({editCatalogPortletURL, namespace}) {
	Liferay.provide(window, `${namespace}apiSubmit`, (form) => {
		const API_URL = '/o/headless-commerce-admin-catalog/v1.0/catalogs';

		window.parent.Liferay.fire(commerceEvents.IS_LOADING_MODAL, {
			isLoading: true,
		});

		FormUtils.apiSubmit(form, API_URL)
			.then((payload) => {
				const redirectURL = createPortletURL(editCatalogPortletURL, {
					commerceCatalogId: payload.id,
					p_auth: Liferay.authToken,
				});

				window.parent.Liferay.fire(commerceEvents.CLOSE_MODAL, {
					redirectURL,
					successNotification: {
						message: Liferay.Language.get(
							'your-request-completed-successfully'
						),
						showSuccessNotification: true,
					},
				});
			})
			.catch((error) => {
				window.parent.Liferay.fire(commerceEvents.IS_LOADING_MODAL, {
					isLoading: false,
				});

				window.parent.Liferay.Util.openToast({
					message:
						error.title ||
						Liferay.Language.get('an-unexpected-error-occurred'),
					title: Liferay.Language.get('danger'),
					type: 'danger',
				});
			});
	});
}
