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

class UserGroupsManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteSelectedUserGroups() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}

	removeUserGroupSiteRole(itemData) {
		if (confirm(itemData.message)) {
			submitForm(this.one('#fm'), itemData.removeUserGroupSiteRoleURL);
		}
	}

	selectRoles(itemData) {
		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					destroyOnHide: true,
					modal: true
				},
				eventName: this.ns('selectSiteRole'),
				title: Liferay.Language.get('select-site-role'),
				uri: itemData.selectRolesURL
			},
			event => {
				location.href = Liferay.Util.addParams(
					`${this.ns('roleId')}=${event.id}`,
					itemData.viewRoleURL
				);
			}
		);
	}

	selectSiteRole(itemData) {
		AUI().use('liferay-item-selector-dialog', A => {
			const itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: this.ns('selectSiteRole'),
				on: {
					selectedItemChange: function(event) {
						const selectedItem = event.newVal;

						if (selectedItem) {
							const fm = this.one('#fm');

							selectedItem.forEach(item => {
								dom.append(fm, item);
							});

							submitForm(fm, itemData.editUserGroupsSiteRolesURL);
						}
					}.bind(this)
				},
				'strings.add': Liferay.Language.get('done'),
				title: Liferay.Language.get('assign-site-roles'),
				url: itemData.selectSiteRoleURL
			});

			itemSelectorDialog.open();
		});
	}

	selectUserGroups(itemData) {
		AUI().use('liferay-item-selector-dialog', A => {
			const itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: this.ns('selectUserGroups'),
				on: {
					selectedItemChange: function(event) {
						const selectedItem = event.newVal;

						if (selectedItem) {
							const addGroupUserGroupsFm = this.one(
								'#addGroupUserGroupsFm'
							);

							selectedItem.forEach(item => {
								dom.append(addGroupUserGroupsFm, item);
							});

							submitForm(addGroupUserGroupsFm);
						}
					}.bind(this)
				},
				'strings.add': Liferay.Language.get('done'),
				title: Liferay.Language.get('assign-user-groups-to-this-site'),
				url: itemData.selectUserGroupsURL
			});

			itemSelectorDialog.open();
		});
	}
}

export default UserGroupsManagementToolbarDefaultEventHandler;
