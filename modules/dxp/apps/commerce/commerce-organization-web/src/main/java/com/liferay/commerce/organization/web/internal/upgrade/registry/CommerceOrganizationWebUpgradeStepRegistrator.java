/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.organization.web.internal.upgrade.registry;

import com.liferay.commerce.organization.web.internal.upgrade.v1_0_1.CommerceOrganizationPortletPreferencesUpgradeProcess;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Fabio Monaco
 */
@Component(service = UpgradeStepRegistrator.class)
public class CommerceOrganizationWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"1.0.0", "1.0.1",
			new CommerceOrganizationPortletPreferencesUpgradeProcess(
				_organizationLocalService));
	}

	@Reference
	private OrganizationLocalService _organizationLocalService;

}