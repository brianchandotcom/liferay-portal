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
import com.liferay.blogs.internal.upgrade.v1_1_1.UpgradeUrlTitle;
import com.liferay.blogs.internal.upgrade.v1_1_2.UpgradeBlogsImages;
import com.liferay.blogs.internal.upgrade.v2_0_0.util.BlogsEntryTable;
import com.liferay.blogs.internal.upgrade.v2_0_0.util.BlogsStatsUserTable;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.comment.upgrade.UpgradeDiscussionSubscriptionClassName;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.upgrade.BaseUpgradeSQLServerDatetime;
import com.liferay.portal.kernel.upgrade.UpgradeMVCCVersion;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.subscription.service.SubscriptionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class BlogsServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.1", "1.0.0", new UpgradeClassNames());

		registry.register(
			"1.0.0", "1.1.0",
			new UpgradeFriendlyURL(_friendlyURLEntryLocalService));

		registry.register("1.1.0", "1.1.1", new UpgradeUrlTitle());

		registry.register(
			"1.1.1", "1.1.2",
			new UpgradeBlogsImages(_imageLocalService, _portletFileRepository));

		registry.register(
			"1.1.2", "1.1.3",
			new UpgradeDiscussionSubscriptionClassName(
				_subscriptionLocalService, BlogsEntry.class.getName(),
				UpgradeDiscussionSubscriptionClassName.DeletionMode.ADD_NEW));

		registry.register(
			"1.1.3", "2.0.0",
			new BaseUpgradeSQLServerDatetime(
				new Class<?>[] {
					BlogsEntryTable.class, BlogsStatsUserTable.class
				}));

		registry.register(
			"2.0.0", "2.0.1",
			new UpgradeDiscussionSubscriptionClassName(
				_subscriptionLocalService, BlogsEntry.class.getName(),
				UpgradeDiscussionSubscriptionClassName.DeletionMode.
					DELETE_OLD));

		registry.register(
			"2.0.1", "2.1.0",
			new UpgradeMVCCVersion() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {"BlogsEntry", "BlogsStatsUser"};
				}

			});
	}

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}