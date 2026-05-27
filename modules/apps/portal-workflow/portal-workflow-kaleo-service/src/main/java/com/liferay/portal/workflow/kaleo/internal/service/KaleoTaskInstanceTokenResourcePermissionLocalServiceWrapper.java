/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import org.osgi.service.component.annotations.Component;

/**
 * @author Nícolas Moura
 */
@Component(service = ServiceWrapper.class)
public class KaleoTaskInstanceTokenResourcePermissionLocalServiceWrapper
	extends ResourcePermissionLocalServiceWrapper {

	@Override
	public boolean hasResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId,
			String actionId)
		throws PortalException {

		if (!StringUtil.startsWith(
				name, KaleoTaskInstanceToken.class.getName())) {

			return super.hasResourcePermission(
				companyId, name, scope, primKey, roleId, actionId);
		}

		return false;
	}

	@Override
	public boolean hasResourcePermission(
			long companyId, String name, int scope, String primKey,
			long[] roleIds, String actionId)
		throws PortalException {

		if (!StringUtil.startsWith(
				name, KaleoTaskInstanceToken.class.getName())) {

			return super.hasResourcePermission(
				companyId, name, scope, primKey, roleIds, actionId);
		}

		return false;
	}

}
