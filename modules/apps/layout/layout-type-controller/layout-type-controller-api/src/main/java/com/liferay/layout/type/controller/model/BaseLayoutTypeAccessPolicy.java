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

package com.liferay.layout.type.controller.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.impl.DefaultLayoutTypeAccessPolicyImpl;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
public abstract class BaseLayoutTypeAccessPolicy
	extends DefaultLayoutTypeAccessPolicyImpl {

	@Override
	protected boolean hasAccessPermission(
			HttpServletRequest httpServletRequest, Layout layout,
			Portlet portlet)
		throws PortalException {

		if (layout.getMasterLayoutPlid() == 0) {
			return super.hasAccessPermission(
				httpServletRequest, layout, portlet);
		}

		Layout masterLayout = layoutLocalService.fetchLayout(
			layout.getMasterLayoutPlid());

		if (masterLayout == null) {
			return super.hasAccessPermission(
				httpServletRequest, layout, portlet);
		}

		javax.portlet.PortletPreferences jxPortletPreferences =
			portletPreferencesLocalService.fetchPreferences(
				portletPreferencesFactory.getPortletPreferencesIds(
					httpServletRequest, masterLayout, portlet.getPortletId()));

		if (jxPortletPreferences == null) {
			return super.hasAccessPermission(
				httpServletRequest, layout, portlet);
		}

		String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
			masterLayout.getPlid(), portlet.getPortletId());

		List<ResourcePermission> resourcePermissions =
			resourcePermissionLocalService.getResourceResourcePermissions(
				masterLayout.getCompanyId(), masterLayout.getGroupId(),
				portlet.getPortletName(), resourcePrimKey);

		if (ListUtil.isNotEmpty(resourcePermissions)) {
			layout = masterLayout;
		}

		return super.hasAccessPermission(httpServletRequest, layout, portlet);
	}

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected PortletPreferencesFactory portletPreferencesFactory;

	@Reference
	protected PortletPreferencesLocalService portletPreferencesLocalService;

	@Reference
	protected ResourcePermissionLocalService resourcePermissionLocalService;

}