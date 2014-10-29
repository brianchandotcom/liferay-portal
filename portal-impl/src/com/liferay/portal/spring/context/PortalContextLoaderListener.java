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

package com.liferay.portal.spring.context;

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.cache.ehcache.ClearEhcacheThreadUtil;
import com.liferay.portal.dao.shard.ShardDataSourceTargetSource;
import com.liferay.portal.deploy.hot.IndexerPostProcessorRegistry;
import com.liferay.portal.deploy.hot.SchedulerEntryRegistry;
import com.liferay.portal.deploy.hot.ServiceWrapperRegistry;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.events.StartupAction;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.deploy.DeployManagerUtil;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.LoggedExceptionInInitializerError;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.servlet.DirectServletRegistryUtil;
import com.liferay.portal.kernel.servlet.SerializableSessionAttributeListener;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;
import com.liferay.portal.kernel.util.CharBufferPool;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.ClearThreadLocalUtil;
import com.liferay.portal.kernel.util.ClearTimerThreadUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletFilter;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.model.Theme;
import com.liferay.portal.module.framework.ModuleFrameworkUtilAdapter;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.lang.SecurityManagerUtil;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.server.capabilities.ServerCapabilitiesUtil;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.servlet.I18nServlet;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.servlet.filters.i18n.I18nFilter;
import com.liferay.portal.setup.SetupWizardUtil;
import com.liferay.portal.spring.bean.BeanReferenceRefreshUtil;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.ExtRegistry;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portlet.PortletBagFactory;
import com.liferay.portlet.PortletConfigFactoryUtil;
import com.liferay.portlet.PortletContextBagPool;
import com.liferay.portlet.PortletFilterFactory;
import com.liferay.portlet.PortletInstanceFactoryUtil;
import com.liferay.portlet.PortletURLListenerFactory;
import com.liferay.portlet.social.util.SocialConfigurationUtil;
import com.liferay.portlet.wiki.util.WikiCacheUtil;

import java.beans.PropertyDescriptor;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

/**
 * @author Michael Young
 * @author Shuyang Zhou
 * @author Raymond Augé
 */
public class PortalContextLoaderListener extends ContextLoaderListener {

	public static String getPortalServletContextName() {
		return _portalServletContextName;
	}

