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
	createActionURL,
	createRenderURL,
	getCheckedCheckboxes,
	openConfirmModal,
	openModal,
	openSelectionModal,
	postForm,
} from 'frontend-js-web';

export const ACTIONS = {
	activateUser(itemData) {
		submitForm(document.hrefFm, itemData.activateUserURL);
	},

	activateUsers(itemData, portletNamespace) {
		updateUsers(portletNamespace, itemData?.activateUsersURL);
	},

	assignOrganizationRoles(itemData) {
		openModal({
			title: itemData.label,
			url: itemData.assignOrganizationRolesURL,
		});
	},

	assignUsers(itemData, portletNamespace) {
		this.selectUsers({
			basePortletURL: itemData.basePortletURL,
			organizationId: itemData.organizationId,
			portletNamespace,
		});
	},

	deactivateUser(itemData) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-deactivate-this'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					submitForm(document.hrefFm, itemData.deactivateUserURL);
				}
			},
		});
	},

	deactivateUsers(itemData, portletNamespace) {
		updateUsers(portletNamespace, itemData?.editUsersURL);
	},

	deleteUser(itemData) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					submitForm(document.hrefFm, itemData.deleteUserURL);
				}
			},
		});
	},

	deleteUserActionContributor(itemData) {
		openConfirmModal({
			message: itemData.confirmation,
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					submitForm(
						document.hrefFm,
						itemData.deleteUserActionContributorURL
					);
				}
			},
		});
	},

	deleteUsers(itemData, portletNamespace) {
		updateUsers(portletNamespace, itemData?.editUsersURL);
	},

	permissions(itemData) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: itemData.permissionsURL,
		});
	},

	removeOrganization(itemData) {
		submitForm(document.hrefFm, itemData.removeOrganizationURL);
	},

	removeUser(itemData) {
		submitForm(document.hrefFm, itemData.removeUserURL);
	},

	selectUsers({basePortletURL, organizationId, portletNamespace}) {
		if (!organizationId) {
			return;
		}

		const selectUsersURL = createRenderURL(basePortletURL, {
			mvcPath: '/select_organization_users.jsp',
			organizationId,
			p_p_state: 'pop_up',
		});

		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect: (selectedItems) => {
				if (selectedItems?.length) {
					const assignmentsRedirectURL = createRenderURL(
						basePortletURL,
						{
							mvcRenderCommandName: '/users_admin/view',
							organizationId,
							toolbarItem: 'view-all-organizations',
							usersListView: 'tree',
						}
					);

					const values = selectedItems.map((item) => item.value);

					const editAssignmentURL = createActionURL(basePortletURL, {
						'addUserIds': values.join(','),
						'assignmentsRedirect': assignmentsRedirectURL.toString(),
						'javax.portlet.action':
							'/users_admin/edit_organization_assignments',
						organizationId,
					});

					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (!form) {
						return;
					}

					submitForm(form, editAssignmentURL.toString());
				}
			},
			title: Liferay.Language.get('assign-users'),
			url: selectUsersURL.toString(),
		});
	},
};

const getUserIds = (portletNamespace) => {
	const form = document.getElementById(`${portletNamespace}fm`);

	return getCheckedCheckboxes(
		form,
		`${portletNamespace}allRowIds`,
		`${portletNamespace}rowIdsUser`
	);
};

const updateUsers = (portletNamespace, url) => {
	const form = document.getElementById(`${portletNamespace}fm`);

	if (form) {
		postForm(form, {
			data: {
				deleteUserIds: getUserIds(portletNamespace),
			},
			url,
		});
	}
};
