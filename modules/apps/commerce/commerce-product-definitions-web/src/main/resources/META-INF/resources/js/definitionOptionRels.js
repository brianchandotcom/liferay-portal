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
	productId,
	productOptions,
	spritemap,
}) {
	const headers = fetchParams.headers;

	function selectItem(option) {
		return Liferay.Util.fetch(
			`/o/headless-commerce-admin-catalog/v1.0/products/${productId}/productOptions/`,
			{
				body: JSON.stringify([
					{
						facetable: option.facetable,
						fieldType: option.fieldType,
						key: option.key,
						name: option.name,
						optionId: option.id,
						productOptionValues: [],
						required: option.required,
						skuContributor: option.skuContributor,
					},
				]),
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
			.then(() => {
				Liferay.fire(commerceEvents.FDS_UPDATE_DISPLAY, {
					id: productOptions,
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
			'/o/headless-commerce-admin-catalog/v1.0/options',
			{
				body: JSON.stringify({
					fieldType: 'select',
					key: slugify(name),
					name: nameDefinition,
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
		apiUrl: '/o/headless-commerce-admin-catalog/v1.0/options',
		createNewItemLabel: Liferay.Language.get('create-new'),
		getSelectedItems,
		inputPlaceholder: Liferay.Language.get('find-or-create-an-option'),
		itemCreatedMessage: Liferay.Language.get('option-created'),
		itemSelectedMessage: Liferay.Language.get('option-selected'),
		itemsKey: 'id',
		linkedDataSetsId: [productOptions],
		multiSelectableEntries: true,
		onItemCreated: addNewItem,
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('add-options'),
		portletId,
		schema: [
			{
				fieldName: ['name', 'LANG'],
			},
		],
		spritemap,
		titleLabel: Liferay.Language.get('add-existing-option'),
	});
}
