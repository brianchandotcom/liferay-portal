/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.dao.shard.ShardPollerProcessorWrapper;
import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.atom.AtomCollectionAdapterRegistryUtil;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.Route;
import com.liferay.portal.kernel.portlet.Router;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.language.LiferayResourceBundle;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.poller.PollerProcessorUtil;
import com.liferay.portal.pop.POPServerUtil;
import com.liferay.portal.security.permission.PermissionPropagator;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.xmlrpc.XmlRpcServlet;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.dynamicdatamapping.util.DDMDisplay;
import com.liferay.portlet.dynamicdatamapping.util.DDMDisplayRegistryUtil;
import com.liferay.portlet.expando.model.CustomAttributesDisplay;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialRequestInterpreter;
import com.liferay.portlet.social.model.impl.SocialActivityInterpreterImpl;
import com.liferay.portlet.social.model.impl.SocialRequestInterpreterImpl;
import com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil;
import com.liferay.portlet.social.service.SocialRequestInterpreterLocalServiceUtil;
import com.liferay.util.portlet.PortletProps;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PreferencesValidator;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Ivica Cardic
 * @author Raymond Augé
 */
public class PortletBagFactory {

	public PortletBag create(Portlet portlet) throws Exception {
		PortletApp portletApp = portlet.getPortletApp();

		if (!portletApp.isWARFile() && _warFile) {
			String contextPath = PortalUtil.getPathContext();

			_servletContext = ServletContextPool.get(contextPath);

			_classLoader = ClassLoaderUtil.getPortalClassLoader();
		}

		Class<?> portletClass = null;

		try {
			portletClass = _classLoader.loadClass(portlet.getPortletClass());
		}
		catch (Throwable t) {
			_log.error(t, t);

			PortletLocalServiceUtil.destroyPortlet(portlet);

			return null;
		}

		javax.portlet.Portlet portletInstance =
			(javax.portlet.Portlet)portletClass.newInstance();

		ConfigurationAction configurationActionInstance =
			newConfigurationAction(portlet);

		List<Indexer> indexerInstances = newIndexers(portlet);

		OpenSearch openSearchInstance = newOpenSearch(portlet);

		FriendlyURLMapper friendlyURLMapperInstance = newFriendlyURLMapper(
			portlet);

		URLEncoder urlEncoderInstance = newURLEncoder(portlet);

		PortletDataHandler portletDataHandlerInstance = newPortletDataHandler(
			portlet);

		List<StagedModelDataHandler<?>> stagedModelDataHandlerInstances =
			newStagedModelDataHandler(portlet);

		TemplateHandler templateHandlerInstance = newTemplateHandler(portlet);

		if (templateHandlerInstance != null) {
			TemplateHandlerRegistryUtil.register(templateHandlerInstance);
		}

		PortletLayoutListener portletLayoutListenerInstance =
			newPortletLayoutListener(portlet);

		PollerProcessor pollerProcessorInstance = newPollerProcessor(portlet);

		MessageListener popMessageListenerInstance = newPOPMessageListener(
			portlet);

		List<SocialActivityInterpreter> socialActivityInterpreterInstances =
			newSocialActivityInterpreterInstances(portlet);

		SocialRequestInterpreter socialRequestInterpreterInstance = null;

		if (Validator.isNotNull(portlet.getSocialRequestInterpreterClass())) {
			socialRequestInterpreterInstance =
				(SocialRequestInterpreter)newInstance(
					SocialRequestInterpreter.class,
					portlet.getSocialRequestInterpreterClass());

			socialRequestInterpreterInstance = new SocialRequestInterpreterImpl(
				portlet.getPortletId(), socialRequestInterpreterInstance);

			SocialRequestInterpreterLocalServiceUtil.addRequestInterpreter(
				socialRequestInterpreterInstance);
		}

		WebDAVStorage webDAVStorageInstance = null;

		if (Validator.isNotNull(portlet.getWebDAVStorageClass())) {
			webDAVStorageInstance = (WebDAVStorage)newInstance(
				WebDAVStorage.class, portlet.getWebDAVStorageClass());

			webDAVStorageInstance.setToken(portlet.getWebDAVStorageToken());

			WebDAVUtil.addStorage(webDAVStorageInstance);
		}

		Method xmlRpcMethodInstance = null;

		if (Validator.isNotNull(portlet.getXmlRpcMethodClass())) {
			xmlRpcMethodInstance = (Method)newInstance(
				Method.class, portlet.getXmlRpcMethodClass());

			XmlRpcServlet.registerMethod(xmlRpcMethodInstance);
		}

		ControlPanelEntry controlPanelEntryInstance = null;

		if (Validator.isNotNull(portlet.getControlPanelEntryClass())) {
			controlPanelEntryInstance = (ControlPanelEntry)newInstance(
				ControlPanelEntry.class, portlet.getControlPanelEntryClass());
		}

		List<AssetRendererFactory> assetRendererFactoryInstances =
			newAssetRendererFactoryInstances(portlet);

		List<AtomCollectionAdapter<?>> atomCollectionAdapterInstances =
			newAtomCollectionAdapterInstances(portlet);

		List<CustomAttributesDisplay> customAttributesDisplayInstances =
			new ArrayList<CustomAttributesDisplay>();

		for (String customAttributesDisplayClass :
				portlet.getCustomAttributesDisplayClasses()) {

			CustomAttributesDisplay customAttributesDisplayInstance =
				(CustomAttributesDisplay)newInstance(
					CustomAttributesDisplay.class,
					customAttributesDisplayClass);

			customAttributesDisplayInstance.setClassNameId(
				PortalUtil.getClassNameId(
					customAttributesDisplayInstance.getClassName()));
			customAttributesDisplayInstance.setPortletId(
				portlet.getPortletId());

			customAttributesDisplayInstances.add(
				customAttributesDisplayInstance);
		}

		DDMDisplay ddmDisplayInstance = newDDMDisplay(portlet);

		if (ddmDisplayInstance != null) {
			DDMDisplayRegistryUtil.register(ddmDisplayInstance);
		}

		PermissionPropagator permissionPropagatorInstance =
			newPermissionPropagator(portlet);

		List<TrashHandler> trashHandlerInstances =
			new ArrayList<TrashHandler>();

		for (String trashHandlerClass : portlet.getTrashHandlerClasses()) {
			TrashHandler trashHandlerInstance = (TrashHandler)newInstance(
				TrashHandler.class, trashHandlerClass);

			trashHandlerInstances.add(trashHandlerInstance);

			TrashHandlerRegistryUtil.register(trashHandlerInstance);
		}

		List<WorkflowHandler> workflowHandlerInstances =
			new ArrayList<WorkflowHandler>();

		for (String workflowHandlerClass :
				portlet.getWorkflowHandlerClasses()) {

			WorkflowHandler workflowHandlerInstance =
				(WorkflowHandler)newInstance(
					WorkflowHandler.class, workflowHandlerClass);

			workflowHandlerInstances.add(workflowHandlerInstance);

			WorkflowHandlerRegistryUtil.register(workflowHandlerInstance);
		}

		PreferencesValidator preferencesValidatorInstance = null;

		if (Validator.isNotNull(portlet.getPreferencesValidator())) {
			preferencesValidatorInstance = (PreferencesValidator)newInstance(
				PreferencesValidator.class, portlet.getPreferencesValidator());

			try {
				if (PropsValues.PREFERENCE_VALIDATE_ON_STARTUP) {
					preferencesValidatorInstance.validate(
						PortletPreferencesFactoryUtil.fromDefaultXML(
							portlet.getDefaultPreferences()));
				}
			}
			catch (Exception e) {
				_log.warn(
					"Portlet with the name " + portlet.getPortletId() +
						" does not have valid default preferences");
			}
		}

		Map<String, ResourceBundle> resourceBundles = null;

		String resourceBundle = portlet.getResourceBundle();

		if (Validator.isNotNull(resourceBundle) &&
			!resourceBundle.equals(StrutsResourceBundle.class.getName())) {

			resourceBundles = new HashMap<String, ResourceBundle>();

			initResourceBundle(resourceBundles, portlet, null);
			initResourceBundle(
				resourceBundles, portlet, LocaleUtil.getDefault());

			Set<String> supportedLocales = portlet.getSupportedLocales();

			if (supportedLocales.isEmpty()) {
				supportedLocales = SetUtil.fromArray(PropsValues.LOCALES);
			}

			for (String supportedLocale : supportedLocales) {
				Locale locale = LocaleUtil.fromLanguageId(supportedLocale);

				initResourceBundle(resourceBundles, portlet, locale);
			}
		}

		PortletBag portletBag = new PortletBagImpl(
			portlet.getPortletId(), _servletContext, portletInstance,
			configurationActionInstance, indexerInstances, openSearchInstance,
			friendlyURLMapperInstance, urlEncoderInstance,
			portletDataHandlerInstance, stagedModelDataHandlerInstances,
			templateHandlerInstance, portletLayoutListenerInstance,
			pollerProcessorInstance, popMessageListenerInstance,
			socialActivityInterpreterInstances,
			socialRequestInterpreterInstance, webDAVStorageInstance,
			xmlRpcMethodInstance, controlPanelEntryInstance,
			assetRendererFactoryInstances, atomCollectionAdapterInstances,
			customAttributesDisplayInstances, permissionPropagatorInstance,
			trashHandlerInstances, workflowHandlerInstances,
			preferencesValidatorInstance, resourceBundles);

		PortletBagPool.put(portlet.getRootPortletId(), portletBag);

		initSchedulers(portlet);

		try {
			PortletInstanceFactoryUtil.create(portlet, _servletContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return portletBag;
	}

	public void setClassLoader(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	public void setWARFile(boolean warFile) {
		_warFile = warFile;
	}

	protected String getContent(String fileName) throws Exception {
		String queryString = HttpUtil.getQueryString(fileName);

		if (Validator.isNull(queryString)) {
			return StringUtil.read(_classLoader, fileName);
		}

		int pos = fileName.indexOf(StringPool.QUESTION);

		String xml = StringUtil.read(_classLoader, fileName.substring(0, pos));

		Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
			queryString);

		if (parameterMap == null) {
			return xml;
		}

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();

			if (values.length == 0) {
				continue;
			}

			String value = values[0];

			xml = StringUtil.replace(xml, "@" + name + "@", value);
		}

		return xml;
	}

	protected String getPluginPropertyValue(String propertyKey)
		throws Exception {

		Class<?> clazz = _classLoader.loadClass(PortletProps.class.getName());

		java.lang.reflect.Method method = clazz.getMethod("get", String.class);

		return (String)method.invoke(null, propertyKey);
	}

	protected InputStream getResourceBundleInputStream(
		String resourceBundleName, Locale locale) {

		resourceBundleName = resourceBundleName.replace(
			StringPool.PERIOD, StringPool.SLASH);

		Locale newLocale = locale;

		InputStream inputStream = null;

		while (inputStream == null) {
			locale = newLocale;

			StringBundler sb = new StringBundler(4);

			sb.append(resourceBundleName);

			if (locale != null) {
				String localeName = locale.toString();

				if (localeName.length() > 0) {
					sb.append(StringPool.UNDERLINE);
					sb.append(localeName);
				}
			}

			if (!resourceBundleName.endsWith(".properties")) {
				sb.append(".properties");
			}

			String localizedResourceBundleName = sb.toString();

			if (_log.isInfoEnabled()) {
				_log.info("Attempting to load " + localizedResourceBundleName);
			}

			inputStream = _classLoader.getResourceAsStream(
				localizedResourceBundleName);

			if (locale == null) {
				break;
			}

			newLocale = LanguageResources.getSuperLocale(locale);

			if (newLocale == null) {
				break;
			}

			if (newLocale.equals(locale)) {
				break;
			}
		}

		return inputStream;
	}

	protected void initResourceBundle(
		Map<String, ResourceBundle> resourceBundles, Portlet portlet,
		Locale locale) {

		try {
			InputStream inputStream = getResourceBundleInputStream(
				portlet.getResourceBundle(), locale);

			if (inputStream != null) {
				ResourceBundle parentResourceBundle = null;

				if (locale != null) {
					parentResourceBundle = resourceBundles.get(null);
				}

				ResourceBundle resourceBundle = new LiferayResourceBundle(
					parentResourceBundle, inputStream, StringPool.UTF8);

				String languageId = null;

				if (locale != null) {
					languageId = LocaleUtil.toLanguageId(locale);
				}

				resourceBundles.put(languageId, resourceBundle);
			}
		}
		catch (Exception e) {
			_log.warn(e.getMessage());
		}
	}

	protected void initScheduler(
			SchedulerEntry schedulerEntry, String portletId)
		throws Exception {

		String propertyKey = schedulerEntry.getPropertyKey();

		if (Validator.isNotNull(propertyKey)) {
			String triggerValue = null;

			if (_warFile) {
				triggerValue = getPluginPropertyValue(propertyKey);
			}
			else {
				triggerValue = PrefsPropsUtil.getString(propertyKey);
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Scheduler property key " + propertyKey +
						" has trigger value " + triggerValue);
			}

			if (Validator.isNull(triggerValue)) {
				throw new SchedulerException(
					"Property key " + propertyKey + " requires a value");
			}

			schedulerEntry.setTriggerValue(triggerValue);
		}

		if (_classLoader == ClassLoaderUtil.getPortalClassLoader()) {
			portletId = null;
		}

		SchedulerEngineHelperUtil.schedule(
			schedulerEntry, StorageType.MEMORY_CLUSTERED, portletId, 0);
	}

