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

import {DefaultEventHandler, ItemSelectorDialog} from 'frontend-js-web';

class AccountOrganizationsManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	selectAccountOrganizations(itemData) {
		const itemSelectorDialog = new ItemSelectorDialog({
			buttonAddLabel: Liferay.Language.get('assign'),
			eventName: this.ns('assignAccountOrganizations'),
			title: Liferay.Util.sub(
				Liferay.Language.get('assign-organizations-to-x'),
				itemData.accountEntryName
			),
			url: itemData.selectAccountOrganizationsURL
		});

		itemSelectorDialog.open();

		itemSelectorDialog.on('selectedItemChange', event => {
			const selectedItem = event.selectedItem;

			if (selectedItem) {
				const form = this.one('#fm');

				Liferay.Util.postForm(form, {
					data: {
						accountOrganizationIds: selectedItem.value
					},
					url: itemData.assignAccountOrganizationsURL
				});
			}
		});
	}
}

export default AccountOrganizationsManagementToolbarDefaultEventHandler;
