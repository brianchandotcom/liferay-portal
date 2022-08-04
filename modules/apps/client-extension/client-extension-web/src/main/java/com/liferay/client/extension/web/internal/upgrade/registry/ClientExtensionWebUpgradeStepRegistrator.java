/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.client.extension.web.internal.upgrade.registry;

import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class ClientExtensionWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.0", "1.0.0",
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

		registry.register(
			"2.0.0", "3.0.0",
			new com.liferay.client.extension.web.internal.upgrade.v3_0_0.
				UpgradePortletId());
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.client.extension.service)(release.schema.version>=3.0.0))"
	)
	private Release _release;

}