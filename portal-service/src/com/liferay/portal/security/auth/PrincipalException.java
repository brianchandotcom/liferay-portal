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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 */
public class PrincipalException extends PortalException {

	public static Class[] getNestedClasses() {
		return _NESTED_CLASSES;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public PrincipalException() {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public PrincipalException(String msg) {
		super(msg);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public PrincipalException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public PrincipalException(Throwable cause) {
		super(cause);
	}

	public static class MustBeAuthenticated extends PrincipalException {

		public MustBeAuthenticated(String userId) {
			super(String.format("User %s is not authenticated", userId));

			this.userId = userId;
		}

		public final String userId;

	}

	public static class MustBeCompanyAdmin extends PrincipalException {

		public MustBeCompanyAdmin(long userId) {
			super(
				String.format(
					"User %s must be the company administrator to perform " +
					"the action", userId));

			this.userId = userId;
		}

		public final long userId;

	}

	public static class MustBeEnabled extends PrincipalException {

		public MustBeEnabled(long companyId, String ... resourceName) {
			super(
				String.format(
					"%s must be enabled for company %s",
					StringUtil.merge(resourceName, ","), companyId));

			this.companyId = companyId;
			this.resourceName = resourceName;
		}

		public final long companyId;
		public final String[] resourceName;

	}

	public static class MustBeInvokedByPost extends PrincipalException {

		public MustBeInvokedByPost(String url) {
			super(String.format("URL %s must be invoked using POST", url));

			this.url = url;
		}

		public final String url;

	}

	public static class MustBeMarketplaceAdmin extends PrincipalException {

		public MustBeMarketplaceAdmin(long userId) {
			super(
				String.format(
					"User %s must be a marketplace administrator to perform " +
					"the action", userId));

			this.userId = userId;
		}

		public final long userId;

	}

	public static class MustBeOmniadmin extends PrincipalException {

		public MustBeOmniadmin(long userId) {
			super(
				String.format(
					"User %s must be a universal administrator to perform " +
					"the action", userId));

			this.userId = userId;
		}

		public final long userId;

	}

	public static class MustBeOwnedByCurrentUser extends PrincipalException {

		public MustBeOwnedByCurrentUser(
			long userId, String resourceName, long resourceId,
			String actionId) {
			
			super(
				String.format(
					"User %s must be the owner of %s %s to perform action %s",
					userId, resourceName, resourceId, actionId));

			this.actionId = actionId;
			this.resourceId = resourceId;
			this.resourceName = resourceName;
			this.userId = userId;
		}

		public final long userId;
		public final String resourceName;
		public final long resourceId;
		public final String actionId;

	}

	public static class MustBePortletStrutsPath extends PrincipalException {

		public MustBePortletStrutsPath(String strutsPath, String portletId) {
			super(
				String.format(
					"Struts path %s does not belong to portlet %s", strutsPath,
					portletId));

			this.strutsPath = strutsPath;
			this.portletId = portletId;
		}

		public final String portletId;
		public final String strutsPath;

	}

	public static class MustBeSupportedActionForRole
		extends PrincipalException {

		public MustBeSupportedActionForRole(long roleId, String actionId) {
			super(
				String.format(
					"Action %s is not supported by role %s", actionId, roleId));

			this.actionId = actionId;
			this.roleId = roleId;
		}

		public final String actionId;
		public final long roleId;

	}

	public static class MustBeValidPortlet extends PrincipalException {

		public MustBeValidPortlet(
			String currentPortletName, String validPortletName) {
			
			super(
				String.format(
					"Action is only supported in %s and is not allowed in %s",
					validPortletName, currentPortletName));

			this.currentPortletName = currentPortletName;
			this.validPortletName = validPortletName;
		}

		public final String currentPortletName;
		public final String validPortletName;

	}

	public static class MustHavePermission extends PrincipalException {

		public MustHavePermission(long userId, String ... actionId) {
			super(
				String.format(
					"User %s does not have permission to perform action %s",
					Validator.isNull(userId) ? "" : userId,
					StringUtil.merge(actionId, ",")));

			this.actionId = actionId;
			this.resourceId = 0;
			this.resourceName = null;
			this.userId = userId;
		}

		public MustHavePermission(
			long userId, String resourceName, long resourceId,
			String ... actionId) {

			super(
				String.format(
					"User %s does not have %s permission for %s %s",
					Validator.isNull(userId) ? "" : userId,
					StringUtil.merge(actionId, ","), resourceName,
					Validator.isNull(resourceId) ? "" : resourceId));

			this.actionId = actionId;
			this.resourceName = resourceName;
			this.resourceId = resourceId;
			this.userId = userId;
		}

		public final String[] actionId;
		public final long resourceId;
		public final String resourceName;
		public final long userId;

	}

	public static class MustHaveUserGroupRole extends PrincipalException {

		public MustHaveUserGroupRole(long userId, String role, long groupId) {
			super(
				String.format(
					"User %s does not have the required role %s for " +
					"group %s", Validator.isNull(userId) ? "" : userId, role,
					groupId));

			this.groupId = groupId;
			this.role = role;
			this.userId = userId;
		}

		public final long groupId;
		public final String role;
		public final long userId;

	}

	public static class MustHaveUserRole extends PrincipalException {

		public MustHaveUserRole(long userId, String role) {
			super(
				String.format(
					"User %s does not have the required role %s to " +
					"perform the action",
					Validator.isNull(userId) ? "" : userId, role));

			this.role = role;
			this.userId = userId;
		}

		public final String role;
		public final long userId;

	}

	public static class MustHaveValidPermissionChecker
		extends PrincipalException {

		public MustHaveValidPermissionChecker(
			long userId, PermissionChecker permissionChecker) {

			super(
				String.format(
					"Permission checker for user %s is not valid for current " +
						"user: %s",
					permissionChecker.getUserId(),
					Validator.isNull(userId) ? "" : userId));

			this.userId = userId;
		}

		public final long userId;

	}

	public static class MustHaveValidPortletId extends PrincipalException {

		public MustHaveValidPortletId(String portletId) {
			super(String.format("Portlet ID %s is invalid", portletId));

			this.portletId = portletId;
		}

		public final String portletId;

	}

	public static class MustHaveValidPrincipalName extends PrincipalException {

		public MustHaveValidPrincipalName(String name) {
			super(String.format("Principal name cannot be %s", name));

			this.name = name;
		}

		public final String name;

	}

	public static class MustInitializePermissionChecker
		extends PrincipalException {

		public MustInitializePermissionChecker() {
			super(
				String.format(
					"Permission checker must be initialized so that " +
						"permissions can be checked for this action"));
		}

		public MustInitializePermissionChecker(long userId, Exception e) {
			super(
				String.format(
					"Permission checker for user %s cannot be initialized",
					Validator.isNull(userId) ? "" : userId),
				e);
		}

	}

	public static class MustNotBeGroupAdmin extends PrincipalException {

		public MustNotBeGroupAdmin(
			long userId, String resourceName, long resourceId,
			String actionId) {
			
			super(
				String.format(
					"User %s is not allowed to perform action %s on the " +
						"group administrator in %s %s", userId, actionId,
						resourceName, resourceId));

			this.actionId = actionId;
			this.resourceId = resourceId;
			this.resourceName = resourceName;
			this.userId = userId;
		}

		public final String actionId;
		public final long resourceId;
		public final String resourceName;
		public final long userId;
	}

	private static final Class[] _NESTED_CLASSES = {
		MustBeAuthenticated.class, MustBeCompanyAdmin.class,
		MustBeEnabled.class, MustBeInvokedByPost.class,
		MustBeMarketplaceAdmin.class, MustBeOmniadmin.class,
		MustBeOwnedByCurrentUser.class, MustBePortletStrutsPath.class,
		MustBeSupportedActionForRole.class, MustBeValidPortlet.class,
		MustHavePermission.class, MustHaveUserGroupRole.class,
		MustHaveUserRole.class, MustHaveValidPermissionChecker.class,
		MustHaveValidPortletId.class, MustHaveValidPrincipalName.class,
		MustInitializePermissionChecker.class, MustNotBeGroupAdmin.class
	};

}