	protected void initSchedulers(Portlet portlet) throws Exception {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		List<SchedulerEntry> schedulerEntries = portlet.getSchedulerEntries();

		if ((schedulerEntries == null) || schedulerEntries.isEmpty()) {
			return;
		}

		for (SchedulerEntry schedulerEntry : schedulerEntries) {
			initScheduler(schedulerEntry, portlet.getPortletId());
		}
	}

	protected AssetRendererFactory newAssetRendererFactoryInstance(
			Portlet portlet, String assetRendererFactoryClass)
		throws Exception {

		AssetRendererFactory assetRendererFactoryInstance =
			(AssetRendererFactory)newInstance(
				AssetRendererFactory.class, assetRendererFactoryClass);

		assetRendererFactoryInstance.setClassName(
			assetRendererFactoryInstance.getClassName());
		assetRendererFactoryInstance.setPortletId(portlet.getPortletId());

		AssetRendererFactoryRegistryUtil.register(assetRendererFactoryInstance);

		return assetRendererFactoryInstance;
	}

	protected List<AssetRendererFactory> newAssetRendererFactoryInstances(
			Portlet portlet)
		throws Exception {

		List<AssetRendererFactory> assetRendererFactoryInstances =
			new ArrayList<AssetRendererFactory>();

		for (String assetRendererFactoryClass :
				portlet.getAssetRendererFactoryClasses()) {

			String assetRendererEnabledPropertyKey =
				PropsKeys.ASSET_RENDERER_ENABLED + assetRendererFactoryClass;

			String assetRendererEnabledPropertyValue = null;

			if (_warFile) {
				assetRendererEnabledPropertyValue = getPluginPropertyValue(
					assetRendererEnabledPropertyKey);
			}
			else {
				assetRendererEnabledPropertyValue = PropsUtil.get(
					assetRendererEnabledPropertyKey);
			}

			boolean assetRendererEnabledValue = GetterUtil.getBoolean(
				assetRendererEnabledPropertyValue, true);

			if (assetRendererEnabledValue) {
				AssetRendererFactory assetRendererFactoryInstance =
					newAssetRendererFactoryInstance(
						portlet, assetRendererFactoryClass);

				assetRendererFactoryInstances.add(assetRendererFactoryInstance);
			}
		}

		return assetRendererFactoryInstances;
	}

