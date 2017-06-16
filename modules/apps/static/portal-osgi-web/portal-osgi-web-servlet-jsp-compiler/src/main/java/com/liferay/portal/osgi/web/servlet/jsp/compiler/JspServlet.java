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

package com.liferay.portal.osgi.web.servlet.jsp.compiler;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.osgi.web.servlet.jsp.compiler.internal.JspBundleClassloader;
import com.liferay.portal.osgi.web.servlet.jsp.compiler.internal.JspServletContext;
import com.liferay.portal.osgi.web.servlet.jsp.compiler.internal.JspTagHandlerPool;
import com.liferay.portal.servlet.delegate.ServletContextDelegate;
import com.liferay.portal.util.PropsValues;
import com.liferay.taglib.servlet.JspFactorySwapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.net.MalformedURLException;
import java.net.URL;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.jsp.JspFactory;

import org.apache.felix.utils.log.Logger;
import org.apache.jasper.runtime.JspFactoryImpl;
import org.apache.jasper.runtime.TagHandlerPool;
import org.apache.jasper.xmlparser.ParserUtils;
import org.apache.jasper.xmlparser.TreeNode;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleReference;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Raymond Augé
 */
public class JspServlet extends HttpServlet {

	public static final String JSP_FILE = org.apache.jasper.Constants.JSP_FILE;

	public static void scanTLDs(
		Bundle bundle, ServletContext servletContext,
		List<String> listenerClassNames) {

		Boolean analyzedTlds = (Boolean)servletContext.getAttribute(
			_ANALYZED_TLDS);

		if ((analyzedTlds != null) && analyzedTlds.booleanValue()) {
			return;
		}

		servletContext.setAttribute(_ANALYZED_TLDS, Boolean.TRUE);

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		Collection<String> resources = bundleWiring.listResources(
			"META-INF/", "*.tld", BundleWiring.LISTRESOURCES_RECURSE);

		if (resources == null) {
			return;
		}

		for (String resource : resources) {
			URL url = bundle.getResource(resource);

			if (url == null) {
				continue;
			}

			try (InputStream inputStream = url.openStream()) {
				ParserUtils parserUtils = new ParserUtils(true);

				TreeNode treeNode = parserUtils.parseXMLDocument(
					url.getPath(), inputStream, false);

				Iterator<TreeNode> iterator = treeNode.findChildren("listener");

				while (iterator.hasNext()) {
					TreeNode listenerTreeNode = iterator.next();

					TreeNode listenerClassTreeNode = listenerTreeNode.findChild(
						"listener-class");

					if (listenerClassTreeNode == null) {
						continue;
					}

					String listenerClassName = listenerClassTreeNode.getBody();

					if (listenerClassName == null) {
						continue;
					}

					listenerClassNames.add(listenerClassName);
				}
			}
			catch (Exception e) {
				servletContext.log(e.getMessage(), e);
			}
		}
	}

	@Override
	public void destroy() {
		_jspServlet.destroy();

		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();

		_bundleTracker.close();
	}

	@Override
	public boolean equals(Object obj) {
		return _jspServlet.equals(obj);
	}

