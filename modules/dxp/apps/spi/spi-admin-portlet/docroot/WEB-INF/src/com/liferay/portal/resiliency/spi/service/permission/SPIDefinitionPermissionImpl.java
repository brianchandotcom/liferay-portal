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
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;
import com.liferay.portal.resiliency.spi.service.SPIDefinitionLocalServiceUtil;

/**
 * @author Michael C. Han
 */
public class SPIDefinitionPermissionImpl implements SPIDefinitionPermission {

	public static final String RESOURCE_NAME =
		"com.liferay.portal.resiliency.spi";

	@Override
	public void check(
			PermissionChecker permissionChecker, long spiDefinitionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, spiDefinitionId, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, SPIDefinition spiDefinition,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, spiDefinition, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(PermissionChecker permissionChecker, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long spiDefinitionId,
			String actionId)
		throws PortalException {

		SPIDefinition spiDefinition =
			SPIDefinitionLocalServiceUtil.getSPIDefinition(spiDefinitionId);

		return contains(permissionChecker, spiDefinition, actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, SPIDefinition spiDefinition,
		String actionId) {

		return permissionChecker.hasPermission(
			null, SPIDefinition.class.getName(),
			spiDefinition.getSpiDefinitionId(), actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, String actionId) {

		return permissionChecker.hasPermission(
			null, RESOURCE_NAME, 0, actionId);
	}

}