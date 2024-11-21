/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.osgi.web.servlet.jsp.compiler.internal.util.ClassPathUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.lang.reflect.Field;

import java.net.URL;

import java.security.AccessController;
import java.security.CodeSource;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.apache.jasper.EmbeddedServletOptions;
import org.apache.jasper.JasperException;
import org.apache.jasper.JspCompilationContext;
import org.apache.jasper.Options;
import org.apache.jasper.compiler.ErrorDispatcher;
import org.apache.jasper.compiler.TldCache;
import org.apache.tomcat.util.descriptor.LocalResolver;
import org.apache.tomcat.util.descriptor.tld.TaglibXml;
import org.apache.tomcat.util.descriptor.tld.TldParser;
import org.apache.tomcat.util.descriptor.tld.TldResourcePath;
import org.apache.tomcat.util.digester.Digester;

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
public class JspCompiler {

	public DiagnosticCollector<JavaFileObject> compile(
			String className, ErrorDispatcher errorDispatcher)
		throws JasperException {

		_bytecodeJavaFileObjects = new ArrayList<>();

		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

		if (javaCompiler == null) {
			errorDispatcher.jspError("jsp.error.nojdk");

			throw new JasperException("Unable to find Java compiler");
		}

		DiagnosticCollector<JavaFileObject> diagnosticCollector =
			new DiagnosticCollector<>();

		StandardJavaFileManager standardJavaFileManager =
			javaCompiler.getStandardFileManager(
				diagnosticCollector, null, null);

		try {
			standardJavaFileManager.setLocation(
				StandardLocation.CLASS_PATH, _classPath);
		}
		catch (IOException ioException) {
			throw new JasperException(ioException);
		}

		try (JavaFileManager javaFileManager = new BundleJavaFileManager(
				_bytecodeJavaFileObjects, _classLoader, standardJavaFileManager,
				_javaFileObjectResolvers)) {

			JavaCompiler.CompilationTask compilationTask = javaCompiler.getTask(
				null, javaFileManager, diagnosticCollector, _compilerOptions,
				null,
				Collections.singletonList(
					new StringJavaFileObject(
						className.substring(
							className.lastIndexOf(CharPool.PERIOD) + 1),
						FileUtil.read(
							_jspCompilationContext.getServletJavaFileName()))));

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Compiling JSP: ".concat(_jspCompilationContext.getFQCN()));
			}

			if (compilationTask.call()) {
				saveClassFile(
					_jspCompilationContext.getFQCN(),
					_jspCompilationContext.getClassFileName());

				_bytecodeJavaFileObjects = null;

				return null;
			}
		}
		catch (IOException ioException) {
			throw new JasperException(ioException);
		}