	protected List<AtomCollectionAdapter<?>> newAtomCollectionAdapterInstances(
			Portlet portlet)
		throws Exception {

		List<AtomCollectionAdapter<?>> atomCollectionAdapterInstances =
			new ArrayList<AtomCollectionAdapter<?>>();

		for (String atomCollectionAdapterClass :
				portlet.getAtomCollectionAdapterClasses()) {

			AtomCollectionAdapter<?> atomCollectionAdapterInstance =
				(AtomCollectionAdapter<?>)newInstance(
					AtomCollectionAdapter.class, atomCollectionAdapterClass);

			AtomCollectionAdapterRegistryUtil.register(
				atomCollectionAdapterInstance);

			atomCollectionAdapterInstances.add(atomCollectionAdapterInstance);
		}

		return atomCollectionAdapterInstances;
	}

	protected ConfigurationAction newConfigurationAction(Portlet portlet)
		throws Exception {

		if (Validator.isNull(portlet.getConfigurationActionClass())) {
			return null;
		}

		return (ConfigurationAction)newInstance(
			ConfigurationAction.class, portlet.getConfigurationActionClass());
	}

	protected DDMDisplay newDDMDisplay(Portlet portlet) throws Exception {
		if (Validator.isNull(portlet.getDDMDisplayClass())) {
			return null;
		}

		return (DDMDisplay)newInstance(
			DDMDisplay.class, portlet.getDDMDisplayClass());
	}

