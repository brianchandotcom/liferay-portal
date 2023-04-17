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

package com.liferay.layout.seo.internal.upgrade.v2_2_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Alicia García
 */
public class LayoutSEODynamicRenderingConfigurationUpgradeProcess
	extends UpgradeProcess {

	public LayoutSEODynamicRenderingConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Configuration layoutSEODynamicRenderingConfiguration =
			_configurationAdmin.getConfiguration(
				"com.liferay.layout.seo.web.internal.configuration." +
					"LayoutSEODynamicRenderingConfiguration",
				StringPool.QUESTION);

		Dictionary<String, Object> layoutSEODynamicRenderingProperties =
			layoutSEODynamicRenderingConfiguration.getProperties();

		if (layoutSEODynamicRenderingProperties == null) {
			return;
		}

		String[] layoutSEODynamicRenderingCrawlerUserAgents =
			GetterUtil.getStringValues(
				layoutSEODynamicRenderingProperties.get("crawlerUserAgents"));

		if (layoutSEODynamicRenderingCrawlerUserAgents.length == 0) {
			return;
		}

		Configuration crawlerUserAgentsConfiguration =
			_configurationAdmin.getConfiguration(
				"com.liferay.redirect.configuration." +
					"CrawlerUserAgentsConfiguration",
				StringPool.QUESTION);

		Dictionary<String, Object> crawlerUserAgentsProperties =
			layoutSEODynamicRenderingConfiguration.getProperties();

		if (crawlerUserAgentsProperties == null) {
			crawlerUserAgentsProperties = new HashMapDictionary<>();
		}

		crawlerUserAgentsProperties.put(
			"crawlerUserAgents", layoutSEODynamicRenderingCrawlerUserAgents);

		crawlerUserAgentsConfiguration.update(crawlerUserAgentsProperties);

		layoutSEODynamicRenderingProperties.remove("crawlerUserAgents");

		layoutSEODynamicRenderingConfiguration.update(
			layoutSEODynamicRenderingProperties);
	}

	private final ConfigurationAdmin _configurationAdmin;

}