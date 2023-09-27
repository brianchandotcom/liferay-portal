/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.web.internal.upgrade.registry;

import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 * @author Brian Wing Shun Chan
 */
@Component(service = UpgradeStepRegistrator.class)
public class ClientExtensionWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0",
			new com.liferay.client.extension.web.internal.upgrade.v1_0_0.
				UpgradePortletId());

		registry.register(
			"1.0.0", "2.0.0",
			new UpgradeProcess() {

				@Override
				protected void doUpgrade() throws Exception {
					runSQL(
						"delete from Release_ where servletContextName = " +
							"'com.liferay.remote.app.web'");
				}

			},
			new com.liferay.client.extension.web.internal.upgrade.v2_0_0.
				UpgradePortletId());

		registry.register("2.0.0", "3.0.0", new DummyUpgradeStep());

		registry.register(
			"3.0.0", "3.0.1",
			new com.liferay.client.extension.web.internal.upgrade.v3_0_1.
				UpgradePortletId());
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.client.extension.service)(release.schema.version>=3.0.0))"
	)
	private Release _release;

}