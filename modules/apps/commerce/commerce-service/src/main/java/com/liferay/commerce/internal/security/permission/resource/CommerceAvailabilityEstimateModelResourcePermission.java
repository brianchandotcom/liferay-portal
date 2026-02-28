/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.security.permission.resource;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.permission.CommerceAvailabilityEstimatePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(
	property = "model.class.name=com.liferay.commerce.model.CommerceAvailabilityEstimate",
	service = ModelResourcePermission.class
)
public class CommerceAvailabilityEstimateModelResourcePermission
	implements ModelResourcePermission<CommerceAvailabilityEstimate> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceAvailabilityEstimate commerceAvailabilityEstimate,
			String actionId)
		throws PortalException {

		_commerceAvailabilityEstimatePermission.check(
			permissionChecker, commerceAvailabilityEstimate, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commerceAvailabilityEstimateId, String actionId)
		throws PortalException {

		_commerceAvailabilityEstimatePermission.check(
			permissionChecker, commerceAvailabilityEstimateId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceAvailabilityEstimate commerceAvailabilityEstimate,
			String actionId)
		throws PortalException {

		return _commerceAvailabilityEstimatePermission.contains(
			permissionChecker, commerceAvailabilityEstimate, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commerceAvailabilityEstimateId, String actionId)
		throws PortalException {

		return _commerceAvailabilityEstimatePermission.contains(
			permissionChecker, commerceAvailabilityEstimateId, actionId);
	}

	@Override
	public String getModelName() {
		return CommerceAvailabilityEstimate.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private CommerceAvailabilityEstimatePermission
		_commerceAvailabilityEstimatePermission;

	@Reference(
		target = "(resource.name=" + CommerceConstants.RESOURCE_NAME_COMMERCE_AVAILABILITY + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}