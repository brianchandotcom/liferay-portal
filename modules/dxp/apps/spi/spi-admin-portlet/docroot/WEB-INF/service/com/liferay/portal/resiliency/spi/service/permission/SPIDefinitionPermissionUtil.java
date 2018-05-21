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
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;

/**
 * @author Michael C. Han
 */
public class SPIDefinitionPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long spiDefinitionId,
			String actionId)
		throws PortalException {

		getSpiDefinitionPermission().check(
			permissionChecker, spiDefinitionId, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, SPIDefinition spiDefinition,
			String actionId)
		throws PortalException {

		getSpiDefinitionPermission().check(
			permissionChecker, spiDefinition, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException {

		getSpiDefinitionPermission().check(permissionChecker, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long spiDefinitionId,
			String actionId)
		throws PortalException {

		return getSpiDefinitionPermission().contains(
			permissionChecker, spiDefinitionId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, SPIDefinition spiDefinition,
		String actionId) {

		return getSpiDefinitionPermission().contains(
			permissionChecker, spiDefinition, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, String actionId) {

		return getSpiDefinitionPermission().contains(
			permissionChecker, actionId);
	}

	public static SPIDefinitionPermission getSpiDefinitionPermission() {
		PortalRuntimePermission.checkGetBeanProperty(
			SPIDefinitionPermissionUtil.class);

		return _spiDefinitionPermission;
	}

	public void setSPIDefinitionPermission(
		SPIDefinitionPermission spiDefinitionPermission) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_spiDefinitionPermission = spiDefinitionPermission;
	}

	private static SPIDefinitionPermission _spiDefinitionPermission;

}