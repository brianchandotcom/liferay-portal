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