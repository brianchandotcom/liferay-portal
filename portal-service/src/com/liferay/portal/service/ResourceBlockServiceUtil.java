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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for ResourceBlock. This utility wraps
 * {@link com.liferay.portal.service.impl.ResourceBlockServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockService
 * @see com.liferay.portal.service.base.ResourceBlockServiceBaseImpl
 * @see com.liferay.portal.service.impl.ResourceBlockServiceImpl
 * @generated
 */
@ProviderType
public class ResourceBlockServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.ResourceBlockServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addCompanyScopePermission(long scopeGroupId,
		long companyId, java.lang.String name, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addCompanyScopePermission(scopeGroupId, companyId, name, roleId,
			actionId);
	}

	public static void addGroupScopePermission(long scopeGroupId,
		long companyId, long groupId, java.lang.String name, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addGroupScopePermission(scopeGroupId, companyId, groupId, name,
			roleId, actionId);
	}

	public static void addIndividualScopePermission(long companyId,
		long groupId, java.lang.String name, long primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addIndividualScopePermission(companyId, groupId, name, primKey,
			roleId, actionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void removeAllGroupScopePermissions(long scopeGroupId,
		long companyId, java.lang.String name, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.removeAllGroupScopePermissions(scopeGroupId, companyId, name,
			roleId, actionId);
	}

	public static void removeCompanyScopePermission(long scopeGroupId,
		long companyId, java.lang.String name, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.removeCompanyScopePermission(scopeGroupId, companyId, name,
			roleId, actionId);
	}

	public static void removeGroupScopePermission(long scopeGroupId,
		long companyId, long groupId, java.lang.String name, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.removeGroupScopePermission(scopeGroupId, companyId, groupId, name,
			roleId, actionId);
	}

	public static void removeIndividualScopePermission(long companyId,
		long groupId, java.lang.String name, long primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.removeIndividualScopePermission(companyId, groupId, name, primKey,
			roleId, actionId);
	}

	public static void setCompanyScopePermissions(long scopeGroupId,
		long companyId, java.lang.String name, long roleId,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.setCompanyScopePermissions(scopeGroupId, companyId, name, roleId,
			actionIds);
	}

	public static void setGroupScopePermissions(long scopeGroupId,
		long companyId, long groupId, java.lang.String name, long roleId,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.setGroupScopePermissions(scopeGroupId, companyId, groupId, name,
			roleId, actionIds);
	}

	public static void setIndividualScopePermissions(long companyId,
		long groupId, java.lang.String name, long primKey, long roleId,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.setIndividualScopePermissions(companyId, groupId, name, primKey,
			roleId, actionIds);
	}

	public static void setIndividualScopePermissions(long companyId,
		long groupId, java.lang.String name, long primKey,
		java.util.Map<java.lang.Long, java.lang.String[]> roleIdsToActionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.setIndividualScopePermissions(companyId, groupId, name, primKey,
			roleIdsToActionIds);
	}

	public static ResourceBlockService getService() {
		if (_service == null) {
			_service = (ResourceBlockService)PortalBeanLocatorUtil.locate(ResourceBlockService.class.getName());

			ReferenceRegistry.registerReference(ResourceBlockServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static ResourceBlockService _service;
}