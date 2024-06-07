/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider, commerceEvents} from 'commerce-frontend-js';
import {createPortletURL} from 'frontend-js-web';

export default function ({
	defaultLanguageId,
	editPricingClassPortletURL = '',
	namespace,
}) {
	const CommerceProductGroupsResource =
		CommerceServiceProvider.AdminCatalogAPI('v1');

	Liferay.provide(window, `${namespace}apiSubmit`, () => {
		const description = document.getElementById('description').value;
		const title = document.getElementById('title').value;

		const productGroupData = {
			description: {[defaultLanguageId]: description},
			title: {[defaultLanguageId]: title},
		};

		return CommerceProductGroupsResource.addProductGroup(productGroupData)
			.then((payload) => {
				const redirectURL = createPortletURL(
					editPricingClassPortletURL,
					{
						commercePricingClassId: payload.id,
						p_auth: Liferay.authToken,
					}
				);

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
				return Promise.reject(error);
			});
	});
}
