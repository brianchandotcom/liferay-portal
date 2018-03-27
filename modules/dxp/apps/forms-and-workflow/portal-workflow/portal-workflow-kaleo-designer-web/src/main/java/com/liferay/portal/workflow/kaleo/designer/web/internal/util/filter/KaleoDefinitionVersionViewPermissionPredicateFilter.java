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

package com.liferay.portal.workflow.kaleo.designer.web.internal.util.filter;

import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.workflow.kaleo.designer.web.internal.permission.KaleoDefinitionVersionPermission;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

/**
 * @author Lino Alves
 */
public class KaleoDefinitionVersionViewPermissionPredicateFilter
	implements PredicateFilter<KaleoDefinitionVersion> {

	public KaleoDefinitionVersionViewPermissionPredicateFilter(
		PermissionChecker permissionChecker) {

		_permissionChecker = permissionChecker;
	}

	@Override
	public boolean filter(KaleoDefinitionVersion kaleoDefinitionVersion) {
		return KaleoDefinitionVersionPermission.contains(
			_permissionChecker, kaleoDefinitionVersion, ActionKeys.VIEW);
	}

	private final PermissionChecker _permissionChecker;

}