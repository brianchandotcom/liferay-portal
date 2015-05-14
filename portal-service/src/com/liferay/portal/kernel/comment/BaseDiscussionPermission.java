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
import com.liferay.portal.security.auth.PrincipalException;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseDiscussionPermission implements DiscussionPermission {

	@Override
	public void checkAddPermission(
			long companyId, long groupId, String className, long classPK,
			long userId)
		throws PortalException {

		if (!hasAddPermission(companyId, groupId, className, classPK, userId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void checkDeletePermission(
			String className, long classPK, long commentId, long userId)
		throws PortalException {

		if (!hasDeletePermission(className, classPK, commentId, userId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void checkUpdatePermission(
			String className, long classPK, long commentId, long userId)
		throws PortalException {

		if (!hasUpdatePermission(className, classPK, commentId, userId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void checkViewPermission(
			long companyId, long groupId, String className, long classPK,
			long userId)
		throws PortalException {

		if (!hasViewPermission(
				companyId, groupId, className, classPK, userId)) {

			throw new PrincipalException();
		}
	}

}