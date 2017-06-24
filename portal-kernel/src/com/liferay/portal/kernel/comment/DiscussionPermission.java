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

package com.liferay.portal.kernel.comment;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Adolfo Pérez
 * @author Sergio González
 */
public interface DiscussionPermission {

	public void checkAddPermission(
			long companyId, long groupId, String className, long classPK)
		throws PortalException;

	public void checkDeletePermission(long commentId) throws PortalException;

	public void checkSubscribePermission(
			long companyId, long groupId, String className, long classPK)
		throws PortalException;

	public void checkUpdatePermission(long commentId) throws PortalException;

	public void checkViewPermission(
			long companyId, long groupId, String className, long classPK)
		throws PortalException;

	public boolean hasAddPermission(
			long companyId, long groupId, String className, long classPK)
		throws PortalException;

	public boolean hasDeletePermission(long commentId) throws PortalException;

	public default boolean hasPermission(Comment comment, String actionId)
		throws PortalException {

		return hasPermission(comment.getCommentId(), actionId);
	}

	public boolean hasPermission(long commentId, String actionId)
		throws PortalException;

	public boolean hasSubscribePermission(
			long companyId, long groupId, String className, long classPK)
		throws PortalException;

	public boolean hasUpdatePermission(long commentId) throws PortalException;

	public boolean hasViewPermission(
			long companyId, long groupId, String className, long classPK)
		throws PortalException;

}