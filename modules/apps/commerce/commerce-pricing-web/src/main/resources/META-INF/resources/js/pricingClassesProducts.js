/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	CommerceServiceProvider,
	ItemFinder,
	commerceEvents,
} from 'commerce-frontend-js';

export default function ({
	commercePricingClassId,
	portletId,
	pricingClassExternalReferenceCode,
	pricingFDSName,
	spritemap,
}) {
	const CommerceProductGroupsResource =
		CommerceServiceProvider.AdminCatalogAPI('v1');

	const id = commercePricingClassId;

	function selectItem(product) {
		const productData = {
			productExternalReferenceCode: product.externalReferenceCode,
			productGroupExternalReferenceCode:
				pricingClassExternalReferenceCode,
			productGroupId: id,
			productId: product.productId,
		};

		return CommerceProductGroupsResource.addProductToProductGroup(
			id,
			productData
		)
			.then(() => {
				Liferay.fire(commerceEvents.FDS_UPDATE_DISPLAY, {
					id: pricingFDSName,
				});
			})
			.catch((error) => {
				return Promise.reject(error.errorDescription);
			});
	}

	function getSelectedItems() {
		return Promise.resolve([]);
	}

	ItemFinder('itemFinder', 'item-finder-root', {
		apiUrl: '/o/headless-commerce-admin-catalog/v1.0/products?nestedFields=catalog',
		getSelectedItems,
		inputPlaceholder: Liferay.Language.get('find-a-product'),
		itemCreation: false,
		itemSelectedMessage: Liferay.Language.get('product-selected'),
		itemsKey: 'id',
		linkedDataSetsId: [pricingFDSName],
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('add-products'),
		portletId,
		schema: [
			{
				fieldName: ['name', 'LANG'],
			},
			{
				fieldName: 'productId',
			},
			{
				fieldName: ['catalog', 'name'],
			},
		],
		spritemap,
		titleLabel: Liferay.Language.get('add-existing-product'),
	});
}
