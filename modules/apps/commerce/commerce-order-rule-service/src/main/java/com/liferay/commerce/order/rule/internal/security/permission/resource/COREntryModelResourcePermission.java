/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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