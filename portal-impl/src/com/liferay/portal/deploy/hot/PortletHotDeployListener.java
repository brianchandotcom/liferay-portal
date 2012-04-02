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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.apache.bridges.struts.LiferayServletContextProvider;
import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.atom.AtomCollectionAdapterRegistryUtil;
import com.liferay.portal.kernel.concurrent.LockRegistry;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.javadoc.JavadocManagerUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.servlet.DirectServletRegistry;
import com.liferay.portal.kernel.servlet.FileTimestampUtil;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.ServletContextProvider;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletCategory;
import com.liferay.portal.model.PortletFilter;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.poller.PollerProcessorUtil;
import com.liferay.portal.pop.POPServerUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.spring.context.PortletContextLoader;
import com.liferay.portal.spring.context.PortletContextLoaderListener;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.xmlrpc.XmlRpcServlet;
import com.liferay.portlet.CustomUserAttributes;
import com.liferay.portlet.InvokerPortlet;
import com.liferay.portlet.PortletBagFactory;
import com.liferay.portlet.PortletContextBag;
import com.liferay.portlet.PortletContextBagPool;
import com.liferay.portlet.PortletFilterFactory;
import com.liferay.portlet.PortletInstanceFactoryUtil;
import com.liferay.portlet.PortletResourceBundles;
import com.liferay.portlet.PortletURLListenerFactory;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil;
import com.liferay.portlet.social.service.SocialRequestInterpreterLocalServiceUtil;
import com.liferay.util.log4j.Log4JUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import javax.portlet.PortletURLGenerationListener;

import javax.servlet.ServletContext;

import org.apache.portals.bridges.struts.StrutsPortlet;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Ivica Cardic
 * @author Raymond Augé
 */
public class PortletHotDeployListener extends BaseHotDeployListener {

