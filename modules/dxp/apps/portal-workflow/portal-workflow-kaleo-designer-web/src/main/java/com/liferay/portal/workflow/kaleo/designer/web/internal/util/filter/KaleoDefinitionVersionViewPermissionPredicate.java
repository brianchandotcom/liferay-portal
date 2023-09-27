/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.designer.web.internal.util.filter;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.workflow.kaleo.designer.web.internal.permission.KaleoDefinitionVersionPermission;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import java.util.function.Predicate;

/**
 * @author Lino Alves
 */
public class KaleoDefinitionVersionViewPermissionPredicate
	implements Predicate<KaleoDefinitionVersion> {

	public KaleoDefinitionVersionViewPermissionPredicate(
		PermissionChecker permissionChecker, long companyGroupId) {

		_permissionChecker = permissionChecker;
		_companyGroupId = companyGroupId;
	}

	@Override
	public boolean test(KaleoDefinitionVersion kaleoDefinitionVersion) {
		try {
			return KaleoDefinitionVersionPermission.hasViewPermission(
				_permissionChecker, kaleoDefinitionVersion, _companyGroupId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDefinitionVersionViewPermissionPredicate.class);

	private final long _companyGroupId;
	private final PermissionChecker _permissionChecker;

}