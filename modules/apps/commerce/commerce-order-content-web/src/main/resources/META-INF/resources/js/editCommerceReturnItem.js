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
	dataSetId,
	namespace,
	readOnly,
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
		readOnly,
	});

	const CommerceReturnItemResource = CommerceServiceProvider.ReturnItemAPI();

	const form = document.getElementById(`${namespace}commerceReturnItemsFm`);

	form.addEventListener('submit', () => {
		const quantity = form.querySelector(`#${namespace}quantity`).value;
		const returnReason = form.querySelector(
			`#${namespace}returnReason`
		).value;

		const returnItemData = {
			quantity,
			returnReason,
		};

		function updateReturnItem(response) {
			if (!response) {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});

				return;
			}

			return CommerceReturnItemResource.updateItemById(
				commerceReturnItemId,
				returnItemData
			)
				.then(() => {
					window.location.reload();

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
							Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						type: 'danger',
					});

					if (response > 0) {
						Liferay.Service(
							'/comment.commentmanagerjsonws/delete-comment',
							{
								commentId: response,
							}
						);
					}
				});
		}

		const content = form.querySelector(`#${namespace}content`).value;

		if (content) {
			const className = form.querySelector(
				`#${namespace}className`
			).value;
			const classPK = form.querySelector(`#${namespace}classPK`).value;

			Liferay.Service(
				'/comment.commentmanagerjsonws/add-comment',
				{
					body: content,
					className,
					classPK,
					groupId: themeDisplay.getScopeGroupId(),
				},
				updateReturnItem
			);
		}
		else {
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
							Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						type: 'danger',
					});
				});
		}
	});
}
