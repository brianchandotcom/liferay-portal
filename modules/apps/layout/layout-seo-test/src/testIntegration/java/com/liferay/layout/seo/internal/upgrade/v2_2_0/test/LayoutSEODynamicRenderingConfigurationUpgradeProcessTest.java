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

package com.liferay.layout.seo.internal.upgrade.v2_2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.Dictionary;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class LayoutSEODynamicRenderingConfigurationUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Configuration crawlerUserAgentsConfiguration =
			_configurationAdmin.getConfiguration(
				"com.liferay.redirect.configuration." +
					"CrawlerUserAgentsConfiguration",
				StringPool.QUESTION);

		if (crawlerUserAgentsConfiguration != null) {
			_originalCrawlerUserAgentsProperties =
				crawlerUserAgentsConfiguration.getProperties();
		}

		Configuration layoutSEODynamicRenderingConfiguration =
			_configurationAdmin.getConfiguration(
				"com.liferay.layout.seo.web.internal.configuration." +
					"LayoutSEODynamicRenderingConfiguration",
				StringPool.QUESTION);

		if (layoutSEODynamicRenderingConfiguration != null) {
			Dictionary<String, Object> layoutSEODynamicRenderingProperties =
				layoutSEODynamicRenderingConfiguration.getProperties();

			_originalLayoutSEODynamicRenderingProperties =
				layoutSEODynamicRenderingProperties;

			layoutSEODynamicRenderingConfiguration.update(
				HashMapDictionaryBuilder.putAll(
					layoutSEODynamicRenderingProperties
				).put(
					"crawlerUserAgents", _CRAWLER_USER_AGENTS
				).build());
		}
	}

	@After
	public void tearDown() throws Exception {
		Configuration crawlerUserAgentsConfiguration =
			_configurationAdmin.getConfiguration(
				"com.liferay.redirect.configuration." +
					"CrawlerUserAgentsConfiguration",
				StringPool.QUESTION);

		if (crawlerUserAgentsConfiguration != null) {
			crawlerUserAgentsConfiguration.update(
				_originalCrawlerUserAgentsProperties);
		}

		Configuration layoutSEODynamicRenderingConfiguration =
			_configurationAdmin.getConfiguration(
				"com.liferay.layout.seo.web.internal.configuration." +
					"LayoutSEODynamicRenderingConfiguration",
				StringPool.QUESTION);

		if (layoutSEODynamicRenderingConfiguration != null) {
			layoutSEODynamicRenderingConfiguration.update(
				_originalLayoutSEODynamicRenderingProperties);
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = _getUpgradeProcess();

		upgradeProcess.upgrade();

		Configuration configuration = _configurationAdmin.getConfiguration(
			"com.liferay.redirect.configuration.CrawlerUserAgentsConfiguration",
			StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		Assert.assertNotNull(properties.get("crawlerUserAgents"));
		Assert.assertArrayEquals(
			(String[])properties.get("crawlerUserAgents"),
			_CRAWLER_USER_AGENTS);
	}

	private UpgradeProcess _getUpgradeProcess() {
		UpgradeProcess[] upgradeProcesses = new UpgradeProcess[1];

		_upgradeStepRegistrator.register(
			(fromSchemaVersionString, toSchemaVersionString, upgradeSteps) -> {
				for (UpgradeStep upgradeStep : upgradeSteps) {
					Class<? extends UpgradeStep> clazz = upgradeStep.getClass();

					if (Objects.equals(clazz.getName(), _CLASS_NAME)) {
						upgradeProcesses[0] = (UpgradeProcess)upgradeStep;

						break;
					}
				}
			});

		return upgradeProcesses[0];
	}

	private static final String _CLASS_NAME =
		"com.liferay.layout.seo.internal.upgrade.v2_2_0." +
			"LayoutSEODynamicRenderingConfigurationUpgradeProcess";

	private static final String[] _CRAWLER_USER_AGENTS = {
		"userAgent1", "userAgent2"
	};

	@Inject(
		filter = "(&(component.name=com.liferay.layout.seo.internal.upgrade.registry.LayoutSEOServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	private Dictionary<String, Object> _originalCrawlerUserAgentsProperties;
	private Dictionary<String, Object>
		_originalLayoutSEODynamicRenderingProperties;

}