/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ServiceProvider from 'commerce-frontend-js/ServiceProvider/index';
import renderAutocomplete from 'commerce-frontend-js/components/autocomplete/entry';
import {openToast} from 'frontend-js-web';

export default function ({
	autocompleteAPIURL,
	autocompleteInitialLabel,
	autocompleteInitialValue,
	commerceReturnItemId,
	dataSetId,
	namespace,
}) {
	renderAutocomplete('autocomplete', 'autocomplete-root', {
		apiUrl: autocompleteAPIURL,
		autoLoad: true,
		initialLabel: autocompleteInitialLabel,
		initialValue: autocompleteInitialValue,
		inputId: `${namespace}returnReason`,
		inputName: `${namespace}returnReason`,
		itemsKey: 'key',
		itemsLabel: 'name',
	});

	const CommerceReturnItemResource = ServiceProvider.ReturnItemAPI();

	const form = document.getElementById(`${namespace}commerceReturnItemsFm`);

	form.addEventListener('submit', () => {
		const quantity = form.querySelector(`#${namespace}quantity`).value;
		const returnReason = form.querySelector(`#${namespace}returnReason`)
			.value;

		const returnItemData = {
			quantity,
			returnReason,
		};

		return CommerceReturnItemResource.updateItemById(
			commerceReturnItemId,
			returnItemData
		)
			.then(() => {
				Liferay.fire('fds-display-updated', {id: dataSetId});

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
	});
}
