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

package com.liferay.client.extension.internal.upgrade;

import com.liferay.client.extension.internal.upgrade.v3_0_0.ClassNamesUpgradeProcess;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class ClientExtensionServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.0.1",
			new com.liferay.client.extension.internal.upgrade.v1_0_1.
				RemoteAppEntryUpgradeProcess());

		registry.register(
			"1.0.1", "2.0.0",
			new com.liferay.client.extension.internal.upgrade.v2_0_0.
				RemoteAppEntryUpgradeProcess());

		registry.register(
			"2.0.0", "2.1.0",
			new com.liferay.client.extension.internal.upgrade.v2_1_0.
				ResourcePermissionsUpgradeProcess());

		registry.register(
			"2.1.0", "2.2.0",
			new com.liferay.client.extension.internal.upgrade.v2_2_0.
				RemoteAppEntryUpgradeProcess());

		registry.register(
			"2.2.0", "2.3.0",
			new com.liferay.client.extension.internal.upgrade.v2_3_0.
				RemoteAppEntryUpgradeProcess());

		registry.register(
			"2.3.0", "2.4.0",
			new com.liferay.client.extension.internal.upgrade.v2_4_0.
				RemoteAppEntryUpgradeProcess());

		registry.register(
			"2.4.0", "2.5.0",
			new com.liferay.client.extension.internal.upgrade.v2_5_0.
				RemoteAppEntryUpgradeProcess());

		registry.register(
			"2.5.0", "3.0.0", new ClassNamesUpgradeProcess(),
			new com.liferay.client.extension.internal.upgrade.v3_0_0.
				ClientExtensionEntryUpgradeProcess());

		registry.register(
			"3.0.0", "3.1.0", new ClassNamesUpgradeProcess(),
			new com.liferay.client.extension.internal.upgrade.v3_1_0.
				ClientExtensionEntryUpgradeProcess());

		registry.register(
			"3.1.0", "3.2.0",
			new CTModelUpgradeProcess(
				"ClientExtensionEntry", "ClientExtensionEntryRel"));
	}

	@Reference(
		target = "(!(release.bundle.symbolic.name=com.liferay.remote.app.service))"
	)
	private Release _release;

}