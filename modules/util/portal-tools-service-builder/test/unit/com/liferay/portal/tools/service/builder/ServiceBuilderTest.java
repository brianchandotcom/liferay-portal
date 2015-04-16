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

package com.liferay.portal.tools.service.builder;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelHintsUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import java.net.URL;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Raymond Augé
 */
public class ServiceBuilderTest {

	@BeforeClass
	public static void setUpClass() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		List<String> inputArguments = runtimeMXBean.getInputArguments();

		_debugProcess = inputArguments.toString().indexOf("jdwp") >= 0;

		System.setProperty("system.properties.quiet", "true");
	}

	@Test
	public void testDirectInvocation() throws Exception {
		final URL baseUrl = ServiceBuilderTest.class.getResource("/");
		final URL modelHintsUrl = ServiceBuilderTest.class.getResource(
			"dependencies/bookmarks-model-hints.xml");
		final URL resourceActionsUrl = ServiceBuilderTest.class.getResource(
			"dependencies/bookmarks-resource-actions.xml");
		final URL serviceUrl = ServiceBuilderTest.class.getResource(
			"dependencies/bookmarks-service.xml");

		File baseDir = temporaryFolder.getRoot();

		final Path basePath = baseDir.toPath();

		String apiDir = basePath.resolve("service/src").toString();
		boolean autoImportDefaultReferences = true;
		boolean autoNamespaceTables = false;
		String beanLocatorUtil =
			"com.liferay.portal.kernel.bean.PortalBeanLocatorUtil";
		long buildNumber = 1;
		boolean buildNumberIncrement = true;
		String hbmFileName = basePath.resolve(
			"resource/META-INF/portal-hbm.xml").toString();
		String implDir = basePath.resolve("impl/src").toString();
		String inputFileName = serviceUrl.getFile();
		String modelHintsFileName = basePath.resolve(
			"resource/META-INF/portal-model-hints.xml").toString();
		boolean osgiModule = false;
		String pluginName = "bookmarks-service";
		String propsUtil = "com.liferay.portal.util.PropsUtil";
		String[] readOnlyPrefixes = new String[] {
			"fetch", "get", "has", "is", "load", "reindex", "search"};
		String remotingFileName = basePath.resolve(
			"resource/META-INF/remoting-servlet.xml").toString();
		String resourcesDir = basePath.resolve("impl/src").toString();
		String springFileName = basePath.resolve(
			"resource/META-INF/portal-spring.xml").toString();
		String[] springNamespaces = new String[] {"beans"};
		String sqlDir = basePath.resolve("sql").toString();
		String sqlFileName = "portal-tables.sql";
		String sqlIndexesFileName = "indexes.sql";
		String sqlSequencesFileName = "sequences.sql";
		String targetEntityName = null;
		String testDir = basePath.resolve("test/integration").toString();

		Set<String> resourceActionModels =
			ServiceBuilder.readResourceActionModels(
				baseUrl.getPath(), new String[] {resourceActionsUrl.getFile()});

		String[] modelHintsConfigs = new String[] {
			"classpath*:META-INF/portal-model-hints.xml",
			modelHintsUrl.getFile()};

		ModelHintsImpl modelHintsImpl = new ModelHintsImpl();

		modelHintsImpl.setModelHintsConfigs(modelHintsConfigs);
		modelHintsImpl.afterPropertiesSet();

		ModelHintsUtil modelHintsUtil = new ModelHintsUtil();

		modelHintsUtil.setModelHints(modelHintsImpl);

		Assert.assertTrue(
			resourceActionModels.contains(
				"com.liferay.bookmarks.model.BookmarksEntry"));
		Assert.assertTrue(
			resourceActionModels.contains("com.liferay.bookmarks"));
		Assert.assertFalse(resourceActionModels.contains("com.liferay.test"));

		try {
			new ServiceBuilder(
				apiDir, autoImportDefaultReferences, autoNamespaceTables,
				beanLocatorUtil, buildNumber, buildNumberIncrement, hbmFileName,
				implDir, inputFileName, modelHintsFileName, osgiModule,
				pluginName, propsUtil, readOnlyPrefixes, remotingFileName,
				resourceActionModels, resourcesDir, springFileName,
				springNamespaces, sqlDir, sqlFileName, sqlIndexesFileName,
				sqlSequencesFileName, targetEntityName, testDir, true);

			Assert.assertTrue(true);
		}
		catch (Exception e) {
			e.printStackTrace();

			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testDirectInvocationWithSQLDir() throws Exception {
		final URL baseUrl = ServiceBuilderTest.class.getResource("/");
		final URL modelHintsUrl = ServiceBuilderTest.class.getResource(
			"dependencies/bookmarks-model-hints.xml");
		final URL resourceActionsUrl = ServiceBuilderTest.class.getResource(
			"dependencies/bookmarks-resource-actions.xml");
		final URL serviceUrl = ServiceBuilderTest.class.getResource(
			"dependencies/bookmarks-service.xml");

		File baseDir = temporaryFolder.getRoot();

		File sqlDir = new File(baseDir, "sql");

		sqlDir.mkdirs();

		final Path basePath = baseDir.toPath();

		String apiDir = basePath.resolve("service/src").toString();
		boolean autoImportDefaultReferences = true;
		boolean autoNamespaceTables = false;
		String beanLocatorUtil =
			"com.liferay.portal.kernel.bean.PortalBeanLocatorUtil";
		long buildNumber = 1;
		boolean buildNumberIncrement = true;
		String hbmFileName = basePath.resolve(
			"resource/META-INF/portal-hbm.xml").toString();
		String implDir = basePath.resolve("impl/src").toString();
		String inputFileName = serviceUrl.getFile();
		String modelHintsFileName = basePath.resolve(
			"resource/META-INF/portal-model-hints.xml").toString();
		boolean osgiModule = false;
		String pluginName = "bookmarks-service";
		String propsUtil = "com.liferay.portal.util.PropsUtil";
		String[] readOnlyPrefixes = new String[] {
			"fetch", "get", "has", "is", "load", "reindex", "search"};
		String remotingFileName = basePath.resolve(
			"resource/META-INF/remoting-servlet.xml").toString();
		String resourcesDir = basePath.resolve("impl/src").toString();
		String springFileName = basePath.resolve(
			"resource/META-INF/portal-spring.xml").toString();
		String[] springNamespaces = new String[] {"beans"};
		String sqlDirName = basePath.resolve("sql").toString();
		String sqlFileName = "portal-tables.sql";
		String sqlIndexesFileName = "indexes.sql";
		String sqlSequencesFileName = "sequences.sql";
		String targetEntityName = null;
		String testDir = basePath.resolve("test/integration").toString();

		Set<String> resourceActionModels =
			ServiceBuilder.readResourceActionModels(
				baseUrl.getPath(), new String[] {resourceActionsUrl.getFile()});

		String[] modelHintsConfigs = new String[] {
			"classpath*:META-INF/portal-model-hints.xml",
			modelHintsUrl.getFile()};

		ModelHintsImpl modelHintsImpl = new ModelHintsImpl();

		modelHintsImpl.setModelHintsConfigs(modelHintsConfigs);
		modelHintsImpl.afterPropertiesSet();

		ModelHintsUtil modelHintsUtil = new ModelHintsUtil();

		modelHintsUtil.setModelHints(modelHintsImpl);

		Assert.assertTrue(
			resourceActionModels.contains(
				"com.liferay.bookmarks.model.BookmarksEntry"));
		Assert.assertTrue(
			resourceActionModels.contains("com.liferay.bookmarks"));
		Assert.assertFalse(resourceActionModels.contains("com.liferay.test"));

		try {
			new ServiceBuilder(
				apiDir, autoImportDefaultReferences, autoNamespaceTables,
				beanLocatorUtil, buildNumber, buildNumberIncrement, hbmFileName,
				implDir, inputFileName, modelHintsFileName, osgiModule,
				pluginName, propsUtil, readOnlyPrefixes, remotingFileName,
				resourceActionModels, resourcesDir, springFileName,
				springNamespaces, sqlDirName, sqlFileName, sqlIndexesFileName,
				sqlSequencesFileName, targetEntityName, testDir, true);

			Assert.assertTrue(true);
		}
		catch (Exception e) {
			e.printStackTrace();

			Assert.fail(e.getMessage());
		}
	}

	@Test(expected = NullPointerException.class)
	public void testMainNoArgs() throws Exception {
		call(new Quietly() {

			@Override
			public void call() throws Exception {
				ServiceBuilder.main(new String[0]);
			}

		});
	}

	@Test
	public void testMainThrowMainExceptionFalse() throws Exception {
		call(new Quietly() {

			@Override
			public void call() throws Exception {
				String[] args = new String[] {
					"tools.throw.main.exception=false"};

				ServiceBuilder.main(args);
			}

		});
	}

	@Test
	public void testStandaloneExecution() throws Exception {
		URL baseUrl = ServiceBuilderTest.class.getResource("/");
		URL modelHintsUrl = ServiceBuilderTest.class.getResource(
			"dependencies/bookmarks-model-hints.xml");
		URL resourceActionsUrl = ServiceBuilderTest.class.getResource(
			"dependencies/bookmarks-resource-actions.xml");
		URL serviceUrl = ServiceBuilderTest.class.getResource(
			"dependencies/bookmarks-service-basic1.xml");

		File baseDir = temporaryFolder.getRoot();

		Path basePath = baseDir.toPath();

		String[] args = append(
			getBaseArgs(baseUrl),
			"service.api.dir=" + basePath.resolve("api").toString(),
			"service.auto.import.default.references=true",
			"service.auto.namespace.tables=false",
			"service.bean.locator.util=" +
				"com.liferay.portal.kernel.bean.PortalBeanLocatorUtil",
			"service.build.number=1", "service.build.number.increment=true",
			"service.hbm.file=" + basePath.resolve(
				"resource/portal-hbm.xml").toString(),
			"service.impl.dir=" + basePath.resolve("impl").toString(),
			"service.input.file=" + serviceUrl.getPath(),
			"service.model.hints.configs=" +
				"classpath*:META-INF/portal-model-hints.xml," +
				modelHintsUrl.getFile(),
			"service.model.hints.file=" + basePath.resolve(
				"resource/portal-model-hints.xml").toString(),
			"service.osgi.module=false", "service.plugin.name=",
			"service.props.util=com.liferay.portal.util.PropsUtil",
			"service.resource.actions.configs=" + resourceActionsUrl.getFile(),
			"service.remoting.file=" + basePath.resolve(
				"resource/remoting-servlet.xml").toString(),
			"service.resources.dir=" + basePath.resolve("impl").toString(),
			"service.spring.file=" + basePath.resolve(
				"resource/portal-spring.xml").toString(),
			"service.spring.namespaces=beans",
			"service.sql.dir=" + basePath.resolve("sql").toString(),
			"service.sql.file=portal-tables.sql",
			"service.sql.indexes.file=indexes.sql",
			"service.sql.sequences.file=sequences.sql",
			"service.test.dir=" + basePath.resolve("test").toString());

		if (_debugProcess) {
			System.out.println(
				"Test is executing command line:\n\t" +
					StringUtil.merge(args, " "));
		}

		ProcessBuilder processBuilder = new ProcessBuilder().inheritIO();

		processBuilder.command(args);

		Process process = processBuilder.start();

		Assert.assertEquals(0, process.waitFor());
	}

	@Test
	public void testSynopsisOutput() throws Exception {
		URL synopsisUrl = ServiceBuilderTest.class.getResource(
			"dependencies/synopsis.txt");

		String synopsis = IOUtils.toString(synopsisUrl.openStream());

		UnsyncByteArrayOutputStream ubaos = call(new Quietly() {

			@Override
			public void call() throws Exception {
				String[] args = new String[] {
					"tools.throw.main.exception=false"};

				ServiceBuilder.main(args);
			}

		});

		String output = ubaos.toString();

		Assert.assertEquals(synopsis, output);
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private String[] append(String[] args1, String ... args2) {
		int newLength = args1.length + args2.length;

		String[] newArgs = new String[newLength];

		System.arraycopy(args1, 0, newArgs, 0, args1.length);
		System.arraycopy(args2, 0, newArgs, args1.length, args2.length);

		return newArgs;
	}

	private UnsyncByteArrayOutputStream call(Quietly call) throws Exception {
		PrintStream originalErr = System.err;
		PrintStream originalOut = System.out;

		UnsyncByteArrayOutputStream ubaos = new UnsyncByteArrayOutputStream();

		PrintStream newOut = new PrintStream(ubaos);
		PrintStream newErr = new PrintStream(
			new OutputStream() {

				@Override
				public void write(int b) throws IOException {
				}

			});

		try {
			System.setErr(newErr);
			System.setOut(newOut);

			call.call();

			return ubaos;
		}
		finally {
			System.setOut(originalErr);
			System.setOut(originalOut);
		}
	}

	private String[] getBaseArgs(URL baseUrl) {
		String classpath = getClasspath(baseUrl);

		String debug1 = "-D1";
		String debug2 = "-D2";

		if (_debugProcess) {
			debug1 = "-Xdebug";
			debug2 =
				"-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8001";
		}

		return new String[] {
			"java", debug1, debug2, "-Dsystem.properties.quiet=true",
			"-classpath", classpath, ServiceBuilder.class.getName(),
			"tools.throw.main.exception=false"
		};
	}

	private String getClasspath(URL baseUrl) {
		File file = new File(baseUrl.getFile());
		File libDir = new File(file.getParentFile(), "lib");

		file = getServiceBuilderLibrary();

		List<String> classpath = new ArrayList<>();

		classpath.add(file.getAbsolutePath());

		for (File curFile : libDir.listFiles()) {
			if (!curFile.isDirectory()) {
				classpath.add(curFile.getAbsolutePath());
			}
		}

		return StringUtil.merge(classpath.toArray(), File.pathSeparator);
	}

	private File getServiceBuilderLibrary() {
		File distDir = new File(System.getProperty("sdk.dir"), "dist");

		if (!distDir.exists()) {
			Assert.fail(
				"Please execute 'ant jar' in the service-builder module dir. " +
					"The resulting jar will be tested.");
		}

		File[] files = distDir.listFiles(
			new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if (name.startsWith(
							"com.liferay.portal.tools.service.builder-") &&
						name.endsWith(".jar") &&
						!name.endsWith("-sources.jar")) {

						return true;
					}

					return false;
				}

			});

		if (files.length == 0) {
			Assert.fail(
				"Please execute 'ant jar' in the service-builder module dir. " +
					"The resulting jar will be tested.");
		}
		else if (files.length > 1) {
			Assert.fail(
				"There are more than one possible service-builder jars in " +
					"the dist directory. Please delete all but the most " +
						"recent one. This one will be tested.");
		}

		return files[0];
	}

	private static boolean _debugProcess;

	private interface Quietly {

		public void call() throws Exception;

	}

}