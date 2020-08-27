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

package com.liferay.commerce.bom.internal.permission;

import com.liferay.commerce.bom.model.CommerceBOMDefinition;
import com.liferay.commerce.bom.permission.CommerceBOMDefinitionPermission;
import com.liferay.commerce.bom.service.CommerceBOMDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CommerceBOMDefinitionPermission.class)
public class CommerceBOMDefinitionPermissionImpl
	implements CommerceBOMDefinitionPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceBOMDefinition commerceBOMDefinition, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceBOMDefinition, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceBOMDefinition.class.getName(),
				commerceBOMDefinition.getCommerceBOMDefinitionId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceBOMDefinitionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceBOMDefinitionId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceBOMDefinition.class.getName(),
				commerceBOMDefinitionId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceBOMDefinition commerceBOMDefinition, String actionId)
		throws PortalException {

		if (contains(
				permissionChecker,
				commerceBOMDefinition.getCommerceBOMDefinitionId(), actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceBOMDefinitionId,
			String actionId)
		throws PortalException {

		CommerceBOMDefinition commerceBOMDefinition =
			_commerceBOMDefinitionLocalService.fetchCommerceBOMDefinition(
				commerceBOMDefinitionId);

		if (commerceBOMDefinition == null) {
			return false;
		}

		return _contains(permissionChecker, commerceBOMDefinition, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long[] commerceBOMDefinitionIds, String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(commerceBOMDefinitionIds)) {
			return false;
		}

		for (long commerceBOMDefinitionId : commerceBOMDefinitionIds) {
			if (!contains(
					permissionChecker, commerceBOMDefinitionId, actionId)) {

				return false;
			}
		}

		return true;
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			CommerceBOMDefinition commerceBOMDefinition, String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(
				commerceBOMDefinition.getCompanyId()) ||
			permissionChecker.isOmniadmin()) {

			return true;
		}

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(),
				CommerceBOMDefinition.class.getName(),
				commerceBOMDefinition.getCommerceBOMDefinitionId(),
				permissionChecker.getUserId(), actionId) &&
			(commerceBOMDefinition.getUserId() ==
				permissionChecker.getUserId())) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, CommerceBOMDefinition.class.getName(),
			commerceBOMDefinition.getCommerceBOMDefinitionId(), actionId);
	}

	@Reference
	private CommerceBOMDefinitionLocalService
		_commerceBOMDefinitionLocalService;

}