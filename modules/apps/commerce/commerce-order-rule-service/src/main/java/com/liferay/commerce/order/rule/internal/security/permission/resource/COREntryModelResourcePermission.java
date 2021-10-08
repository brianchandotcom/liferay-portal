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

package com.liferay.commerce.order.rule.internal.security.permission.resource;

import com.liferay.commerce.order.rule.constants.COREntryConstants;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.permission.COREntryPermission;
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
	property = "model.class.name=com.liferay.commerce.order.rule.model.COREntry",
	service = ModelResourcePermission.class
)
public class COREntryModelResourcePermission
	implements ModelResourcePermission<COREntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker, COREntry corEntry,
			String actionId)
		throws PortalException {

		_corEntryPermission.check(permissionChecker, corEntry, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long corEntryId,
			String actionId)
		throws PortalException {

		_corEntryPermission.check(permissionChecker, corEntryId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, COREntry corEntry,
			String actionId)
		throws PortalException {

		return _corEntryPermission.contains(
			permissionChecker, corEntry, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long corEntryId,
			String actionId)
		throws PortalException {

		return _corEntryPermission.contains(
			permissionChecker, corEntryId, actionId);
	}

	@Override
	public String getModelName() {
		return COREntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private COREntryPermission _corEntryPermission;

	@Reference(
		target = "(resource.name=" + COREntryConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}