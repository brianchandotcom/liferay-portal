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

package com.liferay.commerce.product.internal.security.permission.resource;

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.permission.CommerceChannelPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.commerce.product.model.CommerceChannel",
	service = ModelResourcePermission.class
)
public class CommerceChannelModelResourcePermission
	implements ModelResourcePermission<CommerceChannel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceChannel commerceChannel, String actionId)
		throws PortalException {

		commerceChannelPermission.check(
			permissionChecker, commerceChannel, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceChannelId,
			String actionId)
		throws PortalException {

		commerceChannelPermission.check(
			permissionChecker, commerceChannelId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceChannel commerceChannel, String actionId)
		throws PortalException {

		return commerceChannelPermission.contains(
			permissionChecker, commerceChannel, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceChannelId,
			String actionId)
		throws PortalException {

		return commerceChannelPermission.contains(
			permissionChecker, commerceChannelId, actionId);
	}

	@Override
	public String getModelName() {
		return CommerceChannel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommerceChannelPermission commerceChannelPermission;

}