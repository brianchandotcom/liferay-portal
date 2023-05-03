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

	<portlet:namespace />deleteOrganization(
		organizationId,
		organizationsRedirect
	) {
		<portlet:namespace />doDeleteOrganization(
			'<%= Organization.class.getName() %>',
			organizationId,
			organizationsRedirect
		);
	},

	<portlet:namespace />deleteOrganizations(organizationsRedirect) {
		<portlet:namespace />doDeleteOrganization(
			'<%= Organization.class.getName() %>',
			Liferay.Util.getCheckedCheckboxes(
				document.<portlet:namespace />fm,
				'<portlet:namespace />allRowIds',
				'<portlet:namespace />rowIdsOrganization'
			),
			organizationsRedirect
		);
	},

	<portlet:namespace />delete(organizationsRedirect) {
		<portlet:namespace />deleteOrganizations(organizationsRedirect);
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

	<portlet:namespace />removeOrganizationsAndUsers() {
		var form = document.<portlet:namespace />fm;

		Liferay.Util.postForm(form, {
			data: {
				removeOrganizationIds: Liferay.Util.getCheckedCheckboxes(
					form,
					'<portlet:namespace />allRowIds',
					'<portlet:namespace />rowIdsOrganization'
				),
				removeUserIds: Liferay.Util.getCheckedCheckboxes(
					form,
					'<portlet:namespace />allRowIds',
					'<portlet:namespace />rowIdsUser'
				),
			},
			url: '<%= removeOrganizationsAndUsersURL.toString() %>',
		});
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

function <portlet:namespace />doDeleteOrganization(
	className,
	ids,
	organizationsRedirect
) {
	var status = <%= WorkflowConstants.STATUS_INACTIVE %>;

	<portlet:namespace />getUsersCount(
		className,
		ids,
		status,
		(responseData) => {
			var count = parseInt(responseData, 10);

			if (count > 0) {
				status = <%= WorkflowConstants.STATUS_APPROVED %>;

				<portlet:namespace />getUsersCount(
					className,
					ids,
					status,
					(responseData) => {
						count = parseInt(responseData, 10);

						if (count > 0) {
							Liferay.Util.openConfirmModal({
								message:
									'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>',
								onConfirm: (isConfirmed) => {
									if (isConfirmed) {
										<portlet:namespace />doDeleteOrganizations(
											ids,
											organizationsRedirect
										);
									}
								},
							});
						}
						else {
							var message;

							if (ids && ids.toString().split(',').length > 1) {
								message =
									'<%= UnicodeLanguageUtil.get(request, "one-or-more-organizations-are-associated-with-deactivated-users.-do-you-want-to-proceed-with-deleting-the-selected-organizations-by-automatically-unassociating-the-deactivated-users") %>';
							}
							else {
								message =
									'<%= UnicodeLanguageUtil.get(request, "the-selected-organization-is-associated-with-deactivated-users.-do-you-want-to-proceed-with-deleting-the-selected-organization-by-automatically-unassociating-the-deactivated-users") %>';
							}

							Liferay.Util.openConfirmModal({
								message: message,
								onConfirm: (isConfirmed) => {
									if (isConfirmed) {
										<portlet:namespace />doDeleteOrganizations(
											ids,
											organizationsRedirect
										);
									}
								},
							});
						}
					}
				);
			}
			else {
				Liferay.Util.openConfirmModal({
					message:
						'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>',
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							<portlet:namespace />doDeleteOrganizations(
								ids,
								organizationsRedirect
							);
						}
					},
				});
			}
		}
	);
}

function <portlet:namespace />doDeleteOrganizations(
	organizationIds,
	organizationsRedirect
) {
	var form = document.<portlet:namespace />fm;

	if (organizationsRedirect) {
		Liferay.Util.setFormValues(form, {
			redirect: organizationsRedirect,
		});
	}

	Liferay.Util.postForm(form, {
		data: {
			deleteOrganizationIds: organizationIds,
			<%= Constants.CMD %>: '<%= Constants.DELETE %>',
		},
		url: '<portlet:actionURL name="/users_admin/edit_organization" />',
	});
}

function <portlet:namespace />doDeleteOrganizations(
	organizationIds,
	organizationsRedirect
) {
	var form = document.<portlet:namespace />fm;

	if (organizationsRedirect) {
		Liferay.Util.setFormValues(form, {
			redirect: organizationsRedirect,
		});
	}

	Liferay.Util.postForm(form, {
		data: {
			deleteOrganizationIds: organizationIds,
			deleteUserIds: Liferay.Util.getCheckedCheckboxes(
				form,
				'<portlet:namespace />allRowIds',
				'<portlet:namespace />rowIdsUser'
			),
		},
		url:
			'<portlet:actionURL name="/users_admin/delete_organizations_and_users" />',
	});
}

const getUserIds = (portletNamespace) => {
	const form = document.getElementById(`${portletNamespace}fm`);

	return getCheckedCheckboxes(
		form,
		`${portletNamespace}allRowIds`,
		`${portletNamespace}rowIdsUser`
	);
};

function <portlet:namespace />getUsersCount(className, ids, status, callback) {
	var formData = new FormData();

	formData.append('className', className);
	formData.append('ids', ids);
	formData.append('status', status);

	Liferay.Util.fetch(
		'<liferay-portlet:resourceURL id="/users_admin/get_users_count" />',
		{
			body: formData,
			method: 'POST',
		}
	)
		.then((response) => {
			return response.text();
		})
		.then((response) => {
			callback(response);
		})
		.catch((error) => {
			Liferay.Util.openToast({
				message: Liferay.Language.get(
					'an-unexpected-system-error-occurred'
				),
				type: 'danger',
			});
		});
}

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
