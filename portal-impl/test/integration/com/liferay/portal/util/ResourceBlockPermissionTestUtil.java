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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.model.ResourceBlockPermission;
import com.liferay.portal.service.ResourceBlockPermissionLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;

/**
 * @author Alberto Chaparro
 */
public class ResourceBlockPermissionTestUtil {

	public static ResourceBlockPermission addResourceBlockPermission(
			long resourceBlockId, long roleId, long actionIds)
		throws Exception {

		long resourceBlockPermissionId = CounterLocalServiceUtil.increment(
			ResourceBlockPermission.class.getName());

		ResourceBlockPermission resourceBlockPermission =
			ResourceBlockPermissionLocalServiceUtil.
				createResourceBlockPermission(resourceBlockPermissionId);

		resourceBlockPermission.setResourceBlockId(resourceBlockId);
		resourceBlockPermission.setRoleId(roleId);
		resourceBlockPermission.setActionIds(actionIds);

		return ResourceBlockPermissionLocalServiceUtil.
			addResourceBlockPermission(resourceBlockPermission);
	}

}