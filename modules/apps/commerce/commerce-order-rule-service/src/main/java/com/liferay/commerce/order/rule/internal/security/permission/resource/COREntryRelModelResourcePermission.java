/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.internal.security.permission.resource;

import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.permission.COREntryPermission;
import com.liferay.commerce.order.rule.service.COREntryRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	property = "model.class.name=com.liferay.commerce.order.rule.model.COREntryRel",
	service = ModelResourcePermission.class
)
public class COREntryRelModelResourcePermission
	implements ModelResourcePermission<COREntryRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker, COREntryRel corEntryRel,
			String actionId)
		throws PortalException {

		_corEntryPermission.check(
			permissionChecker, corEntryRel.getCOREntryId(), actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long corEntryRelId,
			String actionId)
		throws PortalException {

		COREntryRel corEntryRel = _corEntryRelLocalService.getCOREntryRel(
			corEntryRelId);

		_corEntryPermission.check(
			permissionChecker, corEntryRel.getCOREntryId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, COREntryRel corEntryRel,
			String actionId)
		throws PortalException {

		return _corEntryPermission.contains(
			permissionChecker, corEntryRel.getCOREntryId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long corEntryRelId,
			String actionId)
		throws PortalException {

		COREntryRel corEntryRel = _corEntryRelLocalService.getCOREntryRel(
			corEntryRelId);

		return _corEntryPermission.contains(
			permissionChecker, corEntryRel.getCOREntryId(), actionId);
	}

	@Override
	public String getModelName() {
		return COREntryRel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	private COREntryPermission _corEntryPermission;

	@Reference
	private COREntryRelLocalService _corEntryRelLocalService;

}