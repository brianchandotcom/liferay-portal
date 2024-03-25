/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ItemFinder, commerceEvents, fetchParams} from 'commerce-frontend-js';

export default function ({
	portletId,
	pricingFDSName,
	productExternalReferenceCode,
	productId,
	spritemap,
}) {
	const headers = fetchParams.headers;

	function selectItem(productPricingClass) {
		return Liferay.Util.fetch(
			'/o/headless-commerce-admin-catalog/v1.0/product-groups/' +
				productPricingClass.id +
				'/product-group-products/',
			{
				body: JSON.stringify({
					productExternalReferenceCode,
					productGroupExternalReferenceCode:
						productPricingClass.externalReferenceCode,
					productGroupId: productPricingClass.id,
					productId,
				}),
				headers,
				method: 'POST',
			}
		).then((response) => {
			if (!response.ok) {
				return response.json().then((data) => {
					return Promise.reject(data.errorDescription);
				});
			}
			Liferay.fire(commerceEvents.FDS_UPDATE_DISPLAY, {
				id: pricingFDSName,
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
			'/o/headless-commerce-admin-catalog/v1.0/product-groups',
			{
				body: JSON.stringify({
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
					return Promise.reject(data.message);
				});
			})
			.then(selectItem);
	}

	function getSelectedItems() {
		return Promise.resolve([]);
	}

	ItemFinder('itemFinder', 'item-finder-root', {
		apiUrl: '/o/headless-commerce-admin-catalog/v1.0/product-groups',
		createNewItemLabel: Liferay.Language.get('create-new'),
		getSelectedItems,
		inputPlaceholder: Liferay.Language.get(
			'find-or-create-a-product-group'
		),
		itemSelectedMessage: Liferay.Language.get('product-group-selected'),
		itemsKey: 'id',
		linkedDataSetsId: [pricingFDSName],
		onItemCreated: addNewItem,
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('select-product-group'),
		portletId,
		schema: [
			{
				fieldName: ['title', 'LANG'],
			},
		],
		spritemap,
		titleLabel: Liferay.Language.get('add-existing-product-group'),
	});
}
