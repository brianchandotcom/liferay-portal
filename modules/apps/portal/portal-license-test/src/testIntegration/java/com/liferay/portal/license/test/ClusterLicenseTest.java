/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.rule.TomcatClusterTestRule;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.cluster.tomcat.TomcatCluster;
import com.liferay.portal.test.cluster.tomcat.TomcatNode;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.LicenseUtil;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Dante Wang
 * @author Jiefeng Wu
 */
@RunWith(Arquillian.class)
public class ClusterLicenseTest extends BaseLicenseTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@ClassRule
	public static final TomcatClusterTestRule tomcatClusterTestRule =
		new TomcatClusterTestRule();

	public static void assume() {
		Assume.assumeTrue(isReleaseBundle());
	}

	@BeforeClass
	public static void setUpClass() {
		_originalSystemErrPrintStream = System.err;
		_originalSystemOutPrintStream = System.out;

		System.setErr(
			new TestPrintStream(System.err, _testConsoleMessageListener));
		System.setOut(
			new TestPrintStream(System.out, _testConsoleMessageListener));
	}

	@AfterClass
	public static void tearDownClass() {
		System.setErr(_originalSystemErrPrintStream);
		System.setOut(_originalSystemOutPrintStream);
	}

	@After
	public void tearDown() throws Exception {
		TomcatCluster tomcatCluster = tomcatClusterTestRule.getTomcatCluster();

		List<TomcatNode> tomcatNodes = tomcatCluster.getTomcatNodes();

		for (TomcatNode tomcatNode : tomcatNodes) {
			tomcatNode.stop();

			tomcatNode.destroy();
		}

		tomcatNodes.clear();
	}

	@Test
	public void testEnterpriseLicense() throws Exception {
		TomcatNode tomcatNode1 = _startTomcatNode();

		tomcatNode1.syncExecute(this::_testFreeTierLicense);

		TomcatNode tomcatNode2 = _startTomcatNode();

		tomcatNode2.syncExecute(this::_testFreeTierLicense);

		TomcatNode tomcatNode3 = _startTomcatNode();

		long timestamp = tomcatNode3.syncExecute(
			() -> {
				_testFreeTierLicense();

				Method method = findMethod(
					PortalClassLoaderUtil.getClassLoader(),
					getProperty("timestamp.method"));

				return (long)method.invoke(null);
			});

		Future<String> messageFuture1 = _testConsoleMessageListener.register(
			tomcatNode1.getNodeId(), _CONSOLE_KEY_LICENSED_NODE,
			_CONSOLE_KEY_NODE_EXCEEDED);
		Future<String> messageFuture2 = _testConsoleMessageListener.register(
			tomcatNode2.getNodeId(), _CONSOLE_KEY_LICENSED_NODE,
			_CONSOLE_KEY_NODE_EXCEEDED);
		Future<String> messageFuture3 = _testConsoleMessageListener.register(
			tomcatNode3.getNodeId(), _CONSOLE_KEY_LICENSED_NODE,
			_CONSOLE_KEY_NODE_EXCEEDED);

		TomcatNode tomcatNode4 = _startTomcatNode(
			_getClusterExecutable(timestamp));

		Future<String> messageFuture4 = _testConsoleMessageListener.register(
			tomcatNode4.getNodeId(), _CONSOLE_KEY_TEMPORARY_NODE,
			_CONSOLE_KEY_NODE_EXCEEDED);

		tomcatNode4.syncExecute(this::_testFreeTierLicense);

		_testConsoleMessageListener.assertMessageListened(messageFuture1);
		_testConsoleMessageListener.assertMessageListened(messageFuture2);
		_testConsoleMessageListener.assertMessageListened(messageFuture3);
		_testConsoleMessageListener.assertMessageListened(messageFuture4);

		tomcatNode1.syncExecute(
			() -> {
				deployEnterprisePortalLicense(Time.HOUR);

				return null;
			});

		tomcatNode2.syncExecute(
			() -> {
				deployEnterprisePortalLicense(Time.HOUR);

				return null;
			});

		tomcatNode3.syncExecute(
			() -> {
				deployEnterprisePortalLicense(Time.HOUR);

				return null;
			});

		tomcatNode4.syncExecute(
			() -> {
				deployEnterprisePortalLicense(Time.HOUR);

				return null;
			});

		try {
			tomcatNode4.wait(6L, TimeUnit.MINUTES);

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertTrue(exception instanceof TimeoutException);
		}

		tomcatNode1.syncExecute(
			() -> {
				assertPortalLicenseRegistered();

				return null;
			});

		tomcatNode2.syncExecute(
			() -> {
				assertPortalLicenseRegistered();

				return null;
			});

		tomcatNode3.syncExecute(
			() -> {
				assertPortalLicenseRegistered();

				return null;
			});

		tomcatNode4.syncExecute(
			() -> {
				assertPortalLicenseRegistered();

				return null;
			});
	}

	@Test
	public void testFreeTierLicense() throws Exception {
		TomcatNode tomcatNode1 = _startTomcatNode();

		tomcatNode1.syncExecute(this::_testFreeTierLicense);

		TomcatNode tomcatNode2 = _startTomcatNode();

		tomcatNode2.syncExecute(this::_testFreeTierLicense);

		TomcatNode tomcatNode3 = _startTomcatNode();

		long timestamp = tomcatNode3.syncExecute(
			() -> {
				_testFreeTierLicense();

				Method method = findMethod(
					PortalClassLoaderUtil.getClassLoader(),
					getProperty("timestamp.method"));

				return (long)method.invoke(null);
			});

		Future<String> messageFuture1 = _testConsoleMessageListener.register(
			tomcatNode1.getNodeId(), _CONSOLE_KEY_LICENSED_NODE,
			_CONSOLE_KEY_NODE_EXCEEDED);
		Future<String> messageFuture2 = _testConsoleMessageListener.register(
			tomcatNode2.getNodeId(), _CONSOLE_KEY_LICENSED_NODE,
			_CONSOLE_KEY_NODE_EXCEEDED);
		Future<String> messageFuture3 = _testConsoleMessageListener.register(
			tomcatNode3.getNodeId(), _CONSOLE_KEY_LICENSED_NODE,
			_CONSOLE_KEY_NODE_EXCEEDED);

		TomcatNode.ClusterExecutable<Serializable> clusterExecutable =
			_getClusterExecutable(timestamp);

		TomcatNode tomcatNode4 = _startTomcatNode(clusterExecutable);
		TomcatNode tomcatNode5 = _startTomcatNode(clusterExecutable);
		TomcatNode tomcatNode6 = _startTomcatNode(clusterExecutable);

		_testConsoleMessageListener.assertMessageListened(messageFuture1);
		_testConsoleMessageListener.assertMessageListened(messageFuture2);
		_testConsoleMessageListener.assertMessageListened(messageFuture3);

		messageFuture1 = _testConsoleMessageListener.register(
			tomcatNode1.getNodeId(), _CONSOLE_KEY_FINISHED_SHUTDOWN);
		messageFuture2 = _testConsoleMessageListener.register(
			tomcatNode2.getNodeId(), _CONSOLE_KEY_FINISHED_SHUTDOWN);
		messageFuture3 = _testConsoleMessageListener.register(
			tomcatNode3.getNodeId(), _CONSOLE_KEY_FINISHED_SHUTDOWN);

		Future<String> messageFuture4 = _testConsoleMessageListener.register(
			tomcatNode4.getNodeId(), _CONSOLE_KEY_TEMPORARY_NODE,
			_CONSOLE_KEY_NODE_EXCEEDED);

		tomcatNode4.syncExecute(this::_testFreeTierLicense);

		_testConsoleMessageListener.assertMessageListened(messageFuture4);

		Future<String> messageFuture5 = _testConsoleMessageListener.register(
			tomcatNode5.getNodeId(), _CONSOLE_KEY_TEMPORARY_NODE,
			_CONSOLE_KEY_NODE_EXCEEDED);

		tomcatNode5.syncExecute(this::_testFreeTierLicense);

		_testConsoleMessageListener.assertMessageListened(messageFuture5);

		Future<String> messageFuture6 = _testConsoleMessageListener.register(
			tomcatNode6.getNodeId(), _CONSOLE_KEY_TEMPORARY_NODE,
			_CONSOLE_KEY_NODE_EXCEEDED);

		tomcatNode6.syncExecute(this::_testFreeTierLicense);

		_testConsoleMessageListener.assertMessageListened(messageFuture6);

		TomcatNode tomcatNode7 = _startTomcatNode(clusterExecutable);

		Future<String> messageFuture7 = _testConsoleMessageListener.register(
			tomcatNode7.getNodeId(), _CONSOLE_KEY_BEYOND_TEMPORARY_NODE,
			_CONSOLE_KEY_NODE_EXCEEDED);

		tomcatNode7.syncExecute(
			() -> {
				Map<String, String> licenseProperties =
					LicenseManagerUtil.getLicenseProperties("Portal");

				Assert.assertTrue(
					licenseProperties.toString(), licenseProperties.isEmpty());

				assertPortalLicenseNotRegistered();

				deployFreeTierPortalLicense(Time.HOUR);

				String response = hitHomePage("localhost", getLocalPort());

				Assert.assertTrue(response.contains(_PAGE_KEY_EXCEEDED_LIMIT));

				return null;
			});

		_testConsoleMessageListener.assertMessageListened(messageFuture7);

		tomcatNode4.wait(6L, TimeUnit.MINUTES);
		tomcatNode5.wait(6L, TimeUnit.MINUTES);
		tomcatNode6.wait(6L, TimeUnit.MINUTES);
		tomcatNode7.wait(6L, TimeUnit.MINUTES);

		_testConsoleMessageListener.assertMessageListened(messageFuture1);
		_testConsoleMessageListener.assertMessageListened(messageFuture2);
		_testConsoleMessageListener.assertMessageListened(messageFuture3);
	}

	private TomcatNode.ClusterExecutable<Serializable> _getClusterExecutable(
		long timestamp) {

		return () -> {
			Field field = findField(
				PortalClassLoaderUtil.getClassLoader(),
				getProperty("grace.period.end.field"));

			field.setAccessible(true);

			field.setLong(null, timestamp + (5L * Time.MINUTE));

			return null;
		};
	}

	@SafeVarargs
	private TomcatNode _startTomcatNode(
			TomcatNode.ClusterExecutable<Serializable>...
				additionalClusterExecutables)
		throws Exception {

		TomcatCluster.Builder builder = tomcatClusterTestRule.buildTomcatNode();

		TomcatNode tomcatNode = builder.build();

		tomcatNode.start(true);

		String path = tomcatNode.getLiferayHome(
		).concat(
			"/data/license"
		);

		tomcatNode.syncExecute(
			() -> {
				disableValidate();
				setVersion("2026.Q1.0 LTS");

				ReflectionTestUtil.setFieldValue(
					LicenseUtil.class, "LICENSE_REPOSITORY_DIR", path);

				return null;
			});

		for (TomcatNode.ClusterExecutable<Serializable> clusterExecutable :
				additionalClusterExecutables) {

			tomcatNode.syncExecute(clusterExecutable);
		}

		return tomcatNode;
	}

	private Serializable _testFreeTierLicense() throws Exception {
		Map<String, String> licenseProperties =
			LicenseManagerUtil.getLicenseProperties("Portal");

		Assert.assertTrue(
			licenseProperties.toString(), licenseProperties.isEmpty());

		assertPortalLicenseNotRegistered();

		deployFreeTierPortalLicense(Time.HOUR);

		assertLicensePropertiesExisted(getPortalProductId());

		assertPortalLicenseRegistered();

		return null;
	}

	private static final String _CONSOLE_KEY_BEYOND_TEMPORARY_NODE =
		"This current node is beyond the temporarily permitted node count " +
			"and is deactivatedand will automatically shut down after the " +
				"grace period expires";

	private static final String _CONSOLE_KEY_FINISHED_SHUTDOWN =
		"Finished shutting down overloaded nodes";

	private static final String _CONSOLE_KEY_LICENSED_NODE =
		"This current node is within the licensed node count and will not be " +
			"automatically deactivated nor shut down after the grace period " +
				"expires";

	private static final String _CONSOLE_KEY_NODE_EXCEEDED =
		"The maximum number of 3 nodes licensed for this cluster has been " +
			"exceeded";

	private static final String _CONSOLE_KEY_TEMPORARY_NODE =
		"This current node is within the temporarily permitted node count " +
			"and will be automatically deactivated and shut down after the " +
				"grace period expires";

	private static final String _PAGE_KEY_EXCEEDED_LIMIT =
		"You have exceeded the developer mode connection limit";

	private static PrintStream _originalSystemErrPrintStream;
	private static PrintStream _originalSystemOutPrintStream;
	private static final TestConsoleMessageListener
		_testConsoleMessageListener = new TestConsoleMessageListener();

	private static class TestConsoleMessageListener {

		public void assertMessageListened(Future<String> future)
			throws Exception {

			Assert.assertNotNull(future.get(3, TimeUnit.MINUTES));
		}

		public void onMessage(String message) {
			if (!message.startsWith(_PREFIX)) {
				return;
			}

			int index = message.indexOf(CharPool.CLOSE_BRACKET);

			if (index == -1) {
				return;
			}

			String nodeName = message.substring(0, index + 1);

			Map<String[], CompletableFuture<String>> completableFutures =
				_completableFuturesMap.get(nodeName);

			if (completableFutures == null) {
				return;
			}

			Set<Map.Entry<String[], CompletableFuture<String>>> entries =
				completableFutures.entrySet();

			entries.removeIf(
				entry -> {
					if (_hasKeyword(entry.getKey(), message)) {
						CompletableFuture<String> completableFuture =
							entry.getValue();

						completableFuture.complete(message);

						return true;
					}

					return false;
				});
		}

		public Future<String> register(int nodeId, String... keywords) {
			CompletableFuture<String> completableFuture =
				new CompletableFuture<>();

			Map<String[], CompletableFuture<String>> completableFutures =
				_completableFuturesMap.computeIfAbsent(
					_getKey(nodeId), key -> new ConcurrentHashMap<>());

			completableFutures.put(keywords, completableFuture);

			return completableFuture;
		}

		private String _getKey(int nodeId) {
			return _PREFIX + nodeId + StringPool.CLOSE_BRACKET;
		}

		private boolean _hasKeyword(String[] keywords, String message) {
			for (String keyword : keywords) {
				if (!message.contains(keyword)) {
					return false;
				}
			}

			return true;
		}

		private static final String _PREFIX = "[TomcatNode-";

		private final Map<String, Map<String[], CompletableFuture<String>>>
			_completableFuturesMap = new ConcurrentHashMap<>();

	}

	private static class TestPrintStream extends PrintStream {

		public TestPrintStream(
			OutputStream outputStream,
			TestConsoleMessageListener testConsoleMessageListener) {

			super(outputStream);

			_testConsoleMessageListener = testConsoleMessageListener;
		}

		@Override
		public void print(String message) {
			super.print(message);

			for (String line : StringUtil.split(message, CharPool.NEW_LINE)) {
				_testConsoleMessageListener.onMessage(line);
			}
		}

		private final TestConsoleMessageListener _testConsoleMessageListener;

	}

}