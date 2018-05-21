/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.designer.web.internal.util.filter;

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
		PermissionChecker permissionChecker, long companyGroupId) {

		_permissionChecker = permissionChecker;
		_companyGroupId = companyGroupId;
	}

	@Override
	public boolean filter(KaleoDefinitionVersion kaleoDefinitionVersion) {
		return KaleoDefinitionVersionPermission.hasViewPermission(
			_permissionChecker, kaleoDefinitionVersion, _companyGroupId);
	}

	private final long _companyGroupId;
	private final PermissionChecker _permissionChecker;

}