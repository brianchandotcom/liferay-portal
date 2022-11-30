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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
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
		Configuration[] configurations = _getConfigurations();

		if (ArrayUtil.isEmpty(configurations)) {
			configurations = new Configuration[] {
				_configurationAdmin.getConfiguration(
					_SERVICE_PID + ".test", StringPool.QUESTION)
			};

			_originalProperties = null;
		}
		else {
			_originalProperties = new HashMapDictionary<>();
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (_originalProperties != null) {
				_originalProperties.put(configuration.getPid(), properties);
			}

			configuration.update(
				HashMapDictionaryBuilder.putAll(
					properties
				).put(
					"rssDelta", Integer.valueOf("20")
				).put(
					"rssFormat", "atom10"
				).build());
		}
	}

	@After
	public void tearDown() throws Exception {
		for (Configuration configuration : _getConfigurations()) {
			if (_originalProperties == null) {
				configuration.delete();
			}
			else {
				configuration.update(
					_originalProperties.get(configuration.getPid()));
			}
		}
	}

	@Test
	public void testRSSConfigurationUpgradeProcessWithExistingConfiguration()
		throws Exception {

		for (Configuration configuration : _getConfigurations()) {
			_assertPropertiesBefore(configuration.getProperties());
		}

		_runUpgrade();

		for (Configuration configuration : _getConfigurations()) {
			_assertPropertiesAfter(configuration.getProperties());
		}
	}

	private void _assertPropertiesAfter(Dictionary<String, Object> properties) {
		Assert.assertTrue(
			"The property 'rssDelta' should be of type String",
			properties.get("rssDelta") instanceof String);
		Assert.assertNull(
			"The property 'rssFormat' should not exist",
			properties.get("rssFormat"));
	}

	private void _assertPropertiesBefore(
		Dictionary<String, Object> properties) {

		Assert.assertTrue(
			"The property 'rssDelta' should be of type Integer",
			properties.get("rssDelta") instanceof Integer);
		Assert.assertNotNull(
			"The property 'rssFormat' should exist",
			properties.get("rssFormat"));
	}

	private Configuration[] _getConfigurations() throws Exception {
		String filterString = String.format(
			"(%s=%s*)", Constants.SERVICE_PID, _SERVICE_PID);

		return _configurationAdmin.listConfigurations(filterString);
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
		"com.liferay.knowledge.base.configuration.KBGroupServiceConfiguration";

	@Inject(
		filter = "(&(component.name=com.liferay.knowledge.base.internal.upgrade.registry.KnowledgeBaseServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	private Dictionary<String, Dictionary<String, Object>> _originalProperties;

}