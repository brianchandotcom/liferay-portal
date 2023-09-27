/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.permission.LayoutPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vagner B.C
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.Layout",
	service = ModelResourcePermission.class
)
public class LayoutModelResourcePermission
	implements ModelResourcePermission<Layout> {

	@Override
	public void check(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException {

		_layoutPermission.check(permissionChecker, layout, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long vocabularyId,
			String actionId)
		throws PortalException {

		_layoutPermission.check(permissionChecker, vocabularyId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException {

		return _layoutPermission.contains(permissionChecker, layout, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long vocabularyId,
			String actionId)
		throws PortalException {

		return _layoutPermission.contains(
			permissionChecker, vocabularyId, actionId);
	}

	@Override
	public String getModelName() {
		return Layout.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	private LayoutPermission _layoutPermission;

}