/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider, commerceEvents} from 'commerce-frontend-js';

export default function ({commerceDiscountId, namespace}) {
	const CommerceDiscountRuleResource =
		CommerceServiceProvider.AdminPricingAPI('v2');

	Liferay.provide(window, `${namespace}apiSubmit`, () => {
		const discountRuleData = {
			name: document.getElementById('name').value,
			type: document.getElementById('type').value,
		};

		return CommerceDiscountRuleResource.addDiscountRule(
			commerceDiscountId,
			discountRuleData
		)
			.then(() => {
				window.parent.Liferay.fire(commerceEvents.CLOSE_MODAL, {
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
