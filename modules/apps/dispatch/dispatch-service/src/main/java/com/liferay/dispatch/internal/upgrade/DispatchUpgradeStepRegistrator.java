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

package com.liferay.dispatch.internal.upgrade;

import com.liferay.dispatch.internal.upgrade.v2_0_0.DispatchTriggerUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class DispatchUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "2.0.0", new DispatchTriggerUpgradeProcess());

		registry.register(
			"2.0.0", "2.1.0",
			new com.liferay.dispatch.internal.upgrade.v2_1_0.
				DispatchTriggerUpgradeProcess());

		registry.register(
			"2.1.0", "3.0.0",
			new com.liferay.dispatch.internal.upgrade.v3_0_0.
				DispatchTriggerUpgradeProcess());

		registry.register(
			"3.0.0", "3.1.0",
			new com.liferay.dispatch.internal.upgrade.v3_1_0.
				DispatchTriggerUpgradeProcess());

		registry.register(
			"3.1.0", "3.1.1",
			new com.liferay.dispatch.internal.upgrade.v3_1_1.
				DispatchTriggerModelResourcePermissionUpgradeProcess());

		registry.register(
			"3.1.1", "4.0.0",
			new com.liferay.dispatch.internal.upgrade.v4_0_0.
				DispatchTriggerUpgradeProcess());
	}

}