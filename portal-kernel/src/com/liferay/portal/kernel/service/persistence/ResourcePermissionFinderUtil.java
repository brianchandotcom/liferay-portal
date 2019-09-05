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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ResourcePermissionFinderUtil {

	public static int countByR_S(long roleId, int[] scopes) {
		return getFinder().countByR_S(roleId, scopes);
	}

	public static int countByC_N_S_P_R_A(
		long companyId, String name, int scope, String primKey, long[] roleIds,
		long actionId) {

		return getFinder().countByC_N_S_P_R_A(
			companyId, name, scope, primKey, roleIds, actionId);
	}

	public static java.util.Map
		<java.io.Serializable,
		 com.liferay.portal.kernel.model.ResourcePermission> fetchByPrimaryKeys(
			java.util.Set<java.io.Serializable> primaryKeys) {

		return getFinder().fetchByPrimaryKeys(primaryKeys);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.ResourcePermission> findByResource(
			long companyId, long groupId, String name, String primKey) {

		return getFinder().findByResource(companyId, groupId, name, primKey);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.ResourcePermission> findByR_S(
			long roleId, int[] scopes, int start, int end) {

		return getFinder().findByR_S(roleId, scopes, start, end);
	}

	public static ResourcePermissionFinder getFinder() {
		if (_finder == null) {
			_finder = (ResourcePermissionFinder)PortalBeanLocatorUtil.locate(
				ResourcePermissionFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(ResourcePermissionFinder finder) {
		_finder = finder;
	}

	private static ResourcePermissionFinder _finder;

}