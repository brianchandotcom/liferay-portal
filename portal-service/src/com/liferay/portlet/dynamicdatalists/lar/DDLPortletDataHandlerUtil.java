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

package com.liferay.portlet.dynamicdatalists.lar;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Michael C. Han
 */
public class DDLPortletDataHandlerUtil {

	public static DDLPortletDataHandler getDDLPortletDataHandler() {
		PortalRuntimePermission.checkGetBeanProperty(
			DDLPortletDataHandlerUtil.class);

		return _ddlPortletDataHandler;
	}

	public void setDDLPortletDataHandler(
		DDLPortletDataHandler ddlPortletDataHandler) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ddlPortletDataHandler = ddlPortletDataHandler;
	}

	private static DDLPortletDataHandler _ddlPortletDataHandler;

}