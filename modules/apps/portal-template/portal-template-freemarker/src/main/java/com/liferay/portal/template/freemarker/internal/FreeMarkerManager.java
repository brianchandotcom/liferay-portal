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

package com.liferay.portal.template.freemarker.internal;

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.JSPSupportServlet;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoader;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.template.BaseTemplateManager;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.template.freemarker.configuration.FreeMarkerEngineConfiguration;

import freemarker.cache.TemplateCache;

import freemarker.core.TemplateClassResolver;

import freemarker.debug.impl.DebuggerService;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.jsp.internal.WriterFactoryUtil;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.ServletContextHashModel;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.net.URL;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Mika Koivisto
 * @author Tina Tina
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.portal.template.freemarker.configuration.FreeMarkerEngineConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = "language.type=" + TemplateConstants.LANG_TYPE_FTL,
	service = TemplateManager.class
)
public class FreeMarkerManager extends BaseTemplateManager {

	public static BeansWrapper getBeansWrapper() {
		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		BeansWrapper beansWrapper = _beansWrappers.get(classLoader);

		if (beansWrapper == null) {
			BeansWrapperBuilder beansWrapperBuilder = new BeansWrapperBuilder(
				Configuration.getVersion());

			beansWrapper = beansWrapperBuilder.build();

			_beansWrappers.put(classLoader, beansWrapper);
		}

		return beansWrapper;
	}

