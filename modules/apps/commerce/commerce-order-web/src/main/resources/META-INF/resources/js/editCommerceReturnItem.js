/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	Autocomplete as renderAutocomplete,
	CommerceServiceProvider,
} from 'commerce-frontend-js';
import {openToast} from 'frontend-js-web';

export default function ({
	autocompleteAPIURL,
	autocompleteInitialLabel,
	autocompleteInitialValue,
	commerceReturnItemId,
	namespace,
	readOnly,
}) {
	renderAutocomplete('autocomplete', 'autocomplete-root', {
		apiUrl: autocompleteAPIURL,
		autoLoad: true,
		initialLabel: autocompleteInitialLabel,
		initialValue: autocompleteInitialValue,
		inputId: `${namespace}returnResolutionMethod`,
		inputName: `${namespace}returnResolutionMethod`,
		itemsKey: 'key',
		itemsLabel: 'name',
		readOnly,
	});

	const CommerceReturnItemResource = CommerceServiceProvider.ReturnItemAPI();

	const form = document.getElementById(`${namespace}commerceReturnItemsFm`);

	form.addEventListener('submit', () => {
		const authorizeReturnWithoutReturningProducts = form.querySelector(
			`#${namespace}authorizeReturnWithoutReturningProducts`
		).checked;
		const authorized = form.querySelector(`#${namespace}authorized`).value;
		const received = form.querySelector(`#${namespace}received`).value;
		const returnResolutionMethod = form.querySelector(
			`#${namespace}returnResolutionMethod`
		).value;

		const returnItemData = {
			authorizeReturnWithoutReturningProducts,
			authorized,
			received,
			returnResolutionMethod,
		};

		return CommerceReturnItemResource.updateItemById(
			commerceReturnItemId,
			returnItemData
		)
			.then(() => {
				window.top.Liferay.fire('return-item-updated');

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
