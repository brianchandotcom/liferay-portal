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

package com.liferay.users.admin.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class OrganizationActionDropdownItems {

	public OrganizationActionDropdownItems(
		Organization organization, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_organization = organization;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_organizationGroup = _organization.getGroup();

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws PortalException {
		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		boolean hasUpdatePermission = OrganizationPermissionUtil.contains(
			permissionChecker, _organization, ActionKeys.UPDATE);

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> dropdownGroupItem.setDropdownItems(
				DropdownItemListBuilder.add(
					() -> hasUpdatePermission,
					_getEditOrganizationActionUnsafeConsumer()
				).add(
					() ->
						_organizationGroup.isSite() &&
						(GroupPermissionUtil.contains(
							permissionChecker, _organizationGroup,
							ActionKeys.MANAGE_STAGING) ||
						 hasUpdatePermission),
					_getManageSiteActionUnsafeConsumer()
				).add(
					() ->
						permissionChecker.isGroupOwner(
							_organizationGroup.getGroupId()) ||
						OrganizationPermissionUtil.contains(
							permissionChecker, _organization,
							ActionKeys.ASSIGN_USER_ROLES),
					_getAssignOrganizationRolesActionUnsafeConsumer()
				).add(
					() -> OrganizationPermissionUtil.contains(
						permissionChecker, _organization,
						ActionKeys.ASSIGN_MEMBERS),
					_getAssignUsersActionUnsafeConsumer()
				).add(
					() -> OrganizationPermissionUtil.contains(
						permissionChecker, _organization,
						ActionKeys.MANAGE_USERS),
					_getAddUserActionUnsafeConsumer()
				).addAll(
					_getAddChildrenTypesDropdownItems()
				).add(
					() -> OrganizationPermissionUtil.contains(
						permissionChecker, _organization, ActionKeys.DELETE),
					_getDeleteActionUnsafeConsumer()
				).add(
					() -> {
						long parentOrganizationId = GetterUtil.getLong(
							_httpServletRequest.getAttribute(
								"view.jsp-organizationId"));

						if ((parentOrganizationId > 0) && hasUpdatePermission) {
							return true;
						}

						return false;
					},
					_getRemoveOrganizationActionUnsafeConsumer()
				).build())
		).build();
	}

	private List<DropdownItem> _getAddChildrenTypesDropdownItems()
		throws PortalException {

		if (!_organization.isParentable() ||
			!OrganizationPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), _organization,
				ActionKeys.ADD_ORGANIZATION)) {

			return Collections.emptyList();
		}

		return new DropdownItemList() {
			{
				for (String childrenType : _organization.getChildrenTypes()) {
					add(
						dropdownItem -> {
							dropdownItem.setHref(
								_renderResponse.createRenderURL(),
								"mvcRenderCommandName",
								"/users_admin/edit_organization",
								"parentOrganizationSearchContainerPrimaryKeys",
								_organization.getOrganizationId(), "backURL",
								_themeDisplay.getURLCurrent(), "type",
								childrenType);
							dropdownItem.setLabel(
								LanguageUtil.format(
									_httpServletRequest, "add-x",
									childrenType));
						});
				}
			}
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getAddUserActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcRenderCommandName",
				"/users_admin/edit_user",
				"organizationsSearchContainerPrimaryKeys",
				_organization.getOrganizationId(), "backURL",
				_themeDisplay.getURLCurrent());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "add-user"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getAssignOrganizationRolesActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "assignOrganizationRoles");
			dropdownItem.putData(
				"assignOrganizationRolesURL",
				PortletURLBuilder.create(
					PortletProviderUtil.getPortletURL(
						_httpServletRequest, UserGroupRole.class.getName(),
						PortletProvider.Action.EDIT)
				).setParameter(
					"className", User.class.getName()
				).setParameter(
					"groupId", _organizationGroup.getGroupId()
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString());
			dropdownItem.putData(
				"label",
				LanguageUtil.get(
					_httpServletRequest, "assign-organization-roles"));
			dropdownItem.setLabel(
				LanguageUtil.get(
					_httpServletRequest, "assign-organization-roles"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getAssignUsersActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "assignUsers");
			dropdownItem.putData(
				"basePortletURL",
				PortletURLBuilder.createRenderURL(
					_renderResponse
				).buildString());
			dropdownItem.putData(
				"organizationId",
				String.valueOf(_organization.getOrganizationId()));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "assign-users"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				StringBundler.concat(
					"javascript:", _renderResponse.getNamespace(),
					"deleteOrganization(", _organization.getOrganizationId(),
					");"));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getEditOrganizationActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcRenderCommandName",
				"/users_admin/edit_organization", "organizationId",
				_organization.getOrganizationId(), "backURL",
				_themeDisplay.getURLCurrent());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "edit"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getManageSiteActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				PortletProviderUtil.getPortletURL(
					_httpServletRequest, _organizationGroup,
					Group.class.getName(), PortletProvider.Action.EDIT),
				"viewOrganizationsRedirect", _themeDisplay.getURLCurrent());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "manage-site"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getRemoveOrganizationActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "removeOrganization");
			dropdownItem.putData(
				"removeOrganizationURL",
				PortletURLBuilder.create(
					_renderResponse.createActionURL()
				).setActionName(
					"/users_admin/edit_organization_assignments"
				).setParameter(
					"assignmentsRedirect", _themeDisplay.getURLCurrent()
				).setParameter(
					"removeOrganizationIds", _organization.getOrganizationId()
				).buildString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "remove"));
		};
	}

	private final HttpServletRequest _httpServletRequest;
	private final Organization _organization;
	private final Group _organizationGroup;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}