	@Override
	public void addStaticClassSupport(
		Map<String, Object> contextObjects, String variableName,
		Class<?> variableClass) {

		try {
			BeansWrapper beansWrapper = getBeansWrapper();

			TemplateHashModel templateHashModel =
				beansWrapper.getStaticModels();

			TemplateModel templateModel = templateHashModel.get(
				variableClass.getName());

			contextObjects.put(variableName, templateModel);
		}
		catch (TemplateModelException tme) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Variable " + variableName + " registration fail", tme);
			}
		}
	}

	@Override
	public void addTaglibApplication(
		Map<String, Object> contextObjects, String applicationName,
		ServletContext servletContext) {

		contextObjects.put(
			applicationName, getServletContextHashModel(servletContext));
	}

	@Override
	public void addTaglibFactory(
		Map<String, Object> contextObjects, String taglibFactoryName,
		ServletContext servletContext) {

		contextObjects.put(
			taglibFactoryName, new TaglibFactoryWrapper(servletContext));
	}

	@Override
	public void addTaglibRequest(
		Map<String, Object> contextObjects, String applicationName,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		contextObjects.put(
			applicationName,
			new HttpRequestHashModel(
				httpServletRequest, httpServletResponse,
				_configuration.getObjectWrapper()));
	}

	@Override
	public void addTaglibSupport(
		Map<String, Object> contextObjects,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ServletContext servletContext = httpServletRequest.getServletContext();

		addTaglibApplication(contextObjects, "Application", servletContext);

		addTaglibRequest(
			contextObjects, "Request", httpServletRequest, httpServletResponse);

		// Legacy

		TaglibFactoryWrapper taglibFactoryWrapper = new TaglibFactoryWrapper(
			servletContext);

		contextObjects.put("PortalJspTagLibs", taglibFactoryWrapper);
		contextObjects.put("PortletJspTagLibs", taglibFactoryWrapper);
		contextObjects.put("taglibLiferayHash", taglibFactoryWrapper);

		// Contributed

		for (Map.Entry<String, String> entry : _taglibMappings.entrySet()) {
			try {
				contextObjects.put(
					entry.getKey(), taglibFactoryWrapper.get(entry.getValue()));
			}
			catch (TemplateModelException tme) {
				_log.error(
					"Unable to add taglib " + entry.getKey() + " to context",
					tme);
			}
		}
	}

	@Override
	public void destroy() {
		if (_configuration == null) {
			return;
		}

		_configuration.clearEncodingMap();
		_configuration.clearSharedVariables();
		_configuration.clearTemplateCache();

		_configuration = null;

		templateContextHelper.removeAllHelperUtilities();

		_templateModels.clear();

		if (isEnableDebuggerService()) {
			//DebuggerService.shutdown();
		}
	}

	@Override
	public void destroy(ClassLoader classLoader) {
		templateContextHelper.removeHelperUtilities(classLoader);
	}

	@Override
	public String getName() {
		return TemplateConstants.LANG_TYPE_FTL;
	}

	@Override
	public String[] getRestrictedVariables() {
		return _freeMarkerEngineConfiguration.restrictedVariables();
	}

	@Override
	public void init() throws TemplateException {
		if (_configuration != null) {
			return;
		}

		_configuration = new Configuration(Configuration.getVersion());

		try {
			Field field = ReflectionUtil.getDeclaredField(
				Configuration.class, "cache");

			PortalCache<TemplateResource, TemplateCache.MaybeMissingTemplate>
				portalCache = null;

			if (_freeMarkerTemplateResourceCache.isEnabled()) {
				portalCache =
					(PortalCache
						<TemplateResource, TemplateCache.MaybeMissingTemplate>)
							_singleVMPool.getPortalCache(
								StringBundler.concat(
									TemplateResource.class.getName(),
									StringPool.POUND,
									TemplateConstants.LANG_TYPE_FTL));

				_freeMarkerTemplateResourceCache.setSecondLevelPortalCache(
					portalCache);
			}

			TemplateCache templateCache = new LiferayTemplateCache(
				_configuration, templateResourceLoader, portalCache);

			field.set(_configuration, templateCache);
		}
		catch (Exception e) {
			throw new TemplateException(
				"Unable to Initialize FreeMarker manager", e);
		}

		_configuration.setDefaultEncoding(StringPool.UTF8);
		_configuration.setLocalizedLookup(
			_freeMarkerEngineConfiguration.localizedLookup());
		_configuration.setNewBuiltinClassResolver(_templateClassResolver);

		try {
			_configuration.setSetting("auto_import", _getMacroLibrary());
			_configuration.setSetting(
				"template_exception_handler",
				_freeMarkerEngineConfiguration.templateExceptionHandler());
		}
		catch (Exception e) {
			throw new TemplateException("Unable to init FreeMarker manager", e);
		}

		_defaultObjectWrapper = new LiferayObjectWrapper();
		_restrictedObjectWrapper = new RestrictedLiferayObjectWrapper(
			_freeMarkerEngineConfiguration.allowedClasses(),
			_freeMarkerEngineConfiguration.restrictedClasses(),
			_freeMarkerEngineConfiguration.restrictedMethods());

		if (isEnableDebuggerService()) {
			DebuggerService.getBreakpoints("*");
		}
	}

	@Reference(unbind = "-")
	public void setTemplateClassResolver(
		TemplateClassResolver templateClassResolver) {

		_templateClassResolver = templateClassResolver;
	}

	@Override
	@Reference(service = FreeMarkerTemplateContextHelper.class, unbind = "-")
	public void setTemplateContextHelper(
		TemplateContextHelper templateContextHelper) {

		super.setTemplateContextHelper(templateContextHelper);
	}

	@Override
	@Reference(service = FreeMarkerTemplateResourceLoader.class, unbind = "-")
	public void setTemplateResourceLoader(
		TemplateResourceLoader templateResourceLoader) {

		super.setTemplateResourceLoader(templateResourceLoader);
	}

	@Activate
	@Modified
	protected void activate(ComponentContext componentContext) {
		_freeMarkerEngineConfiguration = ConfigurableUtil.createConfigurable(
			FreeMarkerEngineConfiguration.class,
			componentContext.getProperties());

		BundleContext bundleContext = componentContext.getBundleContext();

		_bundle = bundleContext.getBundle();

		int stateMask = ~Bundle.INSTALLED & ~Bundle.UNINSTALLED;

		_bundleTracker = new BundleTracker<>(
			bundleContext, stateMask, new TaglibBundleTrackerCustomizer());

		_bundleTracker.open();

		WriterFactoryUtil.setWriterFactory(new UnsyncStringWriterFactory());
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	@Override
	protected Template doGetTemplate(
		TemplateResource templateResource, boolean restricted,
		Map<String, Object> helperUtilities) {

		ObjectWrapper objectWrapper = _defaultObjectWrapper;

		if (restricted) {
			objectWrapper = _restrictedObjectWrapper;
		}

		return new FreeMarkerTemplate(
			templateResource, helperUtilities, _configuration,
			templateContextHelper, _freeMarkerTemplateResourceCache, restricted,
			objectWrapper);
	}

	protected FreeMarkerBundleClassloader getFreeMarkerBundleClassloader() {
		int currentCount;

		synchronized (_bundleTracker) {
			if (((currentCount = _bundleTracker.getTrackingCount()) <=
					_bundleTrackingCount) &&
				(_freeMarkerBundleClassloader != null)) {

				return _freeMarkerBundleClassloader;
			}

			_bundleTrackingCount = currentCount;

			Bundle[] bundles = _bundleTracker.getBundles();

			if (bundles == null) {
				bundles = new Bundle[] {_bundle};
			}
			else {
				Bundle[] tempBundles = new Bundle[bundles.length + 1];

				tempBundles[0] = _bundle;

				System.arraycopy(bundles, 0, tempBundles, 1, bundles.length);

				bundles = tempBundles;
			}

			return _freeMarkerBundleClassloader =
				new FreeMarkerBundleClassloader(bundles);
		}
	}

	protected ServletContextHashModel getServletContextHashModel(
		ServletContext servletContext) {

		GenericServlet genericServlet = new JSPSupportServlet(servletContext);

		return new ServletContextHashModel(
			genericServlet, _configuration.getObjectWrapper());
	}

	protected ServletContext getServletContextWrapper(
		ServletContext servletContext,
		FreeMarkerBundleClassloader freeMarkerBundleClassloader) {

		return _servletContextProxyProviderFunction.apply(
			new ServletContextInvocationHandler(
				servletContext, freeMarkerBundleClassloader));
	}

	protected boolean isEnableDebuggerService() {
		if ((System.getProperty("freemarker.debug.password") != null) &&
			(System.getProperty("freemarker.debug.port") != null)) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setSingleVMPool(SingleVMPool singleVMPool) {
		_singleVMPool = singleVMPool;
	}

	private String _getMacroLibrary() {
		Class<?> clazz = getClass();

		String contextName = ClassLoaderPool.getContextName(
			clazz.getClassLoader());

		contextName = contextName.concat(
			TemplateConstants.CLASS_LOADER_SEPARATOR);

		String[] macroLibrary = _freeMarkerEngineConfiguration.macroLibrary();

		StringBundler sb = new StringBundler(3 * macroLibrary.length);

		for (String library : macroLibrary) {
			sb.append(contextName);
			sb.append(library);
			sb.append(StringPool.COMMA);
		}

		if (macroLibrary.length > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FreeMarkerManager.class);

	private static final Map<ClassLoader, BeansWrapper> _beansWrappers =
		new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);
	private static final Function<InvocationHandler, ServletContext>
		_servletContextProxyProviderFunction =
			ProxyUtil.getProxyProviderFunction(ServletContext.class);

	private Bundle _bundle;
	private BundleTracker<Set<String>> _bundleTracker;

	// Set initial to -2 because -1 has significance to bundle trackers

	private volatile int _bundleTrackingCount = -2;
	private Configuration _configuration;
	private ObjectWrapper _defaultObjectWrapper;
	private volatile FreeMarkerBundleClassloader _freeMarkerBundleClassloader;
	private volatile FreeMarkerEngineConfiguration
		_freeMarkerEngineConfiguration;

	@Reference
	private FreeMarkerTemplateResourceCache _freeMarkerTemplateResourceCache;

	private ObjectWrapper _restrictedObjectWrapper;
	private SingleVMPool _singleVMPool;
	private final Map<String, String> _taglibMappings =
		new ConcurrentHashMap<>();
	private TemplateClassResolver _templateClassResolver;
	private final Map<String, TemplateModel> _templateModels =
		new ConcurrentHashMap<>();

	private class ServletContextInvocationHandler implements InvocationHandler {

		public ServletContextInvocationHandler(
			ServletContext servletContext,
			FreeMarkerBundleClassloader freeMarkerBundleClassloader) {

			_servletContext = servletContext;
			_freeMarkerBundleClassloader = freeMarkerBundleClassloader;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			String methodName = method.getName();

			if (methodName.equals("getClassLoader")) {
				return _freeMarkerBundleClassloader;
			}
			else if (methodName.equals("getResource")) {
				return _getResource((String)args[0]);
			}
			else if (methodName.equals("getResourceAsStream")) {
				return _getResourceAsStream((String)args[0]);
			}
			else if (methodName.equals("getResourcePaths")) {
				return _getResourcePaths((String)args[0]);
			}

			return method.invoke(_servletContext, args);
		}

		private URL _getExtension(String path) {
			Enumeration<URL> enumeration = _bundle.findEntries(
				"META-INF/resources", path.substring(1), false);

			if (enumeration == null) {
				return null;
			}

			List<URL> urls = Collections.list(enumeration);

			return urls.get(urls.size() - 1);
		}

		private URL _getResource(String path) {
			if (path.charAt(0) != '/') {
				path = '/' + path;
			}

			URL url = _getExtension(path);

			if (url != null) {
				return url;
			}

			url = _freeMarkerBundleClassloader.getResource(path);

			if (url != null) {
				return url;
			}

			if (path.startsWith("/WEB-INF/tld/")) {
				String adaptedPath =
					"/META-INF/" + path.substring("/WEB-INF/tld/".length());

				url = _getExtension(adaptedPath);

				if (url == null) {
					url = _bundle.getResource(adaptedPath);
				}
			}

			if (url != null) {
				return url;
			}

			if (!path.startsWith("/META-INF/") &&
				!path.startsWith("/WEB-INF/")) {

				url = _bundle.getResource("/META-INF/resources" + path);
			}

			return url;
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
			Enumeration<URL> entries = _bundle.findEntries(path, null, true);

			if (entries == null) {
				return null;
			}

			Set<String> resourcePaths = new HashSet<>();

			while (entries.hasMoreElements()) {
				URL url = entries.nextElement();

				resourcePaths.add(url.getPath());
			}

			return resourcePaths;
		}

		private final FreeMarkerBundleClassloader _freeMarkerBundleClassloader;
		private final ServletContext _servletContext;

	}

	private class TaglibBundleTrackerCustomizer
		implements BundleTrackerCustomizer<Set<String>> {

		@Override
		public Set<String> addingBundle(
			Bundle bundle, BundleEvent bundleEvent) {

			URL url = bundle.getEntry("/META-INF/taglib-mappings.properties");

			if (url == null) {
				BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

				List<BundleCapability> bundleCapabilities =
					bundleWiring.getCapabilities("osgi.extender");

				for (BundleCapability bundleCapability : bundleCapabilities) {
					Map<String, Object> attributes =
						bundleCapability.getAttributes();

					Object value = attributes.get("osgi.extender");

					if (value.equals("jsp.taglib")) {
						return Collections.emptySet();
					}
				}
			}
			else {
				try (InputStream inputStream = url.openStream()) {
					Properties properties = PropertiesUtil.load(
						inputStream, StringPool.UTF8);

					@SuppressWarnings("unchecked")
					Map<String, String> map = PropertiesUtil.toMap(properties);

					_taglibMappings.putAll(map);

					return map.keySet();
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			return null;
		}

		@Override
		public void modifiedBundle(
			Bundle bundle, BundleEvent bundleEvent, Set<String> trackedKeys) {
		}

		@Override
		public void removedBundle(
			Bundle bundle, BundleEvent bundleEvent, Set<String> trackedKeys) {

			for (String key : trackedKeys) {
				_taglibMappings.remove(key);
			}

			_templateModels.clear();
		}

	}

	private class TaglibFactoryWrapper implements TemplateHashModel {

		public TaglibFactoryWrapper(ServletContext servletContext) {
			_freeMarkerBundleClassloader = getFreeMarkerBundleClassloader();

			_taglibFactory = new TaglibFactory(
				getServletContextWrapper(
					servletContext, _freeMarkerBundleClassloader));

			_taglibFactory.setObjectWrapper(getBeansWrapper());
		}

		@Override
		public TemplateModel get(String uri) throws TemplateModelException {
			TemplateModel templateModel = _templateModels.get(uri);

			if (templateModel == null) {
				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader =
					currentThread.getContextClassLoader();

				try {
					currentThread.setContextClassLoader(
						_freeMarkerBundleClassloader);

					templateModel = _taglibFactory.get(uri);
				}
				finally {
					currentThread.setContextClassLoader(contextClassLoader);
				}

				_templateModels.put(uri, templateModel);
			}

			return templateModel;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		private final FreeMarkerBundleClassloader _freeMarkerBundleClassloader;
		private final TaglibFactory _taglibFactory;

	}

}