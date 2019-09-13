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

package com.liferay.wiki.internal.upgrade;

import com.liferay.comment.upgrade.UpgradeDiscussionSubscriptionClassName;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.upgrade.BaseUpgradeSQLServerDatetime;
import com.liferay.portal.kernel.upgrade.UpgradeMVCCVersion;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.subscription.service.SubscriptionLocalService;
import com.liferay.wiki.internal.upgrade.v1_0_0.UpgradeCompanyId;
import com.liferay.wiki.internal.upgrade.v1_0_0.UpgradeKernelPackage;
import com.liferay.wiki.internal.upgrade.v1_0_0.UpgradeLastPublishDate;
import com.liferay.wiki.internal.upgrade.v1_0_0.UpgradePortletPreferences;
import com.liferay.wiki.internal.upgrade.v1_0_0.UpgradePortletSettings;
import com.liferay.wiki.internal.upgrade.v1_0_0.UpgradeSchema;
import com.liferay.wiki.internal.upgrade.v1_0_0.UpgradeWikiPage;
import com.liferay.wiki.internal.upgrade.v1_0_0.UpgradeWikiPageResource;
import com.liferay.wiki.internal.upgrade.v1_1_0.UpgradeWikiNode;
import com.liferay.wiki.internal.upgrade.v2_0_0.util.WikiNodeTable;
import com.liferay.wiki.internal.upgrade.v2_0_0.util.WikiPageTable;
import com.liferay.wiki.model.WikiPage;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 * @author Manuel de la Peña
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class WikiServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.1", "0.0.2", new UpgradeSchema());

		registry.register("0.0.2", "0.0.3", new UpgradeKernelPackage());

		registry.register(
			"0.0.3", "1.0.0", new UpgradeCompanyId(),
			new UpgradeLastPublishDate(), new UpgradePortletPreferences(),
			new UpgradePortletSettings(_settingsFactory), new UpgradeWikiPage(),
			new UpgradeWikiPageResource());

		registry.register("1.0.0", "1.1.0", new UpgradeWikiNode());

		registry.register(
			"1.1.0", "1.1.1",
			new UpgradeDiscussionSubscriptionClassName(
				_subscriptionLocalService, WikiPage.class.getName(),
				UpgradeDiscussionSubscriptionClassName.DeletionMode.ADD_NEW));

		registry.register(
			"1.1.1", "2.0.0",
			new BaseUpgradeSQLServerDatetime(
				new Class<?>[] {WikiNodeTable.class, WikiPageTable.class}));

		registry.register(
			"2.0.0", "2.1.0",
			new UpgradeMVCCVersion() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"WikiNode", "WikiPage", "WikiPageResource"
					};
				}

			});
	}

	@Reference
	private SettingsFactory _settingsFactory;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}