	public void invokeDeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeDeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Error registering portlets for ", t);
		}
	}

	public void invokeUndeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeUndeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Error unregistering portlets for ", t);
		}
	}

	protected void destroyPortlet(Portlet portlet, Set<String> portletIds)
		throws Exception {

		PortletApp portletApp = portlet.getPortletApp();

		Set<PortletFilter> portletFilters = portletApp.getPortletFilters();

		for (PortletFilter portletFilter : portletFilters) {
			PortletFilterFactory.destroy(portletFilter);
		}

		Set<PortletURLListener> portletURLListeners =
			portletApp.getPortletURLListeners();

		for (PortletURLListener portletURLListener : portletURLListeners) {
			PortletURLListenerFactory.destroy(portletURLListener);
		}

		List<Indexer> indexers = portlet.getIndexerInstances();

		for (Indexer indexer : indexers) {
			IndexerRegistryUtil.unregister(indexer);
		}

		if (PropsValues.SCHEDULER_ENABLED) {
			List<SchedulerEntry> schedulerEntries =
				portlet.getSchedulerEntries();

			if ((schedulerEntries != null) && !schedulerEntries.isEmpty()) {
				for (SchedulerEntry schedulerEntry : schedulerEntries) {
					SchedulerEngineUtil.unschedule(
						schedulerEntry, StorageType.MEMORY_CLUSTERED);
				}
			}
		}

		PollerProcessorUtil.deletePollerProcessor(portlet.getPortletId());

		POPServerUtil.deleteListener(portlet.getPopMessageListenerInstance());

		SocialActivityInterpreterLocalServiceUtil.deleteActivityInterpreter(
			portlet.getSocialActivityInterpreterInstance());

		SocialRequestInterpreterLocalServiceUtil.deleteRequestInterpreter(
			portlet.getSocialRequestInterpreterInstance());

		WebDAVUtil.deleteStorage(portlet.getWebDAVStorageInstance());

		XmlRpcServlet.unregisterMethod(portlet.getXmlRpcMethodInstance());

		List<AssetRendererFactory> assetRendererFactories =
			portlet.getAssetRendererFactoryInstances();

		if (assetRendererFactories != null) {
			AssetRendererFactoryRegistryUtil.unregister(assetRendererFactories);
		}

		List<AtomCollectionAdapter<?>> atomCollectionAdapters =
			portlet.getAtomCollectionAdapterInstances();

		if (atomCollectionAdapters != null) {
			AtomCollectionAdapterRegistryUtil.unregister(
				atomCollectionAdapters);
		}

		List<WorkflowHandler> workflowHandlers =
			portlet.getWorkflowHandlerInstances();

		if (workflowHandlers != null) {
			WorkflowHandlerRegistryUtil.unregister(workflowHandlers);
		}

		PortletInstanceFactoryUtil.destroy(portlet);

		portletIds.add(portlet.getPortletId());
	}

	protected void doInvokeDeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		// Servlet context

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		// Spring initialization lock

		String configLocation = servletContext.getInitParameter(
			PortletContextLoader.PORTAL_CONFIG_LOCATION_PARAM);

		Properties serviceBuilderProperties =
			(Properties)servletContext.getAttribute(
				PluginPackageHotDeployListener.SERVICE_BUILDER_PROPERTIES);

		if (Validator.isNotNull(configLocation) ||
			(serviceBuilderProperties != null)) {

			String lockKey = PortletContextLoaderListener.getLockKey(
				servletContext);

			Lock lock = LockRegistry.allocateLock(lockKey, lockKey);

			lock.lock();
		}

		// Company ids

		long[] companyIds = PortalInstances.getCompanyIds();

		// Initialize portlets

		String[] xmls = new String[] {
			HttpUtil.URLtoString(
				servletContext.getResource(
					"/WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_STANDARD)),
			HttpUtil.URLtoString(
				servletContext.getResource(
					"/WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_CUSTOM)),
			HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/liferay-portlet.xml")),
			HttpUtil.URLtoString(servletContext.getResource("/WEB-INF/web.xml"))
		};

		if ((xmls[0] == null) && (xmls[1] == null)) {
			return;
		}

		logRegistration(servletContextName);

		List<Portlet> portlets = PortletLocalServiceUtil.initWAR(
			servletContextName, servletContext, xmls,
			hotDeployEvent.getPluginPackage());

		// Class loader

		ClassLoader portletClassLoader = hotDeployEvent.getContextClassLoader();

		servletContext.setAttribute(
			PortletServlet.PORTLET_CLASS_LOADER, portletClassLoader);

		// Logger

		initLogger(portletClassLoader);

		// Portlet context wrapper

		_portletAppInitialized = false;
		_strutsBridges = false;

		PortletBagFactory portletBagFactory = new PortletBagFactory();

		portletBagFactory.setClassLoader(portletClassLoader);
		portletBagFactory.setServletContext(servletContext);
		portletBagFactory.setWARFile(true);

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			PortletBag portletBag = initPortlet(portlet, portletBagFactory);

			if (portletBag == null) {
				itr.remove();
			}
			else {
				if (!_portletAppInitialized) {
					initPortletApp(
						portlet, servletContextName, servletContext,
						portletClassLoader);

					_portletAppInitialized = true;
				}
			}
		}

		// Struts bridges

		if (!_strutsBridges) {
			_strutsBridges = GetterUtil.getBoolean(
				servletContext.getInitParameter(
					"struts-bridges-context-provider"));
		}

		if (_strutsBridges) {
			servletContext.setAttribute(
				ServletContextProvider.STRUTS_BRIDGES_CONTEXT_PROVIDER,
				new LiferayServletContextProvider());
		}

		// Portlet display

		String xml = HttpUtil.URLtoString(
			servletContext.getResource("/WEB-INF/liferay-display.xml"));

		PortletCategory newPortletCategory =
			PortletLocalServiceUtil.getWARDisplay(servletContextName, xml);

		for (long companyId : companyIds) {
			PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
				companyId, WebKeys.PORTLET_CATEGORY);

			if (portletCategory != null) {
				portletCategory.merge(newPortletCategory);
			}
			else {
				_log.error(
					"Unable to register portlet for company " + companyId +
						" because it does not exist");
			}
		}

		// Portlet properties

		processPortletProperties(servletContextName, portletClassLoader);

		// Resource actions, resource codes, and check

		for (Portlet portlet : portlets) {
			List<String> modelNames =
				ResourceActionsUtil.getPortletModelResources(
					portlet.getPortletId());

			for (long companyId : companyIds) {
				ResourceCodeLocalServiceUtil.checkResourceCodes(
					companyId, portlet.getPortletId());

				for (String modelName : modelNames) {
					ResourceCodeLocalServiceUtil.checkResourceCodes(
						companyId, modelName);
				}
			}

			List<String> portletActions =
				ResourceActionsUtil.getPortletResourceActions(
					portlet.getPortletId());

			ResourceActionLocalServiceUtil.checkResourceActions(
				portlet.getPortletId(), portletActions);

			for (String modelName : modelNames) {
				List<String> modelActions =
					ResourceActionsUtil.getModelResourceActions(modelName);

				ResourceActionLocalServiceUtil.checkResourceActions(
					modelName, modelActions);
			}

			for (long companyId : companyIds) {
				Portlet curPortlet = PortletLocalServiceUtil.getPortletById(
					companyId, portlet.getPortletId());

				PortletLocalServiceUtil.checkPortlet(curPortlet);
			}
		}

		// Ready

		for (Portlet portlet : portlets) {
			boolean ready = GetterUtil.getBoolean(
				servletContext.getInitParameter(
					"portlets-ready-by-default"), true);

			portlet.setReady(ready);
		}

		// ClpMessageListener

		registerClpMessageListeners(servletContext, portletClassLoader);

		// Javadoc

		JavadocManagerUtil.load(servletContextName, portletClassLoader);

		// Clear cache

		DirectServletRegistry.clearServlets();
		FileTimestampUtil.reset();

		// Variables

		_vars.put(
			servletContextName,
			new ObjectValuePair<long[], List<Portlet>>(companyIds, portlets));

		if (_log.isInfoEnabled()) {
			if (portlets.size() == 1) {
				_log.info(
					"1 portlet for " + servletContextName +
						" is available for use");
			}
			else {
				_log.info(
					portlets.size() + " portlets for " + servletContextName +
						" are available for use");
			}
		}
	}

	protected void doInvokeUndeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking undeploy for " + servletContextName);
		}

		ObjectValuePair<long[], List<Portlet>> ovp = _vars.remove(
			servletContextName);

		if (ovp == null) {
			return;
		}

		long[] companyIds = ovp.getKey();
		List<Portlet> portlets = ovp.getValue();

		Set<String> portletIds = new HashSet<String>();

		if (portlets != null) {
			if (_log.isInfoEnabled()) {
				_log.info("Unregistering portlets for " + servletContextName);
			}

			for (Portlet portlet : portlets) {
				destroyPortlet(portlet, portletIds);
			}
		}

		ServletContextPool.remove(servletContextName);

		if (portletIds.size() > 0) {
			for (long companyId : companyIds) {
				PortletCategory portletCategory =
					(PortletCategory)WebAppPool.get(
						companyId, WebKeys.PORTLET_CATEGORY);

				portletCategory.separate(portletIds);
			}
		}

		PortletContextBagPool.remove(servletContextName);
		PortletResourceBundles.remove(servletContextName);

		unregisterClpMessageListeners(servletContext);

		JavadocManagerUtil.unload(servletContextName);

		if (_log.isInfoEnabled()) {
			if (portlets.size() == 1) {
				_log.info(
					"1 portlet for " + servletContextName +
						" was unregistered");
			}
			else {
				_log.info(
					portlets.size() + " portlets for " + servletContextName +
						" was unregistered");
			}
		}
	}

	protected void initLogger(ClassLoader portletClassLoader) {
		Log4JUtil.configureLog4J(
			portletClassLoader.getResource("META-INF/portal-log4j.xml"));
	}

	protected PortletBag initPortlet(
			Portlet portlet, PortletBagFactory portletBagFactory)
		throws Exception {

		PortletBag portletBag = portletBagFactory.create(portlet);

		if (portletBag == null) {
			return null;
		}

		javax.portlet.Portlet portletInstance = portletBag.getPortletInstance();

		if (ClassUtil.isSubclass(
				portletInstance.getClass(), StrutsPortlet.class.getName())) {

			_strutsBridges = true;
		}

		return portletBag;
	}

	protected void initPortletApp(
			Portlet portlet, String servletContextName,
			ServletContext servletContext, ClassLoader portletClassLoader)
		throws Exception {

		PortletContextBag portletContextBag = new PortletContextBag(
			servletContextName);

		PortletContextBagPool.put(servletContextName, portletContextBag);

		PortletApp portletApp = portlet.getPortletApp();

		servletContext.setAttribute(PortletServlet.PORTLET_APP, portletApp);

		Map<String, String> customUserAttributes =
			portletApp.getCustomUserAttributes();

		for (Map.Entry<String, String> entry :
				customUserAttributes.entrySet()) {

			String attrCustomClass = entry.getValue();

			CustomUserAttributes customUserAttributesInstance =
				(CustomUserAttributes)portletClassLoader.loadClass(
					attrCustomClass).newInstance();

			portletContextBag.getCustomUserAttributes().put(
				attrCustomClass, customUserAttributesInstance);
		}

		Set<PortletFilter> portletFilters = portletApp.getPortletFilters();

		for (PortletFilter portletFilter : portletFilters) {
			javax.portlet.filter.PortletFilter portletFilterInstance =
				(javax.portlet.filter.PortletFilter)newInstance(
					portletClassLoader,
					new Class<?>[] {
						javax.portlet.filter.ActionFilter.class,
						javax.portlet.filter.EventFilter.class,
						javax.portlet.filter.PortletFilter.class,
						javax.portlet.filter.RenderFilter.class,
						javax.portlet.filter.ResourceFilter.class
					},
					portletFilter.getFilterClass());

			portletContextBag.getPortletFilters().put(
				portletFilter.getFilterName(), portletFilterInstance);
		}

		InvokerPortlet invokerPortlet = PortletInstanceFactoryUtil.create(
			portlet, servletContext);

		invokerPortlet.setPortletFilters();

		Set<PortletURLListener> portletURLListeners =
			portletApp.getPortletURLListeners();

		for (PortletURLListener portletURLListener : portletURLListeners) {
			PortletURLGenerationListener portletURLListenerInstance =
				(PortletURLGenerationListener)newInstance(
					portletClassLoader, PortletURLGenerationListener.class,
					portletURLListener.getListenerClass());

			portletContextBag.getPortletURLListeners().put(
				portletURLListener.getListenerClass(),
				portletURLListenerInstance);

			PortletURLListenerFactory.create(portletURLListener);
		}
	}

	protected void logRegistration(String servletContextName) {
		if (_log.isInfoEnabled()) {
			_log.info("Registering portlets for " + servletContextName);
		}
	}

	protected void processPortletProperties(
			String servletContextName, ClassLoader portletClassLoader)
		throws Exception {

		Configuration portletPropertiesConfiguration = null;

		try {
			portletPropertiesConfiguration =
				ConfigurationFactoryUtil.getConfiguration(
					portletClassLoader, "portlet");
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read portlet.properties");
			}

			return;
		}

		Properties portletProperties =
			portletPropertiesConfiguration.getProperties();

		if (portletProperties.size() == 0) {
			return;
		}

		String languageBundleName = portletProperties.getProperty(
			"language.bundle");

		if (Validator.isNotNull(languageBundleName)) {
			Locale[] locales = LanguageUtil.getAvailableLocales();

			for (Locale locale : locales) {
				ResourceBundle resourceBundle = ResourceBundle.getBundle(
					languageBundleName, locale, portletClassLoader);

				PortletResourceBundles.put(
					servletContextName, LocaleUtil.toLanguageId(locale),
					resourceBundle);
			}
		}

		String[] resourceActionConfigs = StringUtil.split(
			portletProperties.getProperty(PropsKeys.RESOURCE_ACTIONS_CONFIGS));

		for (String resourceActionConfig : resourceActionConfigs) {
			ResourceActionsUtil.read(
				servletContextName, portletClassLoader, resourceActionConfig);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletHotDeployListener.class);

	private static Map<String, ObjectValuePair<long[], List<Portlet>>> _vars =
		new HashMap<String, ObjectValuePair<long[], List<Portlet>>>();

	private boolean _portletAppInitialized;
	private boolean _strutsBridges;

}