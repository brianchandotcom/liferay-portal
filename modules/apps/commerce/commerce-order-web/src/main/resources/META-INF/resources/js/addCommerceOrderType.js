/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ServiceProvider from 'commerce-frontend-js/ServiceProvider/index';
import {CLOSE_MODAL} from 'commerce-frontend-js/utilities/eventsDefinitions';
import {createPortletURL, openToast} from 'frontend-js-web';

export default function ({
	defaultLanguageId,
	editCommerceOrderTypePortletURL,
	namespace,
}) {
	const CommerceOrderTypeResource = ServiceProvider.AdminOrderAPI('v1');

	const form = document.getElementById(`${namespace}fm`);

	form.addEventListener('submit', (event) => {
		event.preventDefault();

		const description = form.querySelector(`#${namespace}description`)
			.value;
		const name = form.querySelector(`#${namespace}name`).value;

		if (!name) {
			openToast({
				message: Liferay.Language.get('please-enter-a-valid-name'),
				title: Liferay.Language.get('error'),
				type: 'danger',
			});

			return;
		}

		const orderTypeData = {
			description: {[defaultLanguageId]: description},
			name: {[defaultLanguageId]: name},
		};

		return CommerceOrderTypeResource.addOrderType(orderTypeData)
			.then((payload) => {
				const redirectURL = createPortletURL(
					editCommerceOrderTypePortletURL
				);

				redirectURL.searchParams.append(
					`${namespace}commerceOrderTypeId`,
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
			})
			.catch((error) => {
				const errorsMap = {
					'please-enter-a-valid-name': Liferay.Language.get(
						'please-enter-a-valid-name'
					),
				};

				openToast({
					message:
						errorsMap[error.message] ||
						Liferay.Language.get('an-unexpected-error-occurred'),
					title: Liferay.Language.get('error'),
					type: 'danger',
				});
			});
	});
}