	protected FriendlyURLMapper newFriendlyURLMapper(Portlet portlet)
		throws Exception {

		if (Validator.isNull(portlet.getFriendlyURLMapperClass())) {
			return null;
		}

		FriendlyURLMapper friendlyURLMapper = (FriendlyURLMapper)newInstance(
			FriendlyURLMapper.class, portlet.getFriendlyURLMapperClass());

		friendlyURLMapper.setMapping(portlet.getFriendlyURLMapping());
		friendlyURLMapper.setPortletId(portlet.getPortletId());
		friendlyURLMapper.setPortletInstanceable(portlet.isInstanceable());

		Router router = newFriendlyURLRouter(portlet);

		friendlyURLMapper.setRouter(router);

		return friendlyURLMapper;
	}

	protected Router newFriendlyURLRouter(Portlet portlet) throws Exception {
		if (Validator.isNull(portlet.getFriendlyURLRoutes())) {
			return null;
		}

		Router router = new RouterImpl();

		String xml = getContent(portlet.getFriendlyURLRoutes());

		Document document = SAXReaderUtil.read(xml, true);

		Element rootElement = document.getRootElement();

		for (Element routeElement : rootElement.elements("route")) {
			String pattern = routeElement.elementText("pattern");

			Route route = router.addRoute(pattern);

			for (Element generatedParameterElement :
					routeElement.elements("generated-parameter")) {

				String name = generatedParameterElement.attributeValue("name");
				String value = generatedParameterElement.getText();

				route.addGeneratedParameter(name, value);
			}

			for (Element ignoredParameterElement :
					routeElement.elements("ignored-parameter")) {

				String name = ignoredParameterElement.attributeValue("name");

				route.addIgnoredParameter(name);
			}

			for (Element implicitParameterElement :
					routeElement.elements("implicit-parameter")) {

				String name = implicitParameterElement.attributeValue("name");
				String value = implicitParameterElement.getText();

				route.addImplicitParameter(name, value);
			}

			for (Element overriddenParameterElement :
					routeElement.elements("overridden-parameter")) {

				String name = overriddenParameterElement.attributeValue("name");
				String value = overriddenParameterElement.getText();

				route.addOverriddenParameter(name, value);
			}
		}

		return router;
	}

