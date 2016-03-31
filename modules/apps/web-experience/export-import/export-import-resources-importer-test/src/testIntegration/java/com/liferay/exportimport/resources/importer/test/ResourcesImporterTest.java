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

package com.liferay.exportimport.resources.importer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.exportimport.resources.importer.messaging.ResourcesImporterHotDeployMessageListener;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.HotDeployMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
public class ResourcesImporterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		Collection<MessageListener> messageListeners = registry.getServices(
			MessageListener.class,
			"(destination.name=" + DestinationNames.HOT_DEPLOY + ")");

		for (MessageListener messageListener : messageListeners) {
			if (messageListener instanceof
					ResourcesImporterHotDeployMessageListener) {

				_hotDeployMessageListener =
					(HotDeployMessageListener)messageListener;

				break;
			}
		}

		Assert.assertNotNull(
			"Resources importer is not deployed", messageListeners);
	}

	@Test
	public void testImportResourcesWAR() throws Exception {
		_importedGroup = deployResources(
			"/com/liferay/exportimport/resources/importer/test/" +
				"dependencies/test.war");

		// Verify the deployment

		Assert.assertNotNull(_importedGroup);

		long privateLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			_importedGroup, true);

		Assert.assertEquals(0, privateLayoutsCount);

		long publicLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			_importedGroup, false);

		Assert.assertEquals(4, publicLayoutsCount);

		long journalArticlesCount =
			JournalArticleLocalServiceUtil.getArticlesCount(
				_importedGroup.getGroupId());

		Assert.assertEquals(2, journalArticlesCount);

		long fileEntriesCount = DLFileEntryLocalServiceUtil.getFileEntriesCount(
			_importedGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(2, fileEntriesCount);

		Layout homeLayout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			_importedGroup.getGroupId(), false, "/home");

		Assert.assertTrue(homeLayout.isTypePortlet());

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)homeLayout.getLayoutType();

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets();

		Assert.assertEquals(2, portlets.size());
	}

	protected Group deployResources(String path) throws Exception {

		// Initialize war resource

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL resourceURL = classLoader.getResource(path);

		Assert.assertNotNull(
			"Unable to get resource URL from war file", resourceURL);

		// Initialize deployment context

		ServletContext servletContext = getServletContext(resourceURL);

		Assert.assertNotNull(servletContext);

		try {
			installPluginPackage(servletContext);

			_hotDeployMessageListener.receive(
				getHotDeployMessage(servletContext));

			return GroupLocalServiceUtil.fetchGroup(
				TestPropsValues.getCompanyId(), "ResourcesImporterTest");
		}
		finally {
			unInstallPluginPackage(servletContext);
		}
	}

	protected Message getHotDeployMessage(ServletContext servletContext)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		Message message = new Message();

		message.put("command", "deploy");
		message.put("servletContextName", servletContextName);

		return message;
	}

	protected ServletContext getServletContext(URL url) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		ResourceLoader resourceLoader = new TestURLResourceLoader(
			url, classLoader);

		ServletContext servletContext = new TestServletContext(
			StringPool.BLANK, resourceLoader, url);

		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(ServletContext.class, servletContext);

		return servletContext;
	}

	protected void installPluginPackage(ServletContext servletContext)
		throws Exception {

		PluginPackage pluginPackage =
			PluginPackageUtil.readPluginPackageServletContext(servletContext);

		if (pluginPackage == null) {
			return;
		}

		PluginPackageUtil.registerInstalledPluginPackage(pluginPackage);
	}

	protected void unInstallPluginPackage(ServletContext servletContext)
		throws PortalException {

		PluginPackage pluginPackage =
			PluginPackageUtil.getInstalledPluginPackage(
				servletContext.getServletContextName());

		PluginPackageUtil.unregisterInstalledPluginPackage(pluginPackage);
	}

	private HotDeployMessageListener _hotDeployMessageListener;

	@DeleteAfterTestRun
	private Group _importedGroup;

	private class TestServletContext extends MockServletContext {

		public TestServletContext(
			String resourceBasePath, ResourceLoader resourceLoader, URL url) {

			super(resourceBasePath, resourceLoader);

			_resourcePaths = getResourcePaths(url);
			_servletContextName = StringUtil.randomString();
		}

		public Set<String> getResourcePaths(String path) {
			Set<String> resourcePaths = new HashSet<>();

			for (String resourcePath : _resourcePaths) {
				if (isChildPath(path, resourcePath)) {
					resourcePaths.add(resourcePath);
				}
			}

			return resourcePaths;
		}

		@Override
		public String getServletContextName() {
			return _servletContextName;
		}

		protected Set<String> getResourcePaths(URL url) {
			Set<String> resourcePaths = new HashSet<>();

			try (ZipInputStream zip = new ZipInputStream(url.openStream())) {
				ZipEntry e = null;

				while ((e = zip.getNextEntry()) != null) {
					String resourcePath = e.getName();

					if (!resourcePath.startsWith(StringPool.SLASH)) {
						resourcePath = StringPool.SLASH + resourcePath;
					}

					resourcePaths.add(resourcePath);
				}
			}
			catch (Exception e) {
				Assert.fail("Unable to initialize resources paths");
			}

			return resourcePaths;
		}

		protected boolean isChildPath(String parent, String child) {
			if (Validator.equals(parent, child)) {
				return false;
			}

			if (!StringUtil.startsWith(child, parent)) {
				return false;
			}

			int i = child.indexOf(StringPool.SLASH, parent.length());

			if ((i != -1) && (i != (child.length() - 1))) {
				return false;
			}

			return true;
		}

		private final Set<String> _resourcePaths;
		private final String _servletContextName;

	}

	private class TestURLResourceLoader extends DefaultResourceLoader {

		public TestURLResourceLoader(URL url, ClassLoader classLoader) {
			_urlClassLoader = new URLClassLoader(new URL[] {url}, classLoader);
		}

		@Override
		public Resource getResource(String location) {
			if (_urlClassLoader == null) {
				return _invalidResource;
			}

			return new ClassPathResource(location, _urlClassLoader);
		}

		private final Resource _invalidResource = new ClassPathResource("/") {

			@Override
			public boolean exists() {
				return false;
			}

		};

		private final URLClassLoader _urlClassLoader;

	}

}