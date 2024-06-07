/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider, commerceEvents} from 'commerce-frontend-js';
import {createPortletURL} from 'frontend-js-web';

export default function ({
	editCommerceDiscountRenderURL = '',
	level,
	limitationType,
	namespace,
}) {
	const CommerceDiscountResource =
		CommerceServiceProvider.AdminPricingAPI('v2');

	Liferay.provide(window, `${namespace}apiSubmit`, () => {
		const discountData = {
			level,
			limitationType,
			target: document.getElementById('commerceDiscountTarget').value,
			title: document.getElementById('title').value,
			usePercentage: document.getElementById('commerceDiscountType')
				.value,
		};

		return CommerceDiscountResource.addDiscount(discountData)
			.then((payload) => {
				const redirectURL = createPortletURL(
					editCommerceDiscountRenderURL,
					{
						commerceDiscountId: payload.id,
						p_auth: Liferay.authToken,
						usePercentage: payload.usePercentage,
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
