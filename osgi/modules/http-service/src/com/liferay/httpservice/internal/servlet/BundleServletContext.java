/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.httpservice.internal.servlet;

import com.liferay.httpservice.internal.http.DefaultHttpContext;
import com.liferay.httpservice.internal.http.HttpServiceTracker;
import com.liferay.portal.apache.bridges.struts.LiferayServletContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.HttpContext;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class BundleServletContext extends LiferayServletContext {

	public static String getServletContextName(Bundle bundle) {
		return getServletContextName(bundle, false);
	}

	public static String getServletContextName(
		Bundle bundle, boolean generate) {

		Dictionary<String, String> headers = bundle.getHeaders();

		String webContextPath = headers.get(_WEB_CONTEXT_PATH);

		if (Validator.isNotNull(webContextPath)) {
			return webContextPath.substring(1);
		}

		String deploymentContext = null;

		try {
			String pluginPackageXml = HttpUtil.URLtoString(
				bundle.getResource("/WEB-INF/liferay-plugin-package.xml"));

			if (pluginPackageXml != null) {
				Document document = SAXReaderUtil.read(pluginPackageXml);

				Element rootElement = document.getRootElement();

				deploymentContext = GetterUtil.getString(
					rootElement.elementText("recommended-deployment-context"));
			}
			else {
				String pluginPackageProperties = HttpUtil.URLtoString(
					bundle.getResource(
						"/WEB-INF/liferay-plugin-package.properties"));

				if (pluginPackageProperties != null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Reading plugin package from " +
								"liferay-plugin-package.properties");
					}

					Properties properties = PropertiesUtil.load(
						pluginPackageProperties);

					deploymentContext = GetterUtil.getString(
						properties.getProperty(
							"recommended-deployment-context"),
						deploymentContext);
				}
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		if (Validator.isNull(deploymentContext) && generate) {
			deploymentContext = PortalUtil.getJsSafePortletId(
				bundle.getSymbolicName());
		}

		if (Validator.isNotNull(deploymentContext) &&
			deploymentContext.startsWith(StringPool.SLASH)) {

			deploymentContext = deploymentContext.substring(1);
		}

		return deploymentContext;
	}

	public BundleServletContext(
		Bundle bundle, String servletContextName,
		ServletContext servletContext) {

		super(servletContext);

		_bundle = bundle;
		_servletContextName = servletContextName;

		_httpContext = new DefaultHttpContext(_bundle);
	}

	public void close() {
		_httpServiceTracker.close();

		_servletContextRegistration.unregister();

		FileUtil.deltree(_tempDir);
	}

	@Override
	public Object getAttribute(String name) {
		if (name.equals("osgi-bundle")) {
			return _bundle;
		}
		else if (name.equals("osgi-bundlecontext")) {

			// This is required to meet OSGi Comp 4.3, WAS 128.6.1.

			return _bundle.getBundleContext();
		}
		else if (name.equals(JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR)) {
			return _getTempDir();
		}

		Object value = _contextAttributes.get(name);

		if (value == null) {
			if (name.equals(PluginContextListener.PLUGIN_CLASS_LOADER)) {
				return getClassLoader();
			}
			else if (name.equals("org.apache.catalina.JSP_PROPERTY_GROUPS")) {
				value = new HashMap<String, Object>();

				_contextAttributes.put(name, value);
			}
			else if (name.equals("org.apache.tomcat.InstanceManager")) {
				return super.getAttribute(name);
			}
		}

		return value;
	}

	public Enumeration<String> getAttributeNames() {
		Set<String> _contextAttributesNames = new HashSet<String>(
			_contextAttributes.keySet());

		for (Enumeration<String> attributeNames = super.getAttributeNames();
				attributeNames.hasMoreElements();) {

			_contextAttributesNames.add(attributeNames.nextElement());
		}

		return Collections.enumeration(_contextAttributesNames);
	}

	public ClassLoader getClassLoader() {
		Object value = _contextAttributes.get(
			PluginContextListener.PLUGIN_CLASS_LOADER);

		if (value == null) {
			BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

			value = bundleWiring.getClassLoader();

			_contextAttributes.put(
				PluginContextListener.PLUGIN_CLASS_LOADER, value);
		}

		return (ClassLoader)value;
	}

	public String getContextPath() {
		if (_contextPath == null) {
			StringBundler sb = new StringBundler(4);

			String contextPath = super.getContextPath();

			if (!contextPath.equals(StringPool.SLASH)) {
				sb.append(contextPath);
			}

			sb.append(PortalUtil.getPathContext());
			sb.append(Portal.PATH_MODULE);
			sb.append(getServletContextName());

			_contextPath = sb.toString();
		}

		return _contextPath;
	}

	public HttpContext getHttpContext() {
		return _httpContext;
	}

	public String getInitParameter(String name) {
		return _initParameters.get(name);
	}

	public Enumeration<String> getInitParameterNames() {
		return Collections.enumeration(_initParameters.keySet());
	}

	public String getMimeType(String name) {
		return super.getMimeType(name);
	}

	public String getRealPath(String path) {
		URL resourceURL = _httpContext.getResource(path);

		if (resourceURL != null) {
			return resourceURL.toExternalForm();
		}

		return path;
	}

	@Override
	public URL getResource(String path) throws MalformedURLException {
		return _httpContext.getResource(path);
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		try {
			URL resourceURL = getResource(path);

			if (resourceURL != null) {
				return resourceURL.openStream();
			}
		}
		catch (IOException e) {
			_log.error(e, e);
		}

		return null;
	}

	public Set<String> getResourcePaths(String path) {
		Enumeration<String> resources = _bundle.getEntryPaths(path);

		if (resources == null) {
			return Collections.EMPTY_SET;
		}

		Set<String> resourcePaths = new HashSet<String>();

		while (resources.hasMoreElements()) {
			String resourcePath = resources.nextElement();

			resourcePaths.add(StringPool.SLASH.concat(resourcePath));
		}

		return resourcePaths;
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	public List<ServletRequestAttributeListener>
		getServletRequestAttributeListeners() {

		return _servletRequestAttributeListeners;
	}

	public List<ServletRequestListener> getServletRequestListeners() {
		return _servletRequestListeners;
	}

	public void open() throws DocumentException {
		Hashtable<String, Object> properties = new Hashtable<String, Object>();

		properties.put("bundle", _bundle);
		properties.put("bundle.id", _bundle.getBundleId());
		properties.put("bundle.symbolicName", _bundle.getSymbolicName());
		properties.put("bundle.version", _bundle.getVersion());
		properties.put(
			_WEB_CONTEXT_PATH, StringPool.SLASH.concat(_servletContextName));

		BundleContext bundleContext = _bundle.getBundleContext();

		_servletContextRegistration = bundleContext.registerService(
			BundleServletContext.class, this, properties);

		_httpServiceTracker = new HttpServiceTracker(bundleContext, _bundle);

		_httpServiceTracker.open();
	}

	public void removeAttribute(String name) {
		Object value = _contextAttributes.remove(name);

		for (ServletContextAttributeListener servletContextAttributeListener :
				_servletContextAttributeListeners) {

			servletContextAttributeListener.attributeRemoved(
				new ServletContextAttributeEvent(this, name, value));
		}
	}

	public void setAttribute(String name, Object value) {
		if (name.equals(JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR) ||
			name.equals(PluginContextListener.PLUGIN_CLASS_LOADER)) {

			return;
		}

		Object originalValue = _contextAttributes.get(name);

		_contextAttributes.put(name, value);

		for (ServletContextAttributeListener listener :
				_servletContextAttributeListeners) {

			if (originalValue != null) {
				listener.attributeReplaced(
					new ServletContextAttributeEvent(
						this, name, originalValue));
			}
			else {
				listener.attributeAdded(
					new ServletContextAttributeEvent(this, name, value));
			}
		}
	}

	public void setServletContextName(String servletContextName) {
		_servletContextName = servletContextName;
	}

	private File _getTempDir() {
		if (_tempDir == null) {
			File parentTempDir = (File)super.getAttribute(
				JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR);

			String servletContextName = getServletContextName();

			File file = new File(parentTempDir, servletContextName);

			if (!file.exists() && !file.mkdirs()) {
				throw new IllegalStateException(
					"Can't create webapp temp dir for " +
						getServletContextName());
			}

			_tempDir = file;
		}

		return _tempDir;
	}

	private static final String _WEB_CONTEXT_PATH = "Web-ContextPath";

	private static Log _log = LogFactoryUtil.getLog(BundleServletContext.class);

	private Bundle _bundle;
	private Map<String, Object> _contextAttributes =
		new ConcurrentHashMap<String, Object>();
	private String _contextPath;
	private HttpContext _httpContext;
	private HttpServiceTracker _httpServiceTracker;
	private Map<String, String> _initParameters = new HashMap<String, String>();
	private List<ServletContextAttributeListener>
		_servletContextAttributeListeners =
			new ArrayList<ServletContextAttributeListener>();
	private List<ServletRequestAttributeListener>
		_servletRequestAttributeListeners =
			new ArrayList<ServletRequestAttributeListener>();
	private String _servletContextName;
	private ServiceRegistration<BundleServletContext>
		_servletContextRegistration;
	private List<ServletRequestListener> _servletRequestListeners =
		new ArrayList<ServletRequestListener>();
	private File _tempDir;

}