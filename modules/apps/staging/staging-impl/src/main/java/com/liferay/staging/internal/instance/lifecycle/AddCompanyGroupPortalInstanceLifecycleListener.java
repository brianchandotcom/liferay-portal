/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.internal.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.internal.constants.CompanyGroupConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AddCompanyGroupPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		Group group = _groupLocalService.fetchFriendlyURLGroup(
			company.getCompanyId(), CompanyGroupConstants.FRIENDLY_URL);

		if (group != null) {
			return;
		}

		_groupLocalService.addGroup(
			_userLocalService.getGuestUserId(company.getCompanyId()),
			GroupConstants.DEFAULT_PARENT_GROUP_ID,
			StagingGroupHelper.class.getName(), company.getCompanyId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, null, null,
			GroupConstants.TYPE_SITE_RESTRICTED, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			CompanyGroupConstants.FRIENDLY_URL, false, true, null);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private UserLocalService _userLocalService;

}