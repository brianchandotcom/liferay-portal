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

package com.liferay.blogs.internal.upgrade;

import com.liferay.blogs.internal.upgrade.v1_1_0.UpgradeClassNames;
import com.liferay.blogs.internal.upgrade.v1_1_0.UpgradeFriendlyURL;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class BlogsServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.blogs.service", "0.0.1", "1.0.0",
			new UpgradeClassNames());

		registry.register(
			"com.liferay.blogs.service", "1.0.0", "1.1.0",
			new UpgradeFriendlyURL(_friendlyURLEntryLocalService));
	}

	@Reference(unbind = "-")
	protected void setFriendlyURLEntryLocalService(
		FriendlyURLEntryLocalService friendlyURLEntryLocalService) {

		_friendlyURLEntryLocalService = friendlyURLEntryLocalService;
	}

	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

}