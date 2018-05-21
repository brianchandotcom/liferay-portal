/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.resiliency.spi.provider.tomcat;

import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.net.MalformedURLException;
import java.net.URI;

import java.rmi.RemoteException;

import java.util.Properties;

import javax.servlet.Servlet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Constants;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.Tomcat.DefaultWebXmlListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Shuyang Zhou
 */
public class TomcatRemoteSPI extends RemoteSPI {

	public TomcatRemoteSPI(SPIConfiguration spiConfiguration) {
		super(spiConfiguration);
	}

	@Override
	public void addServlet(
			String contextPath, String docBasePath, String mappingPattern,
			String servletClassName)
		throws RemoteException {

		try {
			Host host = _tomcat.getHost();

			Context context = (Context)host.findChild(contextPath);

			if (context == null) {
				context = _tomcat.addContext(contextPath, docBasePath);
			}

			context.setCrossContext(true);

			Thread currentThread = Thread.currentThread();

			ClassLoader classLoader = currentThread.getContextClassLoader();

			Class<Servlet> servletClass = (Class<Servlet>)classLoader.loadClass(
				servletClassName);

			Servlet servlet = servletClass.newInstance();

			Tomcat.addServlet(context, servletClassName, servlet);

			context.addServletMapping(mappingPattern, servletClassName);
		}
		catch (Exception e) {
			throw new RemoteException("Unable to add servlet", e);
		}
	}

	@Override
	public void addWebapp(String contextPath, String docBasePath)
		throws RemoteException {

		try {
			Context context = new StandardContext();

			File contextXMLFile = new File(docBasePath, "META-INF/context.xml");

			if (contextXMLFile.exists()) {
				try {
					contextXMLFile = disableJARandResourceLocking(
						contextXMLFile);
				}
				catch (Exception e) {
					throw new RemoteException(
						"Unable to convert " + contextXMLFile +
							" to disable JAR locking and resource locking",
						e);
				}

				URI uri = contextXMLFile.toURI();

				context.setConfigFile(uri.toURL());
			}

			context.setCrossContext(true);
			context.setDocBase(docBasePath);
			context.setName(contextPath);
			context.setPath(contextPath);

			context.addLifecycleListener(new DefaultWebXmlListener());

			ContextConfig contextConfig = new ContextConfig();

			contextConfig.setDefaultWebXml(Constants.NoDefaultWebXml);

			context.addLifecycleListener(contextConfig);

			Host host = _tomcat.getHost();

			host.addChild(context);
		}
		catch (MalformedURLException murle) {
			throw new RemoteException("Unable to add web application", murle);
		}
	}

	@Override
	public String getSPIProviderName() {
		return TomcatSPIProvider.NAME;
	}

	@Override
	public void init() throws RemoteException {
		try {
			_tomcat.init();
		}
		catch (LifecycleException le) {
			throw new RemoteException("Unable to init", le);
		}
	}

	@Override
	public void start() throws RemoteException {
		try {
			_tomcat.start();
		}
		catch (LifecycleException le) {
			throw new RemoteException("Unable to start", le);
		}
	}

	@Override
	public void stop() throws RemoteException {
		try {
			_tomcat.stop();
		}
		catch (LifecycleException le) {
			throw new RemoteException("Unable to stop", le);
		}
	}

	@Override
	public String toString() {
		return spiConfiguration.getSPIId();
	}

	protected File disableJARandResourceLocking(File contextXMLFile)
		throws Exception {

		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		Document document = documentBuilder.parse(contextXMLFile);

		Element element = document.getDocumentElement();

		boolean modified = false;

		if (Boolean.valueOf(element.getAttribute("antiJARLocking"))) {
			element.setAttribute("antiJARLocking", StringPool.FALSE);

			modified = true;
		}

		if (Boolean.valueOf(element.getAttribute("antiResourceLocking"))) {
			element.setAttribute("antiResourceLocking", StringPool.FALSE);

			modified = true;
		}

		if (modified) {
			TransformerFactory transformerFactory =
				TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();

			File tempContextXMLFile = File.createTempFile(
				"temp-context-", ".xml");

			transformer.transform(
				new DOMSource(document),
				new StreamResult(new FileOutputStream(tempContextXMLFile)));

			tempContextXMLFile.deleteOnExit();

			contextXMLFile = tempContextXMLFile;
		}

		return contextXMLFile;
	}

	@Override
	protected void doDestroy() throws RemoteException {
		try {
			_tomcat.destroy();
		}
		catch (LifecycleException le) {
			throw new RemoteException("Unable to destroy", le);
		}
	}

	private void readObject(ObjectInputStream objectInputStream)
		throws ClassNotFoundException, IOException {

		objectInputStream.defaultReadObject();

		String baseDir = spiConfiguration.getBaseDir();

		File tempDir = new File(baseDir, "temp");

		tempDir.mkdirs();

		if (!tempDir.exists() || !tempDir.isDirectory()) {
			throw new IOException(
				"Unable to create temp dir " + tempDir.getAbsolutePath());
		}

		System.setProperty("java.io.tmpdir", tempDir.getAbsolutePath());

		_tomcat = new Tomcat();

		_tomcat.setBaseDir(baseDir);
		_tomcat.setPort(spiConfiguration.getConnectorPort());

		Connector connector = _tomcat.getConnector();

		Properties properties = PropertiesUtil.load(
			spiConfiguration.getExtraSettings());

		properties.put("keepAliveTimeout", "-1");
		properties.put("maxKeepAliveRequests", "-1");

		for (String name : properties.stringPropertyNames()) {
			connector.setProperty(name, properties.getProperty(name));
		}
	}

	private static final long serialVersionUID = 1L;

	private transient Tomcat _tomcat;

}