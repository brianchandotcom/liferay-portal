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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.security.AccessController;
import java.security.CodeSource;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.apache.felix.utils.log.Logger;
import org.apache.jasper.Constants;
import org.apache.jasper.JasperException;
import org.apache.jasper.JspCompilationContext;
import org.apache.jasper.compiler.ErrorDispatcher;
import org.apache.jasper.compiler.JavacErrorDetail;
import org.apache.jasper.compiler.Jsr199JavaCompiler;
import org.apache.jasper.compiler.Node.Nodes;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class JspCompiler extends Jsr199JavaCompiler {

	@Override
	public JavacErrorDetail[] compile(String className, Nodes pageNodes)
		throws JasperException {

		classFiles = new ArrayList<>();

		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

		if (javaCompiler == null) {
			errDispatcher.jspError("jsp.error.nojdk");

			throw new JasperException("Unable to find Java compiler");
		}

		DiagnosticCollector<JavaFileObject> diagnosticCollector =
			new DiagnosticCollector<>();

		StandardJavaFileManager standardJavaFileManager =
			javaCompiler.getStandardFileManager(
				diagnosticCollector, null, null);

		try {
			standardJavaFileManager.setLocation(
				StandardLocation.CLASS_PATH, cpath);
		}
		catch (IOException ioe) {
			throw new JasperException(ioe);
		}

		try (JavaFileManager javaFileManager = getJavaFileManager(
			standardJavaFileManager)) {

			CompilationTask compilationTask = javaCompiler.getTask(
				null, javaFileManager, diagnosticCollector, options, null,
				Arrays.asList(
					new StringJavaFileObject(
						className.substring(className.lastIndexOf('.') + 1),
						charArrayWriter.toString())));

			if (compilationTask.call()) {
				for (BytecodeFile bytecodeFile : classFiles) {
					rtctxt.setBytecode(
						bytecodeFile.getClassName(),
						bytecodeFile.getBytecode());
				}

				return null;
			}
		}
		catch (IOException ioe) {
			throw new JasperException(ioe);
		}

		List<JavacErrorDetail> javacErrorDetails = new ArrayList<>();

		for (Diagnostic<? extends JavaFileObject> diagnostic :
				diagnosticCollector.getDiagnostics()) {

			javacErrorDetails.add(
				ErrorDispatcher.createJavacError(
					javaFileName, pageNodes,
					new StringBuilder(diagnostic.getMessage(null)),
					(int)diagnostic.getLineNumber()));
		}

		return javacErrorDetails.toArray(
			new JavacErrorDetail[javacErrorDetails.size()]);
	}

	@Override
	public void init(
		JspCompilationContext jspCompilationContext,
		ErrorDispatcher errorDispatcher, boolean suppressLogging) {

		Bundle jspBundle = _jspBundleWiring.getBundle();

		_logger = new Logger(jspBundle.getBundleContext());

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

		Bundle bundle = _allParticipatingBundles[0];

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		_classLoader = bundleWiring.getClassLoader();

		for (BundleWire bundleWire : bundleWiring.getRequiredWires(null)) {
			BundleWiring providedBundleWiring = bundleWire.getProviderWiring();

			_bundleWiringPackageNames.put(
				providedBundleWiring,
				_collectPackageNames(providedBundleWiring));
		}

		_bundleWiringPackageNames.putAll(_jspBundleWiringPackageNames);

		if (options.contains(BundleJavaFileManager.OPT_VERBOSE)) {
			StringBundler sb = new StringBundler(
				_bundleWiringPackageNames.size() * 4 + 6);

			sb.append("Jsp compiler for bundle ");
			sb.append(bundle.getSymbolicName());
			sb.append(StringPool.DASH);
			sb.append(bundle.getVersion());
			sb.append(" has dependent bundle wirings: ");

			for (BundleWiring curBundleWiring :
					_bundleWiringPackageNames.keySet()) {

				Bundle currentBundle = curBundleWiring.getBundle();

				sb.append(currentBundle.getSymbolicName());
				sb.append(StringPool.DASH);
				sb.append(currentBundle.getVersion());
				sb.append(StringPool.COMMA_AND_SPACE);
			}

			sb.setIndex(sb.index() - 1);

			_logger.log(Logger.LOG_INFO, sb.toString());
		}

		_javaFileObjectResolver = new JspJavaFileObjectResolver(
			bundleWiring, _jspBundleWiring, _bundleWiringPackageNames, _logger);

		jspCompilationContext.setClassLoader(jspBundleClassloader);

		initClassPath(servletContext);
		initTLDMappings(servletContext);

		super.init(jspCompilationContext, errorDispatcher, suppressLogging);
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
			Map<String, String[]> tldMappings, Bundle bundle)
		throws IOException {

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		List<String> resourcePaths = new ArrayList<>(
			bundleWiring.listResources(
				"/META-INF/", "*.tld", BundleWiring.LISTRESOURCES_RECURSE));

		resourcePaths.addAll(
			bundleWiring.listResources(
				"/WEB-INF/", "*.tld", BundleWiring.LISTRESOURCES_RECURSE));

		for (String resourcePath : resourcePaths) {
			URL url = bundle.getResource(resourcePath);

			String uri = TldURIUtil.getTldURI(url);

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
			}
			catch (IOException ioe) {
				_logger.log(Logger.LOG_ERROR, ioe.getMessage(), ioe);
			}

			javaFileManager = new BundleJavaFileManager(
				_classLoader, _systemPackageNames, standardJavaFileManager,
				_logger, options.contains(BundleJavaFileManager.OPT_VERBOSE),
				_javaFileObjectResolver);
		}

		return super.getJavaFileManager(javaFileManager);
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

	private static Set<String> _collectPackageNames(BundleWiring bundleWiring) {
		Set<String> packageNames = new HashSet<>();

		for (BundleCapability bundleCapability :
				bundleWiring.getCapabilities(
					BundleRevision.PACKAGE_NAMESPACE)) {

			Map<String, Object> attributes = bundleCapability.getAttributes();

			Object packageName = attributes.get(
				BundleRevision.PACKAGE_NAMESPACE);

			if (packageName != null) {
				packageNames.add((String)packageName);
			}
		}

		return packageNames;
	}

	private static final String[] _JSP_COMPILER_DEPENDENCIES = {
		"com.liferay.portal.kernel.exception.PortalException",
		"com.liferay.portal.util.PortalImpl", "javax.portlet.PortletException",
		"javax.servlet.ServletException"
	};

	private static final BundleWiring _jspBundleWiring;
	private static final Map<BundleWiring, Set<String>>
		_jspBundleWiringPackageNames = new LinkedHashMap<>();
	private static final Set<String> _systemPackageNames;

	static {
		Bundle jspBundle = FrameworkUtil.getBundle(JspCompiler.class);

		_jspBundleWiring = jspBundle.adapt(BundleWiring.class);

		for (BundleWire bundleWire : _jspBundleWiring.getRequiredWires(null)) {
			BundleWiring providedBundleWiring = bundleWire.getProviderWiring();

			_jspBundleWiringPackageNames.put(
				providedBundleWiring,
				_collectPackageNames(providedBundleWiring));
		}

		BundleContext bundleContext = jspBundle.getBundleContext();

		Bundle systemBundle = bundleContext.getBundle(0);

		if (systemBundle == null) {
			throw new ExceptionInInitializerError(
				"Unable to access to system bundle");
		}

		_systemPackageNames = _collectPackageNames(
			systemBundle.adapt(BundleWiring.class));
	}

	private Bundle[] _allParticipatingBundles;
	private final Map<BundleWiring, Set<String>> _bundleWiringPackageNames =
		new LinkedHashMap<>();
	private ClassLoader _classLoader;
	private final List<File> _classPath = new ArrayList<>();
	private JavaFileObjectResolver _javaFileObjectResolver;
	private Logger _logger;

}