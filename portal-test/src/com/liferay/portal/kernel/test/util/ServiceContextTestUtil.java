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

package com.liferay.portal.kernel.test.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

/**
 * @author Manuel de la Peña
 */
public class ServiceContextTestUtil {

	public static ServiceContext getServiceContext() throws PortalException {
		return getServiceContext(TestPropsValues.getGroupId());
	}

	public static ServiceContext getServiceContext(Group group, long userId) {
		return getServiceContext(
			group.getCompanyId(), group.getGroupId(), userId);
	}

	public static ServiceContext getServiceContext(long groupId)
		throws PortalException {

		if (groupId == TestPropsValues.getGroupId()) {
			return getServiceContext(groupId, TestPropsValues.getUserId());
		}
		else {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			User user = UserTestUtil.getAdminUser(group.getCompanyId());

			return getServiceContext(group, user.getUserId());
		}
	}

	public static ServiceContext getServiceContext(long groupId, long userId)
		throws PortalException {

		if (groupId == TestPropsValues.getGroupId()) {
			return getServiceContext(
				TestPropsValues.getCompanyId(), groupId, userId);
		}
		else {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			return getServiceContext(
				group.getCompanyId(), group.getGroupId(), userId);
		}
	}

	public static ServiceContext getServiceContext(
		long companyId, long groupId, long userId) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(companyId);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		return serviceContext;
	}

}