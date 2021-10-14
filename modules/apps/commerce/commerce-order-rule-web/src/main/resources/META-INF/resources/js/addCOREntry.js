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
