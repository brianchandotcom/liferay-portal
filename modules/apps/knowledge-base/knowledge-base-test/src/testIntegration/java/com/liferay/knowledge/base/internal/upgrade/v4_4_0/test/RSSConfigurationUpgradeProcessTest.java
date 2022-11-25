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

package com.liferay.knowledge.base.internal.upgrade.v4_4_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.knowledge.base.configuration.KBGroupServiceConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
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

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Marco Galluzzi
 */
@RunWith(Arquillian.class)
public class RSSConfigurationUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_configuration = _getConfiguration();

		if (_configuration == null) {
			_configuration = _configurationAdmin.getConfiguration(
				_SERVICE_PID, StringPool.QUESTION);
			_originalProperties = null;
		}
		else {
			_originalProperties = _configuration.getProperties();
		}

		_configuration.update(
			HashMapDictionaryBuilder.putAll(
				_originalProperties
			).put(
				"rssDelta", Integer.valueOf("20")
			).put(
				"rssFormat", "atom10"
			).build());
	}

	@After
	public void tearDown() throws Exception {
		if (_originalProperties == null) {
			_configuration.delete();
		}
		else {
			_configuration.update(_originalProperties);
		}
	}

	@Test
	public void testRSSConfigurationUpgradeProcessWithExistingConfiguration()
		throws Exception {

		_checkPropertiesBefore(_configuration.getProperties());

		_runUpgrade();

		_configuration = _getConfiguration();

		_checkPropertiesAfter(_configuration.getProperties());
	}

	private void _checkPropertiesAfter(Dictionary<String, Object> properties) {
		Assert.assertTrue(
			"The property 'rssDelta' should be of type String",
			properties.get("rssDelta") instanceof String);
		Assert.assertNull(
			"The property 'rssFormat' should not exist",
			properties.get("rssFormat"));
	}

	private void _checkPropertiesBefore(Dictionary<String, Object> properties) {
		Assert.assertTrue(
			"The property 'rssDelta' should be of type Integer",
			properties.get("rssDelta") instanceof Integer);
		Assert.assertNotNull(
			"The property 'rssFormat' should exist",
			properties.get("rssFormat"));
	}

	private Configuration _getConfiguration() throws Exception {
		String filterString = String.format(
			"(%s=%s)", Constants.SERVICE_PID, _SERVICE_PID);

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (configurations == null) {
			return null;
		}

		return configurations[0];
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

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = _getUpgradeProcess();

			upgradeProcess.upgrade();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.knowledge.base.internal.upgrade.v4_4_0." +
			"RSSConfigurationUpgradeProcess";

	private static final String _SERVICE_PID =
		KBGroupServiceConfiguration.class.getName();

	@Inject(
		filter = "(&(component.name=com.liferay.knowledge.base.internal.upgrade.registry.KnowledgeBaseServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private Configuration _configuration;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	private Dictionary<String, Object> _originalProperties;

}