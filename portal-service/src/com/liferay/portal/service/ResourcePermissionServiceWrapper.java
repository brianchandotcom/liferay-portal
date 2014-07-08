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

/**
 * Provides a wrapper for {@link ResourcePermissionService}.
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePermissionService
 * @generated
 */
@ProviderType
public class ResourcePermissionServiceWrapper
	implements ResourcePermissionService,
		ServiceWrapper<ResourcePermissionService> {
	public ResourcePermissionServiceWrapper(
		ResourcePermissionService resourcePermissionService) {
		_resourcePermissionService = resourcePermissionService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _resourcePermissionService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_resourcePermissionService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Grants the role permission at the scope to perform the action on
	* resources of the type. Existing actions are retained.
	*
	* <p>
	* This method cannot be used to grant individual scope permissions, but is
	* only intended for adding permissions at the company, group, and
	* group-template scopes. For example, this method could be used to grant a
	* company scope permission to edit message board posts.
	* </p>
	*
	* <p>
	* If a company scope permission is granted to resources that the role
	* already had group scope permissions to, the group scope permissions are
	* deleted. Likewise, if a group scope permission is granted to resources
	* that the role already had company scope permissions to, the company scope
	* permissions are deleted. Be aware that this latter behavior can result in
	* an overall reduction in permissions for the role.
	* </p>
	*
	* <p>
	* Depending on the scope, the value of <code>primKey</code> will have
	* different meanings. For more information, see {@link
	* com.liferay.portal.model.impl.ResourcePermissionImpl}.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope. This method only supports company, group, and
	group-template scope.
	* @param primKey the primary key
	* @param roleId the primary key of the role
	* @param actionId the action ID
	* @throws PortalException if the user did not have permission to add
	resource permissions, or if scope was set to individual scope or
	if a role with the primary key or a resource action with the name
	and action ID could not be found
	*/
	@Override
	public void addResourcePermission(long groupId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourcePermissionService.addResourcePermission(groupId, companyId,
			name, scope, primKey, roleId, actionId);
	}

	/**
	* Revokes permission at the scope from the role to perform the action on
	* resources of the type. For example, this method could be used to revoke a
	* group scope permission to edit blog posts.
	*
	* <p>
	* Depending on the scope, the value of <code>primKey</code> will have
	* different meanings. For more information, see {@link
	* com.liferay.portal.model.impl.ResourcePermissionImpl}.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope
	* @param primKey the primary key
	* @param roleId the primary key of the role
	* @param actionId the action ID
	* @throws PortalException if the user did not have permission to remove
	resource permissions, or if a role with the primary key or a
	resource action with the name and action ID could not be found
	*/
	@Override
	public void removeResourcePermission(long groupId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourcePermissionService.removeResourcePermission(groupId, companyId,
			name, scope, primKey, roleId, actionId);
	}

	/**
	* Revokes all permissions at the scope from the role to perform the action
	* on resources of the type. For example, this method could be used to
	* revoke all individual scope permissions to edit blog posts from site
	* members.
	*
	* @param groupId the primary key of the group
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope
	* @param roleId the primary key of the role
	* @param actionId the action ID
	* @throws PortalException if the user did not have permission to remove
	resource permissions, or if a role with the primary key or a
	resource action with the name and action ID could not be found
	*/
	@Override
	public void removeResourcePermissions(long groupId, long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourcePermissionService.removeResourcePermissions(groupId,
			companyId, name, scope, roleId, actionId);
	}

	/**
	* Updates the role's permissions at the scope, setting the actions that can
	* be performed on resources of the type. Existing actions are replaced.
	*
	* <p>
	* This method can be used to set permissions at any scope, but it is
	* generally only used at the individual scope. For example, it could be
	* used to set the guest permissions on a blog post.
	* </p>
	*
	* <p>
	* Depending on the scope, the value of <code>primKey</code> will have
	* different meanings. For more information, see {@link
	* com.liferay.portal.model.impl.ResourcePermissionImpl}.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param primKey the primary key
	* @param roleId the primary key of the role
	* @param actionIds the action IDs of the actions
	* @throws PortalException if the user did not have permission to set
	resource permissions, or if a role with the primary key or a
	resource action with the name and action ID could not be found
	*/
	@Override
	public void setIndividualResourcePermissions(long groupId, long companyId,
		java.lang.String name, java.lang.String primKey, long roleId,
		java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourcePermissionService.setIndividualResourcePermissions(groupId,
			companyId, name, primKey, roleId, actionIds);
	}

	/**
	* Updates the role's permissions at the scope, setting the actions that can
	* be performed on resources of the type. Existing actions are replaced.
	*
	* <p>
	* This method can be used to set permissions at any scope, but it is
	* generally only used at the individual scope. For example, it could be
	* used to set the guest permissions on a blog post.
	* </p>
	*
	* <p>
	* Depending on the scope, the value of <code>primKey</code> will have
	* different meanings. For more information, see {@link
	* com.liferay.portal.model.impl.ResourcePermissionImpl}.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param primKey the primary key
	* @param roleIdsToActionIds a map of role IDs to action IDs of the actions
	* @throws PortalException if the user did not have permission to set
	resource permissions, or if a role with the primary key or a
	resource action with the name and action ID could not be found
	*/
	@Override
	public void setIndividualResourcePermissions(long groupId, long companyId,
		java.lang.String name, java.lang.String primKey,
		java.util.Map<java.lang.Long, java.lang.String[]> roleIdsToActionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourcePermissionService.setIndividualResourcePermissions(groupId,
			companyId, name, primKey, roleIdsToActionIds);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ResourcePermissionService getWrappedResourcePermissionService() {
		return _resourcePermissionService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedResourcePermissionService(
		ResourcePermissionService resourcePermissionService) {
		_resourcePermissionService = resourcePermissionService;
	}

	@Override
	public ResourcePermissionService getWrappedService() {
		return _resourcePermissionService;
	}

	@Override
	public void setWrappedService(
		ResourcePermissionService resourcePermissionService) {
		_resourcePermissionService = resourcePermissionService;
	}

	private ResourcePermissionService _resourcePermissionService;
}