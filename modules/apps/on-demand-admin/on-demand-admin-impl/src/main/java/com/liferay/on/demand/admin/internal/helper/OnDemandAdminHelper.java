/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.on.demand.admin.internal.helper;

import com.liferay.on.demand.admin.constants.OnDemandAdminActionKeys;
import com.liferay.on.demand.admin.constants.OnDemandAdminPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.util.PortalInstances;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = OnDemandAdminHelper.class)
public class OnDemandAdminHelper {

	public void checkRequestAdministratorAccessPermission(
			long companyId, long userId)
		throws PortalException {

		if (companyId == PortalInstances.getDefaultCompanyId()) {
			throw new PrincipalException(
				"Target company must not be the default company");
		}

		User user = _userLocalService.getUser(userId);

		if (user.getCompanyId() != PortalInstances.getDefaultCompanyId()) {
			throw new PrincipalException(
				"Request can only be made from the default company");
		}

		if (!_portletPermission.contains(
				PermissionCheckerFactoryUtil.create(user),
				GroupConstants.DEFAULT_LIVE_GROUP_ID,
				LayoutConstants.DEFAULT_PLID,
				OnDemandAdminPortletKeys.ON_DEMAND_ADMIN,
				OnDemandAdminActionKeys.REQUEST_ADMINISTRATOR_ACCESS, true)) {

			throw new PrincipalException.MustHavePermission(
				userId, OnDemandAdminActionKeys.REQUEST_ADMINISTRATOR_ACCESS);
		}
	}

	@Reference
	private PortletPermission _portletPermission;

	@Reference
	private UserLocalService _userLocalService;

}