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

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.osgi.web.servlet.jsp.compiler.internal.util.ClassPathUtil;

import java.io.File;
import java.io.IOException;

import java.net.URI;
import java.net.URL;

import java.security.AccessController;
import java.security.CodeSource;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.apache.jasper.Constants;
import org.apache.jasper.JasperException;
import org.apache.jasper.JspCompilationContext;
import org.apache.jasper.Options;
import org.apache.jasper.compiler.ErrorDispatcher;
import org.apache.jasper.compiler.JavacErrorDetail;
import org.apache.jasper.compiler.Jsr199JavaCompiler;
import org.apache.jasper.compiler.Node;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class JspCompiler extends Jsr199JavaCompiler {

	@Override
	public JavacErrorDetail[] compile(String className, Node.Nodes pageNodes)
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

			JavaCompiler.CompilationTask compilationTask = javaCompiler.getTask(
				null, javaFileManager, diagnosticCollector, options, null,
				Arrays.asList(
					new StringJavaFileObject(
						className.substring(className.lastIndexOf('.') + 1),
						charArrayWriter.toString())));

			if (_log.isDebugEnabled()) {
				_log.debug("Compiling JSP: ".concat(className));
			}

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

		List<Diagnostic<? extends JavaFileObject>> diagnostics =
			diagnosticCollector.getDiagnostics();

		JavacErrorDetail[] javacErrorDetails =
			new JavacErrorDetail[diagnostics.size()];

		for (int i = 0; i < diagnostics.size(); i++) {
			Diagnostic<? extends JavaFileObject> diagnostic = diagnostics.get(
				i);

			javacErrorDetails[i] = ErrorDispatcher.createJavacError(
				javaFileName, pageNodes,
				new StringBuilder(diagnostic.getMessage(null)),
				(int)diagnostic.getLineNumber());
		}

		return javacErrorDetails;
	}

	@Override
	public void init(
		JspCompilationContext jspCompilationContext,
		ErrorDispatcher errorDispatcher, boolean suppressLogging) {

		options.add("-XDuseUnsharedTable");

		Options options = jspCompilationContext.getOptions();

		_classPath.add(options.getScratchDir());

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

		for (Bundle participatingBundle : _allParticipatingBundles) {
			bundleWiring = participatingBundle.adapt(BundleWiring.class);

			for (BundleWire bundleWire : bundleWiring.getRequiredWires(null)) {
				BundleWiring providedBundleWiring =
					bundleWire.getProviderWiring();

				_bundleWiringPackageNames.put(
					providedBundleWiring,
					_collectPackageNames(providedBundleWiring));
			}

			_javaFileObjectResolvers.add(
				new JspJavaFileObjectResolver(
					bundleWiring, _jspBundleWiring, _bundleWiringPackageNames,
					_serviceTracker));
		}

		if (_log.isInfoEnabled()) {
			StringBundler sb = new StringBundler(
				_bundleWiringPackageNames.size() * 4 + 6);

			sb.append("JSP compiler for bundle ");
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

			_log.info(sb.toString());
		}

		jspCompilationContext.setClassLoader(jspBundleClassloader);

		initClassPath(servletContext);
		initTLDMappings(
			servletContext, jspCompilationContext.getTagFileJarUrls());

		super.init(jspCompilationContext, errorDispatcher, suppressLogging);
	}

	protected void addDependenciesToClassPath() {
		ClassLoader frameworkClassLoader = Bundle.class.getClassLoader();

		for (String className : _JSP_COMPILER_DEPENDENCIES) {
			try {
				Class<?> clazz = Class.forName(
					className, true, frameworkClassLoader);

				addDependencyToClassPath(clazz);
			}
			catch (ClassNotFoundException cnfe) {
				_log.error(
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
			File file = ClassPathUtil.getFile(url);

			if ((file == null) && _log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Ignoring URL ", url, " because of unknown protocol ",
						url.getProtocol()));
			}

			if (file.exists() && file.canRead()) {
				_classPath.remove(file);

				_classPath.add(0, file);
			}
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	protected void collectTLDMappings(
			Map<String, String[]> tldMappings, Map<String, URL> tagFileJarUrls,
			Bundle bundle)
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
				String absoluteResourcePath = StringPool.SLASH.concat(
					resourcePath);

				tldMappings.put(
					uri.trim(), new String[] {absoluteResourcePath, null});

				String urlString = url.toExternalForm();

				tagFileJarUrls.put(
					absoluteResourcePath,
					new URL(
						urlString.substring(
							0, urlString.length() - resourcePath.length())));
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
				_log.error(ioe.getMessage(), ioe);
			}

			javaFileManager = new BundleJavaFileManager(
				_classLoader, standardJavaFileManager,
				_javaFileObjectResolvers);
		}

		return super.getJavaFileManager(javaFileManager);
	}

	@Override
	protected JavaFileObject getOutputFile(String className, URI uri) {
		Map<String, Map<String, JavaFileObject>> packageMap =
			rtctxt.getPackageMap();

		String packageName = className.substring(
			0, className.lastIndexOf(CharPool.PERIOD));

		// Swap the parent class's packageJavaFileObjects reference from a plain
		// HashMap to a thread safe ConcurrentHashMap

		Map<String, JavaFileObject> packageJavaFileObjects = packageMap.get(
			packageName);

		JavaFileObject javaFileObject = super.getOutputFile(className, uri);

		if (packageJavaFileObjects == null) {
			packageMap.put(
				packageName,
				new ConcurrentHashMap<>(packageMap.get(packageName)));
		}

		return javaFileObject;
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
	protected void initTLDMappings(
		ServletContext servletContext, Map<String, URL> tagFileJarUrls) {

		Map<String, String[]> tldMappings =
			(Map<String, String[]>)servletContext.getAttribute(
				Constants.JSP_TLD_URI_TO_LOCATION_MAP);

		if (tldMappings != null) {
			return;
		}

		tldMappings = new HashMap<>();

		try {
			for (Bundle bundle : _allParticipatingBundles) {
				collectTLDMappings(tldMappings, tagFileJarUrls, bundle);
			}
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
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

	private static Set<String> _collectPackageNames(BundleWiring bundleWiring) {
		Set<String> packageNames = _bundleWiringPackageNamesCache.get(
			bundleWiring);

		if (packageNames != null) {
			return packageNames;
		}

		packageNames = new HashSet<>();

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

		_bundleWiringPackageNamesCache.put(bundleWiring, packageNames);

		return packageNames;
	}

	private static final String[] _JSP_COMPILER_DEPENDENCIES = {
		"com.liferay.portal.kernel.exception.PortalException",
		"com.liferay.portal.util.PortalImpl", "javax.portlet.PortletException",
		"javax.servlet.ServletException"
	};

	private static final Log _log = LogFactoryUtil.getLog(JspCompiler.class);

	private static final Map<BundleWiring, Set<String>>
		_bundleWiringPackageNamesCache = new ConcurrentReferenceKeyHashMap<>(
			new ConcurrentReferenceValueHashMap<>(
				FinalizeManager.SOFT_REFERENCE_FACTORY),
			FinalizeManager.WEAK_REFERENCE_FACTORY);
	private static final BundleWiring _jspBundleWiring;
	private static final Map<BundleWiring, Set<String>>
		_jspBundleWiringPackageNames = new HashMap<>();
	private static final ServiceTracker
		<Map<String, List<URL>>, Map<String, List<URL>>> _serviceTracker;

	static {
		Bundle jspBundle = FrameworkUtil.getBundle(JspCompiler.class);

		_jspBundleWiring = jspBundle.adapt(BundleWiring.class);

		for (BundleWire bundleWire : _jspBundleWiring.getRequiredWires(null)) {
			BundleWiring providedBundleWiring = bundleWire.getProviderWiring();

			Set<String> packageNames = _collectPackageNames(
				providedBundleWiring);

			_jspBundleWiringPackageNames.put(
				providedBundleWiring, packageNames);
		}

		BundleContext bundleContext = jspBundle.getBundleContext();

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext,
			"(&(jsp.compiler.resource.map=*)(objectClass=" +
				Map.class.getName() + "))");
	}

	private Bundle[] _allParticipatingBundles;
	private final Map<BundleWiring, Set<String>> _bundleWiringPackageNames =
		new HashMap<>(_jspBundleWiringPackageNames);
	private ClassLoader _classLoader;
	private final List<File> _classPath = new ArrayList<>();
	private final List<JavaFileObjectResolver> _javaFileObjectResolvers =
		new ArrayList<>();

}