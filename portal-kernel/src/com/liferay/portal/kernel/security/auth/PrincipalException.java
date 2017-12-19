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

package com.liferay.portal.kernel.security.auth;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class PrincipalException extends PortalException {

	public static Class<?>[] getNestedClasses() {
		return _NESTED_CLASSES;
	}

	public PrincipalException() {
	}

	public PrincipalException(String msg) {
		super(msg);
	}

	public PrincipalException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PrincipalException(Throwable cause) {
		super(cause);
	}

	public static class MustBeAuthenticated extends PrincipalException {

		public MustBeAuthenticated(long userId) {
			this(String.valueOf(userId), null);
		}

		public MustBeAuthenticated(long userId, Throwable cause) {
			this(String.valueOf(userId), cause);
		}

		public MustBeAuthenticated(String login) {
			this(login, null);
		}

		public MustBeAuthenticated(String login, Throwable cause) {
			super(String.format("User %s must be authenticated", login), cause);

			this.login = login;
		}

		public final String login;

	}

	public static class MustBeCompanyAdmin extends PrincipalException {

		public MustBeCompanyAdmin(long userId) {
			super(
				String.format(
					"User %s must be the company administrator to perform " +
						"the action",
					userId));

			this.userId = userId;
		}

		public MustBeCompanyAdmin(PermissionChecker permissionChecker) {
			this(permissionChecker.getUserId());
		}

		public final long userId;

	}

	public static class MustBeEnabled extends PrincipalException {

		public MustBeEnabled(long companyId, String... resourceName) {
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

	public static class MustBeInvokedUsingPost extends PrincipalException {

		public MustBeInvokedUsingPost(String url) {
			super(String.format("URL %s must be invoked using POST", url));

			this.url = url;
		}

		public final String url;

	}

	public static class MustBeOmniadmin extends PrincipalException {

		public MustBeOmniadmin(long userId) {
			super(
				String.format(
					"User %s must be a universal administrator to perform " +
						"the action",
					userId));

			this.userId = userId;
		}

		public MustBeOmniadmin(PermissionChecker permissionChecker) {
			this(permissionChecker.getUserId());
		}

		public final long userId;

	}

	public static class MustBePortletStrutsPath extends PrincipalException {

		public MustBePortletStrutsPath(String strutsPath, String portletId) {
			super(
				String.format(
					"Struts path %s must be struts path of portlet %s",
					strutsPath, portletId));

			this.strutsPath = strutsPath;
			this.portletId = portletId;
		}

		public final String portletId;
		public final String strutsPath;

	}

	public static class MustHavePermission extends PrincipalException {

		public MustHavePermission(long userId, String... actionIds) {
			this(userId, null, 0, null, actionIds);
		}

		public MustHavePermission(
			long userId, String resourceName, long resourceId,
			String... actionIds) {

			this (userId, resourceName, resourceId, null, actionIds);
		}

		public MustHavePermission(
			long userId, String resourceName, long resourceId, Throwable cause,
			String... actionIds) {

			super(
				String.format(
					"User %s must have %s permission for %s %s", userId,
					StringUtil.merge(actionIds, ","), resourceName,
					(resourceId == 0) ? "" : resourceId),
				cause);

			this.userId = userId;
			this.resourceName = resourceName;
			this.resourceId = resourceId;

			actionId = actionIds;
		}

		public MustHavePermission(
			long userId, Throwable cause, String... actionIds) {

			this(userId, null, 0, cause, actionIds);
		}

		public MustHavePermission(
			PermissionChecker permissionChecker, String... actionIds) {

			this(permissionChecker.getUserId(), null, 0, null, actionIds);
		}

		public MustHavePermission(
			PermissionChecker permissionChecker, String resourceName,
			long resourceId, String... actionIds) {

			this(
				permissionChecker.getUserId(), resourceName, resourceId, null,
				actionIds);
		}

		public MustHavePermission(
			PermissionChecker permissionChecker, String resourceName,
			long resourceId, Throwable cause, String... actionIds) {

			this(
				permissionChecker.getUserId(), resourceName, resourceId, cause,
				actionIds);
		}

		public MustHavePermission(
			PermissionChecker permissionChecker, Throwable cause,
			String... actionIds) {

			this(permissionChecker.getUserId(), null, 0, cause, actionIds);
		}

		public final String[] actionId;
		public final long resourceId;
		public final String resourceName;
		public final long userId;

	}

	private static final Class<?>[] _NESTED_CLASSES = {
		PrincipalException.class, PrincipalException.MustBeAuthenticated.class,
		PrincipalException.MustBeCompanyAdmin.class,
		PrincipalException.MustBeEnabled.class,
		PrincipalException.MustBeInvokedUsingPost.class,
		PrincipalException.MustBeOmniadmin.class,
		PrincipalException.MustBePortletStrutsPath.class,
		PrincipalException.MustHavePermission.class
	};

}