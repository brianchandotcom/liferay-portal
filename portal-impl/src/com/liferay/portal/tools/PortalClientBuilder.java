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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.io.DummyOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.util.ant.Wsdl2JavaTask;
import com.liferay.util.axis.AxisServlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalClientBuilder {

	public static void main(String[] args) throws Exception {
		ToolDependencies.wireBasic();

		if (args.length == 4) {
			new PortalClientBuilder(args[0], args[1], args[2], args[3]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public PortalClientBuilder(
			String fileName, String outputDir, String mappingFile, String url)
		throws Exception {

		URL.setURLStreamHandlerFactory(new DirectURLStreamHandlerFactory());

		File file = new File(fileName);

		File parentFile = file.getParentFile();

		_axisHttpServlet = _createAxisHttpServlet(parentFile.getParentFile());

		Document document = UnsecureSAXReaderUtil.read(new File(fileName));

		Element rootElement = document.getRootElement();

		List<Element> serviceElements = rootElement.elements("service");

		for (Element serviceElement : serviceElements) {
			String serviceName = serviceElement.attributeValue("name");

			if (serviceName.startsWith("Plugin_") &&
				!FileUtil.exists(mappingFile)) {

				_writePluginMappingFile(mappingFile, serviceElement);
			}

			if (serviceName.startsWith("Plugin_") ||
				serviceName.startsWith("Portal_") ||
				serviceName.startsWith("Portlet_")) {

				Wsdl2JavaTask.generateJava(
					url + "/" + serviceName + "?wsdl", outputDir, mappingFile);
			}
		}

		File testNamespace = new File(outputDir + "/com/liferay/portal");

		if (testNamespace.exists()) {
			throw new RuntimeException(
				"Please update " + mappingFile + " from namespace " +
					"com.liferay.portal to com.liferay.client.soap.portal");
		}
	}

	private HttpServlet _createAxisHttpServlet(final File docRootDir)
		throws ServletException {

		AxisServlet axisServlet = new AxisServlet();

		MockServletConfig mockServletConfig = new MockServletConfig(
			new MockServletContext(
				new ResourceLoader() {

					@Override
					public ClassLoader getClassLoader() {
						return AxisServlet.class.getClassLoader();
					}

					@Override
					public Resource getResource(String name) {
						return new FileSystemResource(
							new File(docRootDir, name));
					}

				}),
			"Axis Servlet");

		axisServlet.init(mockServletConfig);

		return axisServlet;
	}

	private byte[] _getWSDLContent(URL url) throws IOException {
		String path = url.getPath();

		int index = path.lastIndexOf(CharPool.SLASH);

		String servletPath = path.substring(0, index);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(
				_axisHttpServlet.getServletContext(), "GET", path);

		mockHttpServletRequest.setPathInfo(path.substring(index));

		mockHttpServletRequest.setQueryString(url.getQuery());
		mockHttpServletRequest.setScheme(url.getProtocol());
		mockHttpServletRequest.setServerName(url.getHost());
		mockHttpServletRequest.setServerPort(url.getPort());
		mockHttpServletRequest.setServletPath(servletPath);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		try {
			_axisHttpServlet.service(
				mockHttpServletRequest, mockHttpServletResponse);
		}
		catch (ServletException se) {
			throw new IOException(se);
		}

		return mockHttpServletResponse.getContentAsByteArray();
	}

	private void _writePluginMappingFile(
			String mappingFile, Element serviceElement)
		throws Exception {

		String wsdlTargetNamespace = null;

		List<Element> parameterElements = serviceElement.elements("parameter");

		for (Element parameterElement : parameterElements) {
			String parameterName = parameterElement.attributeValue("name");

			if (parameterName.equals("wsdlTargetNamespace")) {
				wsdlTargetNamespace = parameterElement.attributeValue("value");

				break;
			}
		}

		int pos = wsdlTargetNamespace.indexOf(".service.");

		String soapNamespace = wsdlTargetNamespace.substring(pos + 9);

		String[] soapNamespaceArray = StringUtil.split(
			soapNamespace, CharPool.PERIOD);

		ArrayUtil.reverse(soapNamespaceArray);

		soapNamespace = StringUtil.merge(soapNamespaceArray, StringPool.PERIOD);

		pos = soapNamespace.lastIndexOf(StringPool.PERIOD);

		soapNamespace =
			soapNamespace.substring(0, pos) + ".client.soap" +
				soapNamespace.substring(pos);

		StringBundler sb = new StringBundler(10);

		sb.append("com.liferay.client.soap.portal.kernel.util=");
		sb.append("http://util.kernel.portal.liferay.com\n");

		sb.append("com.liferay.client.soap.portal.model=");
		sb.append("http://model.portal.liferay.com\n");

		sb.append("com.liferay.client.soap.portal.service=");
		sb.append("http://service.portal.liferay.com\n");

		sb.append(soapNamespace);
		sb.append(".model=http://model.knowledgebase.liferay.com\n");

		sb.append(soapNamespace);
		sb.append(".service.http=urn:http.service.knowledgebase.liferay.com\n");

		FileUtil.write(mappingFile, sb.toString());
	}

	private final HttpServlet _axisHttpServlet;

	private class DirectURLConnection extends URLConnection {

		public DirectURLConnection(URL url) {
			super(url);
		}

		@Override
		public void connect() {
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return new UnsyncByteArrayInputStream(_getWSDLContent(url));
		}

		@Override
		public OutputStream getOutputStream() {
			return new DummyOutputStream();
		}

	}

	private class DirectURLStreamHandler extends URLStreamHandler {

		@Override
		protected URLConnection openConnection(URL url) {
			return new DirectURLConnection(url);
		}

	}

	private class DirectURLStreamHandlerFactory
		implements URLStreamHandlerFactory {

		@Override
		public URLStreamHandler createURLStreamHandler(String protocol) {
			return new DirectURLStreamHandler();
		}

	}

}