	protected List<Indexer> newIndexers(Portlet portlet) throws Exception {
		List<Indexer> indexerInstances = new ArrayList<Indexer>();

		List<String> indexerClasses = portlet.getIndexerClasses();

		for (String indexerClass : indexerClasses) {
			Indexer indexerInstance = (Indexer)newInstance(
				Indexer.class, indexerClass);

			IndexerRegistryUtil.register(indexerInstance);

			indexerInstances.add(indexerInstance);
		}

		return indexerInstances;
	}

	protected Object newInstance(Class<?> interfaceClass, String implClassName)
		throws Exception {

		return newInstance(new Class[] {interfaceClass}, implClassName);
	}

	protected Object newInstance(
			Class<?>[] interfaceClasses, String implClassName)
		throws Exception {

		if (_warFile) {
			return ProxyFactory.newInstance(
				_classLoader, interfaceClasses, implClassName);
		}
		else {
			Class<?> clazz = _classLoader.loadClass(implClassName);

			return clazz.newInstance();
		}
	}

	protected OpenSearch newOpenSearch(Portlet portlet) throws Exception {
		if (Validator.isNull(portlet.getOpenSearchClass())) {
			return null;
		}

		return (OpenSearch)newInstance(
			OpenSearch.class, portlet.getOpenSearchClass());
	}

	protected PermissionPropagator newPermissionPropagator(Portlet portlet)
		throws Exception {

		if (Validator.isNull(portlet.getPermissionPropagatorClass())) {
			return null;
		}

		return (PermissionPropagator)newInstance(
			PermissionPropagator.class, portlet.getPermissionPropagatorClass());
	}

	protected PollerProcessor newPollerProcessor(Portlet portlet)
		throws Exception {

		if (Validator.isNull(portlet.getPollerProcessorClass())) {
			return null;
		}

		PollerProcessor pollerProcessorInstance = (PollerProcessor)newInstance(
			PollerProcessor.class, portlet.getPollerProcessorClass());

		PollerProcessorUtil.addPollerProcessor(
			portlet.getPortletId(),
			new ShardPollerProcessorWrapper(pollerProcessorInstance));

		return pollerProcessorInstance;
	}

