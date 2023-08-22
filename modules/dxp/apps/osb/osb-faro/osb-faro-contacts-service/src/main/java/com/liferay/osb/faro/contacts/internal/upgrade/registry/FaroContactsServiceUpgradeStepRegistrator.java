/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.contacts.internal.upgrade.registry;

import com.liferay.osb.faro.contacts.internal.upgrade.v2_0_0.UpgradeContactsCriterionUpgradeProcess;
import com.liferay.osb.faro.contacts.internal.upgrade.v2_1_0.UpgradeCompanyId;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Kong
 */
@Component(service = UpgradeStepRegistrator.class)
public class FaroContactsServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "2.0.0", new UpgradeContactsCriterionUpgradeProcess());

		registry.register(
			"2.0.0", "2.1.0", new UpgradeCompanyId(),
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getTableNames() {
					return new String[] {
						"OSBFaro_ContactsCardTemplate",
						"OSBFaro_ContactsLayoutTemplate"
					};
				}

			});
	}

}