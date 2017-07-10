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

package com.liferay.portal.osgi.web.wab.generator.internal.processor;

import aQute.bnd.header.Attrs;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Domain;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Resource;

import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.security.xml.SecureXMLFactoryProviderImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.xml.SAXReaderImpl;

import java.io.File;
import java.io.IOException;

import java.net.URISyntaxException;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class WabProcessorTest {

	@BeforeClass
	public static void setUpClass() {
		com.liferay.portal.kernel.util.PropsUtil.setProps(new PropsImpl());

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(new HttpImpl());

		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		SAXReaderImpl secureSAXReader = new SAXReaderImpl();

		secureSAXReader.setSecure(true);

		saxReaderUtil.setSAXReader(secureSAXReader);

		SecureXMLFactoryProviderUtil secureXMLFactoryProviderUtil =
			new SecureXMLFactoryProviderUtil();

		secureXMLFactoryProviderUtil.setSecureXMLFactoryProvider(
			new SecureXMLFactoryProviderImpl());

		UnsecureSAXReaderUtil unsecureSAXReaderUtil =
			new UnsecureSAXReaderUtil();

		SAXReaderImpl unsecureSAXReader = new SAXReaderImpl();

		unsecureSAXReaderUtil.setSAXReader(unsecureSAXReader);
	}

	@Test
	public void testClassicThemeWab() throws Exception {
		File file = getFile("classic-theme.autodeployed.war");

		Assert.assertNotNull(file);

		try (Jar jar = new Jar(file)) {
			Assert.assertNull(jar.getBsn());

			Map<String, Resource> resources = jar.getResources();

			Assert.assertEquals(resources.toString(), 1244, resources.size());
		}

		Map<String, String[]> parameters = new HashMap<>();

		parameters.put("Bundle-Version", new String[] {"7.0.0.8"});
		parameters.put("Web-ContextPath", new String[] {"/classic-theme"});

		WabProcessor wabProcessor = new TestWabProcessor(
			getClassLoader(), file, parameters);

		File processedFile = wabProcessor.getProcessedFile();

		Assert.assertNotNull(processedFile);

		try (Jar jar = new Jar(processedFile)) {
			Map<String, Map<String, Resource>> directories =
				jar.getDirectories();
			Map<String, Resource> resources = jar.getResources();

			// Check to see that the right number of resources are in the WAB.

			Assert.assertEquals(resources.toString(), 1240, resources.size());

			// Check if the basic metadata is correct.

			Assert.assertEquals("classic-theme", jar.getBsn());
			Assert.assertEquals("7.0.0.8", jar.getVersion());

			// Assert that the Bundle-ClassPath is properly formed to our
			// conventions.

			Domain domain = Domain.domain(jar.getManifest());

			Parameters bundleClassPath = domain.getBundleClassPath();

			Assert.assertEquals(4, bundleClassPath.size());
			Assert.assertTrue(
				bundleClassPath.containsKey("ext/WEB-INF/classes"));

			for (String bundleClassPathEntry : bundleClassPath.keySet()) {
				if (bundleClassPathEntry.equals("ext/WEB-INF/classes")) {
					Assert.assertNull(resources.get(bundleClassPathEntry));
				}
				else if (bundleClassPathEntry.equals("WEB-INF/classes")) {
					Assert.assertNull(resources.get(bundleClassPathEntry));
					Assert.assertTrue(
						directories.containsKey(bundleClassPathEntry));
				}
				else {

					// Check that all the libraries on the Bundle-ClassPath
					// exist in the WAB.

					Assert.assertNotNull(resources.get(bundleClassPathEntry));
				}
			}

			Parameters importedPackages = domain.getImportPackage();

			// Check basic servlet and jsp packages are imported

			Assert.assertTrue(importedPackages.containsKey("javax.servlet"));
			Assert.assertTrue(
				importedPackages.containsKey("javax.servlet.http"));

			// Check if packages declared in portal property
			// module.framework.web.generator.default.servlet.packages are
			// included.

			Assert.assertTrue(
				importedPackages.containsKey("com.liferay.portal.model"));
			Assert.assertTrue(
				importedPackages.containsKey("com.liferay.portal.service"));
			Assert.assertTrue(
				importedPackages.containsKey(
					"com.liferay.portal.servlet.filters.aggregate"));
			Assert.assertTrue(
				importedPackages.containsKey(
					"com.liferay.portal.osgi.web.servlet.jsp.compiler"));
			Assert.assertTrue(
				importedPackages.containsKey(
					"com.liferay.portal.spring.context"));
			Assert.assertTrue(
				importedPackages.containsKey("com.liferay.portal.util"));
			Assert.assertTrue(
				importedPackages.containsKey("com.liferay.portlet"));
			Assert.assertTrue(importedPackages.containsKey("com.sun.el"));
			Assert.assertTrue(
				importedPackages.containsKey(
					"org.apache.commons.chain.generic"));
			Assert.assertTrue(
				importedPackages.containsKey("org.apache.naming.java"));

			// Check if packages only referenced in web.xml are imported.

			Assert.assertTrue(
				importedPackages.containsKey(
					"com.liferay.portal.kernel.servlet.filters.invoker"));
			Assert.assertTrue(
				importedPackages.containsKey("com.liferay.portal.webserver"));
		}
	}

	@Test
	public void testSampleStrutsWab() throws Exception {
		File file = getFile("sample-struts-portlet.autodeployed.war");

		Assert.assertNotNull(file);

		try (Jar jar = new Jar(file)) {
			Assert.assertNull(jar.getBsn());

			Map<String, Resource> resources = jar.getResources();

			Assert.assertEquals(resources.toString(), 217, resources.size());
		}

		Map<String, String[]> parameters = new HashMap<>();

		parameters.put("Bundle-Version", new String[] {"7.0.0.3"});
		parameters.put(
			"Web-ContextPath", new String[] {"/sample-struts-portlet"});

		WabProcessor wabProcessor = new TestWabProcessor(
			getClassLoader(), file, parameters);

		File processedFile = wabProcessor.getProcessedFile();

		Assert.assertNotNull(processedFile);

		try (Jar jar = new Jar(processedFile)) {
			Map<String, Map<String, Resource>> directories =
				jar.getDirectories();
			Map<String, Resource> resources = jar.getResources();

			// Check to see that the right number of resources are in the WAB.

			Assert.assertEquals(resources.toString(), 207, resources.size());

			// Check if the basic metadata is correct.

			Assert.assertEquals("sample-struts-portlet", jar.getBsn());
			Assert.assertEquals("7.0.0.3", jar.getVersion());

			// Assert that the Bundle-ClassPath is properly formed to our
			// conventions.

			Domain domain = Domain.domain(jar.getManifest());

			Parameters bundleClassPath = domain.getBundleClassPath();

			Assert.assertEquals(16, bundleClassPath.size());
			Assert.assertTrue(
				bundleClassPath.containsKey("ext/WEB-INF/classes"));

			for (String bundleClassPathEntry : bundleClassPath.keySet()) {
				if (bundleClassPathEntry.equals("ext/WEB-INF/classes")) {
					Assert.assertNull(resources.get(bundleClassPathEntry));
				}
				else if (bundleClassPathEntry.equals("WEB-INF/classes")) {
					Assert.assertNull(resources.get(bundleClassPathEntry));
					Assert.assertTrue(
						directories.containsKey(bundleClassPathEntry));
				}
				else {

					// Check that all the libraries on the Bundle-ClassPath
					// exist in the WAB.

					Assert.assertNotNull(resources.get(bundleClassPathEntry));
				}
			}

			// Check that a library which was declared in
			// portal-dependency-jars but which is provided by the portal's
			// system bundle IS NOT in the WAB.

			Assert.assertNull(
				resources.get("WEB-INF/lib/commons-beanutils.jar"));
			Assert.assertNull(resources.get("WEB-INF/lib/commons-lang.jar"));

			// Check that a library which was declared in
			// portal-dependency-jars but which is NOT provided by the portal's
			// system bundle IS in the WAB.

			Assert.assertNotNull(resources.get("WEB-INF/lib/jfreechart.jar"));
			Assert.assertNotNull(
				resources.get("WEB-INF/lib/portals-bridges.jar"));
			Assert.assertNotNull(resources.get("WEB-INF/lib/struts-core.jar"));

			// Check that JSPs were parsed to add requirements for modular
			// taglibs.

			Parameters requiredCapabilities = domain.getRequireCapability();

			Attrs osgiExtenderAttrs = requiredCapabilities.get("osgi.extender");

			Assert.assertNotNull(osgiExtenderAttrs);
			Assert.assertEquals(
				"(&(osgi.extender=jsp.taglib)" +
					"(uri=http://java.sun.com/portlet_2_0))",
				osgiExtenderAttrs.get("filter:"));

			Parameters importedPackages = domain.getImportPackage();

			// Check basic servlet and jsp packages are imported

			Assert.assertTrue(importedPackages.containsKey("javax.servlet"));
			Assert.assertTrue(
				importedPackages.containsKey("javax.servlet.http"));

			// Since there are JSPs check that basic JSP packages are imported.

			Assert.assertTrue(
				importedPackages.containsKey("javax.servlet.jsp"));
			Assert.assertTrue(
				importedPackages.containsKey("javax.servlet.jsp.tagext"));

			// Check if packages declared in portal property
			// module.framework.web.generator.default.servlet.packages are
			// included.

			Assert.assertTrue(
				importedPackages.containsKey("com.liferay.portal.model"));
			Assert.assertTrue(
				importedPackages.containsKey("com.liferay.portal.service"));
			Assert.assertTrue(
				importedPackages.containsKey(
					"com.liferay.portal.servlet.filters.aggregate"));
			Assert.assertTrue(
				importedPackages.containsKey(
					"com.liferay.portal.osgi.web.servlet.jsp.compiler"));
			Assert.assertTrue(
				importedPackages.containsKey(
					"com.liferay.portal.spring.context"));
			Assert.assertTrue(
				importedPackages.containsKey("com.liferay.portal.util"));
			Assert.assertTrue(
				importedPackages.containsKey("com.liferay.portlet"));
			Assert.assertTrue(importedPackages.containsKey("com.sun.el"));
			Assert.assertTrue(
				importedPackages.containsKey(
					"org.apache.commons.chain.generic"));
			Assert.assertTrue(
				importedPackages.containsKey("org.apache.naming.java"));

			// Check if packages imported by code are included.

			Assert.assertTrue(
				importedPackages.containsKey(
					"com.liferay.portal.kernel.resiliency.spi.agent." +
						"annotation"));
			Assert.assertTrue(
				importedPackages.containsKey("com.liferay.portal.kernel.util"));

			// Check if packages used in the code but available on the
			// Bundle-ClassPath are not imported.

			Assert.assertFalse(
				importedPackages.containsKey("org.apache.struts.action"));
			Assert.assertFalse(importedPackages.containsKey("org.jfree.chart"));

			// Check if packages only referenced in web.xml are imported.

			Assert.assertTrue(
				importedPackages.containsKey(
					"com.liferay.portal.kernel.servlet.filters.invoker"));
			Assert.assertTrue(
				importedPackages.containsKey("com.liferay.portal.webserver"));
		}
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	protected File getFile(String fileName) throws URISyntaxException {
		ClassLoader classLoader = getClassLoader();

		URL url = classLoader.getResource(fileName);

		if (!"file".equals(url.getProtocol())) {
			return null;
		}

		Path path = Paths.get(url.toURI());

		return path.toFile();
	}

	protected class TestWabProcessor extends WabProcessor {

		public TestWabProcessor(
			ClassLoader classLoader, File file,
			Map<String, String[]> parameters) {

			super(classLoader, file, parameters);

			_file = file;
		}

		@Override
		protected void executeAutoDeployers(
			AutoDeploymentContext autoDeploymentContext) {

			try {
				File deployDir = autoDeploymentContext.getDeployDir();

				File parent = deployDir.getParentFile();

				Stream<Path> pathsStream = Files.walk(parent.toPath());

				pathsStream.sorted(
					Comparator.reverseOrder()
				).map(
					Path::toFile
				).forEach(
					File::delete
				);

				parent.mkdirs();

				File newFile = new File(parent, _file.getName());

				Files.copy(
					_file.toPath(), newFile.toPath(),
					StandardCopyOption.REPLACE_EXISTING);
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		private final File _file;

	}

}