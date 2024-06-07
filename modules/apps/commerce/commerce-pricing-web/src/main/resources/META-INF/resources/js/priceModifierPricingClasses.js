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
	commercePriceModifierId,
	portletId,
	priceModifierExternalReferenceCode,
	pricingFDSName,
	spritemap,
}) {
	const CommercePriceModifierProductGroupsResource =
		CommerceServiceProvider.AdminPricingAPI('v2');

	const id = commercePriceModifierId;

	function selectItem(productGroup) {
		const productGroupData = {
			priceModifierExternalReferenceCode,
			priceModifierId: id,
			productGroupExternalReferenceCode:
				productGroup.externalReferenceCode,
			productGroupId: productGroup.id,
		};

		return CommercePriceModifierProductGroupsResource.addPriceModifierProductGroup(
			id,
			productGroupData
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
		apiUrl: '/o/headless-commerce-admin-catalog/v1.0/product-groups',
		getSelectedItems,
		inputPlaceholder: Liferay.Language.get('find-a-product-group'),
		itemCreation: false,
		itemSelectedMessage: Liferay.Language.get('product-group-selected'),
		itemsKey: 'id',
		linkedDataSetsId: [pricingFDSName],
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
