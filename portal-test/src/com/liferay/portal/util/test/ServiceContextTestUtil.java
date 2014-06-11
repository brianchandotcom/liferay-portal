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

package com.liferay.portal.util.test;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.service.ServiceContext;

/**
 * @author Manuel de la Peña
 */
public class ServiceContextTestUtil {

	public static ServiceContext getServiceContext() throws PortalException {
		return getServiceContext(TestPropsValues.getGroupId());
	}

	public static ServiceContext getServiceContext(long groupId)
		throws PortalException {

		return getServiceContext(groupId, TestPropsValues.getUserId());
	}

	public static ServiceContext getServiceContext(long groupId, long userId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(TestPropsValues.getCompanyId());
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		return serviceContext;
	}

}