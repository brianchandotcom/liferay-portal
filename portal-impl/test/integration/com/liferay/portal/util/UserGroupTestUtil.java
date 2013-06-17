/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;

/**
 * @author Roberto Díaz
 */
public class UserGroupTestUtil {

	public static UserGroup addUserGroup() throws Exception {
		return addUserGroup(TestPropsValues.getGroupId());
	}

	public static UserGroup addUserGroup(long groupId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return UserGroupLocalServiceUtil.addUserGroup(
			serviceContext.getUserId(), serviceContext.getCompanyId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(50),
			serviceContext);
	}

}