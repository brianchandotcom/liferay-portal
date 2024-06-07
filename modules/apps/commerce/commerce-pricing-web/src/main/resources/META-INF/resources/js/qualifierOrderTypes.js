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
	commercePriceListId,
	portletId,
	priceListExternalReferenceCode,
	pricingFDSName,
	spritemap,
}) {
	const CommercePriceListOrderTypesResource =
		CommerceServiceProvider.AdminPricingAPI('v2');

	const id = commercePriceListId;

	function selectItem(orderType) {
		const orderTypeData = {
			orderTypeExternalReferenceCode: orderType.externalReferenceCode,
			orderTypeId: orderType.id,
			priceListExternalReferenceCode,
			priceListId: id,
		};

		return CommercePriceListOrderTypesResource.addPriceListOrderType(
			id,
			orderTypeData
		)
			.then(() => {
				Liferay.fire(commerceEvents.FDS_UPDATE_DISPLAY, {
					id: pricingFDSName,
				});
			})
			.catch((error) => {
				return Promise.reject(error.title);
			});
	}

	function getSelectedItems() {
		return Promise.resolve([]);
	}

	ItemFinder('itemFinder', 'item-finder-root-order-types', {
		apiUrl: '/o/headless-commerce-admin-order/v1.0/order-types/',
		getSelectedItems,
		inputPlaceholder: Liferay.Language.get('find-an-order-type'),
		itemCreation: false,
		itemSelectedMessage: Liferay.Language.get('order-type-selected'),
		itemsKey: 'id',
		linkedDataSetsId: [pricingFDSName],
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('add-order-types'),
		portletId,
		schema: [
			{
				fieldName: ['name', 'LANG'],
			},
		],
		spritemap,
		titleLabel: Liferay.Language.get('add-existing-order-type'),
	});
}
