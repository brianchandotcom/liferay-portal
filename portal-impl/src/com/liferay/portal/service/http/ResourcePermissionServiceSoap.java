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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ResourcePermissionServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>ResourcePermissionServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.kernel.model.ResourcePermissionSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.kernel.model.ResourcePermission</code>, that is translated to a
 * <code>com.liferay.portal.kernel.model.ResourcePermissionSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePermissionServiceHttp
 * @generated
 */
public class ResourcePermissionServiceSoap {

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
	 */
	public static void addResourcePermission(
			long groupId, long companyId, String name, int scope,
			String primKey, long roleId, String actionId)
		throws RemoteException {

		try {
			ResourcePermissionServiceUtil.addResourcePermission(
				groupId, companyId, name, scope, primKey, roleId, actionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	 */
	public static void removeResourcePermission(
			long groupId, long companyId, String name, int scope,
			String primKey, long roleId, String actionId)
		throws RemoteException {

		try {
			ResourcePermissionServiceUtil.removeResourcePermission(
				groupId, companyId, name, scope, primKey, roleId, actionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	 */
	public static void removeResourcePermissions(
			long groupId, long companyId, String name, int scope, long roleId,
			String actionId)
		throws RemoteException {

		try {
			ResourcePermissionServiceUtil.removeResourcePermissions(
				groupId, companyId, name, scope, roleId, actionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	 */
	public static void setIndividualResourcePermissions(
			long groupId, long companyId, String name, String primKey,
			long roleId, String[] actionIds)
		throws RemoteException {

		try {
			ResourcePermissionServiceUtil.setIndividualResourcePermissions(
				groupId, companyId, name, primKey, roleId, actionIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ResourcePermissionServiceSoap.class);

}