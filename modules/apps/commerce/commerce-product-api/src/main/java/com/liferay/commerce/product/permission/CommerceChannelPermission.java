/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.permission;

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Alec Sloan
 */
public interface CommerceChannelPermission {

	public void check(
			PermissionChecker permissionChecker,
			CommerceChannel commerceChannel, String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long commerceChannelId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceChannel commerceChannel, String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long commerceChannelId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long[] commerceChannelIds,
			String actionId)
		throws PortalException;

}