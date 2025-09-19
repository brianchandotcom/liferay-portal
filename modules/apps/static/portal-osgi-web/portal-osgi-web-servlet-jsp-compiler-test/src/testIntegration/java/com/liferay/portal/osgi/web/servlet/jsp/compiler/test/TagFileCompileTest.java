/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.servlet.jsp.compiler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.portlet.Portlet;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Dante Wang
 */
@RunWith(Arquillian.class)
public class TagFileCompileTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			JspServletPerformanceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		File tempDirFile = new File(
			System.getProperty("java.io.tmpdir"),
			TagFileCompileTest.class.getName());

		tempDirFile.mkdirs();

		InputStream inputStream1 = _createTagBundle();

		inputStream1.mark(0);

		File tagJarFile = new File(tempDirFile, "tag.jar");

		StreamUtil.transfer(inputStream1, new FileOutputStream(tagJarFile));

		inputStream1.reset();

		Bundle tagBundle = bundleContext.installBundle(
			tagJarFile.toURI().toURL().toString(), inputStream1);

		tagBundle.start();

		InputStream inputStream2 = _createPortletBundle();

		inputStream2.mark(0);

		File portletJarFile = new File(tempDirFile, "portlet.jar");

		StreamUtil.transfer(inputStream2, new FileOutputStream(portletJarFile));

		inputStream2.reset();

		Bundle portletBundle = bundleContext.installBundle(
			portletJarFile.toURI().toURL().toString(), inputStream2);

		portletBundle.start();

		try (GroupAutoCloseable groupAutoCloseable = _setUpGroup()) {
			URL url = new URL(
				StringBundler.concat(
					"http://localhost:8080/web",
					groupAutoCloseable._group.getFriendlyURL(), "?p_p_id=",
					JspPrecompilePortlet.PORTLET_NAME, StringPool.AMPERSAND,
					JspPrecompilePortlet.getJspFileNameParameterName(), "=/",
					_TAG_TEST_JSP_FILE_NAME));

			String content = URLUtil.toString(url);

			Assert.assertTrue(content.contains("Tag File Test"));
		}
		finally {
			portletBundle.uninstall();

			tagBundle.uninstall();

			FileUtil.deltree(tempDirFile);
		}
	}

	private String _buildImportPackage(Class<?>... classes) {
		if (ArrayUtil.isEmpty(classes)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(classes.length * 2);

		Set<Package> packages = new HashSet<>();

		for (Class<?> clazz : classes) {
			Package pkg = clazz.getPackage();

			if (packages.add(pkg)) {
				sb.append(pkg.getName());
				sb.append(StringPool.COMMA);
			}
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private InputStream _createPortletBundle() throws Exception {
		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			try (JarOutputStream jarOutputStream = new JarOutputStream(
					unsyncByteArrayOutputStream)) {

				Manifest manifest = new Manifest();

				Attributes attributes = manifest.getMainAttributes();

				attributes.putValue(
					Constants.BUNDLE_ACTIVATOR,
					JspPrecompileBundleActivator.class.getName());
				attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");

				Package pkg = TagFileCompileTest.class.getPackage();

				attributes.putValue(
					Constants.BUNDLE_SYMBOLICNAME, pkg.getName() + ".portlet");

				attributes.putValue(Constants.BUNDLE_VERSION, "1.0.0");
				attributes.putValue(
					Constants.IMPORT_PACKAGE,
					_buildImportPackage(
						BundleActivator.class, HttpServletRequest.class,
						MVCPortlet.class, PortalUtil.class, Portlet.class));
				attributes.putValue("Manifest-Version", "2");
				attributes.putValue(
					"Require-Capability",
					"osgi.extender;filter:=\"(&(osgi.extender=jsp.taglib)(" +
						"uri=http://liferay.com/tld/tag-file-test))\"");

				jarOutputStream.putNextEntry(
					new ZipEntry(JarFile.MANIFEST_NAME));

				manifest.write(jarOutputStream);

				jarOutputStream.closeEntry();

				_writeClasses(
					jarOutputStream, JspPrecompileBundleActivator.class,
					JspPrecompilePortlet.class);

				ClassLoader classLoader =
					TagFileCompileTest.class.getClassLoader();

				jarOutputStream.putNextEntry(
					new ZipEntry(
						"META-INF/resources/".concat(_TAG_TEST_JSP_FILE_NAME)));

				try (InputStream inputStream = classLoader.getResourceAsStream(
						"META-INF/resources/".concat(_TAG_TEST_JSP_FILE_NAME));
					OutputStream outputStream = StreamUtil.uncloseable(
						jarOutputStream)) {

					StreamUtil.transfer(inputStream, outputStream);
				}

				jarOutputStream.closeEntry();
			}

			return new UnsyncByteArrayInputStream(
				unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
				unsyncByteArrayOutputStream.size());
		}
	}

	private InputStream _createTagBundle() throws Exception {
		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			try (JarOutputStream jarOutputStream = new JarOutputStream(
					unsyncByteArrayOutputStream)) {

				Manifest manifest = new Manifest();

				Attributes attributes = manifest.getMainAttributes();

				attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");

				Package pkg = TagFileCompileTest.class.getPackage();

				attributes.putValue(
					Constants.BUNDLE_SYMBOLICNAME, pkg.getName() + ".tag");

				attributes.putValue(Constants.BUNDLE_VERSION, "1.0.0");
				attributes.putValue("Manifest-Version", "1.0");
				attributes.putValue(
					"Provide-Capability",
					"osgi.extender;osgi.extender=\"jsp.taglib\";uri=\"http://" +
						"liferay.com/tld/tag-file-test\";version:Version=\"1." +
							"0.0\"");
				attributes.putValue("Web-ContextPath", "/tag-file-test");

				jarOutputStream.putNextEntry(
					new ZipEntry(JarFile.MANIFEST_NAME));

				manifest.write(jarOutputStream);

				jarOutputStream.closeEntry();

				ClassLoader classLoader =
					TagFileCompileTest.class.getClassLoader();

				for (String taglibFiles : _TAG_TEST_TAGLIB_FILES) {
					String path = "META-INF/".concat(taglibFiles);

					jarOutputStream.putNextEntry(new ZipEntry(path));

					try (InputStream inputStream =
							classLoader.getResourceAsStream(path);
						OutputStream outputStream = StreamUtil.uncloseable(
							jarOutputStream)) {

						StreamUtil.transfer(inputStream, outputStream);
					}

					jarOutputStream.closeEntry();
				}
			}

			return new UnsyncByteArrayInputStream(
				unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
				unsyncByteArrayOutputStream.size());
		}
	}

	private GroupAutoCloseable _setUpGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addTypePortletLayout(group);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		LayoutTemplate layoutTemplate = layoutTypePortlet.getLayoutTemplate();

		List<String> columnIds = layoutTemplate.getColumns();

		String columnId = columnIds.get(0);

		layoutTypePortlet.addPortletId(
			TestPropsValues.getUserId(), JspPrecompilePortlet.PORTLET_NAME,
			columnId, -1, false);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		return new GroupAutoCloseable(group);
	}

	private void _writeClasses(
			JarOutputStream jarOutputStream, Class<?>... classes)
		throws IOException {

		ClassLoader classLoader = JspPrecompileTest.class.getClassLoader();

		for (Class<?> clazz : classes) {
			String className = clazz.getName();

			String path = StringUtil.replace(
				className, CharPool.PERIOD, CharPool.SLASH);

			String resourcePath = path.concat(".class");

			jarOutputStream.putNextEntry(new ZipEntry(resourcePath));

			try (InputStream inputStream = classLoader.getResourceAsStream(
					resourcePath);
				OutputStream outputStream = StreamUtil.uncloseable(
					jarOutputStream)) {

				StreamUtil.transfer(inputStream, outputStream);
			}

			jarOutputStream.closeEntry();
		}
	}

	private static final String _TAG_TEST_JSP_FILE_NAME = "view.jsp";

	private static final String[] _TAG_TEST_TAGLIB_FILES = {
		"tag-file-test.tld", "taglib-mappings.properties", "tags/test.tag"
	};

	private class GroupAutoCloseable implements AutoCloseable {

		public GroupAutoCloseable(Group group) {
			_group = group;
		}

		@Override
		public void close() throws Exception {
			GroupLocalServiceUtil.deleteGroup(_group);
		}

		private final Group _group;

	}

}