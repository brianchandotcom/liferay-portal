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

package com.liferay.layout.type.controller;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.impl.DefaultLayoutTypeAccessPolicyImpl;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
public abstract class BaseLayoutTypeAccessPolicyImpl
	extends DefaultLayoutTypeAccessPolicyImpl {

	@Override
	protected boolean hasAccessPermission(
			HttpServletRequest httpServletRequest, Layout layout,
			Portlet portlet)
		throws PortalException {

		if (layout.getMasterLayoutPlid() > 0) {
			Layout masterLayout = layoutLocalService.fetchLayout(
				layout.getMasterLayoutPlid());

			if (masterLayout != null) {
				String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
					masterLayout.getPlid(), portlet.getPortletId());

				List<ResourcePermission> resourcePermissions =
					resourcePermissionLocalService.
						getResourceResourcePermissions(
							masterLayout.getCompanyId(),
							masterLayout.getGroupId(), portlet.getPortletName(),
							resourcePrimKey);

				if (ListUtil.isNotEmpty(resourcePermissions)) {
					layout = masterLayout;
				}
			}
		}

		return super.hasAccessPermission(httpServletRequest, layout, portlet);
	}

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected ResourcePermissionLocalService resourcePermissionLocalService;

}