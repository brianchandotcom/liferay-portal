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

package com.liferay.portal.osgi.web.wab.reference.support.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.osgi.web.wab.generator.WabGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.url.AbstractURLStreamHandlerService;
import org.osgi.service.url.URLConstants;
import org.osgi.service.url.URLStreamHandlerService;

/**
 * @author Gregory Amerson
 */
@Component(
	immediate = true,
	property = URLConstants.URL_HANDLER_PROTOCOL + "=webbundledir",
	service = URLStreamHandlerService.class
)
public class WabDirURLStreamHandlerService
	extends AbstractURLStreamHandlerService {

	@Override
	public URLConnection openConnection(URL url) {
		try {
			URI uri = new URI(url.getPath());

			File warDir = new File(uri);

			String bundleSymbolicName = _http.getParameter(
				url.toExternalForm(), "Bundle-SymbolicName");

			if (bundleSymbolicName.equals(StringPool.BLANK)) {
				bundleSymbolicName = _getNameFromDirectory(warDir);
			}

			if (bundleSymbolicName.equals(StringPool.BLANK)) {
				bundleSymbolicName = _getNameFromXMLFile(warDir);
			}

			if (bundleSymbolicName.equals(StringPool.BLANK)) {
				bundleSymbolicName = _getNameFromProperties(warDir);
			}

			Map<String, String[]> parameters =
				HashMapBuilder.<String, String[]>put(
					"Bundle-SymbolicName", new String[] {bundleSymbolicName}
				).build();

			String contextName = _http.getParameter(
				url.toExternalForm(), "Web-ContextPath");

			if (contextName.equals(StringPool.BLANK)) {
				contextName = _getNameFromDirectory(warDir);
			}

			if (contextName.equals(StringPool.BLANK)) {
				contextName = _getNameFromXMLFile(warDir);
			}

			if (contextName.equals(StringPool.BLANK)) {
				contextName = _getNameFromProperties(warDir);
			}

			if (contextName.equals(StringPool.BLANK)) {
				throw new IllegalArgumentException(
					"Unable to determine context name from " + url);
			}

			if (!contextName.startsWith(StringPool.SLASH)) {
				contextName = StringPool.SLASH.concat(contextName);
			}

			parameters.put("Web-ContextPath", new String[] {contextName});

			File generatedJarFile = _wabGenerator.generate(
				_classLoader, warDir, parameters);

			if (generatedJarFile != null) {
				_file.unzip(generatedJarFile, warDir);
			}

			uri = warDir.toURI();

			WabDirHandler wabDirHandler = new WabDirHandler(
				uri.toASCIIString());

			return wabDirHandler.openConnection(url);
		}
		catch (Exception e) {
			_log.error("Unable to open connection", e);
		}

		return null;
	}

	@Activate
	public void start(BundleContext bundleContext) {
		Bundle bundle = bundleContext.getBundle(0);

		Class<?> clazz = bundle.getClass();

		_classLoader = clazz.getClassLoader();
	}

	private String _getNameFromDirectory(File warDir) {
		Matcher matcher = _pattern.matcher(warDir.getAbsolutePath());

		if (matcher.matches()) {
			return matcher.group(1);
		}

		return StringPool.BLANK;
	}

	private String _getNameFromProperties(File warDir) throws IOException {
		File liferayPluginPackagePropertiesFile = new File(
			warDir, "WEB-INF/liferay-plugin-package.properties");

		if (!liferayPluginPackagePropertiesFile.exists()) {
			return StringPool.BLANK;
		}

		try (FileInputStream fileInputStream = new FileInputStream(
				liferayPluginPackagePropertiesFile)) {

			Properties properties = new Properties();

			properties.load(fileInputStream);

			return properties.getProperty("name");
		}
	}

	private String _getNameFromXMLFile(File warDir) throws IOException {
		File lookAndFeelXmlFile = new File(
			warDir, "WEB-INF/liferay-look-and-feel.xml");

		if (!lookAndFeelXmlFile.exists()) {
			return StringPool.BLANK;
		}

		Document document = _readDocument(lookAndFeelXmlFile);

		Element rootElement = document.getRootElement();

		XPath xPath = SAXReaderUtil.createXPath("//theme/@id", null, null);

		List<Node> nodes = xPath.selectNodes(rootElement);

		String themeId = null;

		if ((nodes != null) && !nodes.isEmpty()) {
			Node themeNode = nodes.get(0);

			themeId = themeNode.getText();
		}

		if (themeId != null) {
			return themeId + "-theme";
		}

		return StringPool.BLANK;
	}

	private Document _readDocument(File file) throws IOException {
		String content = FileUtil.read(file);

		try {
			return UnsecureSAXReaderUtil.read(content);
		}
		catch (DocumentException de) {
			throw new IOException(de);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WabDirURLStreamHandlerService.class);

	private static final Pattern _pattern = Pattern.compile(
		".*\\/(.*-(T|t)heme)\\/?.*");

	private ClassLoader _classLoader;

	@Reference
	private com.liferay.portal.kernel.util.File _file;

	@Reference
	private Http _http;

	@Reference
	private WabGenerator _wabGenerator;

}