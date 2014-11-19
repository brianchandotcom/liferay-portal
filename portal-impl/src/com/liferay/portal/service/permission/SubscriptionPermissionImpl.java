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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerUtil;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;

/**
 * @author Mate Thurzo
 * @author Raymond Augé
 */
public class SubscriptionPermissionImpl implements SubscriptionPermission {

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #check(PermissionChecker,
	 *             String, long, String, long)}
	 */
	@Deprecated
	@Override
	public void check(
			PermissionChecker permissionChecker, String className, long classPK)
		throws PortalException {

		check(permissionChecker, className, classPK, null, 0);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, String subscriptionClassName,
			long subscriptionClassPK, String inferredClassName,
			long inferredClassPK)
		throws PortalException {

		if (!contains(
				permissionChecker, subscriptionClassName, subscriptionClassPK,
				inferredClassName, inferredClassPK)) {

			throw new PrincipalException();
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #contains(PermissionChecker,
	 *             String, long, String, long)}
	 */
	@Deprecated
	@Override
	public boolean contains(
			PermissionChecker permissionChecker, String className, long classPK)
		throws PortalException {

		return contains(permissionChecker, className, classPK, null, 0);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, String subscriptionClassName,
			long subscriptionClassPK, String inferredClassName,
			long inferredClassPK)
		throws PortalException {

		if (subscriptionClassName == null) {
			return false;
		}

		if (Validator.isNotNull(inferredClassName)) {
			Boolean hasPermission = hasPermission(
				permissionChecker, inferredClassName, inferredClassPK,
				ActionKeys.VIEW);

			if ((hasPermission == null) || !hasPermission) {
				return false;
			}
		}

		Boolean hasPermission = hasPermission(
			permissionChecker, subscriptionClassName, subscriptionClassPK,
			ActionKeys.SUBSCRIBE);

		if (hasPermission != null) {
			return hasPermission;
		}

		return true;
	}

	protected Boolean hasPermission(
			PermissionChecker permissionChecker, String className, long classPK,
			String actionId)
		throws PortalException {

		MBDiscussion mbDiscussion =
			MBDiscussionLocalServiceUtil.fetchDiscussion(className, classPK);

		if (mbDiscussion != null) {
			if (className.equals(Layout.class.getName())) {
				return LayoutPermissionUtil.contains(
					permissionChecker, classPK, ActionKeys.VIEW);
			}

			return MBDiscussionPermission.contains(
				permissionChecker, mbDiscussion.getCompanyId(),
				mbDiscussion.getGroupId(), className, classPK,
				mbDiscussion.getUserId(), ActionKeys.VIEW);
		}

		return PermissionCheckerUtil.containsResourcePermission(
			permissionChecker, className, classPK, actionId);
	}

}