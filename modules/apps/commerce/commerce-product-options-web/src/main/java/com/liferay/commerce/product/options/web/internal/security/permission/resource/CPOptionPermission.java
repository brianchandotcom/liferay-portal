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

package com.liferay.commerce.product.options.web.internal.security.permission.resource;

import com.liferay.commerce.product.model.CPOption;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = {})
public class CPOptionPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, CPOption cpOption,
			String actionId)
		throws PortalException {

		return _cpOptionModelResourcePermission.contains(
			permissionChecker, cpOption, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long cpOptionId,
			String actionId)
		throws PortalException {

		return _cpOptionModelResourcePermission.contains(
			permissionChecker, cpOptionId, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPOption)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<CPOption> modelResourcePermission) {

		_cpOptionModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<CPOption>
		_cpOptionModelResourcePermission;

}