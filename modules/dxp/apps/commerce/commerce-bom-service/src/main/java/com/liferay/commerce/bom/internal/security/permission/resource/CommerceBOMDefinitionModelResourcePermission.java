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

package com.liferay.commerce.bom.internal.security.permission.resource;

import com.liferay.commerce.bom.model.CommerceBOMDefinition;
import com.liferay.commerce.bom.permission.CommerceBOMDefinitionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "model.class.name=com.liferay.commerce.bom.model.CommerceBOMDefinition",
	service = ModelResourcePermission.class
)
public class CommerceBOMDefinitionModelResourcePermission
	implements ModelResourcePermission<CommerceBOMDefinition> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceBOMDefinition commerceBOMDefinition, String actionId)
		throws PortalException {

		commerceBOMDefinitionPermission.check(
			permissionChecker, commerceBOMDefinition, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceBOMDefinitionId,
			String actionId)
		throws PortalException {

		commerceBOMDefinitionPermission.check(
			permissionChecker, commerceBOMDefinitionId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceBOMDefinition commerceBOMDefinition, String actionId)
		throws PortalException {

		return commerceBOMDefinitionPermission.contains(
			permissionChecker, commerceBOMDefinition, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceBOMDefinitionId,
			String actionId)
		throws PortalException {

		return commerceBOMDefinitionPermission.contains(
			permissionChecker, commerceBOMDefinitionId, actionId);
	}

	@Override
	public String getModelName() {
		return CommerceBOMDefinition.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	protected CommerceBOMDefinitionPermission commerceBOMDefinitionPermission;

	@Reference(target = "(resource.name=com.liferay.commerce.bom)")
	private PortletResourcePermission _portletResourcePermission;

}