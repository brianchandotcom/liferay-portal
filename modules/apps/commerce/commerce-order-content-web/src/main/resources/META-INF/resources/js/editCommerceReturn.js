/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider} from 'commerce-frontend-js';
import {openToast} from 'frontend-js-web';

function handleEvent(
	{commerceReturnId, fieldName, namespace, redirectURL, returnStatus},
	form
) {
	const CommerceReturnResource = CommerceServiceProvider.ReturnAPI();

	const field = form.querySelector(`#${namespace}${fieldName}`);

	const returnData = {
		...(field && fieldName && {[fieldName]: field.value}),
		...(returnStatus && {returnStatus}),
	};

	return CommerceReturnResource.updateItemById(commerceReturnId, returnData)
		.then(() => {
			window.top.location.href = redirectURL;

			openToast({
				message: Liferay.Language.get(
					'your-request-completed-successfully'
				),
				type: 'success',
			});
		})
		.catch((error) => {
			openToast({
				message:
					error.message ||
					Liferay.Language.get('an-unexpected-error-occurred'),
				type: 'danger',
			});
		});
}

export default function ({
	commerceReturnId,
	fieldName,
	namespace,
	redirectURL,
	returnStatus,
}) {
	const form = document.getElementById(`${namespace}fm`);

	if (form) {
		form.addEventListener('submit', (event) => {
			event.preventDefault();

			handleEvent(
				{
					commerceReturnId,
					fieldName,
					namespace,
					redirectURL,
					returnStatus,
				},
				form
			);
		});
	}

	const submitReturnRequestButton = document.getElementById(
		`${namespace}submitReturnRequestButton`
	);

	if (form && submitReturnRequestButton) {
		submitReturnRequestButton.addEventListener('click', (event) => {
			event.preventDefault();

			handleEvent(
				{
					commerceReturnId,
					fieldName,
					namespace,
					redirectURL,
					returnStatus,
				},
				form
			);
		});
	}
}
