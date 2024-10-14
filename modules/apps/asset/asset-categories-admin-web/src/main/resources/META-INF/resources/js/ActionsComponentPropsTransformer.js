/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	fetch,
	navigate,
	objectToFormData,
	openSelectionModal,
	openToast,
} from 'frontend-js-web';

import openDeleteVocabularyModal from './openDeleteVocabularyModal';

const ACTIONS = {
	deleteVocabularies(itemData, portletNamespace) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('delete'),
			multiple: true,
			onSelect: (selectedItems) => {
				if (selectedItems.length) {
					openDeleteVocabularyModal({
						multiple: true,
						onDelete: () => {
							fetch(itemData.deleteVocabulariesURL, {
								body: objectToFormData({
									[`${portletNamespace}rowIds`]: selectedItems
										.map((item) => {
											const selectedValue = JSON.parse(
												item.value
											);

											return selectedValue.vocabularyId;
										})
										.join(','),
								}),
								method: 'POST',
							})
								.then((response) => {
									if (response.ok) {
										return response.json();
									}
									else {
										showErorMessage();
									}
								})
								.then((data) => {
									if (data.success) {
										navigate(itemData.redirectURL);

										openToast({
											message: Liferay.Language.get(
												'your-request-completed-successfully'
											),
											type: 'success',
										});
									}
									else {
										showErorMessage(data.errorMessage);
									}
								})
								.catch(() => {
									showErorMessage();
								});
						},
					});
				}
			},
			title: Liferay.Language.get('delete-vocabulary'),
			url: itemData.viewVocabulariesURL,
		});
	},
};

const showErorMessage = (errorMessage) => {
	openToast({
		message:
			errorMessage ||
			Liferay.Language.get('an-unexpected-error-occurred'),
		type: 'danger',
	});
};

export default function propsTransformer({
	items,
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		items: items.map((item) => {
			return {
				...item,
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						event.preventDefault();

						ACTIONS[action](item.data, portletNamespace);
					}
				},
			};
		}),
	};
}
