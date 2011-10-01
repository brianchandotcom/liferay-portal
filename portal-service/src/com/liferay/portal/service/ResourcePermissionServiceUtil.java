/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the resource permission remote service. This utility wraps {@link com.liferay.portal.service.impl.ResourcePermissionServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePermissionService
 * @see com.liferay.portal.service.base.ResourcePermissionServiceBaseImpl
 * @see com.liferay.portal.service.impl.ResourcePermissionServiceImpl
 * @generated
 */
public class ResourcePermissionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.ResourcePermissionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addResourcePermission(long groupId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addResourcePermission(groupId, companyId, name, scope, primKey,
			roleId, actionId);
	}

	public static void setIndividualResourcePermissions(long groupId,
		long companyId, java.lang.String name, java.lang.String primKey,
		java.util.Map<java.lang.Long, java.lang.String[]> roleIdsToActionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setIndividualResourcePermissions(groupId, companyId, name,
			primKey, roleIdsToActionIds);
	}

	public static void setIndividualResourcePermissions(long groupId,
		long companyId, java.lang.String name, java.lang.String primKey,
		long roleId, java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setIndividualResourcePermissions(groupId, companyId, name,
			primKey, roleId, actionIds);
	}

	public static void removeResourcePermission(long groupId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.removeResourcePermission(groupId, companyId, name, scope, primKey,
			roleId, actionId);
	}

	public static void removeResourcePermissions(long groupId, long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.removeResourcePermissions(groupId, companyId, name, scope, roleId,
			actionId);
	}

	public static ResourcePermissionService getService() {
		if (_service == null) {
			_service = (ResourcePermissionService)PortalBeanLocatorUtil.locate(ResourcePermissionService.class.getName());

			ReferenceRegistry.registerReference(ResourcePermissionServiceUtil.class,
				"_service");
			MethodCache.remove(ResourcePermissionService.class);
		}

		return _service;
	}

	public void setService(ResourcePermissionService service) {
		MethodCache.remove(ResourcePermissionService.class);

		_service = service;

		ReferenceRegistry.registerReference(ResourcePermissionServiceUtil.class,
			"_service");
		MethodCache.remove(ResourcePermissionService.class);
	}

	private static ResourcePermissionService _service;
}