	protected MessageListener newPOPMessageListener(Portlet portlet)
		throws Exception {

		if (Validator.isNull(portlet.getPopMessageListenerClass())) {
			return null;
		}

		MessageListener popMessageListenerInstance =
			(MessageListener)newInstance(
				MessageListener.class, portlet.getPopMessageListenerClass());

		POPServerUtil.addListener(popMessageListenerInstance);

		return popMessageListenerInstance;
	}

	protected PortletDataHandler newPortletDataHandler(Portlet portlet)
		throws Exception {

		if (Validator.isNull(portlet.getPortletDataHandlerClass())) {
			return null;
		}

		return (PortletDataHandler)newInstance(
			PortletDataHandler.class, portlet.getPortletDataHandlerClass());
	}

	protected PortletLayoutListener newPortletLayoutListener(Portlet portlet)
		throws Exception {

		if (Validator.isNull(portlet.getPortletLayoutListenerClass())) {
			return null;
		}

		return (PortletLayoutListener)newInstance(
			PortletLayoutListener.class,
			portlet.getPortletLayoutListenerClass());
	}

	protected SocialActivityInterpreter newSocialActivityInterpreterInstance(
			Portlet portlet, String socialActivityInterpreterClass)
		throws Exception {

		SocialActivityInterpreter socialActivityInterpreterInstance =
			(SocialActivityInterpreter)newInstance(
				SocialActivityInterpreter.class,
				socialActivityInterpreterClass);

		socialActivityInterpreterInstance = new SocialActivityInterpreterImpl(
			portlet.getPortletId(), socialActivityInterpreterInstance);

		SocialActivityInterpreterLocalServiceUtil.addActivityInterpreter(
			socialActivityInterpreterInstance);

		return socialActivityInterpreterInstance;
	}

	protected List<SocialActivityInterpreter>
			newSocialActivityInterpreterInstances(Portlet portlet)
		throws Exception {

		List<SocialActivityInterpreter> socialActivityInterpreterInstances =
			new ArrayList<SocialActivityInterpreter>();

		for (String socialActivityInterpreterClass :
				portlet.getSocialActivityInterpreterClasses()) {

			SocialActivityInterpreter socialActivityInterpreterInstance =
				newSocialActivityInterpreterInstance(
					portlet, socialActivityInterpreterClass);

			socialActivityInterpreterInstances.add(
				socialActivityInterpreterInstance);
		}

		return socialActivityInterpreterInstances;
	}

	protected List<StagedModelDataHandler<?>> newStagedModelDataHandler(
			Portlet portlet)
		throws Exception {

		List<StagedModelDataHandler<?>> stagedModelDataHandlerInstances =
			new ArrayList<StagedModelDataHandler<?>>();

		List<String> stagedModelDataHandlerClasses =
			portlet.getStagedModelDataHandlerClasses();

		for (String stagedModelDataHandlerClass :
				stagedModelDataHandlerClasses) {

			StagedModelDataHandler<?> stagedModelDataHandler =
				(StagedModelDataHandler<?>)newInstance(
					StagedModelDataHandler.class, stagedModelDataHandlerClass);

			stagedModelDataHandlerInstances.add(stagedModelDataHandler);

			StagedModelDataHandlerRegistryUtil.register(stagedModelDataHandler);
		}

		return stagedModelDataHandlerInstances;
	}

	protected TemplateHandler newTemplateHandler(Portlet portlet)
		throws Exception {

		if (Validator.isNull(portlet.getTemplateHandlerClass())) {
			return null;
		}

		return (TemplateHandler)newInstance(
			TemplateHandler.class, portlet.getTemplateHandlerClass());
	}

	protected URLEncoder newURLEncoder(Portlet portlet) throws Exception {
		if (Validator.isNull(portlet.getURLEncoderClass())) {
			return null;
		}

		return (URLEncoder)newInstance(
			URLEncoder.class, portlet.getURLEncoderClass());
	}

	private static Log _log = LogFactoryUtil.getLog(PortletBagFactory.class);

	private ClassLoader _classLoader;
	private ServletContext _servletContext;
	private boolean _warFile;

}