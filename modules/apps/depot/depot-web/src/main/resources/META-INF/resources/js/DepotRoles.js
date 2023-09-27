/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate, escapeHTML, openSelectionModal, sub} from 'frontend-js-web';

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

							const rowColumns = [];

							rowColumns.push(
								`<i class="${
									selectedItem.iconcssclass
								}"></i>${escapeHTML(selectedItem.rolename)}`
							);

							rowColumns.push(selectedItem.groupdescriptivename);

							rowColumns.push(
								`<a class="modify-link" data-entityid="${selectedItem.entityid}" href="javascript:void(0);">${removeDepotRoleIcon}</a>`
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
						title: sub(
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
