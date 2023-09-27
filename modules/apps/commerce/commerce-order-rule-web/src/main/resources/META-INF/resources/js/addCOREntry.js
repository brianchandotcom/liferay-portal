/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ServiceProvider from 'commerce-frontend-js/ServiceProvider/index';
import {CLOSE_MODAL} from 'commerce-frontend-js/utilities/eventsDefinitions';
import {createPortletURL} from 'frontend-js-web';

export default function ({editCOREntryPortletURL, namespace}) {
	const orderRuleResource = ServiceProvider.AdminOrderAPI('v1');

	const form = document.getElementById(`${namespace}fm`);

	form.addEventListener('submit', (event) => {
		event.preventDefault();

		const description = form.querySelector(`#${namespace}description`)
			.value;
		const name = form.querySelector(`#${namespace}name`).value;
		const type = form.querySelector(`#${namespace}type`).value;

		const orderRuleEntryData = {
			description,
			name,
			type,
		};

		return orderRuleResource
			.addOrderRule(orderRuleEntryData)
			.then((payload) => {
				const redirectURL = createPortletURL(editCOREntryPortletURL);

				redirectURL.searchParams.append(
					`${namespace}corEntryId`,
					payload.id
				);
				redirectURL.searchParams.append('p_auth', Liferay.authToken);

				window.parent.Liferay.fire(CLOSE_MODAL, {
					redirectURL: redirectURL.toString(),
					successNotification: {
						message: Liferay.Language.get(
							'your-request-completed-successfully'
						),
						showSuccessNotification: true,
					},
				});
			});
	});
}
