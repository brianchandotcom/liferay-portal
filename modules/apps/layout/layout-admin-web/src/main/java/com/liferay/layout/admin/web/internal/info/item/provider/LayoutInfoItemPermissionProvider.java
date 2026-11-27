/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.info.item.provider;

import com.liferay.info.exception.InfoItemPermissionException;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.layout.admin.web.internal.util.LayoutInfoItemPermissionProviderUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = InfoItemPermissionProvider.class)
public class LayoutInfoItemPermissionProvider
	implements InfoItemPermissionProvider<Layout> {

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker,
			InfoItemReference infoItemReference, String actionId)
		throws InfoItemPermissionException {

		return LayoutInfoItemPermissionProviderUtil.hasPermission(
			actionId, infoItemReference, _layoutModelResourcePermission,
			permissionChecker);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws InfoItemPermissionException {

		return LayoutInfoItemPermissionProviderUtil.hasPermission(
			actionId, layout, _layoutModelResourcePermission,
			permissionChecker);
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.Layout)"
	)
	private ModelResourcePermission<Layout> _layoutModelResourcePermission;

}