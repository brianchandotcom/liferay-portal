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

class ManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	selectAccountEntries(itemData) {
		this._openAccountEntrySelector(
			Liferay.Language.get('select'),
			this.ns('selectAccountEntries'),
			Liferay.Language.get(itemData.dialogTitle),
			itemData.accountEntriesSelectorURL,
			selectedItems => {
				var redirectURL = Liferay.Util.PortletURL.createPortletURL(
					itemData.redirectURL,
					{
						accountEntriesNavigation: 'accounts',
						accountEntryIds: selectedItems.value
					}
				);

				window.location.href = redirectURL;
			}
		);
	}

	addAccountUser(itemData) {
		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					modal: true
				},
				eventName: this.ns('addAccountUser'),
				id: this.ns('addAccountUser'),
				title: Liferay.Language.get(itemData.dialogTitle),
				uri: itemData.accountEntrySelectorURL
			},
			event => {
				var addAccountUserURL = Liferay.Util.PortletURL.createPortletURL(
					itemData.addAccountUserURL,
					{
						accountEntryId: event.accountentryid
					}
				);

				window.location.href = addAccountUserURL;
			}
		);
	}

	_openAccountEntrySelector(
		dialogButtonLabel,
		dialogEventName,
		dialogTitle,
		accountEntrySelectorURL,
		callback
	) {
		const itemSelectorDialog = new ItemSelectorDialog({
			buttonAddLabel: dialogButtonLabel,
			eventName: dialogEventName,
			title: dialogTitle,
			url: accountEntrySelectorURL
		});

		itemSelectorDialog.on('selectedItemChange', event => {
			if (event.selectedItem) {
				callback(event.selectedItem);
			}
		});

		itemSelectorDialog.open();
	}
}

export default ManagementToolbarDefaultEventHandler;
