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

package com.liferay.dynamic.data.mapping.form.web.internal.instance.lifecycle;

import com.liferay.dynamic.data.mapping.form.web.internal.layout.type.constants.DDMFormPortletLayoutTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	service = {
		AddDefaultSharedFormLayoutPortalInstanceLifecycleListener.class,
		PortalInstanceLifecycleListener.class
	}
)
public class AddDefaultSharedFormLayoutPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	public String getFormLayoutURL(ThemeDisplay themeDisplay) {
		return StringBundler.concat(
			themeDisplay.getPortalURL(),
			themeDisplay.getPathFriendlyURLPublic(), "/forms/shared/-/form/");
	}

	public boolean isSharedLayout(ThemeDisplay themeDisplay) {
		Layout layout = themeDisplay.getLayout();

		String type = layout.getType();

		return type.equals(DDMFormPortletLayoutTypeConstants.LAYOUT_TYPE);
	}

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		Group group = _groupLocalService.fetchFriendlyURLGroup(
			company.getCompanyId(), "/forms");

		if (group == null) {
			group = _addFormsGroup(company.getCompanyId());
		}

		Layout sharedLayout = _layoutLocalService.fetchLayoutByFriendlyURL(
			group.getGroupId(), false, "/shared");

		if (sharedLayout == null) {
			sharedLayout = _addPublicLayout(
				company.getCompanyId(), group.getGroupId());
		}

		_verifyLayout(sharedLayout);

		Layout privateLayout = _layoutLocalService.fetchLayoutByFriendlyURL(
			group.getGroupId(), true, "/shared");

		if (privateLayout == null) {
			privateLayout = _addPrivateLayout(
				company.getCompanyId(), group.getGroupId());
		}

		_verifyLayout(privateLayout);
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setResourcePermissionLocalService(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private Group _addFormsGroup(long companyId) throws PortalException {
		return _groupLocalService.addGroup(
			_userLocalService.getDefaultUserId(companyId),
			GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			HashMapBuilder.put(
				LocaleUtil.getDefault(), GroupConstants.FORMS
			).build(),
			null, GroupConstants.TYPE_SITE_PRIVATE, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			GroupConstants.FORMS_FRIENDLY_URL, false, false, true, null);
	}

	private Layout _addPrivateLayout(long companyId, long groupId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);
		serviceContext.setAttribute("layoutUpdateable", Boolean.FALSE);

		serviceContext.setScopeGroupId(groupId);

		long defaultUserId = _userLocalService.getDefaultUserId(companyId);

		serviceContext.setUserId(defaultUserId);

		Layout layout = _layoutLocalService.addLayout(
			defaultUserId, groupId, true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Shared",
			StringPool.BLANK, StringPool.BLANK,
			DDMFormPortletLayoutTypeConstants.LAYOUT_TYPE, true, "/shared",
			serviceContext);

		_updateUserLayoutViewPermissionPermission(companyId, layout);

		return layout;
	}

	private Layout _addPublicLayout(long companyId, long groupId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);
		serviceContext.setAttribute("layoutUpdateable", Boolean.FALSE);

		serviceContext.setScopeGroupId(groupId);

		long defaultUserId = _userLocalService.getDefaultUserId(companyId);

		serviceContext.setUserId(defaultUserId);

		return _layoutLocalService.addLayout(
			defaultUserId, groupId, false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Shared",
			StringPool.BLANK, StringPool.BLANK,
			DDMFormPortletLayoutTypeConstants.LAYOUT_TYPE, true, "/shared",
			serviceContext);
	}

	private void _updateUserLayoutViewPermissionPermission(
			long companyId, Layout layout)
		throws PortalException {

		Role role = _roleLocalService.getRole(companyId, RoleConstants.USER);

		_resourcePermissionLocalService.addResourcePermission(
			role.getCompanyId(), Layout.class.getName(),
			ResourceConstants.SCOPE_GROUP, String.valueOf(layout.getGroupId()),
			role.getRoleId(), ActionKeys.VIEW);
	}

	private void _verifyLayout(Layout layout) throws PortalException {
		if (StringUtil.equals(
				layout.getType(),
				DDMFormPortletLayoutTypeConstants.LAYOUT_TYPE)) {

			return;
		}

		layout.setType(DDMFormPortletLayoutTypeConstants.LAYOUT_TYPE);

		_layoutLocalService.updateLayout(layout);
	}

	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;
	private ResourcePermissionLocalService _resourcePermissionLocalService;
	private RoleLocalService _roleLocalService;
	private UserLocalService _userLocalService;

}