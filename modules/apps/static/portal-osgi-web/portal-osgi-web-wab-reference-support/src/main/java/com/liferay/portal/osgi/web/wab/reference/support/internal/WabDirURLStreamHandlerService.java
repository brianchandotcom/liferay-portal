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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.osgi.web.wab.generator.WabGenerator;

import java.io.File;
import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	property = {URLConstants.URL_HANDLER_PROTOCOL + "=webbundledir"},
	service = URLStreamHandlerService.class
)
public class WabDirURLStreamHandlerService
	extends AbstractURLStreamHandlerService {

	@Override
	public URLConnection openConnection(URL url) {
		try {
			String contextName = HttpUtil.getParameter(
				url.toExternalForm(), "Web-ContextPath");

			URI uri = new URI(url.getPath());

			File warDir = new File(uri);

			if (contextName == StringPool.BLANK) {
				contextName = _getContextNameFromDirectory(warDir);
			}

			if (contextName == StringPool.BLANK) {
				contextName = _getContextNameFromXMLFile(warDir);
			}

			if (contextName == StringPool.BLANK) {
				throw new IllegalArgumentException(
					"Unable to determine context name from " + url);
			}

			Map<String, String[]> parameters = new HashMap<>();

			parameters.put("Web-ContextPath", new String[] {contextName});

			_wabGenerator.generate(_classLoader, warDir, parameters);

			uri = warDir.toURI();

			WabDirHandler wabDirHandler = new WabDirHandler(
				uri.toASCIIString());

			return wabDirHandler.openConnection(url);
		}
		catch (IOException | URISyntaxException e) {
		}

		return null;
	}

	@Reference(unbind = "-")
	public void setWabGenerator(WabGenerator wabGenerator) {
		_wabGenerator = wabGenerator;
	}

	@Activate
	public void start(BundleContext bundleContext) {
		Bundle bundle = bundleContext.getBundle(0);

		Class<?> clazz = bundle.getClass();

		_classLoader = clazz.getClassLoader();
	}

	private String _getContextNameFromDirectory(File warDir) {
		Matcher matcher = _pattern.matcher(warDir.getAbsolutePath());

		if (matcher.matches()) {
			return matcher.group(1);
		}

		return null;
	}

	private String _getContextNameFromXMLFile(File warDir) throws IOException {
		File lookAndFeelXmlFile = new File(
			warDir, "WEB-INF/liferay-look-and-feel.xml");

		Document document = _readDocument(lookAndFeelXmlFile);

		Element rootElement = document.getRootElement();

		XPath xPath = SAXReaderUtil.createXPath("//theme/@id", null, null);

		List<Node> nodes = xPath.selectNodes(rootElement);

		String themeId = null;

		if ((nodes != null) && (nodes.size() > 0)) {
			Node themeNode = nodes.get(0);

			themeId = themeNode.getText();
		}

		if (themeId != null) {
			return themeId + "-theme";
		}

		return null;
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

	private static final Pattern _pattern = Pattern.compile(
		".*\\/(.*-(T|t)heme)\\/.*");

	private ClassLoader _classLoader;
	private WabGenerator _wabGenerator;

}