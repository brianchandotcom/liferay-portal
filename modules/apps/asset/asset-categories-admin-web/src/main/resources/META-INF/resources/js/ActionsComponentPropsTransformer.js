/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openItemSelectorModal} from '@liferay/frontend-js-item-selector-web';
import {openToast} from 'frontend-js-components-web';
import {fetch, navigate, objectToFormData} from 'frontend-js-web';

import openDeleteVocabularyModal from './openDeleteVocabularyModal';

const HEADLESS_TAXONOMY_VOCABULARIES_BASE =
	'/o/headless-admin-taxonomy/v1.0/sites';

const VISIBILITY_TYPE_PUBLIC = 0;

function buildVocabulariesURL(currentSiteId) {
	const url = new URL(
		`${HEADLESS_TAXONOMY_VOCABULARIES_BASE}/${String(
			currentSiteId
		)}/taxonomy-vocabularies`,
		window.location.origin
	);

	url.searchParams.set(
		'filter',
		`visibilityType eq ${VISIBILITY_TYPE_PUBLIC} and siteId eq ${String(
			currentSiteId
		)}`
	);

	return url.toString();
}

const ACTIONS = {
	deleteVocabularies(itemData, portletNamespace) {
		openItemSelectorModal({
			apiURL: buildVocabulariesURL(itemData.currentSiteId),
			confirmButtonLabel: Liferay.Language.get('delete'),
			fdsProps: {
				configInURLBehavior: 'OFF',
				id: 'assetCategoriesAdminVocabularyDeleteFDS',
				pagination: {
					deltas: [{label: 20}, {label: 50}],
					initialDelta: 20,
				},
				views: [
					{
						contentRenderer: 'table',
						label: '',
						name: 'list',
						schema: {
							fields: [
								{
									fieldName: 'name',
									label: Liferay.Language.get('title'),
								},
								{
									fieldName: 'creator.name',
									label: Liferay.Language.get('user'),
								},
								{
									contentRenderer: 'dateTime',
									fieldName: 'dateModified',
									label: Liferay.Language.get(
										'modified-date'
									),
									sortable: true,
								},
							],
						},
					},
				],
			},
			itemTypeLabel: Liferay.Language.get('vocabulary'),
			items: [],
			locator: {id: 'id', label: 'name', value: 'id'},
			multiSelect: true,
			onItemsChange: (selected) => {
				if (!selected.length) {
					return;
				}

				openDeleteVocabularyModal({
					multiple: true,
					onDelete: () => {
						fetch(itemData.deleteVocabulariesURL, {
							body: objectToFormData({
								[`${portletNamespace}rowIds`]: selected
									.map((vocabulary) => vocabulary.id)
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
			},
			size: 'lg',
			title: Liferay.Language.get('delete-vocabulary'),
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
