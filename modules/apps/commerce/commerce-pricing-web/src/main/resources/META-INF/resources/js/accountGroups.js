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
	pricingName,
	spritemap,
}) {
	const CommerceDiscountAccountGroupsResource =
		CommerceServiceProvider.AdminPricingAPI('v2');
	const id = commerceDiscountId;

	function selectItem(accountGroup) {
		const accountGroupData = {
			accountGroupExternalReferenceCode:
				accountGroup.externalReferenceCode,
			accountGroupId: accountGroup.id,
			discountExternalReferenceCode,
			discountId: id,
		};

		return CommerceDiscountAccountGroupsResource.addDiscountAccountGroup(
			id,
			accountGroupData
		)
			.then(() => {
				Liferay.fire(commerceEvents.FDS_UPDATE_DISPLAY, {
					id: pricingName,
				});
			})
			.catch((error) => {
				return Promise.reject(error.title);
			});
	}

	function getSelectedItems() {
		return Promise.resolve([]);
	}

	ItemFinder('itemFinder', 'item-finder-root', {
		apiUrl: '/o/headless-commerce-admin-account/v1.0/accountGroups/',
		getSelectedItems,
		inputPlaceholder: Liferay.Language.get('find-an-account-group'),
		itemCreation: false,
		itemSelectedMessage: Liferay.Language.get('account-group-selected'),
		itemsKey: 'id',
		linkedDataSetsId: [pricingName],
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('add-account-groups'),
		portletId,
		schema: [
			{
				fieldName: 'name',
			},
		],
		spritemap,
		titleLabel: Liferay.Language.get('add-existing-account-group'),
	});
}
