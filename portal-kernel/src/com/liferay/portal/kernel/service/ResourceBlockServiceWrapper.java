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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link ResourceBlockService}.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockService
 * @deprecated
 * @generated
 */
@Deprecated
@ProviderType
public class ResourceBlockServiceWrapper implements ResourceBlockService,
	ServiceWrapper<ResourceBlockService> {
	public ResourceBlockServiceWrapper(
		ResourceBlockService resourceBlockService) {
		_resourceBlockService = resourceBlockService;
	}

	@Override
	public void addCompanyScopePermission(long scopeGroupId, long companyId,
		String name, long roleId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockService.addCompanyScopePermission(scopeGroupId,
			companyId, name, roleId, actionId);
	}

	@Override
	public void addGroupScopePermission(long scopeGroupId, long companyId,
		long groupId, String name, long roleId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockService.addGroupScopePermission(scopeGroupId, companyId,
			groupId, name, roleId, actionId);
	}

	@Override
	public void addIndividualScopePermission(long companyId, long groupId,
		String name, long primKey, long roleId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockService.addIndividualScopePermission(companyId, groupId,
			name, primKey, roleId, actionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _resourceBlockService.getOSGiServiceIdentifier();
	}

	@Override
	public void removeAllGroupScopePermissions(long scopeGroupId,
		long companyId, String name, long roleId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockService.removeAllGroupScopePermissions(scopeGroupId,
			companyId, name, roleId, actionId);
	}

	@Override
	public void removeCompanyScopePermission(long scopeGroupId, long companyId,
		String name, long roleId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockService.removeCompanyScopePermission(scopeGroupId,
			companyId, name, roleId, actionId);
	}

	@Override
	public void removeGroupScopePermission(long scopeGroupId, long companyId,
		long groupId, String name, long roleId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockService.removeGroupScopePermission(scopeGroupId,
			companyId, groupId, name, roleId, actionId);
	}

	@Override
	public void removeIndividualScopePermission(long companyId, long groupId,
		String name, long primKey, long roleId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockService.removeIndividualScopePermission(companyId,
			groupId, name, primKey, roleId, actionId);
	}

	@Override
	public void setCompanyScopePermissions(long scopeGroupId, long companyId,
		String name, long roleId, java.util.List<String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockService.setCompanyScopePermissions(scopeGroupId,
			companyId, name, roleId, actionIds);
	}

	@Override
	public void setGroupScopePermissions(long scopeGroupId, long companyId,
		long groupId, String name, long roleId, java.util.List<String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockService.setGroupScopePermissions(scopeGroupId, companyId,
			groupId, name, roleId, actionIds);
	}

	@Override
	public void setIndividualScopePermissions(long companyId, long groupId,
		String name, long primKey, long roleId, java.util.List<String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockService.setIndividualScopePermissions(companyId, groupId,
			name, primKey, roleId, actionIds);
	}

	@Override
	public void setIndividualScopePermissions(long companyId, long groupId,
		String name, long primKey,
		java.util.Map<Long, String[]> roleIdsToActionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockService.setIndividualScopePermissions(companyId, groupId,
			name, primKey, roleIdsToActionIds);
	}

	@Override
	public ResourceBlockService getWrappedService() {
		return _resourceBlockService;
	}

	@Override
	public void setWrappedService(ResourceBlockService resourceBlockService) {
		_resourceBlockService = resourceBlockService;
	}

	private ResourceBlockService _resourceBlockService;
}