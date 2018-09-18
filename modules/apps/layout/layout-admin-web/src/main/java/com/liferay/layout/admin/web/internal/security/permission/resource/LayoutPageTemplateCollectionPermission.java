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

package com.liferay.layout.admin.web.internal.security.permission.resource;

import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class LayoutPageTemplateCollectionPermission {

	public static boolean contains(
			PermissionChecker permissionChecker,
			LayoutPageTemplateCollection layoutPageTemplateCollection,
			String actionId)
		throws PortalException {

		return _layoutPageTemplateCollectionModelResourcePermission.contains(
			permissionChecker, layoutPageTemplateCollection, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			long layoutPageTemplateCollectionId, String actionId)
		throws PortalException {

		return _layoutPageTemplateCollectionModelResourcePermission.contains(
			permissionChecker, layoutPageTemplateCollectionId, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateCollection)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<LayoutPageTemplateCollection>
			modelResourcePermission) {

		_layoutPageTemplateCollectionModelResourcePermission =
			modelResourcePermission;
	}

	private static ModelResourcePermission<LayoutPageTemplateCollection>
		_layoutPageTemplateCollectionModelResourcePermission;

}