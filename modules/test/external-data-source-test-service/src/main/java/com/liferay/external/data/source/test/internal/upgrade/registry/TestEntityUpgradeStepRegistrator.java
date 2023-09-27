/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.external.data.source.test.internal.upgrade.registry;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(service = UpgradeStepRegistrator.class)
public class TestEntityUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.1.0",
			new UpgradeProcess() {

				@Override
				protected void doUpgrade() throws Exception {
					runSQL("delete from TestEntity");
					runSQL(
						"insert into TestEntity (id_, data_) values (-1, " +
							"'Test Upgrade Value')");
				}

			});
	}

}