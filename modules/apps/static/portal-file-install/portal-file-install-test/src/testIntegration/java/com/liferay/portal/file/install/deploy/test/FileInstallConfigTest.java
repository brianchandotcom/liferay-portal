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

package com.liferay.portal.file.install.deploy.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Dictionary;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedService;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class FileInstallConfigTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(FileInstallConfigTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Test
	public void testConfigurationBoolean() throws Exception {
		_testConfiguration("B\"True\"", true);
	}

	@Test
	public void testConfigurationBooleanArray() throws Exception {
		Boolean[] expectedBooleans = new Boolean[2];

		expectedBooleans[0] = true;
		expectedBooleans[1] = false;

		_testConfiguration("B[\"True\",\"False\"]", expectedBooleans);
	}

	@Test
	public void testConfigurationByte() throws Exception {
		_testConfiguration("X\"1\"", (byte)1);
	}

	@Test
	public void testConfigurationByteArray() throws Exception {
		Byte[] expectedBytes = new Byte[2];

		expectedBytes[0] = 0b1;
		expectedBytes[1] = 0b11;

		_testConfiguration("X[\"1\",\"3\"]", expectedBytes);
	}

	@Test
	public void testConfigurationCharacter() throws Exception {
		_testConfiguration("C\"A\"", 'A');
	}

	@Test
	public void testConfigurationCharacterArray() throws Exception {
		Character[] expectedCharacters = new Character[2];

		expectedCharacters[0] = 'A';
		expectedCharacters[1] = 'Z';

		_testConfiguration("C[\"A\",\"Z\"]", expectedCharacters);
	}

	@Test
	public void testConfigurationDouble() throws Exception {
		_testConfiguration("D\"12.2\"", 12.2D);
	}

	@Test
	public void testConfigurationDoubleArray() throws Exception {
		Double[] expectedDoubles = new Double[2];

		expectedDoubles[0] = 12.2D;
		expectedDoubles[1] = 12.3D;

		_testConfiguration("D[\"12.2\",\"12.3\"]", expectedDoubles);
	}

	@Test
	public void testConfigurationFloat() throws Exception {
		_testConfiguration("F\"12.2\"", 12.2F);
	}

	@Test
	public void testConfigurationFloatArray() throws Exception {
		Float[] expectedFloats = new Float[2];

		expectedFloats[0] = 12.2F;
		expectedFloats[1] = 12.3F;

		_testConfiguration("F[\"12.2\",\"12.3\"]", expectedFloats);
	}

	@Test
	public void testConfigurationInteger() throws Exception {
		_testConfiguration("I\"20\"", 20);
	}

	@Test
	public void testConfigurationIntegerArray() throws Exception {
		Integer[] expectedIntegers = new Integer[2];

		expectedIntegers[0] = 20;
		expectedIntegers[1] = 21;

		_testConfiguration("I[\"20\",\"21\"]", expectedIntegers);
	}

	@Test
	public void testConfigurationLong() throws Exception {
		_testConfiguration("L\"30\"", 30L);
	}

	@Test
	public void testConfigurationLongArray() throws Exception {
		Long[] expectedLongs = new Long[2];

		expectedLongs[0] = 30L;
		expectedLongs[1] = 31L;

		_testConfiguration("L[\"30\",\"31\"]", expectedLongs);
	}

	@Test
	public void testConfigurationShort() throws Exception {
		_testConfiguration("S\"2\"", (short)2);
	}

	@Test
	public void testConfigurationShortArray() throws Exception {
		Short[] expectedShorts = new Short[2];

		expectedShorts[0] = (short)2;
		expectedShorts[1] = (short)3;

		_testConfiguration("S[\"2\",\"3\"]", expectedShorts);
	}

	@Test
	public void testConfigurationString() throws Exception {
		_testConfiguration("\"testString\"", "testString");
	}

	@Test
	public void testConfigurationStringArray() throws Exception {
		String[] expectedStrings = new String[2];

		expectedStrings[0] = "testString";
		expectedStrings[1] = "testString2";

		_testConfiguration("[\"testString\",\"testString2\"]", expectedStrings);
	}

	@Test
	public void testConfigurationTypedAndNontypedValues() throws Exception {
		Path path = Paths.get(
			PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR,
			_CONFIGURATION_PID.concat(".config"));

		try {
			String[] configs = {
				"configBoolean=B\"True\"\n", "configByte=X\"1\"\n",
				"configCharacter=C\"A\"\n", "configDouble=D\"12.2\"\n",
				"configFloat=F\"12.2\"\n", "configInteger=I\"20\"\n",
				"configLong=L\"30\"\n", "configShort=S\"2\"\n",
				"configString=\"testString\""
			};

			_updateConfiguration(
				() -> {
					StringBundler sb = new StringBundler(configs);

					String content = sb.toString();

					Files.write(path, content.getBytes());
				});

			Configuration configuration = _configurationAdmin.getConfiguration(
				_CONFIGURATION_PID, StringPool.QUESTION);

			Dictionary<String, Object> properties =
				configuration.getProperties();

			Assert.assertEquals(true, properties.get("configBoolean"));
			Assert.assertEquals((byte)1, properties.get("configByte"));
			Assert.assertEquals('A', properties.get("configCharacter"));
			Assert.assertEquals(12.2D, properties.get("configDouble"));
			Assert.assertEquals(12.2F, properties.get("configFloat"));
			Assert.assertEquals(20, properties.get("configInteger"));
			Assert.assertEquals(30L, properties.get("configLong"));
			Assert.assertEquals((short)2, properties.get("configShort"));
			Assert.assertEquals("testString", properties.get("configString"));
		}
		finally {
			Files.deleteIfExists(path);
		}
	}

	private void _testConfiguration(String config, Object configValue)
		throws Exception {

		Path path = Paths.get(
			PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR,
			_CONFIGURATION_PID.concat(".config"));

		try {
			_updateConfiguration(
				() -> {
					String content = StringBundler.concat(
						_TEST_KEY, StringPool.EQUAL, config);

					Files.write(path, content.getBytes());
				});

			Configuration configuration = _configurationAdmin.getConfiguration(
				_CONFIGURATION_PID, StringPool.QUESTION);

			Dictionary<String, Object> properties =
				configuration.getProperties();

			Object property = properties.get(_TEST_KEY);

			Class<?> clazz = configValue.getClass();

			if (clazz.isArray()) {
				Assert.assertArrayEquals(
					(Object[])configValue, (Object[])property);
			}
			else {
				Assert.assertEquals(configValue, property);
			}
		}
		finally {
			Files.deleteIfExists(path);
		}
	}

	private void _updateConfiguration(UnsafeRunnable<Exception> runnable)
		throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(2);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(Constants.SERVICE_PID, _CONFIGURATION_PID);

		ServiceRegistration<ManagedService> serviceRegistration =
			_bundleContext.registerService(
				ManagedService.class, props -> countDownLatch.countDown(),
				properties);

		try {
			runnable.run();

			countDownLatch.await();
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private static final String _CONFIGURATION_PID =
		FileInstallConfigTest.class.getName() + "Configuration";

	private static final String _TEST_KEY = "testKey";

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private BundleContext _bundleContext;

}