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

package com.liferay.site.memberships.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.user.groups.admin.constants.UserGroupsAdminPortletKeys;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = CTDisplayRenderer.class)
public class UserGroupCTDisplayRenderer
	extends BaseCTDisplayRenderer<UserGroup> {

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest, UserGroup userGroup)
		throws PortalException {

		Group group = _groupLocalService.getGroup(userGroup.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, group,
				UserGroupsAdminPortletKeys.USER_GROUPS_ADMIN, 0, 0,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_user_group.jsp"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setBackURL(
			ParamUtil.getString(httpServletRequest, "backURL")
		).setParameter(
			"userGroupId", userGroup.getUserGroupId()
		).buildString();
	}

	@Override
	public Class<UserGroup> getModelClass() {
		return UserGroup.class;
	}

	@Override
	public String getTitle(Locale locale, UserGroup userGroup) {
		return userGroup.getName();
	}

	@Override
	protected void buildDisplay(DisplayBuilder<UserGroup> displayBuilder) {
		UserGroup userGroup = displayBuilder.getModel();

		displayBuilder.display(
			"name", userGroup.getName()
		).display(
			"created-by",
			() -> {
				String userName = userGroup.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"description", userGroup.getDescription()
		).display(
			"users",
			() -> _userLocalService.searchCount(
				userGroup.getCompanyId(), StringPool.BLANK,
				WorkflowConstants.STATUS_ANY,
				LinkedHashMapBuilder.<String, Object>put(
					"usersUserGroups", userGroup.getUserGroupId()
				).build())
		).display(
			"roles",
			() -> ListUtil.toString(
				_userGroupGroupRoleLocalService.getUserGroupGroupRoles(
					userGroup.getUserGroupId(), userGroup.getGroupId()),
				UsersAdmin.USER_GROUP_GROUP_ROLE_TITLE_ACCESSOR,
				StringPool.COMMA_AND_SPACE)
		);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserGroupGroupRoleLocalService _userGroupGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}