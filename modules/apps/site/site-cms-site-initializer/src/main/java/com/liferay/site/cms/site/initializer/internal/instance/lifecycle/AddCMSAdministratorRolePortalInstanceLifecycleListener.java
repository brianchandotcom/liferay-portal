/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.site.cms.site.initializer.internal.util.CMSRoleUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AddCMSAdministratorRolePortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled(
				company.getCompanyId(), "LPD-17564")) {

			return;
		}

		CMSRoleUtil.getOrAddCMSAdministratorRoleAndPermissions(
			company.getCompanyId());
	}

}