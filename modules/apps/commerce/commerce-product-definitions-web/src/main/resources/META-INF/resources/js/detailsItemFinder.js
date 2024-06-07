/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ItemFinder,
	commerceEvents,
	fetchParams,
	slugify,
} from 'commerce-frontend-js';

export default function main({
	portletId,
	productDefinitonSpecifications,
	productId,
	spritemap,
}) {
	const headers = fetchParams.headers;

	function selectItem(specification) {
		return Liferay.Util.fetch(
			`/o/headless-commerce-admin-catalog/v1.0/products/${productId}/productSpecifications/`,
			{
				body: JSON.stringify({
					productId,
					specificationId: specification.id,
					specificationKey: specification.key,
					value: {},
					...(specification.optionCategory
						? {
								optionCategoryId:
									specification.optionCategory.id,
							}
						: {}),
				}),
				headers,
				method: 'POST',
			}
		).then(() => {
			Liferay.fire(commerceEvents.FDS_UPDATE_DISPLAY, {
				id: productDefinitonSpecifications,
			});

			return null;
		});
	}

	function addNewItem(name) {
		const nameDefinition = {};

		nameDefinition[themeDisplay.getLanguageId()] = name;

		if (
			themeDisplay.getLanguageId() !== themeDisplay.getDefaultLanguageId()
		) {
			nameDefinition[themeDisplay.getDefaultLanguageId()] = name;
		}

		return Liferay.Util.fetch(
			'/o/headless-commerce-admin-catalog/v1.0/specifications',
			{
				body: JSON.stringify({
					key: slugify(name),
					title: nameDefinition,
				}),
				headers,
				method: 'POST',
			}
		)
			.then((response) => {
				if (response.ok) {
					return response.json();
				}

				return response.json().then((data) => {
					return Promise.reject(data.errorDescription);
				});
			})
			.then(selectItem);
	}

	function getSelectedItems() {
		return Promise.resolve([]);
	}

	ItemFinder('itemFinder', 'item-finder-root', {
		apiUrl: '/o/headless-commerce-admin-catalog/v1.0/specifications',
		createNewItemLabel: Liferay.Language.get('create-new-specification'),
		getSelectedItems,
		inputPlaceholder: Liferay.Language.get(
			'find-or-create-a-specification'
		),
		itemSelectedMessage: Liferay.Language.get('specification-selected'),
		itemsKey: 'id',
		linkedDataSetsId: [productDefinitonSpecifications],
		multiSelectableEntries: true,
		onItemCreated: addNewItem,
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('add-specifications'),
		portletId,
		schema: [
			{
				fieldName: ['title', 'LANG'],
			},
			{
				fieldName: 'key',
			},
		],
		spritemap,
		titleLabel: Liferay.Language.get('add-existing-specification'),
	});
}
