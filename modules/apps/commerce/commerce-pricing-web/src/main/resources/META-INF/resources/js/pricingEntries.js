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
	commerceCatalogId,
	commercePriceListId,
	dataSetId,
	portletId,
	priceListExternalReferenceCode,
	spritemap,
}) {
	const CommercePriceEntriesResource =
		CommerceServiceProvider.AdminPricingAPI('v2');

	const id = commercePriceListId;

	function selectItem(sku) {
		const priceEntryData = {
			price: '0.0',
			priceListExternalReferenceCode,
			priceListId: id,
			skuExternalReferenceCode: sku.externalReferenceCode,
			skuId: sku.id,
			unitOfMeasureKey: sku.unitOfMeasureKey,
		};

		return CommercePriceEntriesResource.addPriceEntry(id, priceEntryData)
			.then(() => {
				setTimeout(() => {
					Liferay.fire(commerceEvents.FDS_UPDATE_DISPLAY, {
						id: dataSetId,
					});
				}, 500);
			})
			.catch((error) => {
				return Promise.reject(error);
			});
	}

	function getSelectedItems() {
		return Promise.resolve([]);
	}

	ItemFinder('itemFinder', 'item-finder-root', {
		apiUrl: `/o/headless-commerce-admin-catalog/v1.0/unit-of-measure-skus?filter=catalogId eq ${commerceCatalogId}`,
		getSelectedItems,
		inputPlaceholder: Liferay.Language.get('find-a-sku'),
		itemCreation: false,
		itemSelectedMessage: Liferay.Language.get('sku-selected'),
		itemsKey: 'unitOfMeasureSkuId',
		linkedDataSetsId: [dataSetId],
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('add-skus'),
		portletId,
		schema: [
			{
				fieldName: 'sku',
			},
			{
				fieldName: 'unitOfMeasureKey',
			},
			{
				fieldName: ['productName', 'LANG'],
			},
		],
		spritemap,
		titleLabel: Liferay.Language.get('add-existing-sku'),
	});
}
