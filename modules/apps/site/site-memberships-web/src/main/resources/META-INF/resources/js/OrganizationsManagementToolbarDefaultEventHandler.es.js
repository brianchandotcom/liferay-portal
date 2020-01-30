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
import dom from 'metal-dom';

class OrganizationsManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteSelectedOrganizations() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}

	selectOrganizations(itemData) {
		const itemSelectorDialog = new ItemSelectorDialog({
			buttonAddLabel: Liferay.Language.get('done'),
			eventName: this.ns('selectOrganizations'),
			title: Liferay.Util.sub(
				Liferay.Language.get('assign-organizations-to-this-x'),
				itemData.groupTypeLabel
			),
			url: itemData.selectOrganizationsURL
		});

		itemSelectorDialog.on('selectedItemChange', event => {
			const selectedItem = event.selectedItem;

			if (selectedItem) {
				const addGroupOrganizationsFm = this.one(
					'#addGroupOrganizationsFm'
				);

				selectedItem.forEach(item => {
					dom.append(addGroupOrganizationsFm, item);
				});

				submitForm(addGroupOrganizationsFm);
			}
		});

		itemSelectorDialog.open();
	}
}

export default OrganizationsManagementToolbarDefaultEventHandler;
