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

import {DefaultEventHandler} from 'frontend-js-web';
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
		AUI().use('liferay-item-selector-dialog', A => {
			const itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: this.ns('selectOrganizations'),
				on: {
					selectedItemChange: function(event) {
						const selectedItem = event.newVal;

						if (selectedItem) {
							const addGroupOrganizationsFm = this.one(
								'#addGroupOrganizationsFm'
							);

							selectedItem.forEach(item => {
								dom.append(addGroupOrganizationsFm, item);
							});

							submitForm(addGroupOrganizationsFm);
						}
					}.bind(this)
				},
				'strings.add': Liferay.Language.get('done'),
				title: Liferay.Language.get(
					'assign-organizations-to-this-site'
				),
				url: itemData.selectOrganizationsURL
			});

			itemSelectorDialog.open();
		});
	}
}

export default OrganizationsManagementToolbarDefaultEventHandler;
