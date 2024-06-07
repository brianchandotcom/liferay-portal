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
	const CommerceDiscountSkusResource =
		CommerceServiceProvider.AdminPricingAPI('v2');

	const id = commerceDiscountId;

	function selectItem(sku) {
		const skuData = {
			discountExternalReferenceCode,
			discountId: id,
			skuExternalReferenceCode: sku.externalReferenceCode,
			skuId: sku.id,
			unitOfMeasureKey: sku.unitOfMeasureKey,
		};

		return CommerceDiscountSkusResource.addDiscountSku(id, skuData)
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
		apiUrl: '/o/headless-commerce-admin-catalog/v1.0/unit-of-measure-skus',
		getSelectedItems,
		inputPlaceholder: Liferay.Language.get('find-a-sku'),
		itemCreation: false,
		itemSelectedMessage: Liferay.Language.get('sku-selected'),
		itemsKey: 'unitOfMeasureSkuId',
		linkedDataSetsId: [pricingFDSName],
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('add-skus'),
		portletId,
		schema: [
			{
				fieldName: 'id',
			},
			{
				fieldName: ['productName', 'LANG'],
			},
			{
				fieldName: 'sku',
			},
			{
				fieldName: 'unitOfMeasureKey',
			},
		],
		spritemap,
		titleLabel: Liferay.Language.get('add-existing-sku'),
	});
}
