/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.k8s.agent.internal.osgi.commands.test;

import com.liferay.account.configuration.AccountEntryEmailConfiguration;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.type.configuration.CETConfiguration;
import com.liferay.osgi.util.osgi.commands.OSGiCommands;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Dictionary;
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
public class ClientExtensionsOSGiCommandsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			ClientExtensionsOSGiCommandsTest.class);

		_bundleContext = bundle.getBundleContext();

		Company company = CompanyTestUtil.addCompany();

		_companyWebId = company.getWebId();

		_configurationPids = new ArrayList<>();

		_configurationPids.add(
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
					"name", "Liferay Sample CX 1"
				).put(
					"projectName", "liferay-sample-cx-1"
				).put(
					"test.only", "true"
				).put(
					"type", "customElement"
				).build()));
		_configurationPids.add(
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
					"name", "Liferay Sample CX 2"
				).put(
					"projectName", "liferay-sample-cx-2"
				).put(
					"test.only", "true"
				).put(
					"type", "customElement"
				).build()));
		_configurationPids.add(
			ConfigurationTestUtil.createFactoryConfiguration(
				AccountEntryEmailConfiguration.class.getName(),
				"liferay-sample-cx-3/" + _companyWebId,
				HashMapDictionaryBuilder.<String, Object>put(
					".k8s.config.key",
					AccountEntryEmailConfiguration.class.getName() +
						"~liferay-sample-cx-3/" + _companyWebId
				).put(
					"baseURL", "${portalURL}/o/liferay-sample-cx-3"
				).put(
					"dxp.lxc.liferay.com.virtualInstanceId", _companyWebId
				).put(
					"name", "Liferay Sample CX 3"
				).put(
					"projectName", "liferay-sample-cx-3"
				).put(
					"test.only", "true"
				).put(
					"type", "instanceSettings"
				).build()));
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		for (String configurationPid : _configurationPids) {
			ConfigurationTestUtil.deleteConfiguration(configurationPid);
		}
	}

	@Test
	public void testGetConfiguration() {
		String pid =
			CETConfiguration.class.getName() +
				"~liferay-sample-cx-1/liferay.com";

		Configuration configuration = _getConfiguration(pid);

		Assert.assertEquals(pid, configuration.getPid());
	}

	@Test
	public void testGetConfigurationNonexistentPid() {
		String pid = "non-existent-pid";

		Assert.assertNull(_getConfiguration(pid));
	}

	@Test
	public void testGetConfigurations() throws Exception {
		_testGetConfigurations(
			List.of(),
			List.of(
				"Liferay Sample CX 1", "Liferay Sample CX 2",
				"Liferay Sample CX 3"));
		_testGetConfigurations(
			List.of("deploymentType=bundle"), List.of("Liferay Sample CX 1"));
		_testGetConfigurations(
			List.of("deploymentType=agent"),
			List.of("Liferay Sample CX 2", "Liferay Sample CX 3"));
		_testGetConfigurations(
			List.of("webId=default"),
			List.of("Liferay Sample CX 1", "Liferay Sample CX 2"));
		_testGetConfigurations(
			List.of("webId=liferay.com"),
			List.of("Liferay Sample CX 1", "Liferay Sample CX 2"));
		_testGetConfigurations(
			List.of("webId=" + _companyWebId), List.of("Liferay Sample CX 3"));
		_testGetConfigurations(
			List.of("type=customElement"),
			List.of("Liferay Sample CX 1", "Liferay Sample CX 2"));
		_testGetConfigurations(
			List.of("type=instanceSettings"), List.of("Liferay Sample CX 3"));
		_testGetConfigurations(
			List.of("deploymentType=bundle", "type=customElement"),
			List.of("Liferay Sample CX 1"));
		_testGetConfigurations(
			List.of(
				"deploymentType=agent", "webId=" + _companyWebId,
				"type=instanceSettings"),
			List.of("Liferay Sample CX 3"));

		_testGetConfigurations(List.of("name=Non Existent Name"), List.of());
		_testGetConfigurations(
			List.of("deploymentType=prod", "name=Non Existent Name"),
			List.of());
		_testGetConfigurations(List.of("nonExistentFilter=foo"), List.of());
		_testGetConfigurations(List.of("foo"), List.of());
		_testGetConfigurations(List.of("foo", "bar"), List.of());
	}

	@Test
	public void testList() throws Exception {
		String output = _captureStout(() -> _list("deploymentType=bundle"));

		String[] lines = output.split(System.lineSeparator());

		Assert.assertTrue(lines.length >= 3);

		String header = lines[0];

		Assert.assertTrue(
			header.matches(
				"\\| pid\\s*\\| name\\s*\\| type\\s*\\| webId\\s*\\|"));

		String dataRow = lines[2];

		String expectedDataPattern = StringBundler.concat(
			"\\| ", _configurationPids.get(0),
			"\\s*\\| Liferay Sample CX 1\\s*\\| customElement \\s*\\| ",
			"default\\s*\\|");

		Assert.assertTrue(
			"Row does not match pattern: \n" + dataRow,
			dataRow.matches(expectedDataPattern));
	}

	@Test
	public void testReload() throws Exception {
		String basePid = CETConfiguration.class.getName();

		_testReload(
			basePid + "~liferay-sample-cx-1/liferay.com",
			StringBundler.concat(
				"Reloaded configuration for PID ", basePid,
				"~liferay-sample-cx-1/liferay.com"));

		_testReload(
			"non-existent-pid",
			"Could not find configuration for PID non-existent-pid");
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
		_testShow(
			CETConfiguration.class.getName() +
				"~liferay-sample-cx-1/liferay.com",
			"projectName: liferay-sample-cx-1");
		_testShow(
			"non-existent-pid",
			"Could not find configuration for PID non-existent-pid");
	}

	private String _captureStout(UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		PrintStream originalPrintStream = System.out;

		try {
			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			System.setOut(new PrintStream(byteArrayOutputStream));

			unsafeRunnable.run();

			return byteArrayOutputStream.toString();
		}
		finally {
			System.setOut(originalPrintStream);
		}
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

	private void _reload(String pid) throws Exception {
		Class<?> clazz = _osgiCommands.getClass();

		Method method = clazz.getMethod("reload", String.class);

		method.invoke(_osgiCommands, pid);
	}

	private void _reloadConfiguration(Configuration configuration) {
		ReflectionTestUtil.invoke(
			_osgiCommands, "_reloadConfiguration",
			new Class<?>[] {Configuration.class}, configuration);
	}

	private void _show(String pid) throws Exception {
		Class<?> clazz = _osgiCommands.getClass();

		Method method = clazz.getMethod("show", String.class);

		method.invoke(_osgiCommands, pid);
	}

	private void _testGetConfigurations(
		List<String> filters, List<String> expectedConfigurationNames) {

		String[] filtersArray = filters.toArray(new String[0]);

		Set<String> expectedConfigurationNamesSet = new HashSet<>(
			expectedConfigurationNames);

		Configuration[] configurations = _getConfigurations(
			ArrayUtil.append(filtersArray, "test.only=true"));

		Set<String> namesFound = new HashSet<>();

		if (configurations != null) {
			for (Configuration configuration : configurations) {
				Dictionary<String, Object> properties =
					configuration.getProperties();

				namesFound.add(String.valueOf(properties.get("name")));
			}
		}

		Assert.assertEquals(expectedConfigurationNamesSet, namesFound);
	}

	private void _testReload(String pid, String expectedOutput)
		throws Exception {

		String output = _captureStout(() -> _reload(pid));

		Assert.assertTrue(output.contains(expectedOutput));
	}

	private void _testShow(String pid, String expectedOutput) throws Exception {
		String output = _captureStout(() -> _show(pid));

		Assert.assertTrue(output.contains(expectedOutput));
	}

	private static BundleContext _bundleContext;
	private static String _companyWebId;
	private static List<String> _configurationPids;

	@Inject(filter = "osgi.command.scope=clientextensions")
	private OSGiCommands _osgiCommands;

}