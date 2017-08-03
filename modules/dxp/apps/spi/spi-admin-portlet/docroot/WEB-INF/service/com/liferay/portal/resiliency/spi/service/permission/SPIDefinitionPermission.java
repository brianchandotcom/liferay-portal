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

package com.liferay.portal.resiliency.spi.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;

/**
 * @author Michael C. Han
 */
public interface SPIDefinitionPermission {

	public void check(
			PermissionChecker permissionChecker, long spiDefinitionId,
			String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, SPIDefinition spiDefinition,
			String actionId)
		throws PortalException;

	public void check(PermissionChecker permissionChecker, String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long spiDefinitionId,
			String actionId)
		throws PortalException;

	public boolean contains(
		PermissionChecker permissionChecker, SPIDefinition spiDefinition,
		String actionId);

	public boolean contains(
		PermissionChecker permissionChecker, String actionId);

}