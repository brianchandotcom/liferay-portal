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

package com.liferay.portal.configuration.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.persistence.ConfigurationOverridePropertiesUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Closeable;
import java.io.IOException;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.felix.cm.PersistenceManager;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class ConfigurationPersistenceManagerTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testConfigurationOverride() throws IOException {

		// Override non-exist configuration

		String testPid = "testPid";

		_persistenceManager.delete(testPid);

		Map<String, String> overrideProperties = HashMapBuilder.put(
			"a", "A"
		).put(
			"b", "B"
		).build();

		try (Closeable closeable = _reloadWithOverrideProperties(
				testPid, overrideProperties)) {

			// Initial populating

			Dictionary<Object, Object> dictionary = _persistenceManager.load(
				testPid);

			Assert.assertNotNull(dictionary);
			Assert.assertEquals("A", dictionary.get("a"));
			Assert.assertEquals("B", dictionary.get("b"));

			// Runtime reload

			ReflectionTestUtil.invoke(
				_persistenceManager, "reload", new Class<?>[] {String.class},
				testPid);

			dictionary = _persistenceManager.load(testPid);

			Assert.assertNotNull(dictionary);
			Assert.assertEquals("A", dictionary.get("a"));
			Assert.assertEquals("B", dictionary.get("b"));

			// Runtime creation should not affect override

			Properties newProperties = new Properties();

			newProperties.put("a", "a");
			newProperties.put("b", "b");

			_persistenceManager.store(testPid, newProperties);

			dictionary = _persistenceManager.load(testPid);

			Assert.assertNotNull(dictionary);
			Assert.assertEquals("A", dictionary.get("a"));
			Assert.assertEquals("B", dictionary.get("b"));

			ReflectionTestUtil.invoke(
				_persistenceManager, "reload", new Class<?>[] {String.class},
				testPid);

			dictionary = _persistenceManager.load(testPid);

			Assert.assertNotNull(dictionary);
			Assert.assertEquals("A", dictionary.get("a"));
			Assert.assertEquals("B", dictionary.get("b"));
		}
		finally {
			_persistenceManager.delete(testPid);
		}

		// Override exist configuration

		Properties existProperties = new Properties();

		existProperties.put("a", "a");
		existProperties.put("b", "b");

		_persistenceManager.store(testPid, existProperties);

		Dictionary<Object, Object> dictionary = _persistenceManager.load(
			testPid);

		Assert.assertNotNull(dictionary);
		Assert.assertEquals("a", dictionary.get("a"));
		Assert.assertEquals("b", dictionary.get("b"));

		try (Closeable closeable = _reloadWithOverrideProperties(
				testPid, overrideProperties)) {

			// Initial populating

			dictionary = _persistenceManager.load(testPid);

			Assert.assertNotNull(dictionary);
			Assert.assertEquals("A", dictionary.get("a"));
			Assert.assertEquals("B", dictionary.get("b"));

			// Runtime reload

			ReflectionTestUtil.invoke(
				_persistenceManager, "reload", new Class<?>[] {String.class},
				testPid);

			dictionary = _persistenceManager.load(testPid);

			Assert.assertNotNull(dictionary);
			Assert.assertEquals("A", dictionary.get("a"));
			Assert.assertEquals("B", dictionary.get("b"));

			// Runtime update should not affect override

			Properties newProperties = new Properties();

			newProperties.put("a", "c");
			newProperties.put("b", "d");

			_persistenceManager.store(testPid, newProperties);

			dictionary = _persistenceManager.load(testPid);

			Assert.assertNotNull(dictionary);
			Assert.assertEquals("A", dictionary.get("a"));
			Assert.assertEquals("B", dictionary.get("b"));

			ReflectionTestUtil.invoke(
				_persistenceManager, "reload", new Class<?>[] {String.class},
				testPid);

			dictionary = _persistenceManager.load(testPid);

			Assert.assertNotNull(dictionary);
			Assert.assertEquals("A", dictionary.get("a"));
			Assert.assertEquals("B", dictionary.get("b"));
		}
		finally {
			_persistenceManager.delete(testPid);
		}
	}

	@Test
	public void testConfigurationPersistenceManager() {
		Class<?> clazz = _persistenceManager.getClass();

		Assert.assertEquals(
			"com.liferay.portal.configuration.persistence.internal." +
				"ConfigurationPersistenceManager",
			clazz.getName());
	}

	@Test
	public void testCreateFactoryConfiguration() throws Exception {
		_assertConfiguration(true);
	}

	@Test
	public void testGetConfiguration() throws Exception {
		_assertConfiguration(false);
	}

	@Test
	public void testWhiteSpacedFelixFileInstallFileName() throws Exception {
		ReflectionTestUtil.invoke(
			_persistenceManager, "_verifyDictionary",
			new Class<?>[] {String.class, String.class}, "whitespace.pid",
			"felix.fileinstall.filename=\"file:/whitespace path/file.config\"");
	}

	private void _assertConfiguration(boolean factory) throws Exception {
		Configuration configuration = null;

		if (factory) {
			configuration = _configurationAdmin.getConfiguration("test.pid");

			Assert.assertTrue(_persistenceManager.exists("test.pid"));
		}
		else {
			configuration = _configurationAdmin.createFactoryConfiguration(
				"test.pid", StringPool.QUESTION);
		}

		String pid = configuration.getPid();

		ConfigurationTestUtil.saveConfiguration(
			configuration, MapUtil.singletonDictionary("foo", "bar"));

		Assert.assertTrue(_persistenceManager.exists(pid));

		Dictionary<String, Object> properties = _persistenceManager.load(pid);

		Assert.assertEquals("bar", properties.get("foo"));

		properties.put("fee", "fum");

		ConfigurationTestUtil.saveConfiguration(configuration, properties);

		properties = _persistenceManager.load(pid);

		Assert.assertEquals("bar", properties.get("foo"));
		Assert.assertEquals("fum", properties.get("fee"));

		ConfigurationTestUtil.deleteConfiguration(configuration);

		Assert.assertFalse(_persistenceManager.exists(pid));
	}

	private void _reload() {
		ReflectionTestUtil.invoke(_persistenceManager, "stop", new Class<?>[0]);

		ReflectionTestUtil.invoke(
			_persistenceManager, "start", new Class<?>[0]);
	}

	private Closeable _reloadWithOverrideProperties(
		String pid, Map<String, String> properties) {

		Map<String, Map<String, String>> innerMap =
			ReflectionTestUtil.getFieldValue(
				ConfigurationOverridePropertiesUtil.getOverridePropertiesMap(),
				"m");

		Map<String, Map<String, String>> backupMap = new HashMap<>(innerMap);

		innerMap.clear();

		innerMap.put(pid, properties);

		_reload();

		return () -> {
			innerMap.clear();

			innerMap.putAll(backupMap);

			_reload();
		};
	}

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject
	private static PersistenceManager _persistenceManager;

}