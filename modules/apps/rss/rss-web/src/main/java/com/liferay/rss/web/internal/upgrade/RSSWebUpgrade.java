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

package com.liferay.rss.web.internal.upgrade;

import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletId;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.v7_0_0.UpgradeKernelPackage;
import com.liferay.rss.constants.RSSPortletKeys;
import com.liferay.rss.web.internal.configuration.RSSPortletInstanceConfiguration;
import com.liferay.rss.web.internal.configuration.RSSWebCacheConfiguration;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class RSSWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.0", "3.0.0", new DummyUpgradeStep());

		registry.register(
			"0.0.1", "1.0.0",
			new BaseUpgradePortletId() {

				@Override
				protected String[][] getRenamePortletIdsArray() {
					return new String[][] {{"39", RSSPortletKeys.RSS}};
				}

			});

		registry.register(
			"1.0.0", "3.0.0",
			new UpgradeKernelPackage() {

				@Override
				protected String[][] getClassNames() {
					return new String[][] {
						{
							"com.liferay.rss.web.util.RSSFeed",
							"com.liferay.rss.web.internal.util.RSSFeed"
						}
					};
				}

				@Override
				protected String[][] getResourceNames() {
					return new String[0][0];
				}

			});

		registry.register(
			"3.0.0", "3.0.1",
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.rss.web.configuration." +
					"RSSPortletInstanceConfiguration",
				RSSPortletInstanceConfiguration.class.getName()),
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.rss.web.configuration.RSSWebCacheConfiguration",
				RSSWebCacheConfiguration.class.getName()));
	}

	@Reference
	private ConfigurationUpgradeStepFactory _configurationUpgradeStepFactory;

}