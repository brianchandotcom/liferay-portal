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

import {
	DefaultEventHandler,
	addParams,
	openSelectionModal,
} from 'frontend-js-web';
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

	removeUserGroupRole(itemData) {
		if (confirm(itemData.message)) {
			submitForm(this.one('#fm'), itemData.removeUserGroupRoleURL);
		}
	}

	selectRoles(itemData) {
		openSelectionModal({
			onSelect: (event) => {
				location.href = addParams(
					`${this.ns('roleId')}=${event.id}`,
					itemData.viewRoleURL
				);
			},
			selectEventName: this.ns('selectRole'),
			title: Liferay.Language.get('select-role'),
			url: itemData.selectRolesURL,
		});
	}

	selectRole(itemData) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const fm = this.one('#fm');

					selectedItem.forEach((item) => {
						fm.append(item);
					});

					submitForm(fm, itemData.editUserGroupsRolesURL);
				}
			},
			selectEventName: this.ns('selectRole'),
			title: Liferay.Language.get('assign-roles'),
			url: itemData.selectRoleURL,
		});
	}

	selectUserGroups(itemData) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const addGroupUserGroupsFm = this.one(
						'#addGroupUserGroupsFm'
					);

					selectedItem.forEach((item) => {
						addGroupUserGroupsFm.append(item);
					});

					submitForm(addGroupUserGroupsFm);
				}
			},
			selectEventName: this.ns('selectUserGroups'),
			title: Liferay.Util.sub(
				Liferay.Language.get('assign-user-groups-to-this-x'),
				itemData.groupTypeLabel
			),
			url: itemData.selectUserGroupsURL,
		});
	}
}

export default UserGroupsManagementToolbarDefaultEventHandler;
