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
	commerceDiscountId,
	discountExternalReferenceCode,
	portletId,
	pricingFDSName,
	spritemap,
}) {
	const CommerceDiscountProductsResource =
		CommerceServiceProvider.AdminPricingAPI('v2');

	const id = commerceDiscountId;

	function selectItem(product) {
		const productData = {
			discountExternalReferenceCode,
			discountId: id,
			productExternalReferenceCode: product.externalReferenceCode,
			productId: product.productId,
		};

		return CommerceDiscountProductsResource.addDiscountProduct(
			id,
			productData
		)
			.then(() => {
				Liferay.fire(commerceEvents.FDS_UPDATE_DISPLAY, {
					id: pricingFDSName,
				});
			})
			.catch((error) => {
				return Promise.reject(error);
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
