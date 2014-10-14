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

package com.liferay.portlet;

import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationDeliveryType;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperTracker;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.ResourceBundleTracker;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.language.LiferayResourceBundle;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.notifications.UserNotificationHandlerImpl;
import com.liferay.portal.security.permission.PermissionPropagator;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.JavaFieldsParser;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.dynamicdatamapping.util.DDMDisplay;
import com.liferay.portlet.expando.model.CustomAttributesDisplay;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialRequestInterpreter;
import com.liferay.portlet.social.model.impl.SocialActivityInterpreterImpl;
import com.liferay.portlet.social.model.impl.SocialRequestInterpreterImpl;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import java.io.InputStream;

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
		validate();

		javax.portlet.Portlet portletInstance = getPortletInstance(portlet);

		List<ConfigurationAction> configurationActionInstances =
			newConfigurationActions(portlet);

		List<Indexer> indexerInstances = newIndexers(portlet);

		List<OpenSearch> openSearchInstances = newOpenSearches(portlet);

		List<SchedulerEntry> schedulerEntryInstances =
			newSchedulerEntryInstances(portlet);

		FriendlyURLMapperTracker friendlyURLMapperTracker =
			newFriendlyURLMappers(portlet);

		List<URLEncoder> urlEncoderInstances = newURLEncoders(portlet);

		List<PortletDataHandler> portletDataHandlerInstances =
			newPortletDataHandlers(portlet);

		List<StagedModelDataHandler<?>> stagedModelDataHandlerInstances =
			newStagedModelDataHandler(portlet);

		List<TemplateHandler> templateHandlerInstances = newTemplateHandlers(
			portlet);

		List<PortletLayoutListener> portletLayoutListenerInstances =
			newPortletLayoutListeners(portlet);

		List<PollerProcessor> pollerProcessorInstances = newPollerProcessors(
			portlet);

		List<MessageListener> popMessageListenerInstances =
			newPOPMessageListeners(portlet);

		List<SocialActivityInterpreter> socialActivityInterpreterInstances =
			newSocialActivityInterpreterInstances(portlet);

		List<SocialRequestInterpreter> socialRequestInterpreterInstances =
			newSocialRequestInterpreterInstances(portlet);

		List<UserNotificationHandler> userNotificationHandlerInstances =
			newUserNotificationHandlerInstances(portlet);

		initUserNotificationDefinition(portlet);

		List<WebDAVStorage> webDAVStorageInstances = newWebDAVStorageInstances(
			portlet);

		List<Method> xmlRpcMethodInstances = newXmlRpcMethodInstances(portlet);

		List<ControlPanelEntry> controlPanelEntryInstances =
			newControlPanelEntryInstances(portlet);

		List<AssetRendererFactory> assetRendererFactoryInstances =
			newAssetRendererFactoryInstances(portlet);

		List<AtomCollectionAdapter<?>> atomCollectionAdapterInstances =
			newAtomCollectionAdapterInstances(portlet);

		List<CustomAttributesDisplay> customAttributesDisplayInstances =
			newCustomAttributesDisplayInstances(portlet);

		List<DDMDisplay> ddmDisplayInstances = newDDMDisplayInstances(portlet);

		List<PermissionPropagator> permissionPropagatorInstances =
			newPermissionPropagators(portlet);

		List<TrashHandler> trashHandlerInstances = newTrashHandlerInstances(
			portlet);

		List<WorkflowHandler<?>> workflowHandlerInstances =
			newWorkflowHandlerInstances(portlet);

		List<PreferencesValidator> preferencesValidatorInstances =
			newPreferencesValidatorInstances(portlet);

		ResourceBundleTracker resourceBundleTracker = new ResourceBundleTracker(
			portlet.getPortletId());

		String resourceBundle = portlet.getResourceBundle();

		if (Validator.isNotNull(resourceBundle) &&
			!resourceBundle.equals(StrutsResourceBundle.class.getName())) {

			initResourceBundle(resourceBundleTracker, portlet, null);
			initResourceBundle(
				resourceBundleTracker, portlet, LocaleUtil.getDefault());

			Set<String> supportedLanguageIds = portlet.getSupportedLocales();

			if (supportedLanguageIds.isEmpty()) {
				supportedLanguageIds = SetUtil.fromArray(PropsValues.LOCALES);
			}

			for (String supportedLanguageId : supportedLanguageIds) {
				Locale locale = LocaleUtil.fromLanguageId(supportedLanguageId);

				initResourceBundle(resourceBundleTracker, portlet, locale);
			}
		}

		PortletBag portletBag = new PortletBagImpl(
			portlet.getPortletId(), _servletContext, portletInstance,
			resourceBundleTracker, configurationActionInstances,
			indexerInstances, openSearchInstances, schedulerEntryInstances,
			friendlyURLMapperTracker, urlEncoderInstances,
			portletDataHandlerInstances, stagedModelDataHandlerInstances,
			templateHandlerInstances, portletLayoutListenerInstances,
			pollerProcessorInstances, popMessageListenerInstances,
			socialActivityInterpreterInstances,
			socialRequestInterpreterInstances, userNotificationHandlerInstances,
			webDAVStorageInstances, xmlRpcMethodInstances,
			controlPanelEntryInstances, assetRendererFactoryInstances,
			atomCollectionAdapterInstances, customAttributesDisplayInstances,
			ddmDisplayInstances, permissionPropagatorInstances,
			trashHandlerInstances, workflowHandlerInstances,
			preferencesValidatorInstances);

		PortletBagPool.put(portlet.getRootPortletId(), portletBag);

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

		if (_configuration == null) {
			_configuration = ConfigurationFactoryUtil.getConfiguration(
				_classLoader, "portlet");
		}

		return _configuration.get(propertyKey);
	}

	protected javax.portlet.Portlet getPortletInstance(Portlet portlet)
		throws IllegalAccessException, InstantiationException {

		Class<?> portletClass = null;

		try {
			portletClass = _classLoader.loadClass(portlet.getPortletClass());
		}
		catch (Throwable t) {
			_log.error(t, t);

			PortletLocalServiceUtil.destroyPortlet(portlet);

			return null;
		}

		return (javax.portlet.Portlet)portletClass.newInstance();
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

	protected <S> ServiceTrackerList<S> getServiceTrackerList(
		Class<S> clazz, Portlet portlet) {

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("javax.portlet.name", portlet.getPortletId());

		return ServiceTrackerCollections.list(
			clazz, "(javax.portlet.name=" + portlet.getPortletId() + ")",
			properties);
	}

	protected void initResourceBundle(
		ResourceBundleTracker resourceBundleTracker, Portlet portlet,
		Locale locale) {

		try {
			InputStream inputStream = getResourceBundleInputStream(
				portlet.getResourceBundle(), locale);

			if (inputStream != null) {
				ResourceBundle parentResourceBundle = null;

				if (locale != null) {
					parentResourceBundle =
						resourceBundleTracker.getResouceBundle(
							StringPool.BLANK);
				}

				ResourceBundle resourceBundle = new LiferayResourceBundle(
					parentResourceBundle, inputStream, StringPool.UTF8);

				String languageId = null;

				if (locale != null) {
					languageId = LocaleUtil.toLanguageId(locale);
				}

				resourceBundleTracker.register(languageId, resourceBundle);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}
	}

	protected void initUserNotificationDefinition(Portlet portlet)
		throws Exception {

		if (Validator.isNull(portlet.getUserNotificationDefinitions())) {
			return;
		}

		String xml = getContent(portlet.getUserNotificationDefinitions());

		xml = JavaFieldsParser.parse(_classLoader, xml);

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		for (Element definitionElement : rootElement.elements("definition")) {
			String modelName = definitionElement.elementText("model-name");

			long classNameId = 0;

			if (Validator.isNotNull(modelName)) {
				classNameId = PortalUtil.getClassNameId(modelName);
			}

			int notificationType = GetterUtil.getInteger(
				definitionElement.elementText("notification-type"));

			String description = GetterUtil.getString(
				definitionElement.elementText("description"));

			UserNotificationDefinition userNotificationDefinition =
				new UserNotificationDefinition(
					portlet.getPortletId(), classNameId, notificationType,
					description);

			for (Element deliveryTypeElement :
					definitionElement.elements("delivery-type")) {

				String name = deliveryTypeElement.elementText("name");
				int type = GetterUtil.getInteger(
					deliveryTypeElement.elementText("type"));
				boolean defaultValue = GetterUtil.getBoolean(
					deliveryTypeElement.elementText("default"));
				boolean modifiable = GetterUtil.getBoolean(
					deliveryTypeElement.elementText("modifiable"));

				userNotificationDefinition.addUserNotificationDeliveryType(
					new UserNotificationDeliveryType(
						name, type, defaultValue, modifiable));
			}

			UserNotificationManagerUtil.addUserNotificationDefinition(
				portlet.getPortletId(), userNotificationDefinition);
		}
	}

	protected List<AssetRendererFactory> newAssetRendererFactoryInstances(
			Portlet portlet)
		throws Exception {

		ServiceTrackerList<AssetRendererFactory> assetRendererFactoryInstances =
			getServiceTrackerList(AssetRendererFactory.class, portlet);

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
					(AssetRendererFactory)newInstance(
						AssetRendererFactory.class, assetRendererFactoryClass);

				assetRendererFactoryInstance.setClassName(
					assetRendererFactoryInstance.getClassName());
				assetRendererFactoryInstance.setPortletId(
					portlet.getPortletId());

				assetRendererFactoryInstances.add(assetRendererFactoryInstance);
			}
		}

		return assetRendererFactoryInstances;
	}

	protected List<AtomCollectionAdapter<?>> newAtomCollectionAdapterInstances(
			Portlet portlet)
		throws Exception {

		ServiceTrackerList<AtomCollectionAdapter<?>>
			atomCollectionAdapterInstances = getServiceTrackerList(
				(Class<AtomCollectionAdapter<?>>)(Class<?>)
					AtomCollectionAdapter.class, portlet);

		for (String atomCollectionAdapterClass :
				portlet.getAtomCollectionAdapterClasses()) {

			AtomCollectionAdapter<?> atomCollectionAdapterInstance =
				(AtomCollectionAdapter<?>)newInstance(
					AtomCollectionAdapter.class, atomCollectionAdapterClass);

			atomCollectionAdapterInstances.add(atomCollectionAdapterInstance);
		}

		return atomCollectionAdapterInstances;
	}

	protected List<ConfigurationAction> newConfigurationActions(Portlet portlet)
		throws Exception {

		ServiceTrackerList<ConfigurationAction> configurationActionInstances =
			getServiceTrackerList(ConfigurationAction.class, portlet);

		if (Validator.isNotNull(portlet.getConfigurationActionClass())) {
			ConfigurationAction configurationAction =
				(ConfigurationAction)newInstance(
					ConfigurationAction.class,
					portlet.getConfigurationActionClass());

			configurationActionInstances.add(configurationAction);
		}

		return configurationActionInstances;
	}

	protected List<ControlPanelEntry> newControlPanelEntryInstances(
			Portlet portlet)
		throws Exception {

		ServiceTrackerList<ControlPanelEntry> controlPanelEntryInstances =
			getServiceTrackerList(ControlPanelEntry.class, portlet);

		if (Validator.isNotNull(portlet.getControlPanelEntryClass())) {
			ControlPanelEntry controlPanelEntryInstance =
				(ControlPanelEntry)newInstance(
					ControlPanelEntry.class,
					portlet.getControlPanelEntryClass());

			controlPanelEntryInstances.add(controlPanelEntryInstance);
		}

		return controlPanelEntryInstances;
	}

	protected List<CustomAttributesDisplay> newCustomAttributesDisplayInstances(
			Portlet portlet)
		throws Exception {

		ServiceTrackerList<CustomAttributesDisplay>
			customAttributesDisplayInstances = getServiceTrackerList(
				CustomAttributesDisplay.class, portlet);

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

		return customAttributesDisplayInstances;
	}

	protected List<DDMDisplay> newDDMDisplayInstances(Portlet portlet)
		throws Exception {

		ServiceTrackerList<DDMDisplay> ddmDisplayInstances =
			getServiceTrackerList(DDMDisplay.class, portlet);

		if (Validator.isNotNull(portlet.getDDMDisplayClass())) {
			DDMDisplay ddmDisplayInstance = (DDMDisplay)newInstance(
				DDMDisplay.class, portlet.getDDMDisplayClass());

			ddmDisplayInstances.add(ddmDisplayInstance);
		}

		return ddmDisplayInstances;
	}

	protected FriendlyURLMapperTracker newFriendlyURLMappers(Portlet portlet)
		throws Exception {

		FriendlyURLMapperTracker friendlyURLMapperTracker =
			new FriendlyURLMapperTrackerImpl(portlet);

		if (Validator.isNotNull(portlet.getFriendlyURLMapperClass())) {
			FriendlyURLMapper friendlyURLMapper =
				(FriendlyURLMapper)newInstance(
					FriendlyURLMapper.class,
					portlet.getFriendlyURLMapperClass());

			friendlyURLMapperTracker.register(friendlyURLMapper);
		}

		return friendlyURLMapperTracker;
	}

	protected List<Indexer> newIndexers(Portlet portlet) throws Exception {
		ServiceTrackerList<Indexer> indexerInstances = getServiceTrackerList(
			Indexer.class, portlet);

		List<String> indexerClasses = portlet.getIndexerClasses();

		for (String indexerClass : indexerClasses) {
			Indexer indexerInstance = (Indexer)newInstance(
				Indexer.class, indexerClass);

			Map<String, Object> properties = new HashMap<String, Object>();

			String[] classNames = ArrayUtil.append(
				indexerInstance.getClassNames(),
				ClassUtil.getClassName(indexerInstance));

			properties.put("indexer.classNames", classNames);

			indexerInstances.add(indexerInstance, properties);
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

	protected List<OpenSearch> newOpenSearches(Portlet portlet)
		throws Exception {

		ServiceTrackerList<OpenSearch> openSearchInstances =
			getServiceTrackerList(OpenSearch.class, portlet);

		if (Validator.isNotNull(portlet.getOpenSearchClass())) {
			OpenSearch openSearch = (OpenSearch)newInstance(
				OpenSearch.class, portlet.getOpenSearchClass());

			openSearchInstances.add(openSearch);
		}

		return openSearchInstances;
	}

	protected List<PermissionPropagator> newPermissionPropagators(
			Portlet portlet)
		throws Exception {

		ServiceTrackerList<PermissionPropagator> permissionPropagatorInstances =
			getServiceTrackerList(PermissionPropagator.class, portlet);

		if (Validator.isNotNull(portlet.getPermissionPropagatorClass())) {
			PermissionPropagator permissionPropagatorInstance =
				(PermissionPropagator)newInstance(
					PermissionPropagator.class,
					portlet.getPermissionPropagatorClass());

			permissionPropagatorInstances.add(permissionPropagatorInstance);
		}

		return permissionPropagatorInstances;
	}

	protected List<PollerProcessor> newPollerProcessors(Portlet portlet)
		throws Exception {

		ServiceTrackerList<PollerProcessor> pollerProcessorInstances =
			getServiceTrackerList(PollerProcessor.class, portlet);

		if (Validator.isNotNull(portlet.getPollerProcessorClass())) {
			PollerProcessor pollerProcessorInstance =
				(PollerProcessor)newInstance(
					PollerProcessor.class, portlet.getPollerProcessorClass());

			pollerProcessorInstances.add(pollerProcessorInstance);
		}

		return pollerProcessorInstances;
	}

	protected List<MessageListener> newPOPMessageListeners(Portlet portlet)
		throws Exception {

		ServiceTrackerList<MessageListener> messageListenerInstances =
			getServiceTrackerList(MessageListener.class, portlet);

		if (Validator.isNotNull(portlet.getPopMessageListenerClass())) {
			MessageListener popMessageListenerInstance =
				(MessageListener)newInstance(
					MessageListener.class,
					portlet.getPopMessageListenerClass());

			messageListenerInstances.add(popMessageListenerInstance);
		}

		return messageListenerInstances;
	}

	protected List<PortletDataHandler> newPortletDataHandlers(Portlet portlet)
		throws Exception {

		ServiceTrackerList<PortletDataHandler> portletDataHandlerInstances =
			getServiceTrackerList(PortletDataHandler.class, portlet);

		if (Validator.isNotNull(portlet.getPortletDataHandlerClass())) {
			PortletDataHandler portletDataHandlerInstance =
				(PortletDataHandler)newInstance(
					PortletDataHandler.class,
					portlet.getPortletDataHandlerClass());

			portletDataHandlerInstance.setPortletId(portlet.getPortletId());

			portletDataHandlerInstances.add(portletDataHandlerInstance);
		}

		return portletDataHandlerInstances;
	}

	protected List<PortletLayoutListener> newPortletLayoutListeners(
			Portlet portlet)
		throws Exception {

		ServiceTrackerList<PortletLayoutListener>
			portletLayoutListenerInstances = getServiceTrackerList(
				PortletLayoutListener.class, portlet);

		if (Validator.isNotNull(portlet.getPortletLayoutListenerClass())) {
			PortletLayoutListener portletLayoutListener =
				(PortletLayoutListener)newInstance(
					PortletLayoutListener.class,
					portlet.getPortletLayoutListenerClass());

			portletLayoutListenerInstances.add(portletLayoutListener);
		}

		return portletLayoutListenerInstances;
	}

	protected List<PreferencesValidator> newPreferencesValidatorInstances(
			Portlet portlet)
		throws Exception {

		ServiceTrackerList<PreferencesValidator>
			preferencesValidatorInstances = getServiceTrackerList(
				PreferencesValidator.class, portlet);

		if (Validator.isNotNull(portlet.getPreferencesValidator())) {
			PreferencesValidator preferencesValidatorInstance =
				(PreferencesValidator)newInstance(
					PreferencesValidator.class,
					portlet.getPreferencesValidator());

			try {
				if (PropsValues.PREFERENCE_VALIDATE_ON_STARTUP) {
					preferencesValidatorInstance.validate(
						PortletPreferencesFactoryUtil.fromDefaultXML(
							portlet.getDefaultPreferences()));
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Portlet with the name " + portlet.getPortletId() +
							" does not have valid default preferences");
				}
			}

			preferencesValidatorInstances.add(preferencesValidatorInstance);
		}

		return preferencesValidatorInstances;
	}

	protected List<SchedulerEntry> newSchedulerEntryInstances(Portlet portlet) {
		ServiceTrackerList<SchedulerEntry> schedulerEntries =
			getServiceTrackerList(SchedulerEntry.class, portlet);

		if (PropsValues.SCHEDULER_ENABLED) {
			schedulerEntries.addAll(portlet.getSchedulerEntries());
		}

		return schedulerEntries;
	}

	protected List<SocialActivityInterpreter>
			newSocialActivityInterpreterInstances(Portlet portlet)
		throws Exception {

		ServiceTrackerList<SocialActivityInterpreter>
			socialActivityInterpreterInstances = getServiceTrackerList(
				SocialActivityInterpreter.class, portlet);

		for (String socialActivityInterpreterClass :
				portlet.getSocialActivityInterpreterClasses()) {

			SocialActivityInterpreter socialActivityInterpreterInstance =
				(SocialActivityInterpreter)newInstance(
					SocialActivityInterpreter.class,
					socialActivityInterpreterClass);

			socialActivityInterpreterInstance =
				new SocialActivityInterpreterImpl(
					portlet.getPortletId(), socialActivityInterpreterInstance);

			socialActivityInterpreterInstances.add(
				socialActivityInterpreterInstance);
		}

		return socialActivityInterpreterInstances;
	}

	protected List<SocialRequestInterpreter>
			newSocialRequestInterpreterInstances(Portlet portlet)
		throws Exception {

		ServiceTrackerList<SocialRequestInterpreter>
			socialRequestInterpreterInstances = getServiceTrackerList(
				SocialRequestInterpreter.class, portlet);

		if (Validator.isNotNull(portlet.getSocialRequestInterpreterClass())) {
			SocialRequestInterpreter socialRequestInterpreterInstance =
				(SocialRequestInterpreter)newInstance(
					SocialRequestInterpreter.class,
					portlet.getSocialRequestInterpreterClass());

			socialRequestInterpreterInstance = new SocialRequestInterpreterImpl(
				portlet.getPortletId(), socialRequestInterpreterInstance);

			socialRequestInterpreterInstances.add(
				socialRequestInterpreterInstance);
		}

		return socialRequestInterpreterInstances;
	}

	protected List<StagedModelDataHandler<?>> newStagedModelDataHandler(
			Portlet portlet)
		throws Exception {

		ServiceTrackerList<StagedModelDataHandler<?>>
			stagedModelDataHandlerInstances =
				getServiceTrackerList(
					(Class<StagedModelDataHandler<?>>)(Class<?>)
						StagedModelDataHandler.class, portlet);

		List<String> stagedModelDataHandlerClasses =
			portlet.getStagedModelDataHandlerClasses();

		for (String stagedModelDataHandlerClass :
				stagedModelDataHandlerClasses) {

			StagedModelDataHandler<?> stagedModelDataHandler =
				(StagedModelDataHandler<?>)newInstance(
					StagedModelDataHandler.class, stagedModelDataHandlerClass);

			stagedModelDataHandlerInstances.add(stagedModelDataHandler);
		}

		return stagedModelDataHandlerInstances;
	}

	protected List<TemplateHandler> newTemplateHandlers(Portlet portlet)
		throws Exception {

		ServiceTrackerList<TemplateHandler> templateHandlerInstances =
			getServiceTrackerList(TemplateHandler.class, portlet);

		if (Validator.isNotNull(portlet.getTemplateHandlerClass())) {
			TemplateHandler templateHandler = (TemplateHandler)newInstance(
				TemplateHandler.class, portlet.getTemplateHandlerClass());

			templateHandlerInstances.add(templateHandler);
		}

		return templateHandlerInstances;
	}

	protected List<TrashHandler> newTrashHandlerInstances(Portlet portlet)
		throws Exception {

		ServiceTrackerList<TrashHandler> trashHandlerInstances =
			getServiceTrackerList(TrashHandler.class, portlet);

		for (String trashHandlerClass : portlet.getTrashHandlerClasses()) {
			TrashHandler trashHandlerInstance = (TrashHandler)newInstance(
				TrashHandler.class, trashHandlerClass);

			trashHandlerInstances.add(trashHandlerInstance);
		}

		return trashHandlerInstances;
	}

	protected List<URLEncoder> newURLEncoders(Portlet portlet)
		throws Exception {

		ServiceTrackerList<URLEncoder> urlEncoderInstances =
			getServiceTrackerList(URLEncoder.class, portlet);

		if (Validator.isNotNull(portlet.getURLEncoderClass())) {
			URLEncoder urlEncoder = (URLEncoder)newInstance(
				URLEncoder.class, portlet.getURLEncoderClass());

			urlEncoderInstances.add(urlEncoder);
		}

		return urlEncoderInstances;
	}

	protected List<UserNotificationHandler> newUserNotificationHandlerInstances(
			Portlet portlet)
		throws Exception {

		ServiceTrackerList<UserNotificationHandler>
			userNotificationHandlerInstances = getServiceTrackerList(
				UserNotificationHandler.class, portlet);

		for (String userNotificationHandlerClass :
				portlet.getUserNotificationHandlerClasses()) {

			UserNotificationHandler userNotificationHandlerInstance =
				(UserNotificationHandler)newInstance(
					UserNotificationHandler.class,
					userNotificationHandlerClass);

			userNotificationHandlerInstance = new UserNotificationHandlerImpl(
				userNotificationHandlerInstance);

			userNotificationHandlerInstances.add(
				userNotificationHandlerInstance);
		}

		return userNotificationHandlerInstances;
	}

	protected List<WebDAVStorage> newWebDAVStorageInstances(Portlet portlet)
		throws Exception {

		ServiceTrackerList<WebDAVStorage> webDAVStorageInstances =
			getServiceTrackerList(WebDAVStorage.class, portlet);

		if (Validator.isNotNull(portlet.getWebDAVStorageClass())) {
			WebDAVStorage webDAVStorageInstance = (WebDAVStorage)newInstance(
				WebDAVStorage.class, portlet.getWebDAVStorageClass());

			webDAVStorageInstance.setToken(portlet.getWebDAVStorageToken());

			webDAVStorageInstances.add(webDAVStorageInstance);
		}

		return webDAVStorageInstances;
	}

	protected List<WorkflowHandler<?>> newWorkflowHandlerInstances(
			Portlet portlet)
		throws Exception {

		ServiceTrackerList<WorkflowHandler<?>> workflowHandlerInstances =
			getServiceTrackerList(
				(Class<WorkflowHandler<?>>)(Class<?>)WorkflowHandler.class,
				portlet);

		for (String workflowHandlerClass :
				portlet.getWorkflowHandlerClasses()) {

			WorkflowHandler<?> workflowHandlerInstance =
				(WorkflowHandler<?>)newInstance(
					WorkflowHandler.class, workflowHandlerClass);

			workflowHandlerInstances.add(workflowHandlerInstance);
		}

		return workflowHandlerInstances;
	}

	protected List<Method> newXmlRpcMethodInstances(Portlet portlet)
		throws Exception {

		ServiceTrackerList<Method> xmlRpcMethodInstances =
			getServiceTrackerList(Method.class, portlet);

		if (Validator.isNotNull(portlet.getXmlRpcMethodClass())) {
			Method xmlRpcMethodInstance = (Method)newInstance(
				Method.class, portlet.getXmlRpcMethodClass());

			xmlRpcMethodInstances.add(xmlRpcMethodInstance);
		}

		return xmlRpcMethodInstances;
	}

	protected void validate() {
		if (_classLoader == null) {
			throw new IllegalStateException("Class loader is null");
		}

		if (_servletContext == null) {
			throw new IllegalStateException("Servlet context is null");
		}

		if (_warFile == null) {
			throw new IllegalStateException("WAR file is null");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletBagFactory.class);

	private ClassLoader _classLoader;
	private Configuration _configuration;
	private ServletContext _servletContext;
	private Boolean _warFile;

}