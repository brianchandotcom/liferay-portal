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

package com.liferay.polls.upgrade;

import com.liferay.polls.upgrade.v1_0_0.UpgradeKernelPackage;
import com.liferay.polls.upgrade.v1_0_0.UpgradeLastPublishDate;
import com.liferay.polls.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Miguel Pastor
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class PollsServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.polls.service", "0.0.1", "0.0.2",
			new UpgradeKernelPackage(), new UpgradePortletId());

		registry.register(
			"com.liferay.polls.service", "0.0.2", "1.0.0",
			new UpgradeLastPublishDate());

		registry.register(
			"com.liferay.polls.service", "1.0.0", "1.0.4",
			new DummyUpgradeStep());

		registry.register(
			"com.liferay.polls.service", "1.0.3", "1.0.4",
			new com.liferay.polls.upgrade.v1_0_4.UpgradePortletId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PollsServiceUpgrade.class);

}