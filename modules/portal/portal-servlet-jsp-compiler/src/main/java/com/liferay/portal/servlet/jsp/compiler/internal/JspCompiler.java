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

package com.liferay.portal.servlet.jsp.compiler.internal;

import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.security.AccessController;
import java.security.CodeSource;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import javax.tools.JavaFileManager;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;

import org.apache.felix.utils.log.Logger;
import org.apache.jasper.Constants;
import org.apache.jasper.JspCompilationContext;
import org.apache.jasper.compiler.ErrorDispatcher;
import org.apache.jasper.compiler.Jsr199JavaCompiler;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

import org.phidias.compile.BundleJavaManager;
import org.phidias.compile.ResourceResolver;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class JspCompiler extends Jsr199JavaCompiler {

	@Override
	public void init(
		JspCompilationContext jspCompilationContext,
		ErrorDispatcher errorDispatcher, boolean suppressLogging) {

		_jspBundle = FrameworkUtil.getBundle(
			com.liferay.portal.servlet.jsp.compiler.JspServlet.class);

		_logger = new Logger(_jspBundle.getBundleContext());

		ServletContext servletContext =
			jspCompilationContext.getServletContext();

		ClassLoader classLoader = servletContext.getClassLoader();

		if (!(classLoader instanceof JspBundleClassloader)) {
			throw new IllegalStateException(
				"Class loader is not an instance of JspBundleClassloader");
		}

		JspBundleClassloader jspBundleClassloader =
			(JspBundleClassloader)classLoader;

		_allParticipatingBundles = jspBundleClassloader.getBundles();

		_bundle = _allParticipatingBundles[0];

		_resourceResolver = new JspResourceResolver(
			_bundle, _jspBundle, _logger);

		jspCompilationContext.setClassLoader(jspBundleClassloader);

		initClassPath(servletContext);
		initTLDMappings(servletContext);

		super.init(jspCompilationContext, errorDispatcher, suppressLogging);
	}

	protected void addBundleWirings(BundleJavaManager bundleJavaManager) {
		BundleWiring bundleWiring = _jspBundle.adapt(BundleWiring.class);

		bundleJavaManager.addBundleWiring(bundleWiring);

		List<BundleWire> requiredBundleWires = bundleWiring.getRequiredWires(
			null);

		for (BundleWire bundleWire : requiredBundleWires) {
			BundleWiring providedBundleWiring = bundleWire.getProviderWiring();

			bundleJavaManager.addBundleWiring(providedBundleWiring);
		}
	}

	protected void addDependenciesToClassPath() {
		ClassLoader frameworkClassLoader = Bundle.class.getClassLoader();

		for (String className : _JSP_COMPILER_DEPENDENCIES) {
			File file = new File(className);

			if (file.exists() && file.canRead()) {
				if (_classPath.contains(file)) {
					_classPath.remove(file);
				}

				_classPath.add(0, file);

				continue;
			}

			try {
				Class<?> clazz = Class.forName(
					className, true, frameworkClassLoader);

				addDependencyToClassPath(clazz);
			}
			catch (ClassNotFoundException e) {
				_logger.log(
					Logger.LOG_ERROR,
					"Unable to add depedency " + className +
						" to the classpath");
			}
		}
	}

	protected void addDependencyToClassPath(Class<?> clazz) {
		ProtectionDomain protectionDomain = clazz.getProtectionDomain();

		if (protectionDomain == null) {
			return;
		}

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		try {
			File file = new File(toURI(url));

			if (file.exists() && file.canRead()) {
				if (_classPath.contains(file)) {
					_classPath.remove(file);
				}

				_classPath.add(0, file);
			}
		}
		catch (Exception e) {
			_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
		}
	}

	protected void collectTLDMappings(
		Map<String, String[]> tldMappings, Bundle bundle) {

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		List<String> resourcePaths = new ArrayList<>(
			bundleWiring.listResources(
				"/META-INF/", "*.tld", BundleWiring.LISTRESOURCES_RECURSE));

		resourcePaths.addAll(
			bundleWiring.listResources(
				"/WEB-INF/", "*.tld", BundleWiring.LISTRESOURCES_RECURSE));

		for (String resourcePath : resourcePaths) {
			URL url = bundle.getResource(resourcePath);

			String uri = getTldUri(url);

			if (uri != null) {
				tldMappings.put(uri, new String[] {"/" + resourcePath, null});
			}
		}
	}

	@Override
	protected JavaFileManager getJavaFileManager(
		JavaFileManager javaFileManager) {

		if (javaFileManager instanceof StandardJavaFileManager) {
			StandardJavaFileManager standardJavaFileManager =
				(StandardJavaFileManager)javaFileManager;

			try {
				standardJavaFileManager.setLocation(
					StandardLocation.CLASS_PATH, _classPath);

				BundleJavaManager bundleJavaManager = new BundleJavaManager(
					_bundle, standardJavaFileManager, options, true);

				addBundleWirings(bundleJavaManager);

				bundleJavaManager.setResourceResolver(_resourceResolver);

				javaFileManager = bundleJavaManager;
			}
			catch (IOException ioe) {
				_logger.log(Logger.LOG_ERROR, ioe.getMessage(), ioe);
			}
		}

		return super.getJavaFileManager(javaFileManager);
	}

	protected String getTldUri(URL url) {
		try (InputStream inputStream = url.openStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream);
			BufferedReader bufferedReader = new BufferedReader(
				inputStreamReader)) {

			StringBuilder sb = null;

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				if (sb == null) {
					int x = line.indexOf("<uri>");

					if (x < 0) {
						continue;
					}

					x += 5;

					int y = line.indexOf("</uri>", x);

					if (y >= 0) {
						return line.substring(x, y);
					}

					sb = new StringBuilder(line.substring(x));
				}
				else {
					int y = line.indexOf("</uri>");

					if (y >= 0) {
						sb.append(line.substring(0, y));

						return sb.toString();
					}

					sb.append(line);
				}
			}

			return null;
		}
		catch (IOException ioe) {
			return ReflectionUtil.throwException(ioe);
		}
	}

	protected void initClassPath(ServletContext servletContext) {
		if (System.getSecurityManager() != null) {
			AccessController.doPrivileged(
				new PrivilegedAction<Void>() {

					@Override
					public Void run() {
						addDependenciesToClassPath();

						return null;
					}

				});
		}
		else {
			addDependenciesToClassPath();
		}
	}

	@SuppressWarnings("unchecked")
	protected void initTLDMappings(ServletContext servletContext) {
		Map<String, String[]> tldMappings =
			(Map<String, String[]>)servletContext.getAttribute(
				Constants.JSP_TLD_URI_TO_LOCATION_MAP);

		if (tldMappings != null) {
			return;
		}

		tldMappings = new HashMap<>();

		try {
			for (Bundle bundle : _allParticipatingBundles) {
				collectTLDMappings(tldMappings, bundle);
			}
		}
		catch (Exception e) {
			_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
		}

		Map<String, String> map =
			(Map<String, String>)servletContext.getAttribute(
				"jsp.taglib.mappings");

		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				tldMappings.put(
					entry.getKey(), new String[] {entry.getValue(), null});
			}
		}

		servletContext.setAttribute(
			Constants.JSP_TLD_URI_TO_LOCATION_MAP, tldMappings);
	}

	protected URI toURI(URL url)
		throws MalformedURLException, URISyntaxException {

		String protocol = url.getProtocol();

		if (protocol.equals("reference")) {
			String file = url.getFile();

			url = new URL(file);

			protocol = url.getProtocol();
		}

		if (protocol.equals("file")) {
			return url.toURI();
		}
		else if (protocol.equals("jar")) {
			String file = url.getFile();

			int pos = file.indexOf("!/");

			if (pos != -1) {
				file = file.substring(0, pos);
			}

			return new URI(file);
		}

		throw new URISyntaxException(
			url.toString(), "Unknown protocol " + protocol);
	}

	private static final String[] _JSP_COMPILER_DEPENDENCIES = {
		"com.liferay.portal.kernel.exception.PortalException",
		"com.liferay.portal.util.PortalImpl", "javax.portlet.PortletException",
		"javax.servlet.ServletException"
	};

	private Bundle[] _allParticipatingBundles;
	private Bundle _bundle;
	private final List<File> _classPath = new ArrayList<>();
	private Bundle _jspBundle;
	private Logger _logger;
	private ResourceResolver _resourceResolver;

}