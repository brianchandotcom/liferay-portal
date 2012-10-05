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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourcePermissionFinderUtil {
	public static int countByR_S(long roleId, int[] scopes)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByR_S(roleId, scopes);
	}

	public static int countByC_N_S_P_R_A(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long[] roleIds, long actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByC_N_S_P_R_A(companyId, name, scope, primKey,
			roleIds, actionId);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByResource(
		long companyId, long groupId, java.lang.String name,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByResource(companyId, groupId, name, primKey);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByR_S(
		long roleId, int[] scopes, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByR_S(roleId, scopes, start, end);
	}

	public static ResourcePermissionFinder getFinder() {
		if (_finder == null) {
			_finder = (ResourcePermissionFinder)PortalBeanLocatorUtil.locate(ResourcePermissionFinder.class.getName());

			ReferenceRegistry.registerReference(ResourcePermissionFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(ResourcePermissionFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(ResourcePermissionFinderUtil.class,
			"_finder");
	}

	private static ResourcePermissionFinder _finder;
}