/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * Retrieves and checks permissions with respect to subscriptions.
 *
 * @author Mate Thurzo
 * @author Raymond Augé
 */
public interface SubscriptionPermission {

	/**
	 * Performs a security check determining if a user can subscribe or receive
	 * notifications about subscribed entities.
	 *
	 * <p>
	 * A view permission check is performed on the inferred entity which is the
	 * subject of the notification when the subscribed entity is a container
	 * entity. A failed view check on the inferred entity will prevent the
	 * notification from being sent. This is useful for enforcing a private
	 * subtree within a larger container to which a user is subscribed.
	 * </p>
	 *
	 * @param  permissionChecker the permission checker
	 * @param  subscriptionClassName the class name of the subscribed entity
	 * @param  subscriptionClassPK the primary key of the subscribed entity
	 * @param  inferredClassName the class name of the inferred entity when the
	 *         subscribed entity is a container entity
	 * @param  inferredClassPK the primary key of the inferred entity when the
	 *         subscribed entity is a container entity
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void check(
			PermissionChecker permissionChecker, String subscriptionClassName,
			long subscriptionClassPK, String inferredClassName,
			long inferredClassPK)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if a user can subscribe or receive
	 * notifications about subscribed entities.
	 *
	 * @param  permissionChecker the permission checker
	 * @param  subscriptionClassName the class name of the subscribed entity
	 * @param  subscriptionClassPK the primary key of the subscribed entity
	 * @param  inferredClassName the class name of the inferred entity when the
	 *         subscribed entity is a container entity
	 * @param  inferredClassPK the primary key of the inferred entity when the
	 *         subscribed entity is a container entity
	 * @return <code>true</code> if a user can subscribe or receive
	 *         notifications about subscribed entities; <code>false</code>
	 *         otherwise
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 * @see    #check(PermissionChecker, String, long, String, long)
	 */
	public boolean contains(
			PermissionChecker permissionChecker, String subscriptionClassName,
			long subscriptionClassPK, String inferredClassName,
			long inferredClassPK)
		throws PortalException, SystemException;

}