	public static String getPortalServletContextPath() {
		return _portalServletContextPath;
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		PortalContextLoaderLifecycleThreadLocal.setDestroying(true);

		destroyPlugins();

		destroyPortlets();

		destroyCompanies();

		processGlobalShutdownEvents();

		ThreadLocalCacheManager.destroy();

		try {
			ClearThreadLocalUtil.clearThreadLocal();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			ClearTimerThreadUtil.clearTimerThread();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			ClearEhcacheThreadUtil.clearEhcacheReplicationThread();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			DirectServletRegistryUtil.clearServlets();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			HotDeployUtil.reset();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		_indexerPostProcessorRegistry.close();
		_schedulerEntryRegistry.close();
		_serviceWrapperRegistry.close();

		try {
			ModuleFrameworkUtilAdapter.stopRuntime();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			PortalLifecycleUtil.reset();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			SettingsFactoryUtil.clearCache();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			super.contextDestroyed(servletContextEvent);

			try {
				ModuleFrameworkUtilAdapter.stopFramework();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
		finally {
			PortalContextLoaderLifecycleThreadLocal.setDestroying(false);

			SecurityManagerUtil.destroy();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		SystemProperties.reload();

		DBFactoryUtil.reset();
		DeployManagerUtil.reset();
		InstancePool.reset();
		MethodCache.reset();
		PortalBeanLocatorUtil.reset();
		PortletBagPool.reset();

		ReferenceRegistry.releaseReferences();

		InitUtil.init();

		final ServletContext servletContext =
			servletContextEvent.getServletContext();

		_portalServletContextName = servletContext.getServletContextName();

		if (_portalServletContextName == null) {
			_portalServletContextName = StringPool.BLANK;
		}

		if (ServerDetector.isJetty() &&
			_portalServletContextName.equals(StringPool.SLASH)) {

			_portalServletContextName = StringPool.BLANK;
		}

		_portalServletContextPath = servletContext.getContextPath();

		if (ServerDetector.isWebSphere() &&
			_portalServletContextPath.isEmpty()) {

			_portalServletContextName = StringPool.BLANK;
		}

		ClassPathUtil.initializeClassPaths(servletContext);

		CacheRegistryUtil.clear();
		CharBufferPool.cleanUp();
		PortletContextBagPool.clear();
		WebAppPool.clear();

		File tempDir = (File)servletContext.getAttribute(
			JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR);

		PropsValues.LIFERAY_WEB_PORTAL_CONTEXT_TEMPDIR =
			tempDir.getAbsolutePath();

		try {
			ModuleFrameworkUtilAdapter.startFramework();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		PortalContextLoaderLifecycleThreadLocal.setInitializing(true);

		try {
			super.contextInitialized(servletContextEvent);
		}
		finally {
			PortalContextLoaderLifecycleThreadLocal.setInitializing(false);
		}

		ApplicationContext applicationContext =
			ContextLoader.getCurrentWebApplicationContext();

		try {
			BeanReferenceRefreshUtil.refresh(applicationContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (PropsValues.CACHE_CLEAR_ON_CONTEXT_INITIALIZATION) {
			FinderCacheUtil.clearCache();
			FinderCacheUtil.clearLocalCache();
			EntityCacheUtil.clearCache();
			EntityCacheUtil.clearLocalCache();
			PermissionCacheUtil.clearCache();
			TemplateResourceLoaderUtil.clearCache();
			WikiCacheUtil.clearCache(0);

			ServletContextPool.clear();

			CacheUtil.clearCache();
			MultiVMPoolUtil.clear();
			SingleVMPoolUtil.clear();
			WebCachePoolUtil.clear();
		}

		ClassLoader portalClassLoader = ClassLoaderUtil.getPortalClassLoader();

		ClassLoaderPool.register(_portalServletContextName, portalClassLoader);

		ServletContextPool.put(_portalServletContextName, servletContext);

		BeanLocatorImpl beanLocatorImpl = new BeanLocatorImpl(
			portalClassLoader, applicationContext);

		PortalBeanLocatorUtil.setBeanLocator(beanLocatorImpl);

		ClassLoader classLoader = portalClassLoader;

		while (classLoader != null) {
			CachedIntrospectionResults.clearClassLoader(classLoader);

			classLoader = classLoader.getParent();
		}

		AutowireCapableBeanFactory autowireCapableBeanFactory =
			applicationContext.getAutowireCapableBeanFactory();

		clearFilteredPropertyDescriptorsCache(autowireCapableBeanFactory);

		_indexerPostProcessorRegistry = new IndexerPostProcessorRegistry();
		_schedulerEntryRegistry = new SchedulerEntryRegistry();
		_serviceWrapperRegistry = new ServiceWrapperRegistry();

		try {
			PortalLifecycleUtil.register(
				new PortalLifecycle() {

					@Override
					public void portalInit() {
						ModuleFrameworkUtilAdapter.registerContext(
							servletContext);
					}

					@Override
					public void portalDestroy() {
					}

				});

			ModuleFrameworkUtilAdapter.registerContext(applicationContext);

			ModuleFrameworkUtilAdapter.startRuntime();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		processStartupEvents();

		processGlobalStartupEvents();

		initListeners(servletContext);

		initServerDetector(servletContext);

		PluginPackage pluginPackage = initPluginPackage(servletContext);

		List<Portlet> portlets = initPortlets(pluginPackage, servletContext);

		initLayoutTemplates(pluginPackage, servletContext);

		initSocial(servletContext);

		initThemes(pluginPackage, servletContext);

		initWebSettings(servletContext);

		initExt(servletContext);

		initResourceActions(portlets);

		initCompanies(servletContext);

		initPlugins();

		markStartupAsFinished(servletContext);
	}

	protected void checkWebSettings(String xml) throws DocumentException {
		Document doc = SAXReaderUtil.read(xml);

		Element root = doc.getRootElement();

		int timeout = PropsValues.SESSION_TIMEOUT;

		Element sessionConfig = root.element("session-config");

		if (sessionConfig != null) {
			String sessionTimeout = sessionConfig.elementText(
				"session-timeout");

			timeout = GetterUtil.getInteger(sessionTimeout, timeout);
		}

		PropsUtil.set(PropsKeys.SESSION_TIMEOUT, String.valueOf(timeout));

		PropsValues.SESSION_TIMEOUT = timeout;

		I18nServlet.setLanguageIds(root);
		I18nFilter.setLanguageIds(I18nServlet.getLanguageIds());
	}

	protected void clearFilteredPropertyDescriptorsCache(
		AutowireCapableBeanFactory autowireCapableBeanFactory) {

		try {
			Map<Class<?>, PropertyDescriptor[]>
				filteredPropertyDescriptorsCache =
					(Map<Class<?>, PropertyDescriptor[]>)
						_FILTERED_PROPERTY_DESCRIPTORS_CACHE_FIELD.get(
							autowireCapableBeanFactory);

			filteredPropertyDescriptorsCache.clear();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void destroyCompanies() {
		if (_log.isDebugEnabled()) {
			_log.debug("Destroy companies");
		}

		long[] companyIds = PortalInstances.getCompanyIds();

		for (long companyId : companyIds) {
			destroyCompany(companyId);
		}
	}

	protected void destroyCompany(long companyId) {
		if (_log.isDebugEnabled()) {
			_log.debug("Process shutdown events");
		}

		try {
			EventsProcessorUtil.process(
				PropsKeys.APPLICATION_SHUTDOWN_EVENTS,
				PropsValues.APPLICATION_SHUTDOWN_EVENTS,
				new String[] {String.valueOf(companyId)});
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void destroyPlugins() {
		if (_log.isDebugEnabled()) {
			_log.debug("Destroy plugins");
		}

		PortalLifecycleUtil.flushDestroys();
	}

	protected void destroyPortlets() {
		if (_log.isDebugEnabled()) {
			_log.debug("Destroy portlets");
		}

		for (Portlet portlet : PortletLocalServiceUtil.getPortlets()) {
			PortletInstanceFactoryUtil.destroy(portlet);
		}
	}

	protected void initCompanies(ServletContext servletContext) {
		if (_log.isDebugEnabled()) {
			_log.debug("Initialize companies");
		}

		try {
			String[] webIds = PortalInstances.getWebIds();

			for (String webId : webIds) {
				PortalInstances.initCompany(servletContext, webId);
			}
		}
		finally {
			CompanyThreadLocal.setCompanyId(
				PortalInstances.getDefaultCompanyId());

			ShardDataSourceTargetSource shardDataSourceTargetSource =
				(ShardDataSourceTargetSource)
					InfrastructureUtil.getShardDataSourceTargetSource();

			if (shardDataSourceTargetSource != null) {
				shardDataSourceTargetSource.resetDataSource();
			}
		}
	}

	protected void initExt(ServletContext servletContext) {
		if (_log.isDebugEnabled()) {
			_log.debug("Initialize extension environment");
		}

		try {
			ExtRegistry.registerPortal(servletContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RuntimeException(e);
		}
	}

	protected void initLayoutTemplates(
		PluginPackage pluginPackage, ServletContext servletContext) {

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize layout templates");
		}

		String[] xmls = new String[0];

		try {
			xmls = new String[] {
				HttpUtil.URLtoString(
					servletContext.getResource(
						"/WEB-INF/liferay-layout-templates.xml")),
				HttpUtil.URLtoString(
					servletContext.getResource(
						"/WEB-INF/liferay-layout-templates-ext.xml"))
			};
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);

			throw new RuntimeException(ioe);
		}

		List<LayoutTemplate> layoutTemplates =
			LayoutTemplateLocalServiceUtil.init(
				servletContext, xmls, pluginPackage);

		servletContext.setAttribute(
			com.liferay.portal.util.WebKeys.PLUGIN_LAYOUT_TEMPLATES,
			layoutTemplates);
	}

	protected void initListeners(ServletContext servletContext) {
		if (PropsValues.SESSION_VERIFY_SERIALIZABLE_ATTRIBUTE) {
			servletContext.addListener(
				SerializableSessionAttributeListener.class);
		}
	}

	protected PluginPackage initPluginPackage(ServletContext servletContext) {
		if (_log.isDebugEnabled()) {
			_log.debug("Initialize plugin package");
		}

		try {
			return PluginPackageUtil.readPluginPackageServletContext(
				servletContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RuntimeException(e);
		}
	}

	/**
	 * @see com.liferay.portal.setup.SetupWizardUtil#_initPlugins
	 */
	protected void initPlugins() {

		// See LEP-2885. Don't flush hot deploy events until after the portal
		// has initialized.

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize plugins");
		}

		if (SetupWizardUtil.isSetupFinished()) {
			HotDeployUtil.setCapturePrematureEvents(false);

			PortalLifecycleUtil.flushInits();
		}
	}

	protected void initPortletApp(
			Portlet portlet, ServletContext servletContext)
		throws PortletException {

		PortletApp portletApp = portlet.getPortletApp();

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);

		PortletContext portletContext = portletConfig.getPortletContext();

		Set<PortletFilter> portletFilters = portletApp.getPortletFilters();

		for (PortletFilter portletFilter : portletFilters) {
			PortletFilterFactory.create(portletFilter, portletContext);
		}

		Set<PortletURLListener> portletURLListeners =
			portletApp.getPortletURLListeners();

		for (PortletURLListener portletURLListener : portletURLListeners) {
			PortletURLListenerFactory.create(portletURLListener);
		}
	}

	protected List<Portlet> initPortlets(
		PluginPackage pluginPackage, ServletContext servletContext) {

		List<Portlet> portlets = null;

		try {
			String[] xmls = new String[] {
				HttpUtil.URLtoString(
					servletContext.getResource(
						"/WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_CUSTOM)),
				HttpUtil.URLtoString(
					servletContext.getResource("/WEB-INF/portlet-ext.xml")),
				HttpUtil.URLtoString(
					servletContext.getResource("/WEB-INF/liferay-portlet.xml")),
				HttpUtil.URLtoString(
					servletContext.getResource(
						"/WEB-INF/liferay-portlet-ext.xml")),
				HttpUtil.URLtoString(
					servletContext.getResource("/WEB-INF/web.xml"))
			};

			PortletLocalServiceUtil.initEAR(
				servletContext, xmls, pluginPackage);

			PortletBagFactory portletBagFactory = new PortletBagFactory();

			portletBagFactory.setClassLoader(
				ClassLoaderUtil.getPortalClassLoader());
			portletBagFactory.setServletContext(servletContext);
			portletBagFactory.setWARFile(false);

			portlets = PortletLocalServiceUtil.getPortlets();

			for (int i = 0; i < portlets.size(); i++) {
				Portlet portlet = portlets.get(i);

				portletBagFactory.create(portlet);

				if (i == 0) {
					initPortletApp(portlet, servletContext);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		servletContext.setAttribute(
			com.liferay.portal.util.WebKeys.PLUGIN_PORTLETS, portlets);

		return portlets;
	}

	protected void initResourceActions(List<Portlet> portlets) {
		if (_log.isDebugEnabled()) {
			_log.debug("Initialize resource actions");
		}

		for (Portlet portlet : portlets) {
			List<String> portletActions =
				ResourceActionsUtil.getPortletResourceActions(portlet);

			ResourceActionLocalServiceUtil.checkResourceActions(
				portlet.getPortletId(), portletActions);

			List<String> modelNames =
				ResourceActionsUtil.getPortletModelResources(
					portlet.getPortletId());

			for (String modelName : modelNames) {
				List<String> modelActions =
					ResourceActionsUtil.getModelResourceActions(modelName);

				ResourceActionLocalServiceUtil.checkResourceActions(
					modelName, modelActions);
			}
		}
	}

	protected void initServerDetector(ServletContext servletContext) {
		if (_log.isDebugEnabled()) {
			_log.debug("Initialize server detector");
		}

		ServerCapabilitiesUtil.determineServerCapabilities(servletContext);
	}

	protected void initSocial(ServletContext servletContext) {
		if (_log.isDebugEnabled()) {
			_log.debug("Initialize social");
		}

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		String[] xmls = new String[0];

		try {
			xmls = new String[] {
				HttpUtil.URLtoString(
					servletContext.getResource("/WEB-INF/liferay-social.xml")),
				HttpUtil.URLtoString(
					servletContext.getResource(
						"/WEB-INF/liferay-social-ext.xml"))
			};

			SocialConfigurationUtil.read(classLoader, xmls);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RuntimeException(e);
		}
	}

	protected void initThemes(
		PluginPackage pluginPackage, ServletContext servletContext) {

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize themes");
		}

		String[] xmls = new String[0];
		try {
			xmls = new String[] {
				HttpUtil.URLtoString(
					servletContext.getResource(
						"/WEB-INF/liferay-look-and-feel.xml")),
				HttpUtil.URLtoString(
					servletContext.getResource(
						"/WEB-INF/liferay-look-and-feel-ext.xml"))
			};
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);

			throw new RuntimeException(ioe);
		}

		List<Theme> themes = ThemeLocalServiceUtil.init(
			servletContext, null, true, xmls, pluginPackage);

		servletContext.setAttribute(
			com.liferay.portal.util.WebKeys.PLUGIN_THEMES, themes);
	}

	protected void initWebSettings(ServletContext servletContext) {
		if (_log.isDebugEnabled()) {
			_log.debug("Initialize web settings");
		}

		String xml = null;
		try {
			xml = HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/web.xml"));

			checkWebSettings(xml);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RuntimeException(e);
		}
	}

	protected void markStartupAsFinished(ServletContext servletContext) {
		servletContext.setAttribute(
			com.liferay.portal.util.WebKeys.STARTUP_FINISHED, true);

		StartupHelperUtil.setStartupFinished(true);
	}

	protected void processGlobalShutdownEvents() {
		if (_log.isDebugEnabled()) {
			_log.debug("Process global shutdown events");
		}

		try {
			EventsProcessorUtil.process(
				PropsKeys.GLOBAL_SHUTDOWN_EVENTS,
				PropsValues.GLOBAL_SHUTDOWN_EVENTS);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RuntimeException(e);
		}
	}

	protected void processGlobalStartupEvents() {
		if (_log.isDebugEnabled()) {
			_log.debug("Process global startup events");
		}

		try {
			EventsProcessorUtil.process(
				PropsKeys.GLOBAL_STARTUP_EVENTS,
				PropsValues.GLOBAL_STARTUP_EVENTS);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RuntimeException(e);
		}
	}

	protected void processStartupEvents() {
		if (_log.isDebugEnabled()) {
			_log.debug("Process startup events");
		}

		StartupAction startupAction = new StartupAction();

		try {
			startupAction.run(null);
		}
		catch (ActionException ae) {
			_log.error(ae, ae);

			System.out.println(
				"Stopping the server due to unexpected startup errors");

			System.exit(0);
		}
	}

	private static final Field _FILTERED_PROPERTY_DESCRIPTORS_CACHE_FIELD;

	private static final Log _log = LogFactoryUtil.getLog(
		PortalContextLoaderListener.class);

	private static String _portalServletContextName = StringPool.BLANK;
	private static String _portalServletContextPath = StringPool.SLASH;

	static {
		try {
			_FILTERED_PROPERTY_DESCRIPTORS_CACHE_FIELD =
				ReflectionUtil.getDeclaredField(
					AbstractAutowireCapableBeanFactory.class,
					"filteredPropertyDescriptorsCache");
		}
		catch (Exception e) {
			throw new LoggedExceptionInInitializerError(e);
		}
	}

	private IndexerPostProcessorRegistry _indexerPostProcessorRegistry;
	private SchedulerEntryRegistry _schedulerEntryRegistry;
	private ServiceWrapperRegistry _serviceWrapperRegistry;

}