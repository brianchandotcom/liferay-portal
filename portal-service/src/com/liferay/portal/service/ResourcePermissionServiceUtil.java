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
 * Provides the remote service utility for ResourcePermission. This utility wraps
 * {@link com.liferay.portal.service.impl.ResourcePermissionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePermissionService
 * @see com.liferay.portal.service.base.ResourcePermissionServiceBaseImpl
 * @see com.liferay.portal.service.impl.ResourcePermissionServiceImpl
 * @generated
 */
@ProviderType
public class ResourcePermissionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.ResourcePermissionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
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
	public static void addResourcePermission(long groupId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addResourcePermission(groupId, companyId, name, scope, primKey,
			roleId, actionId);
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
	public static void removeResourcePermission(long groupId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.removeResourcePermission(groupId, companyId, name, scope, primKey,
			roleId, actionId);
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
	public static void removeResourcePermissions(long groupId, long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.removeResourcePermissions(groupId, companyId, name, scope, roleId,
			actionId);
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
	public static void setIndividualResourcePermissions(long groupId,
		long companyId, java.lang.String name, java.lang.String primKey,
		long roleId, java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.setIndividualResourcePermissions(groupId, companyId, name,
			primKey, roleId, actionIds);
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
	public static void setIndividualResourcePermissions(long groupId,
		long companyId, java.lang.String name, java.lang.String primKey,
		java.util.Map<java.lang.Long, java.lang.String[]> roleIdsToActionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.setIndividualResourcePermissions(groupId, companyId, name,
			primKey, roleIdsToActionIds);
	}

	public static ResourcePermissionService getService() {
		if (_service == null) {
			_service = (ResourcePermissionService)PortalBeanLocatorUtil.locate(ResourcePermissionService.class.getName());

			ReferenceRegistry.registerReference(ResourcePermissionServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(ResourcePermissionService service) {
	}

	private static ResourcePermissionService _service;
}