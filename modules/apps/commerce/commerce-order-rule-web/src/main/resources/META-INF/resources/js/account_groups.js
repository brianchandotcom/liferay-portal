/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ServiceProvider from 'commerce-frontend-js/ServiceProvider/index';
import itemFinder from 'commerce-frontend-js/components/item_finder/entry';
import {UPDATE_DATASET_DISPLAY} from 'commerce-frontend-js/utilities/eventsDefinitions';

export default function ({
	datasetId,
	orderRuleExternalReferenceCode,
	orderRuleId,
	rootPortletId,
	spritemap,
}) {
	const orderRuleAccountGroupsResource = ServiceProvider.AdminOrderAPI('v1');

	function selectItem(accountGroup) {
		const accountGroupData = {
			accountGroupExternalReferenceCode:
				accountGroup.externalReferenceCode,
			accountGroupId: accountGroup.id,
			orderRuleExternalReferenceCode,
			orderRuleId,
		};

		return orderRuleAccountGroupsResource
			.addOrderRuleAccountGroup(orderRuleId, accountGroupData)
			.then(() => {
				Liferay.fire(UPDATE_DATASET_DISPLAY, {
					id: datasetId,
				});
			});
	}

	itemFinder('itemFinder', 'item-finder-root', {
		apiUrl: '/o/headless-commerce-admin-account/v1.0/accountGroups/',
		getSelectedItems: () => Promise.resolve([]),
		inputPlaceholder: Liferay.Language.get('find-an-account-group'),
		itemCreation: false,
		itemSelectedMessage: Liferay.Language.get('account-group-selected'),
		itemsKey: 'id',
		linkedDatasetsId: [datasetId],
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('add-account-groups'),
		portletId: rootPortletId,
		schema: [
			{
				fieldName: 'name',
			},
		],
		spritemap,
		titleLabel: Liferay.Language.get('add-existing-account-group'),
	});
}
