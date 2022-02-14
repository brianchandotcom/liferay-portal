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

package com.liferay.commerce.product.content.search.web.internal.facet.display.builder;

import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.permission.CPSpecificationOptionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Crescenzo Rega
 */
public class CPSpecificationOptionPermissionChecker {

	public CPSpecificationOptionPermissionChecker(
		PermissionChecker permissionChecker,
		CPSpecificationOptionPermission cpSpecificationOptionPermission) {

		_permissionChecker = permissionChecker;
		_cpSpecificationOptionPermission = cpSpecificationOptionPermission;
	}

	public boolean hasPermission(CPSpecificationOption cpSpecificationOption) {
		try {
			return _cpSpecificationOptionPermission.contains(
				_permissionChecker, cpSpecificationOption, ActionKeys.VIEW);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private final CPSpecificationOptionPermission
		_cpSpecificationOptionPermission;
	private final PermissionChecker _permissionChecker;

}