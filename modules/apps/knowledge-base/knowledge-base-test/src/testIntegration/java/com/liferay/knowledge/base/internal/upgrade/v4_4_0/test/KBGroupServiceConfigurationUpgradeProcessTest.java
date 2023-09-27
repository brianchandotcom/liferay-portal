/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class KBGroupServiceConfigurationUpgradeProcessTest {

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
	public void testUpgrade() throws Exception {
		for (Configuration configuration : _getConfigurations()) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			Assert.assertTrue(properties.get("rssDelta") instanceof Integer);
			Assert.assertNotNull(properties.get("rssFormat"));
		}

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = _getUpgradeProcess();

			upgradeProcess.upgrade();
		}

		for (Configuration configuration : _getConfigurations()) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			Assert.assertTrue(properties.get("rssDelta") instanceof String);
			Assert.assertNull(properties.get("rssFormat"));
		}
	}

	private Configuration[] _getConfigurations() throws Exception {
		return _configurationAdmin.listConfigurations(
			String.format("(%s=%s*)", Constants.SERVICE_PID, _SERVICE_PID));
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
		"com.liferay.knowledge.base.internal.upgrade.v4_4_0." +
			"KBGroupServiceConfigurationUpgradeProcess";

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