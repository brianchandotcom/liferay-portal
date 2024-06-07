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
	dataSetId,
	orderRuleExternalReferenceCode,
	orderRuleId,
	rootPortletId,
}) {
	const orderRuleAccountsResource =
		CommerceServiceProvider.AdminOrderAPI('v1');

	function selectItem(account) {
		const accountData = {
			accountExternalReferenceCode: account.externalReferenceCode,
			accountId: account.id,
			orderRuleExternalReferenceCode,
			orderRuleId,
		};

		return orderRuleAccountsResource
			.addOrderRuleAccount(orderRuleId, accountData)
			.then(() => {
				Liferay.fire(commerceEvents.FDS_UPDATE_DISPLAY, {
					id: dataSetId,
				});
			});
	}

	ItemFinder('itemFinder', 'item-finder-root', {
		apiUrl: '/o/headless-commerce-admin-account/v1.0/accounts/',
		getSelectedItems: () => Promise.resolve([]),
		inputPlaceholder: Liferay.Language.get('find-an-account'),
		itemCreation: false,
		itemSelectedMessage: Liferay.Language.get('account-selected'),
		itemsKey: 'id',
		linkedDataSetsId: [dataSetId],
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('add-accounts'),
		portletId: rootPortletId,
		schema: [
			{
				fieldName: 'name',
			},
		],
		titleLabel: Liferay.Language.get('add-existing-account'),
	});
}
