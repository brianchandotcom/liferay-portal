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

package com.liferay.layout.admin.web.internal.info.item.provider;

import com.liferay.info.exception.InfoItemPermissionException;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.layout.admin.web.internal.info.item.helper.LayoutInfoItemPermissionProviderHelper;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
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

		return _layoutInfoItemPermissionProviderHelper.hasPermission(
			permissionChecker, infoItemReference, actionId);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws InfoItemPermissionException {

		return _layoutInfoItemPermissionProviderHelper.hasPermission(
			permissionChecker, layout, actionId);
	}

	@Activate
	@Modified
	protected void activate() {
		_layoutInfoItemPermissionProviderHelper =
			new LayoutInfoItemPermissionProviderHelper(
				_layoutModelResourcePermission);
	}

	private volatile LayoutInfoItemPermissionProviderHelper
		_layoutInfoItemPermissionProviderHelper;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.Layout)"
	)
	private ModelResourcePermission<Layout> _layoutModelResourcePermission;

}