/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.k8s.agent.internal.osgi.commands.test;

import com.liferay.account.configuration.AccountEntryEmailConfiguration;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.type.configuration.CETConfiguration;
import com.liferay.osgi.util.osgi.commands.OSGiCommands;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationEvent;
import org.osgi.service.cm.ConfigurationListener;

/**
 * @author Anna Zombori-Suszter
 */
@RunWith(Arquillian.class)
public class CXConfigOSGiCommandsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(CXConfigOSGiCommandsTest.class);

		_bundleContext = bundle.getBundleContext();

		Company company = CompanyTestUtil.addCompany();

		_companyWebId = company.getWebId();

		_configurationPids = new ArrayList<>();

		String configurationPid1 =
			ConfigurationTestUtil.createFactoryConfiguration(
				CETConfiguration.class.getName(),
				"liferay-sample-cx-1/liferay.com",
				HashMapDictionaryBuilder.<String, Object>put(
					".cx.config.key",
					CETConfiguration.class.getName() +
						"~liferay-sample-cx-1/liferay.com"
				).put(
					"baseURL", "${portalURL}/o/liferay-sample-cx-1"
				).put(
					"dxp.lxc.liferay.com.virtualInstanceId", "default"
				).put(
					"name", _CONFIGURATION_NAME_1
				).put(
					"projectName", "liferay-sample-cx-1"
				).put(
					"type", "customElement"
				).build());

		String configurationPid2 =
			ConfigurationTestUtil.createFactoryConfiguration(
				CETConfiguration.class.getName(),
				"liferay-sample-cx-2/liferay.com",
				HashMapDictionaryBuilder.<String, Object>put(
					".k8s.config.key",
					CETConfiguration.class.getName() + "~liferay-sample-cx-2"
				).put(
					"baseURL", "${portalURL}/o/liferay-sample-cx-2"
				).put(
					"dxp.lxc.liferay.com.virtualInstanceId", "default"
				).put(
					"name", _CONFIGURATION_NAME_2
				).put(
					"projectName", "liferay-sample-cx-2"
				).put(
					"type", "customElement"
				).build());

		String configurationPid3 =
			ConfigurationTestUtil.createFactoryConfiguration(
				AccountEntryEmailConfiguration.class.getName(),
				"liferay-sample-cx-3/liferay.com",
				HashMapDictionaryBuilder.<String, Object>put(
					".k8s.config.key",
					AccountEntryEmailConfiguration.class.getName() +
						"~liferay-sample-cx-3/" + _companyWebId
				).put(
					"baseURL", "${portalURL}/o/liferay-sample-cx-3"
				).put(
					"dxp.lxc.liferay.com.virtualInstanceId", _companyWebId
				).put(
					"name", _CONFIGURATION_NAME_3
				).put(
					"projectName", "liferay-sample-cx-3"
				).put(
					"type", "instanceSettings"
				).build());

		_configurationPids.add(configurationPid1);
		_configurationPids.add(configurationPid2);
		_configurationPids.add(configurationPid3);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		for (String configurationPid : _configurationPids) {
			ConfigurationTestUtil.deleteConfiguration(configurationPid);
		}
	}

	@Test
	public void testGetConfigurationDataValidPid() {
		String pid =
			"com.liferay.client.extension.type.configuration.CETConfiguration" +
				"~liferay-sample-cx-1/liferay.com";

		Configuration configuration = _getConfiguration(pid);

		Assert.assertEquals(pid, configuration.getPid());
	}

	@Test
	public void testGetConfigurationInvalidPid() {
		String pid = "non-existing-pid";

		Assert.assertNull(_getConfiguration(pid));
	}

	@Test
	public void testGetConfigurations() throws Exception {
		List<String> failures = new ArrayList<>();

		Object[][] testCasesCorrectParameters = {
			{
				new String[0],
				new HashSet<>(
					Arrays.asList(
						_CONFIGURATION_NAME_1, _CONFIGURATION_NAME_2,
						_CONFIGURATION_NAME_3))
			},
			{
				new String[] {"deploymentType=bundle"},
				new HashSet<>(Arrays.asList(_CONFIGURATION_NAME_1))
			},
			{
				new String[] {"deploymentType=agent"},
				new HashSet<>(
					Arrays.asList(_CONFIGURATION_NAME_2, _CONFIGURATION_NAME_3))
			},
			{
				new String[] {"webId=default"},
				new HashSet<>(
					Arrays.asList(_CONFIGURATION_NAME_1, _CONFIGURATION_NAME_2))
			},
			{
				new String[] {"webId=liferay.com"},
				new HashSet<>(
					Arrays.asList(_CONFIGURATION_NAME_1, _CONFIGURATION_NAME_2))
			},
			{
				new String[] {"webId=" + _companyWebId},
				new HashSet<>(Arrays.asList(_CONFIGURATION_NAME_3))
			},
			{
				new String[] {"type=customElement"},
				new HashSet<>(
					Arrays.asList(_CONFIGURATION_NAME_1, _CONFIGURATION_NAME_2))
			},
			{
				new String[] {"type=instanceSettings"},
				new HashSet<>(Arrays.asList(_CONFIGURATION_NAME_3))
			},
			{
				new String[] {"deploymentType=bundle", "type=customElement"},
				new HashSet<>(Arrays.asList(_CONFIGURATION_NAME_1))
			},
			{
				new String[] {
					"deploymentType=agent", "webId=" + _companyWebId,
					"type=instanceSettings"
				},
				new HashSet<>(Arrays.asList(_CONFIGURATION_NAME_3))
			}
		};

		for (Object[] testCase : testCasesCorrectParameters) {
			String result = _assertListDataOutput(testCase);

			if (!result.isEmpty()) {
				failures.add(result);
			}
		}

		Object[][] testCasesIncorrectParameters = {
			{new String[] {"name=Non Existing Name"}, null},
			{
				new String[] {"deploymentType=prod", "name=Non Existing Name"},
				null
			},
			{new String[] {"nonExistentFilter=foo"}, null},
			{new String[] {"foo"}, null}, {new String[] {"foo", "bar"}, null}
		};

		for (Object[] testCase : testCasesIncorrectParameters) {
			String result = _assertListDataOutput(testCase);

			if (!result.isEmpty()) {
				failures.add(result);
			}
		}

		Assert.assertTrue(
			"Failures: " + String.join("", failures), failures.isEmpty());
	}

	@Test
	public void testList() throws Exception {
		PrintStream printStream = System.out;

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		System.setOut(new PrintStream(byteArrayOutputStream));

		_list("deploymentType=bundle");

		String output = byteArrayOutputStream.toString();

		String[] lines = output.split(System.lineSeparator());

		Assert.assertTrue(lines.length >= 3);

		String header = lines[0];

		Assert.assertTrue(
			header.matches(
				"\\| pid\\s*\\| name\\s*\\| type\\s*\\| webId\\s*\\|"));

		String dataRow = lines[2];

		String expectedDataPattern = StringBundler.concat(
			"\\| ", _configurationPids.get(0), "\\s*\\| ",
			_CONFIGURATION_NAME_1, "\\s*\\| customElement \\s*\\| ",
			"default\\s*\\|");

		System.setOut(printStream);

		Assert.assertTrue(dataRow.matches(expectedDataPattern));
	}

	@Test
	public void testReload() throws Exception {
		Object[][] testCases = {
			{
				new String[] {
					"com.liferay.client.extension.type.configuration." +
						"CETConfiguration~liferay-sample-cx-1/liferay.com"
				},
				"Reloaded configuration for " +
					"com.liferay.client.extension.type.configuration." +
						"CETConfiguration~liferay-sample-cx-1/liferay.com"
			},
			{new String[] {"non-existing-pid"}, "No configuration found."},
			{new String[] {"pid-1", "pid-2"}, "Too many arguments."},
			{new String[0], "No PID provided."}
		};

		List<String> failures = new ArrayList<>();

		PrintStream printStream = System.out;

		for (Object[] testCase : testCases) {
			String[] params = (String[])testCase[0];
			String expectedOutput = (String)testCase[1];

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			System.setOut(new PrintStream(byteArrayOutputStream));

			_reload(params);

			String output = byteArrayOutputStream.toString();

			if (!output.contains(expectedOutput)) {
				failures.add("FAILURE: " + Arrays.toString(params) + "\n");
			}

			System.setOut(printStream);
		}

		Assert.assertTrue(
			"Failures: " + String.join("", failures), failures.isEmpty());
	}

	@Test
	public void testReloadConfiguration() throws Exception {
		String pid = _configurationPids.get(0);

		CountDownLatch latch = new CountDownLatch(2);

		List<Integer> receivedEventTypes = new ArrayList<>();

		ConfigurationListener configurationListener = event -> {
			if (Objects.equals(event.getPid(), pid)) {
				receivedEventTypes.add(event.getType());

				latch.countDown();
			}
		};

		ServiceRegistration<ConfigurationListener> serviceRegistration =
			_bundleContext.registerService(
				ConfigurationListener.class, configurationListener, null);

		try {
			_reloadConfiguration(_getConfiguration(pid));

			Assert.assertTrue(latch.await(5, TimeUnit.SECONDS));

			Assert.assertEquals(
				receivedEventTypes.toString(), 2, receivedEventTypes.size());
			Assert.assertTrue(
				receivedEventTypes.contains(ConfigurationEvent.CM_DELETED));
			Assert.assertTrue(
				receivedEventTypes.contains(ConfigurationEvent.CM_UPDATED));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@Test
	public void testShow() throws Exception {
		Object[][] testCases = {
			{
				new String[] {
					"com.liferay.client.extension.type.configuration." +
						"CETConfiguration~liferay-sample-cx-1/liferay.com"
				},
				"projectName: liferay-sample-cx-1"
			},
			{new String[] {"non-existing-pid"}, "No configuration found."},
			{new String[] {"pid-1", "pid-2"}, "Too many arguments."},
			{new String[0], "No PID provided."}
		};

		List<String> failures = new ArrayList<>();

		PrintStream printStream = System.out;

		for (Object[] testCase : testCases) {
			String[] params = (String[])testCase[0];
			String expectedOutput = (String)testCase[1];

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			System.setOut(new PrintStream(byteArrayOutputStream));

			_show(params);

			String output = byteArrayOutputStream.toString();

			if (!output.contains(expectedOutput)) {
				failures.add("FAILURE: " + Arrays.toString(params) + "\n");
			}

			System.setOut(printStream);
		}

		Assert.assertTrue(
			"Failures: " + String.join("", failures), failures.isEmpty());
	}

	private String _assertListDataOutput(Object[] testCase) throws Exception {
		String[] filter = (String[])testCase[0];
		HashSet<String> expectedOutput = (HashSet<String>)testCase[1];

		String result = "";

		Configuration[] configurations = _getConfigurations(filter);

		Set<String> namesFound = new HashSet<>();

		if (configurations == null) {
			namesFound = null;
		}
		else {
			for (Configuration configuration : configurations) {
				namesFound.add(
					configuration.getProperties(
					).get(
						"name"
					).toString());
			}
		}

		if (!Objects.equals(expectedOutput, namesFound)) {
			result = StringBundler.concat(
				"FAILURE: ", Arrays.toString(filter), "\nexpected output: ",
				expectedOutput, "\nactual output: ", namesFound, "\n");
		}

		return result;
	}

	private Configuration _getConfiguration(String pid) {
		return ReflectionTestUtil.invoke(
			_osgiCommands, "_getConfiguration", new Class<?>[] {String.class},
			pid);
	}

	private Configuration[] _getConfigurations(String[] filter) {
		return ReflectionTestUtil.invoke(
			_osgiCommands, "_getConfigurations",
			new Class<?>[] {String[].class}, (Object)filter);
	}

	private void _list(String... filters) throws Exception {
		Class<?> clazz = _osgiCommands.getClass();

		Method method = clazz.getMethod("list", String[].class);

		method.invoke(_osgiCommands, (Object)filters);
	}

	private void _reload(String... args) throws Exception {
		Class<?> clazz = _osgiCommands.getClass();

		Method method = clazz.getMethod("reload", String[].class);

		method.invoke(_osgiCommands, (Object)args);
	}

	private void _reloadConfiguration(Configuration configuration) {
		ReflectionTestUtil.invoke(
			_osgiCommands, "_reloadConfiguration",
			new Class<?>[] {Configuration.class}, configuration);
	}

	private void _show(String... args) throws Exception {
		Class<?> clazz = _osgiCommands.getClass();

		Method method = clazz.getMethod("show", String[].class);

		method.invoke(_osgiCommands, (Object)args);
	}

	private static final String _CONFIGURATION_NAME_1 = "Liferay Sample CX 1";

	private static final String _CONFIGURATION_NAME_2 = "Liferay Sample CX 2";

	private static final String _CONFIGURATION_NAME_3 = "Liferay Sample CX 3";

	private static BundleContext _bundleContext;
	private static String _companyWebId;
	private static List<String> _configurationPids;

	@Inject(filter = "osgi.command.scope=cxconfig")
	private OSGiCommands _osgiCommands;

}