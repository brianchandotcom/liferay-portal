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

package com.liferay.social.privatemessaging.upgrade;

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.social.privatemessaging.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.social.privatemessaging.upgrade.v1_0_0.UpgradePrivateMessaging;
import com.liferay.social.privatemessaging.upgrade.v1_0_1.UpgradeResourcePermission;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	service = {
		PrivateMessagingServiceUpgrade.class, UpgradeStepRegistrator.class
	}
)
public class PrivateMessagingServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.social.privatemessaging.service", "0.0.1", "1.0.0",
			new UpgradePrivateMessaging(), new UpgradePortletId());

		registry.register(
			"com.liferay.social.privatemessaging.web", "1.0.0", "1.0.1",
			new UpgradeResourcePermission());
	}

}