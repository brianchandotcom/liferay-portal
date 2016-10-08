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

package com.liferay.portal.security.pacl;

import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessChannel;
import com.liferay.portal.kernel.process.ProcessConfig;
import com.liferay.portal.kernel.process.ProcessConfig.Builder;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.local.LocalProcessExecutor;
import com.liferay.portal.kernel.process.local.LocalProcessLauncher.ProcessContext;
import com.liferay.portal.kernel.process.log.ProcessOutputStream;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.ci.AutoBalanceTestCase;
import com.liferay.portal.kernel.test.junit.BridgeJUnitTestRunner;
import com.liferay.portal.kernel.test.junit.BridgeJUnitTestRunner.BridgeRunListener;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.rule.ExpectedLogs;
import com.liferay.portal.test.rule.HypersonicServerTestRule;
import com.liferay.portal.test.rule.PACLTestRule;
import com.liferay.portal.test.rule.callback.LogAssertionTestCallback;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.log4j.Log4JUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Serializable;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Future;

import javax.naming.Context;

import org.junit.Assume;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(BridgeJUnitTestRunner.class)
public class PACLAggregateTest extends AutoBalanceTestCase {

	@ClassRule
	public static final HypersonicServerTestRule hypersonicServerTestRule =
		HypersonicServerTestRule.INSTANCE;

	@Test
	public void testPACLTests() throws Exception {
		LocalProcessExecutor localProcessExecutor = new LocalProcessExecutor();

		try {
			List<Class<?>> classes = scanTestClasses();

			Assume.assumeFalse(
				"No PACL test available, skip running.", classes.isEmpty());

			ProcessChannel<Result> processChannel =
				localProcessExecutor.execute(
					createProcessConfig(),
					new PACLTestsProcessCallable(classes));

			Future<Result> future = processChannel.getProcessNoticeableFuture();

			future.get();
		}
		finally {
			localProcessExecutor.destroy();
		}
	}

	protected ProcessConfig createProcessConfig() {
		Builder builder = new Builder();

		List<String> arguments = new ArrayList<>();

		arguments.add(_JVM_XMX);
		arguments.add(_JVM_MAX_PERM_SIZE);
		arguments.add("-Djava.security.manager");

		URL url = PACLAggregateTest.class.getResource("security.policy");

		arguments.add("-Djava.security.policy==" + url.getFile());

		arguments.add("-Dliferay.mode=test");

		boolean junitDebug = Boolean.getBoolean("jvm.debug");

		if (junitDebug) {
			arguments.add(_JPDA_OPTIONS);
			arguments.add("-Djvm.debug=true");
		}

		arguments.add(
			"-D" + PropsKeys.LIFERAY_LIB_PORTAL_DIR + "=" +
				PropsValues.LIFERAY_LIB_PORTAL_DIR);
		arguments.add(
			"-Dportal:" + PropsKeys.CLUSTER_LINK_AUTODETECT_ADDRESS +
				StringPool.EQUAL);
		arguments.add(
			"-Dportal:" + PropsKeys.MODULE_FRAMEWORK_PROPERTIES +
				_OSGI_CONSOLE);

		for (String property : hypersonicServerTestRule.getJdbcProperties()) {
			arguments.add("-D" + property);
		}

		builder.setArguments(arguments);
		builder.setBootstrapClassPath(System.getProperty("java.class.path"));
		builder.setReactClassLoader(PACLAggregateTest.class.getClassLoader());

		return builder.build();
	}

	protected List<Class<?>> scanTestClasses() throws ClassNotFoundException {
		URL url = PACLAggregateTest.class.getResource("test");

		File folder = new File(url.getFile());

		File[] files = folder.listFiles(
			new FileFilter() {

				@Override
				public boolean accept(File file) {
					if (!file.isFile()) {
						return false;
					}

					String fileName = file.getName();

					if (fileName.indexOf('$') != -1) {
						return false;
					}

					return fileName.endsWith(".class");
				}

			});

		Arrays.sort(files);

		if (isCIMode()) {
			files = slice(files);
		}

		Package pkg = PACLAggregateTest.class.getPackage();

		String packageName = pkg.getName();

		packageName = packageName.concat(".test.");

		ClassLoader classLoader = PACLAggregateTest.class.getClassLoader();

		List<Class<?>> classes = new ArrayList<>();

		for (File file : files) {
			String fileName = file.getName();

			classes.add(
				classLoader.loadClass(
					packageName.concat(
						fileName.substring(0, fileName.lastIndexOf('.')))));
		}

		return classes;
	}

	private static final String _JPDA_OPTIONS =
		"-agentlib:jdwp=transport=dt_socket,address=8001,server=y,suspend=y";

	private static final String _JVM_MAX_PERM_SIZE = "-XX:MaxPermSize=256m";

	private static final String _JVM_XMX = "-Xmx1024m";

