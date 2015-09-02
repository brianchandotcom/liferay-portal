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

package com.liferay.layout.type.controller.full.page.application.access.policy;

import com.liferay.layout.type.controller.full.page.application.constants.FullPageApplicationLayoutTypeControllerConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypeAccessPolicy;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.DefaultLayoutTypeAccessPolicyImpl;
import com.liferay.portal.security.permission.PermissionChecker;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {"layout.type=" + FullPageApplicationLayoutTypeControllerConstants.LAYOUT_TYPE_FULL_PAGE_APPLICATION},
	service = LayoutTypeAccessPolicy.class
)
public class FullPageApplicationLayoutTypeAccessPolicy
	extends DefaultLayoutTypeAccessPolicyImpl {

	@Override
	public boolean isAddLayoutAllowed(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		return false;
	}

	@Override
	public boolean isCustomizeLayoutAllowed(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		return false;
	}

	@Override
	public boolean isDeleteLayoutAllowed(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		return false;
	}

	@Override
	public boolean isUpdateLayoutAllowed(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		return false;
	}

	@Override
	public boolean isViewLayoutAllowed(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		return true;
	}

	@Override
	protected boolean hasAccessPermission(
			HttpServletRequest request, Layout layout, Portlet portlet)
		throws PortalException {

		return true;
	}

	@Override
	protected boolean isAccessAllowedToLayoutPortlet(
			HttpServletRequest request, Layout layout, Portlet portlet)
		throws PortalException {

		return true;
	}

}