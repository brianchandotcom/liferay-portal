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
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;

/**
 * @author Alberto Chaparro
 */
public class ResourcePermissionTestUtil {

	public static ResourcePermission addResourcePermission() throws Exception {
		return addResourcePermission(
			ServiceTestUtil.nextLong(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), ServiceTestUtil.nextLong(),
			ServiceTestUtil.nextInt());
	}

	public static ResourcePermission addResourcePermission(long roleId)
		throws Exception {

		return addResourcePermission(
			ServiceTestUtil.nextLong(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), roleId, ServiceTestUtil.nextInt());
	}

	public static ResourcePermission addResourcePermission(
			long actionIds, String name, long roleId)
		throws Exception {

		return addResourcePermission(
			actionIds, name, ServiceTestUtil.randomString(), roleId,
			ServiceTestUtil.nextInt());
	}

	public static ResourcePermission addResourcePermission(
			long actionIds, String name, String primKey)
		throws Exception {

		return addResourcePermission(
			actionIds, name, primKey, ServiceTestUtil.nextLong(),
			ServiceTestUtil.nextInt());
	}

	public static ResourcePermission addResourcePermission(
			long actionIds, String name, String primKey, int scope)
		throws Exception {

		return addResourcePermission(
			actionIds, name, primKey, ServiceTestUtil.nextInt(), scope);
	}

	public static ResourcePermission addResourcePermission(
			long actionIds, String name, String primKey, long roleId, int scope)
		throws Exception {

		long resourcePermissionId = CounterLocalServiceUtil.increment(
			ResourcePermission.class.getName());

		ResourcePermission resourcePermission =
			ResourcePermissionLocalServiceUtil.createResourcePermission(
				resourcePermissionId);

		resourcePermission.setActionIds(actionIds);
		resourcePermission.setCompanyId(TestPropsValues.getCompanyId());
		resourcePermission.setName(name);
		resourcePermission.setPrimKey(primKey);
		resourcePermission.setRoleId(roleId);
		resourcePermission.setScope(scope);

		return ResourcePermissionLocalServiceUtil.addResourcePermission(
			resourcePermission);
	}

	public static ResourcePermission addResourcePermission(String primKey)
		throws Exception {

		return addResourcePermission(
			ServiceTestUtil.nextLong(), ServiceTestUtil.randomString(), primKey,
			ServiceTestUtil.nextLong(), ServiceTestUtil.nextInt());
	}

}