	private static final String _OSGI_CONSOLE = "osgi.console=localhost:11312";

	private static class DummySocksProxySelector extends ProxySelector {

		@Override
		public void connectFailed(
			URI uri, SocketAddress socketAddress, IOException ioe) {
		}

		@Override
		public List<Proxy> select(URI uri) {
			if (_socketAddresses.contains(
					uri.getHost() + StringPool.COLON + uri.getPort())) {

				return Collections.singletonList(
					new Proxy(Type.HTTP, new InetSocketAddress(0)));
			}

			return Collections.singletonList(Proxy.NO_PROXY);
		}

		private DummySocksProxySelector() throws ProcessException {
			Properties properties = new Properties();

			try {
				properties.load(
					PACLTestsProcessCallable.class.getResourceAsStream(
						"test/dependencies/WEB-INF/liferay-plugin-package." +
							"properties"));
			}
			catch (IOException ioe) {
				throw new ProcessException(ioe);
			}

			for (String socketAddress :
					StringUtil.split(
						properties.getProperty(
							"security-manager-sockets-connect"))) {

				if (!socketAddress.startsWith("localhost")) {
					_socketAddresses.add(socketAddress);
				}
			}
		}

		private final Set<String> _socketAddresses = new HashSet<>();

	}

	private static class PACLTestsProcessCallable
		implements ProcessCallable<Result> {

		@Override
		public Result call() throws ProcessException {
			ProxySelector.setDefault(new DummySocksProxySelector());

			URL resource = PACLTestRule.class.getResource(
				"pacl-test.properties");

			if (resource != null) {
				System.setProperty("external-properties", resource.getPath());
			}

			System.setProperty(
				Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.naming.java.javaURLContextFactory");

			System.setProperty("catalina.base", ".");

			List<CaptureAppender> captureAppenders = null;

			Path tempStatePath = null;

			try {
				tempStatePath = Files.createTempDirectory(null);

				System.setProperty(
					"portal:" + PropsKeys.MODULE_FRAMEWORK_STATE_DIR,
					tempStatePath.toString());

				com.liferay.portal.kernel.util.PropsUtil.setProps(
					new PropsImpl());

				SystemProperties.set(
					"log4j.configure.on.startup", StringPool.FALSE);

				Log4JUtil.configureLog4J(
					PACLTestsProcessCallable.class.getClassLoader());

				captureAppenders = LogAssertionTestCallback.startAssert(
					Collections.<ExpectedLogs>emptyList());

				return BridgeJUnitTestRunner.runBridgeTests(
					new ProcessBridgeRunListener(PACLAggregateTest.class),
					_classes.toArray(new Class<?>[_classes.size()]));
			}
			catch (IOException ioe) {
				throw new ProcessException(ioe);
			}
			finally {
				InitUtil.stopRuntime();

				InitUtil.stopModuleFramework();

				MPIHelperUtil.shutdown();

				if (tempStatePath != null) {
					try {
						Files.walkFileTree(
							tempStatePath,
							new SimpleFileVisitor<Path>() {

								@Override
								public FileVisitResult postVisitDirectory(
										Path path, IOException ioe)
									throws IOException {

									Files.delete(path);

									return FileVisitResult.CONTINUE;
								}

								@Override
								public FileVisitResult visitFile(
										Path path,
										BasicFileAttributes basicFileAttributes)
									throws IOException {

									Files.delete(path);

									return FileVisitResult.CONTINUE;
								}

							});
					}
					catch (IOException ioe) {
						throw new ProcessException(ioe);
					}

					LogAssertionTestCallback.endAssert(
						Collections.<ExpectedLogs>emptyList(),
						captureAppenders);
				}
			}
		}

		@Override
		public String toString() {
			return "PACLTestSuite";
		}

		private PACLTestsProcessCallable(List<Class<?>> classes) {
			_classes = classes;
		}

		private static final long serialVersionUID = 1L;

		private final List<Class<?>> _classes;

	}

	private static class ProcessBridgeRunListener extends BridgeRunListener {

		@Override
		protected void bridge(final String methodName, final Object argument) {
			ProcessOutputStream processOutputStream =
				ProcessContext.getProcessOutputStream();

			try {
				processOutputStream.writeProcessCallable(
					new ProcessCallable<Serializable>() {

						@Override
						public Serializable call() {
							ReflectionTestUtil.invoke(
								BridgeJUnitTestRunner.getRunNotifier(testClass),
								methodName,
								new Class<?>[] {argument.getClass()}, argument);

							return null;
						}

						private static final long serialVersionUID = 1L;

					});
			}
			catch (IOException ioe) {
				ReflectionUtil.throwException(ioe);
			}
		}

		private ProcessBridgeRunListener(Class<?> testClass) {
			super(testClass);
		}

		private static final long serialVersionUID = 1L;

	}

}