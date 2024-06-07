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
	const CommercePriceModifierCategoriesResource =
		CommerceServiceProvider.AdminPricingAPI('v2');

	const id = commercePriceModifierId;

	function selectItem(category) {
		const categoryData = {
			categoryExternalReferenceCode: category.externalReferenceCode,
			categoryId: category.id,
			priceModifierExternalReferenceCode,
			priceModifierId: id,
		};

		return CommercePriceModifierCategoriesResource.addPriceModifierCategory(
			id,
			categoryData
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
		apiUrl: '/o/headless-admin-taxonomy/v1.0/taxonomy-categories/0/taxonomy-categories',
		getSelectedItems,
		inputPlaceholder: Liferay.Language.get('find-a-category'),
		itemCreation: false,
		itemSelectedMessage: Liferay.Language.get('category-selected'),
		itemsKey: 'id',
		linkedDataSetsId: [pricingFDSName],
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('select-category'),
		portletId,
		schema: [
			{
				fieldName: ['name'],
			},
		],
		spritemap,
		titleLabel: Liferay.Language.get('add-existing-category'),
	});
}
