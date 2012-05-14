/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.PermissionChecker;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Zsolt Berentey
 */
public interface TrashRenderer {

	public String getIconPath(PortletRequest portletRequest);

	public String getPortletId();

	public String getSummary(Locale locale);

	public String getTitle(Locale locale);

	public String getType();

	public boolean hasDeletePermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException;

	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException;

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception;

}