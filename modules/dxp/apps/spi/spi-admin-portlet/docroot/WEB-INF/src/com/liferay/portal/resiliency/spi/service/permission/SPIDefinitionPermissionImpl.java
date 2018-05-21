/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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