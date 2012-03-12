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

package com.liferay.portlet.sitesadmin.util;

import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;

import javax.portlet.PortletRequest;

/**
 * @author Zsolt Szabo
 */
public interface SitesAdmin {

	public static final boolean DEFAULT_BRANCHING_PRIVATE = false;

	public static final boolean DEFAULT_BRANCHING_PUBLIC = false;

	public static final long DEFAULT_REMOTE_GROUP_ID = 0;

	public static final int DEFAULT_REMOTE_PORT = 0;

	public static final boolean DEFAULT_SECURE_CONNECTION = false;

	public int getStagingType(Group liveGroup, PortletRequest portletRequest);

	public void updateAssetCategoryIds(
			PortletRequest portletRequest, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception;

	public void updateAssetTagNames(
			PortletRequest portletRequest, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception;

}