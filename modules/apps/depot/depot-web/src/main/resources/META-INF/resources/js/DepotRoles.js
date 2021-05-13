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

import {delegate, openSelectionModal} from 'frontend-js-web';

export default function ({
	portletNamespace,
	removeDepotRoleIcon,
	searchContainerId,
	selectDepotRolesURL,
	selectEventName,
}) {
	const addGroupIds = [];
	const addRoleIds = [];
	const deleteGroupIds = [];
	const deleteRoleIds = [];

	const deleteDepotGroupRole = (groupId, roleId) => {
		for (let i = 0; i < addGroupIds.length; i++) {
			if (addGroupIds[i] === groupId && addRoleIds[i] === roleId) {
				addGroupIds.splice(i, 1);
				addRoleIds.splice(i, 1);

				break;
			}
		}

		deleteGroupIds.push(groupId);
		deleteRoleIds.push(roleId);

		const form = document.getElementById(`${portletNamespace}fm`);

		if (!form) {
			return;
		}

		form[
			`${portletNamespace}addDepotGroupRolesGroupIds`
		].value = addGroupIds.join(',');

		form[
			`${portletNamespace}addDepotGroupRolesRoleIds`
		].value = addRoleIds.join(',');

		form[
			`${portletNamespace}deleteDepotGroupRolesGroupIds`
		].value = deleteGroupIds.join(',');

		form[
			`${portletNamespace}deleteDepotGroupRolesRoleIds`
		].value = deleteRoleIds.join(',');
	};

	Liferay.componentReady(`${portletNamespace}${searchContainerId}`).then(
		(searchContainer) => {
			const searchContainerContentBox = searchContainer.get('contentBox');

			searchContainer.updateDataStore(
				searchContainerContentBox
					.all('.modify-link')
					.getData()
					.map((data) => {
						return data.entityid;
					})
			);

			const selectDepotRoleLink = document.getElementById(
				`${portletNamespace}selectDepotRoleLink`
			);

			if (selectDepotRoleLink) {
				selectDepotRoleLink.addEventListener('click', (event) => {
					event.preventDefault();

					openSelectionModal({
						onSelect: (selectedItem) => {
							if (!selectedItem) {
								return;
							}

							var rowColumns = [];

							rowColumns.push(
								`<i class="${
									selectedItem.iconcssclass
								}"></i>${Liferay.Util.escapeHTML(
									selectedItem.rolename
								)}`
							);

							rowColumns.push(selectedItem.groupdescriptivename);

							rowColumns.push(
								`<a class="modify-link" data-entityid="${selectedItem.entityid}" href="javascript:;">${removeDepotRoleIcon}</a>`
							);

							searchContainer.addRow(
								rowColumns,
								selectedItem.entityid
							);

							searchContainer.updateDataStore();

							const [
								groupId,
								roleId,
							] = selectedItem.entityid.split('-');

							for (let i = 0; i < deleteRoleIds.length; i++) {
								if (
									deleteGroupIds[i] === groupId &&
									deleteRoleIds[i] === roleId
								) {
									deleteGroupIds.splice(i, 1);
									deleteRoleIds.splice(i, 1);

									break;
								}
							}

							addGroupIds.push(groupId);
							addRoleIds.push(roleId);

							const form = document.getElementById(
								`${portletNamespace}fm`
							);

							if (!form) {
								return;
							}

							form[
								`${portletNamespace}addDepotGroupRolesGroupIds`
							].value = addGroupIds.join(',');

							form[
								`${portletNamespace}addDepotGroupRolesRoleIds`
							].value = addRoleIds.join(',');

							form[
								`${portletNamespace}deleteDepotGroupRolesGroupIds`
							].value = deleteGroupIds.join(',');

							form[
								`${portletNamespace}deleteDepotGroupRolesRoleIds`
							].value = deleteRoleIds.join(',');
						},
						selectEventName,
						selectedData: searchContainer.getData(true),
						title: Liferay.Util.sub(
							Liferay.Language.get('select-x'),
							Liferay.Language.get('role')
						),
						url: selectDepotRolesURL,
					});
				});
			}

			delegate(
				searchContainerContentBox.getDOMNode(),
				'click',
				'.modify-link',
				(event) => {
					const link = event.delegateTarget;

					const row = link.closest('tr');

					const entityId = link.dataset.entityid;

					searchContainer.deleteRow(row, entityId);

					const [groupId, roleId] = entityId.split('-');

					deleteDepotGroupRole(groupId, roleId);
				}
			);
		}
	);
}