		return diagnosticCollector;
	}

	public void init(JspCompilationContext jspCompilationContext) {
		_jspCompilationContext = jspCompilationContext;

		_compilerOptions.add("-XDuseUnsharedTable");
		_compilerOptions.add("-proc:none");

		String extDirs = System.getProperty("java.ext.dirs");

		if (extDirs != null) {
			_compilerOptions.add("-extdirs");
			_compilerOptions.add(extDirs);
		}

		Options options = jspCompilationContext.getOptions();

		if (options.getClassDebugInfo()) {
			_compilerOptions.add("-g");
		}
		else {
			_compilerOptions.add("-g:none");
		}

		_compilerOptions.add("-source");
		_compilerOptions.add(options.getCompilerSourceVM());

		_compilerOptions.add("-target");
		_compilerOptions.add(options.getCompilerTargetVM());

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
				(_bundleWiringPackageNames.size() * 4) + 6);

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

		_initClassPath();
		_initTLDMappings(options, servletContext);
	}

	public void saveClassFile(String className, String classFileName) {
		for (BytecodeJavaFileObject bytecodeJavaFileObject :
				_bytecodeJavaFileObjects) {

			String bytecodeFileClassName =
				bytecodeJavaFileObject.getClassName();
			String outputFileName = classFileName;

			if (!className.equals(bytecodeFileClassName)) {
				outputFileName = outputFileName.substring(
					0, outputFileName.lastIndexOf(File.separator) + 1);

				outputFileName = outputFileName.concat(
					bytecodeFileClassName.substring(
						bytecodeFileClassName.lastIndexOf(CharPool.PERIOD) + 1)
				).concat(
					".class"
				);
			}

			try (FileOutputStream fileOutputStream = new FileOutputStream(
					outputFileName)) {

				StreamUtil.transfer(
					bytecodeJavaFileObject.openInputStream(), fileOutputStream);
			}
			catch (IOException ioException) {
				ServletContext servletContext =
					_jspCompilationContext.getServletContext();

				servletContext.log("Unable to save class file", ioException);
			}
		}
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

	private void _addDependenciesToClassPath() {
		ClassLoader frameworkClassLoader = Bundle.class.getClassLoader();

		for (String className : _JSP_COMPILER_DEPENDENCIES) {
			try {
				Class<?> clazz = Class.forName(
					className, true, frameworkClassLoader);

				_addDependencyToClassPath(clazz);
			}
			catch (ClassNotFoundException classNotFoundException) {
				_log.error(
					"Unable to add depedency " + className +
						" to the classpath",
					classNotFoundException);
			}
		}
	}

	private void _addDependencyToClassPath(Class<?> clazz) {
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
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _collectTLDMappings(
			Bundle bundle, Map<TldResourcePath, TaglibXml> taglibXmls,
			Map<String, TldResourcePath> tldResourcePaths)
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

			if (url == null) {
				continue;
			}

			_populateTldMappings(
				StringPool.SLASH.concat(resourcePath), taglibXmls,
				tldResourcePaths, url);
		}

		List<URL> urls = new ArrayList<>(
			bundleWiring.findEntries(
				"/META-INF/", "*.tld", BundleWiring.LISTRESOURCES_RECURSE));

		urls.addAll(
			bundleWiring.findEntries(
				"/WEB-INF/", "*.tld", BundleWiring.LISTRESOURCES_RECURSE));

		for (URL url : urls) {
			_populateTldMappings(
				url.getPath(), taglibXmls, tldResourcePaths, url);
		}
	}

	private void _initClassPath() {
		if (System.getSecurityManager() != null) {
			AccessController.doPrivileged(
				(PrivilegedAction<Void>)() -> {
					_addDependenciesToClassPath();

					return null;
				});
		}
		else {
			_addDependenciesToClassPath();
		}
	}

	@SuppressWarnings("unchecked")
	private void _initTLDMappings(
		Options options, ServletContext servletContext) {

		Map<String, TldResourcePath> tldResourcePaths = new HashMap<>();
		Map<TldResourcePath, TaglibXml> taglibXmls = new HashMap<>();

		try {
			for (Bundle bundle : _allParticipatingBundles) {
				_collectTLDMappings(bundle, taglibXmls, tldResourcePaths);
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		Map<String, String> map =
			(Map<String, String>)servletContext.getAttribute(
				"jsp.taglib.mappings");

		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				try {
					URL url = servletContext.getResource(entry.getValue());

					if (url != null) {
						TldResourcePath tldResourcePath = new TldResourcePath(
							url, entry.getValue());

						tldResourcePaths.put(entry.getValue(), tldResourcePath);

						TldParser tldParser = new TldParser(true, false, true);

						taglibXmls.put(
							tldResourcePath, tldParser.parse(tldResourcePath));
					}
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			}
		}

		TldCache tldCache = new TldCache(
			servletContext, tldResourcePaths, taglibXmls);

		servletContext.setAttribute(
			TldCache.SERVLET_CONTEXT_ATTRIBUTE_NAME, tldCache);

		if (options instanceof EmbeddedServletOptions) {
			EmbeddedServletOptions embeddedServletOptions =
				(EmbeddedServletOptions)options;

			embeddedServletOptions.setTldCache(tldCache);
		}
	}

	private void _populateTldMappings(
			String absoluteResourcePath,
			Map<TldResourcePath, TaglibXml> taglibXmls,
			Map<String, TldResourcePath> tldResourcePaths, URL url)
		throws IOException {

		String uri = TldURIUtil.getTldURI(url);

		if ((uri == null) || tldResourcePaths.containsKey(uri)) {
			return;
		}

		uri = uri.trim();

		try {
			TldResourcePath tldResourcePath = new TldResourcePath(
				url, absoluteResourcePath);

			tldResourcePaths.put(uri, tldResourcePath);

			TldParser tldParser = new TldParser(true, false, true);

			Digester digester = (Digester)_digesterField.get(tldParser);

			digester.setEntityResolver(
				new LocalResolver(
					JspTaglibIDUtil.servletApiPublicIdsMap,
					JspTaglibIDUtil.servletApiSystemIdsMap, true));

			taglibXmls.put(tldResourcePath, tldParser.parse(tldResourcePath));
		}
		catch (Exception exception) {
			_log.error(exception);
		}
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
	private static final Field _digesterField;
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

		try {
			_digesterField = ReflectionUtil.getDeclaredField(
				TldParser.class, "digester");
		}
		catch (Exception exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	private Bundle[] _allParticipatingBundles;
	private final Map<BundleWiring, Set<String>> _bundleWiringPackageNames =
		new HashMap<>(_jspBundleWiringPackageNames);
	private List<BytecodeJavaFileObject> _bytecodeJavaFileObjects;
	private ClassLoader _classLoader;
	private final List<File> _classPath = new ArrayList<>();
	private final List<String> _compilerOptions = new ArrayList<>();
	private final List<JavaFileObjectResolver> _javaFileObjectResolvers =
		new ArrayList<>();
	private JspCompilationContext _jspCompilationContext;

}