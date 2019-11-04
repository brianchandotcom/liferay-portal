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

package com.liferay.layout.type.controller.link.to.page.internal.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypeAccessPolicy;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.impl.DefaultLayoutTypeAccessPolicyImpl;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Sivanov
 */
@Component(
	immediate = true,
	property = "layout.type=" + LayoutConstants.TYPE_LINK_TO_LAYOUT,
	service = LayoutTypeAccessPolicy.class
)
public class LinkToPageLayoutTypeAccessPolicy
	extends DefaultLayoutTypeAccessPolicyImpl {

	@Override
	public boolean isAddLayoutAllowed(
		PermissionChecker permissionChecker, Layout layout) {

		return false;
	}

	@Override
	public boolean isCustomizeLayoutAllowed(
		PermissionChecker permissionChecker, Layout layout) {

		return false;
	}

	@Override
	public boolean isDeleteLayoutAllowed(
		PermissionChecker permissionChecker, Layout layout) {

		return false;
	}

	@Override
	public boolean isUpdateLayoutAllowed(
		PermissionChecker permissionChecker, Layout layout) {

		return false;
	}

	@Override
	public boolean isViewLayoutAllowed(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		return super.isViewLayoutAllowed(permissionChecker, layout);
	}

	@Override
	protected boolean hasAccessPermission(
			HttpServletRequest httpServletRequest, Layout layout,
			Portlet portlet)
		throws PortalException {

		return super.hasAccessPermission(httpServletRequest, layout, portlet);
	}

	@Override
	protected boolean isAccessAllowedToLayoutPortlet(
		HttpServletRequest httpServletRequest, Layout layout, Portlet portlet) {

		return super.isAccessAllowedToLayoutPortlet(
			httpServletRequest, layout, portlet);
	}

}