	@Override
	public String getInitParameter(String name) {
		return _jspServlet.getInitParameter(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return _jspServlet.getInitParameterNames();
	}

	@Override
	public ServletConfig getServletConfig() {
		return _jspServlet.getServletConfig();
	}

	@Override
	public ServletContext getServletContext() {
		return _jspServlet.getServletContext();
	}

	@Override
	public String getServletInfo() {
		return _jspServlet.getServletInfo();
	}

	@Override
	public String getServletName() {
		return _jspServlet.getServletName();
	}

	@Override
	public int hashCode() {
		return _jspServlet.hashCode();
	}

	@Override
	public void init() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void init(final ServletConfig servletConfig)
		throws ServletException {

		final ServletContext servletContext = ServletContextDelegate.create(
			servletConfig.getServletContext());

		ClassLoader classLoader = servletContext.getClassLoader();

		if (!(classLoader instanceof BundleReference)) {
			throw new IllegalStateException();
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(classLoader);

			JspFactory.setDefaultFactory(new JspFactoryImpl());

			JspFactorySwapper.swap();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		List<Bundle> bundles = new ArrayList<>();

		BundleReference bundleReference = (BundleReference)classLoader;

		_bundle = bundleReference.getBundle();

		bundles.add(_bundle);

		bundles.add(_jspBundle);

		_logger = new Logger(_bundle.getBundleContext());

		collectTaglibProviderBundles(bundles);

		_allParticipatingBundles = bundles.toArray(new Bundle[bundles.size()]);

		_jspBundleClassloader = new JspBundleClassloader(
			_allParticipatingBundles);

		final Map<String, String> defaults = new HashMap<>();

		defaults.put(
			"compilerClassName",
			"com.liferay.portal.osgi.web.servlet.jsp.compiler.internal." +
				"JspCompiler");
		defaults.put("compilerSourceVM", "1.8");
		defaults.put("compilerTargetVM", "1.8");
		defaults.put("development", "false");
		defaults.put("httpMethods", "GET,POST,HEAD");
		defaults.put("keepgenerated", "false");
		defaults.put("logVerbosityLevel", "NONE");
		defaults.put("saveBytecode", "true");

		StringBundler sb = new StringBundler(4);

		sb.append(_WORK_DIR);
		sb.append(_bundle.getSymbolicName());
		sb.append(StringPool.DASH);
		sb.append(_bundle.getVersion());

		defaults.put(_INIT_PARAMETER_NAME_SCRATCH_DIR, sb.toString());

		defaults.put(
			TagHandlerPool.OPTION_TAGPOOL, JspTagHandlerPool.class.getName());

		for (Entry<Object, Object> entry : _initParams.entrySet()) {
			defaults.put(
				String.valueOf(entry.getKey()),
				String.valueOf(entry.getValue()));
		}

		Enumeration<String> names = servletConfig.getInitParameterNames();

		Set<String> nameSet = new HashSet<>(Collections.list(names));

		nameSet.addAll(defaults.keySet());

		final Enumeration<String> initParameterNames = Collections.enumeration(
			nameSet);

		_jspServlet.init(
			new ServletConfig() {

				@Override
				public String getInitParameter(String name) {
					String value = servletConfig.getInitParameter(name);

					if (value == null) {
						value = defaults.get(name);
					}

					return value;
				}

				@Override
				public Enumeration<String> getInitParameterNames() {
					return initParameterNames;
				}

				@Override
				public ServletContext getServletContext() {
					return _jspServletContext;
				}

				@Override
				public String getServletName() {
					return servletConfig.getServletName();
				}

				private final ServletContext _jspServletContext =
					(ServletContext)Proxy.newProxyInstance(
						_jspBundleClassloader, _INTERFACES,
						new JspServletContextInvocationHandler(
							servletContext, _bundle));

			});

		_logVerbosityLevelDebug = Objects.equals(
			_jspServlet.getInitParameter("logVerbosityLevel"), "DEBUG");

		_bundleTracker = new BundleTracker<>(
			_bundle.getBundleContext(), Bundle.RESOLVED,
			new JspFragmentTrackerCustomizer());

		_bundleTracker.open();
	}

	@Override
	public void log(String msg) {
		_jspServlet.log(msg);
	}

	@Override
	public void log(String message, Throwable t) {
		_jspServlet.log(message, t);
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_jspBundleClassloader);

			if (_logVerbosityLevelDebug) {
				String path = (String)request.getAttribute(
					RequestDispatcher.INCLUDE_SERVLET_PATH);

				if (path != null) {
					String pathInfo = (String)request.getAttribute(
						RequestDispatcher.INCLUDE_PATH_INFO);

					if (pathInfo != null) {
						path += pathInfo;
					}
				}
				else {
					path = request.getServletPath();

					String pathInfo = request.getPathInfo();

					if (pathInfo != null) {
						path += pathInfo;
					}
				}

				_jspServlet.log("[JSP DEBUG] " + _bundle + " invoking " + path);
			}

			_jspServlet.service(request, response);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
		throws IOException, ServletException {

		service((HttpServletRequest)request, (HttpServletResponse)response);
	}

	@Override
	public String toString() {
		return _jspServlet.toString();
	}

	protected void collectTaglibProviderBundles(List<Bundle> bundles) {
		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		for (BundleWire bundleWire :
				bundleWiring.getRequiredWires("osgi.extender")) {

			BundleCapability bundleCapability = bundleWire.getCapability();

			Map<String, Object> attributes = bundleCapability.getAttributes();

			Object value = attributes.get("osgi.extender");

			if (value.equals("jsp.taglib")) {
				BundleRevision bundleRevision = bundleWire.getProvider();

				Bundle bundle = bundleRevision.getBundle();

				if (!bundles.contains(bundle)) {
					bundles.add(bundle);
				}
			}
		}
	}

	protected String[] getListenerClassNames(Class<?> clazz) {
		List<String> classNames = new ArrayList<>();

		if (ServletContextListener.class.isAssignableFrom(clazz)) {
			classNames.add(ServletContextListener.class.getName());
		}

		if (ServletContextAttributeListener.class.isAssignableFrom(clazz)) {
			classNames.add(ServletContextAttributeListener.class.getName());
		}

		if (ServletRequestListener.class.isAssignableFrom(clazz)) {
			classNames.add(ServletRequestListener.class.getName());
		}

		if (ServletRequestAttributeListener.class.isAssignableFrom(clazz)) {
			classNames.add(ServletRequestAttributeListener.class.getName());
		}

		if (HttpSessionListener.class.isAssignableFrom(clazz)) {
			classNames.add(HttpSessionListener.class.getName());
		}

		if (HttpSessionAttributeListener.class.isAssignableFrom(clazz)) {
			classNames.add(HttpSessionAttributeListener.class.getName());
		}

		if (classNames.isEmpty()) {
			throw new IllegalArgumentException(
				clazz.getName() + " does not implement one of the supported " +
					"servlet listener interfaces");
		}

		return classNames.toArray(new String[classNames.size()]);
	}

	private static Map<Method, Method> _createContextAdapterMethods() {
		Map<Method, Method> methods = new HashMap<>();

		Method[] adapterMethods =
			JspServletContextInvocationHandler.class.getDeclaredMethods();

		for (Method adapterMethod : adapterMethods) {
			String name = adapterMethod.getName();
			Class<?>[] parameterTypes = adapterMethod.getParameterTypes();

			try {
				Method method = ServletContext.class.getMethod(
					name, parameterTypes);

				methods.put(method, adapterMethod);
			}
			catch (NoSuchMethodException nsme) {
			}
		}

		try {
			Method equalsMethod = Object.class.getMethod(
				"equals", Object.class);

			Method equalsHandlerMethod =
				JspServletContextInvocationHandler.class.getMethod(
					"equals", Object.class);

			methods.put(equalsMethod, equalsHandlerMethod);

			Method hashCodeMethod = Object.class.getMethod(
				"hashCode", (Class<?>[])null);

			Method hashCodeHandlerMethod =
				JspServletContextInvocationHandler.class.getMethod(
					"hashCode", (Class<?>[])null);

			methods.put(hashCodeMethod, hashCodeHandlerMethod);
		}
		catch (NoSuchMethodException nsme) {
		}

		return methods;
	}

	private void _deleteOutdatedJspFiles(String dir, List<Path> paths) {
		FileSystem fileSystem = FileSystems.getDefault();

		Path dirPath = fileSystem.getPath(dir);

		if (Files.exists(dirPath) && !paths.isEmpty()) {
			try {
				Files.walkFileTree(dirPath, new DeleteFileVisitor(paths));
			}
			catch (IOException ioe) {
				_logger.log(
					Logger.LOG_WARNING,
					"Unable to delete outdated files: " + paths);
			}
		}
	}

	private static final String _ANALYZED_TLDS =
		JspServlet.class.getName().concat("#ANALYZED_TLDS");

	private static final String _DIR_NAME_RESOURCES = "/META-INF/resources";

	private static final String _INIT_PARAMETER_NAME_SCRATCH_DIR = "scratchdir";

	private static final Class<?>[] _INTERFACES =
		{JspServletContext.class, ServletContext.class};

	private static final String _WORK_DIR =
		PropsValues.LIFERAY_HOME + File.separator + "work" + File.separator;

	private static final Map<Method, Method> _contextAdapterMethods;
	private static final Properties _initParams = PropsUtil.getProperties(
		"jsp.servlet.init.param.", true);
	private static final Bundle _jspBundle = FrameworkUtil.getBundle(
		JspServlet.class);
	private static final Pattern _originalJspPattern = Pattern.compile(
		"^(?<file>.*)(\\.(portal|original))(?<extension>\\.(jsp|jspf))$");

	static {
		_contextAdapterMethods = _createContextAdapterMethods();
	}

	private Bundle[] _allParticipatingBundles;
	private Bundle _bundle;
	private BundleTracker<List<Path>> _bundleTracker;
	private JspBundleClassloader _jspBundleClassloader;
	private final HttpServlet _jspServlet =
		new org.apache.jasper.servlet.JspServlet();
	private Logger _logger;
	private boolean _logVerbosityLevelDebug;
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new CopyOnWriteArrayList<>();

	private class DeleteFileVisitor extends SimpleFileVisitor<Path> {

		public DeleteFileVisitor(List<Path> paths) {
			_paths = paths;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			throws IOException {

			if (_paths.contains(file.toAbsolutePath())) {
				Files.delete(file);
			}

			return FileVisitResult.CONTINUE;
		}

		private final List<Path> _paths;

	}

	private class JspFragmentTrackerCustomizer
		implements BundleTrackerCustomizer<List<Path>> {

		@Override
		public List<Path> addingBundle(Bundle bundle, BundleEvent bundleEvent) {
			List<Path> paths = new ArrayList<>();

			Dictionary<String, String> headers = bundle.getHeaders();

			String fragmentHost = headers.get("Fragment-Host");

			if (fragmentHost == null) {
				return null;
			}

			String[] fragmentHostParts = StringUtil.split(
				fragmentHost, CharPool.SEMICOLON);

			fragmentHost = fragmentHostParts[0];

			String symbolicName = _bundle.getSymbolicName();

			if (!symbolicName.equals(fragmentHost)) {
				return null;
			}

			Enumeration<URL> enumeration = bundle.findEntries(
				_DIR_NAME_RESOURCES, "*.jsp", true);

			if (enumeration == null) {
				return paths;
			}

			String scratchDirName = _jspServlet.getInitParameter(
				_INIT_PARAMETER_NAME_SCRATCH_DIR);

			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				String pathString = url.getPath();

				if (pathString.startsWith(_DIR_NAME_RESOURCES)) {
					pathString = pathString.substring(
						_DIR_NAME_RESOURCES.length() + 1,
						pathString.length() - 4);
				}
				else {
					pathString = pathString.substring(
						1, pathString.length() - 4);
				}

				pathString = StringUtil.replace(
					pathString, CharPool.UNDERLINE, "_005f");

				paths.add(
					Paths.get(
						scratchDirName,
						"/org/apache/jsp/" + pathString + "_jsp.class"));
			}

			_deleteOutdatedJspFiles(scratchDirName, paths);

			return paths;
		}

		@Override
		public void modifiedBundle(
			Bundle bundle, BundleEvent bundleEvent, List<Path> paths) {
		}

		@Override
		public void removedBundle(
			Bundle bundle, BundleEvent bundleEvent, final List<Path> paths) {

			String scratchDirName = _jspServlet.getInitParameter(
				_INIT_PARAMETER_NAME_SCRATCH_DIR);

			_deleteOutdatedJspFiles(scratchDirName, paths);
		}

	}

	private class JspServletContextInvocationHandler
		implements InvocationHandler, JspServletContext {

		public JspServletContextInvocationHandler(
			ServletContext servletContext, Bundle bundle) {

			_servletContext = servletContext;
			_bundle = bundle;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ServletContext)) {
				return false;
			}

			ServletContext servletContext = (ServletContext)obj;

			if (obj instanceof JspServletContext) {
				JspServletContext jspServletContext = (JspServletContext)obj;

				servletContext = jspServletContext.getWrappedServletContext();
			}

			return servletContext.equals(_servletContext);
		}

		@Override
		public ServletContext getWrappedServletContext() {
			return _servletContext;
		}

		@Override
		public int hashCode() {
			return _servletContext.hashCode();
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (method.getName().equals("getClassLoader")) {
				return _jspBundleClassloader;
			}
			else if (method.getName().equals("getResource")) {
				return _getResource((String)args[0]);
			}
			else if (method.getName().equals("getResourceAsStream")) {
				return _getResourceAsStream((String)args[0]);
			}
			else if (method.getName().equals("getResourcePaths")) {
				return _getResourcePaths((String)args[0]);
			}

			Method adapterMethod = _contextAdapterMethods.get(method);

			if (adapterMethod != null) {
				return adapterMethod.invoke(this, args);
			}

			return method.invoke(_servletContext, args);
		}

		private URL _getExtension(String path) {
			Matcher matcher = _originalJspPattern.matcher(path);

			if (matcher.matches()) {
				path = matcher.group("file") + matcher.group("extension");

				return _bundle.getEntry(_DIR_NAME_RESOURCES + path);
			}

			Enumeration<URL> enumeration = _bundle.findEntries(
				_DIR_NAME_RESOURCES, path.substring(1), false);

			if (enumeration == null) {
				return null;
			}

			List<URL> urls = Collections.list(enumeration);

			return urls.get(urls.size() - 1);
		}

		private URL _getResource(String path) {
			try {
				if ((path == null) || path.equals(StringPool.BLANK)) {
					return null;
				}

				if (path.charAt(0) != '/') {
					path = '/' + path;
				}

				URL url = _getExtension(path);

				if (url != null) {
					return url;
				}

				url = _servletContext.getResource(path);

				if (url != null) {
					return url;
				}

				url = _servletContext.getClassLoader().getResource(path);

				if (url != null) {
					return url;
				}

				if (!path.startsWith("/META-INF/")) {
					url = _servletContext.getResource(
						_DIR_NAME_RESOURCES.concat(path));
				}

				if (url != null) {
					return url;
				}

				for (int i = 2; i < _allParticipatingBundles.length; i++) {
					url = _allParticipatingBundles[i].getEntry(path);

					if (url != null) {
						return url;
					}
				}

				return _jspBundle.getResource(path);
			}
			catch (MalformedURLException murle) {
			}

			return null;
		}

		private InputStream _getResourceAsStream(String path) {
			URL url = _getResource(path);

			if (url == null) {
				return null;
			}

			try {
				return url.openStream();
			}
			catch (IOException ioe) {
				return null;
			}
		}

		private Set<String> _getResourcePaths(String path) {
			Set<String> paths = _servletContext.getResourcePaths(path);

			Enumeration<URL> enumeration = _jspBundle.findEntries(
				path, null, false);

			if (enumeration != null) {
				if ((paths == null) && enumeration.hasMoreElements()) {
					paths = new HashSet<>();
				}

				while (enumeration.hasMoreElements()) {
					URL url = enumeration.nextElement();

					paths.add(url.getPath());
				}
			}

			return paths;
		}

		private final Bundle _bundle;
		private final ServletContext _servletContext;

	}

}