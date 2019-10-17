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

class UserDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteGroupUsers(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, itemData.deleteGroupUsersURL);
		}
	}

	assignSiteRoles(itemData) {
		AUI().use('liferay-item-selector-dialog', A => {
			const itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: this.ns('selectUsersRoles'),
				on: {
					selectedItemChange: function(event) {
						const selectedItem = event.newVal;

						if (selectedItem) {
							const editUserGroupRoleFm = this.one(
								'#editUserGroupRoleFm'
							);

							selectedItem.forEach(item => {
								dom.append(editUserGroupRoleFm, item);
							});

							submitForm(
								editUserGroupRoleFm,
								itemData.editUserGroupRoleURL
							);
						}
					}.bind(this)
				},
				'strings.add': Liferay.Language.get('done'),
				title: Liferay.Language.get('assign-site-roles'),
				url: itemData.assignSiteRolesURL
			});

			itemSelectorDialog.open();
		});
	}
}

export default UserDropdownDefaultEventHandler;
