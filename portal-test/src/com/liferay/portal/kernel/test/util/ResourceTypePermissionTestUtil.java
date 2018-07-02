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

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.model.ResourceTypePermission;
import com.liferay.portal.kernel.service.ResourceTypePermissionLocalServiceUtil;

/**
 * @author     Alberto Chaparro
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class ResourceTypePermissionTestUtil {

	public static ResourceTypePermission addResourceTypePermission(
			long actionIds, long groupId, String name)
		throws Exception {

		return addResourceTypePermission(
			actionIds, groupId, name, RandomTestUtil.nextLong());
	}

	public static ResourceTypePermission addResourceTypePermission(
			long actionIds, long groupId, String name, long roleId)
		throws Exception {

		long resourceTypePermissionId = CounterLocalServiceUtil.increment(
			ResourceTypePermission.class.getName());

		ResourceTypePermission resourceTypePermission =
			ResourceTypePermissionLocalServiceUtil.createResourceTypePermission(
				resourceTypePermissionId);

		resourceTypePermission.setCompanyId(TestPropsValues.getCompanyId());
		resourceTypePermission.setGroupId(groupId);
		resourceTypePermission.setName(name);
		resourceTypePermission.setRoleId(roleId);
		resourceTypePermission.setActionIds(actionIds);

		return ResourceTypePermissionLocalServiceUtil.addResourceTypePermission(
			resourceTypePermission);
	}

}